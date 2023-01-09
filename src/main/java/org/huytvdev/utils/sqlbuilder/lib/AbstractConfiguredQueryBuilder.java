package org.huytvdev.utils.sqlbuilder.lib;


import org.huytvdev.utils.httpsecurityclone.SecurityConfigurer;

import java.util.*;

/**
 * <p>
 * A base {@link QueryBuilder} that allows {@link QueryStatement} to be applied to
 * it. This makes modifying the {@link QueryBuilder} a strategy that can be customized
 * and broken up into a number of {@link QueryStatement} objects that have more
 * specific goals than that of the {@link QueryBuilder}.
 * </p>
 *
 * <p>
 * For example, a {@link QueryBuilder} may build an {@link DelegatingFilterProxy}, but
 * a {@link QueryStatement} might populate the {@link QueryBuilder} with the
 * filters necessary for session management, form based login, authorization, etc.
 * </p>
 *
 * @param <O> The object that this builder returns
 * @param <B> The type of this builder (that is returned by the base class)
 * @author Rob Winch
 */
public abstract class AbstractConfiguredQueryBuilder<O, B extends QueryBuilder<O>>
        extends AbstractQueryBuilder<O> {

    private final Map<Class<? extends QueryStatement<O, B>>, List<QueryStatement<O, B>>> configurers = new LinkedHashMap<>();

    private final List<QueryStatement<O, B>> configurersAddedInInitializing = new ArrayList<>();
    private final Map<Class<?>, Object> sharedObjects = new HashMap<>();
    private final boolean allowConfigurersOfSameType;
    private ObjectPostProcessor<Object> objectPostProcessor;
    private BuildState buildState;

    protected AbstractConfiguredQueryBuilder(boolean allowConfigurersOfSameType,
                                             ObjectPostProcessor<Object> objectPostProcessor) {
        this.allowConfigurersOfSameType = allowConfigurersOfSameType;
        this.objectPostProcessor = objectPostProcessor;
        this.buildState = BuildState.UNBUILT;
    }

    protected AbstractConfiguredQueryBuilder(ObjectPostProcessor<Object> objectPostProcessor) {
        this(false, objectPostProcessor);
    }

    /**
     * Similar to {@link #build()} and {@link #getObject()} but checks the state to
     * determine if {@link #build()} needs to be called first.
     * @return the result of {@link #build()} or {@link #getObject()}. If an error occurs
     * while building, returns null.
     */
    public O getOrBuild() {
        if (!isUnbuilt()) {
            return getObject();
        }
        try {
            return build();
        }
        catch (Exception ex) {
//            this.logger.debug("Failed to perform build. Returning null", ex);
            return null;
        }
    }

    /**
     * Determines if the object is unbuilt.
     * @return true, if unbuilt else false
     */
    private boolean isUnbuilt() {
        synchronized (this.configurers) {
            return this.buildState == BuildState.UNBUILT;
        }
    }

    /**
     * Applies a {@link QueryConfigurerAdapter} to this {@link QueryBuilder} and
     * invokes {@link QueryConfigurerAdapter#setBuilder(QueryBuilder)}.
     * @param configurer
     * @return the {@link QueryConfigurerAdapter} for further customizations
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <C extends QueryStatementAdapter<O, B>> C apply(C configurer) throws Exception {
//        configurer.addObjectPostProcessor(this.objectPostProcessor);
        configurer.setBuilder((B) this);
        this.add(configurer);
        return configurer;
    }

    /**
     * Add a {@link QueryStatementAdapter} to this {@link QueryBuilder} and
     * invokes {@link QueryStatementAdapter#setBuilder(QueryBuilder)}.
     * @param configurer
     * @return the {@link QueryStatementAdapter} for further customizations
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <S extends QueryStatementAdapter<O, B>> S addStatement(S statement) throws Exception {
        statement.setBuilder((B) this);
//        this.add(configurer);
        return statement;
    }

    /**
     * Adds {@link QueryStatement} ensuring that it is allowed and invoking
     * {@link QueryStatement#init(QueryBuilder)} immediately if necessary.
     * @param configurer the {@link QueryStatement} to add
     */
    @SuppressWarnings("unchecked")
    private <C extends QueryStatement<O, B>> void add(C configurer) {

        Assert.notNull(configurer, "configurer cannot be null");

        Class<? extends QueryStatement<O, B>> clazz =
                (Class<? extends QueryStatement<O, B>>) configurer.getClass();

        synchronized (this.configurers) {
            if (this.buildState.isConfigured()) {
                throw new IllegalStateException("Cannot apply " + configurer + " to already built object");
            }
            List<QueryStatement<O, B>> configs = null;
            if (this.allowConfigurersOfSameType) {
                configs = this.configurers.get(clazz);
            }
            configs = (configs != null) ? configs : new ArrayList<>(1);
            configs.add(configurer);

            this.configurers.put(clazz, configs);
            if (this.buildState.isInitializing()) {
                this.configurersAddedInInitializing.add(configurer);
            }
        }
    }

    /**
     * Gets all the {@link QueryStatement} instances by its class name or an empty
     * List if not found. Note that object hierarchies are not considered.
     * @param clazz the {@link QueryStatement} class to look for
     * @return a list of {@link QueryStatement}s for further customization
     */
    @SuppressWarnings("unchecked")
    public <C extends QueryStatement<O, B>> List<C> getConfigurers(Class<C> clazz) {
        List<C> configs = (List<C>) this.configurers.get(clazz);
        if (configs == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(configs);
    }

    /**
     * Removes and returns the {@link QueryStatement} by its class name or
     * <code>null</code> if not found. Note that object hierarchies are not considered.
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <C extends QueryStatement<O, B>> C removeConfigurer(Class<C> clazz) {
        List<QueryStatement<O, B>> configs = this.configurers.remove(clazz);
        if (configs == null) {
            return null;
        }

        Assert.state(configs.size() == 1,
                     () -> "Only one configurer expected for type " + clazz + ", but got " + configs);
        return (C) configs.get(0);
    }

    /**
     * Specifies the {@link ObjectPostProcessor} to use.
     * @param objectPostProcessor the {@link ObjectPostProcessor} to use. Cannot be null
     * @return the {@link QueryBuilder} for further customizations
     */
    @SuppressWarnings("unchecked")
    public B objectPostProcessor(ObjectPostProcessor<Object> objectPostProcessor) {
        Assert.notNull(objectPostProcessor, "objectPostProcessor cannot be null");
        this.objectPostProcessor = objectPostProcessor;
        return (B) this;
    }

    /**
     * Performs post processing of an object. The default is to delegate to the
     * {@link ObjectPostProcessor}.
     * @param object the Object to post process
     * @return the possibly modified Object to use
     */
    protected <P> P postProcess(P object) {
        return this.objectPostProcessor.postProcess(object);
    }

    /**
     * Executes the build using the {@link SecurityConfigurer}'s that have been applied
     * using the following steps:
     *
     * <ul>
     * <li>Invokes {@link #beforeInit()} for any subclass to hook into</li>
     * <li>Invokes {@link QueryStatement#init(QueryBuilder)} for any
     * {@link SecurityConfigurer} that was applied to this builder.</li>
     * <li>Invokes {@link #beforeConfigure()} for any subclass to hook into</li>
     * <li>Invokes {@link #performBuild()} which actually builds the Object</li>
     * </ul>
     */
    @Override
    protected final O doBuild() throws Exception {
        synchronized (this.configurers) {
            this.buildState = BuildState.INITIALIZING;
            beforeInit();
            init();
            this.buildState = BuildState.CONFIGURING;
            beforeConfigure();
            configure();
            this.buildState = BuildState.BUILDING;
            O result = performBuild();
            this.buildState = BuildState.BUILT;
            return result;
        }
    }

    /**
     * Invoked prior to invoking each {@link QueryStatement#init(QueryBuilder)}
     * method. Subclasses may override this method to hook into the lifecycle without
     * using a {@link SecurityConfigurer}.
     */
    protected void beforeInit() throws Exception {
    }

    @SuppressWarnings("unchecked")
    private void init() throws Exception {
        Collection<QueryStatement<O, B>> configurers = getConfigurers();
        for (QueryStatement<O, B> configurer : configurers) {
            configurer.init((B) this);
        }
        for (QueryStatement<O, B> configurer : this.configurersAddedInInitializing) {
            configurer.init((B) this);
        }
    }

    /**
     * Invoked prior to invoking each
     * {@link QueryStatement#configure(QueryBuilder)} method. Subclasses may
     * override this method to hook into the lifecycle without using a
     * {@link SecurityConfigurer}.
     */
    protected void beforeConfigure() throws Exception {
    }

    @SuppressWarnings("unchecked")
    private void configure() throws Exception {
        Collection<QueryStatement<O, B>> configurers = getConfigurers();
        for (QueryStatement<O, B> configurer : configurers) {
            configurer.configure((B) this);
        }
    }


    /**
     * Subclasses must implement this method to build the object that is being returned.
     * @return the Object to be buit or null if the implementation allows it
     */
    protected abstract O performBuild() throws Exception;

    private Collection<QueryStatement<O, B>> getConfigurers() {
        List<QueryStatement<O, B>> result = new ArrayList<>();
        for (List<QueryStatement<O, B>> configs : this.configurers.values()) {
            result.addAll(configs);
        }
        return result;
    }


    /**
     * The build state for the application
     *
     * @author Rob Winch
     * @since 3.2
     */
    private enum BuildState {

        /**
         * This is the state before the {@link QueryBuilder#build()} is invoked
         */
        UNBUILT(0),

        /**
         * The state from when {@link QueryBuilder#build()} is first invoked until all the
         * {@link QueryStatement#init(QueryBuilder)} methods have been invoked.
         */
        INITIALIZING(1),

        /**
         * The state from after all {@link QueryStatement#init(QueryBuilder)} have
         * been invoked until after all the
         * {@link QueryStatement#configure(QueryBuilder)} methods have been
         * invoked.
         */
        CONFIGURING(2),

        /**
         * From the point after all the
         * {@link QueryStatement#configure(QueryBuilder)} have completed to just
         * after {@link AbstractConfiguredQueryBuilder#performBuild()}.
         */
        BUILDING(3),

        /**
         * After the object has been completely built.
         */
        BUILT(4);

        private final int order;

        BuildState(int order) {
            this.order = order;
        }

        public boolean isInitializing() {
            return INITIALIZING.order == this.order;
        }

        /**
         * Determines if the state is CONFIGURING or later
         */
        public boolean isConfigured() {
            return this.order >= CONFIGURING.order;
        }

    }
}
