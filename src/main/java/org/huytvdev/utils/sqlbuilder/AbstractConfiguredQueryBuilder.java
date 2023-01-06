package org.huytvdev.utils.sqlbuilder;


import java.util.*;

/**
 * <p>
 * A base {@link QueryBuilder} that allows {@link QueryConfigurer} to be applied to
 * it. This makes modifying the {@link QueryBuilder} a strategy that can be customized
 * and broken up into a number of {@link QueryConfigurer} objects that have more
 * specific goals than that of the {@link QueryBuilder}.
 * </p>
 *
 * <p>
 * For example, a {@link QueryBuilder} may build an {@link DelegatingFilterProxy}, but
 * a {@link QueryConfigurer} might populate the {@link QueryBuilder} with the
 * filters necessary for session management, form based login, authorization, etc.
 * </p>
 *
 * @param <O> The object that this builder returns
 * @param <B> The type of this builder (that is returned by the base class)
 * @author Rob Winch
 */
public abstract class AbstractConfiguredQueryBuilder<O, B extends QueryBuilder<O>>
        extends AbstractQueryBuilder<O> {

    private final Map<Class<? extends QueryConfigurer<O, B>>, List<QueryConfigurer<O, B>>> configurers = new LinkedHashMap<>();

    private final List<QueryConfigurer<O, B>> configurersAddedInInitializing = new ArrayList<>();
    private final Map<Class<?>, Object> sharedObjects = new HashMap<>();
    private final boolean allowConfigurersOfSameType;
    private final ObjectPostProcessor<Object> objectPostProcessor;
    private final BuildState buildState;

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
         * {@link QueryConfigurer#init(QueryBuilder)} methods have been invoked.
         */
        INITIALIZING(1),

        /**
         * The state from after all {@link QueryConfigurer#init(QueryBuilder)} have
         * been invoked until after all the
         * {@link QueryConfigurer#configure(QueryBuilder)} methods have been
         * invoked.
         */
        CONFIGURING(2),

        /**
         * From the point after all the
         * {@link QueryConfigurer#configure(QueryBuilder)} have completed to just
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
         * @return
         */
        public boolean isConfigured() {
            return this.order >= CONFIGURING.order;
        }

    }
}
