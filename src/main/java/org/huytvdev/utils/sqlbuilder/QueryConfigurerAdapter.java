package org.huytvdev.utils.sqlbuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class QueryConfigurerAdapter<O, B extends QueryBuilder<O>>
        implements QueryConfigurer<O, B> {
    private B queryBuilder;
    private CompositeObjectPostProcessor objectPostProcessor = new CompositeObjectPostProcessor();

    @Override
    public void init(B builder) throws Exception {

    }

    @Override
    public void configure(B builder) throws Exception {

    }

    public B and() {
        return this.getBuilder();
    }

    protected B getBuilder() {
        Assert.state(this.queryBuilder != null, "securityBuilder cannot be null");
        return this.queryBuilder;
    }

    /**
     * Performs post processing of an object. The default is to delegate to the
     * {@link ObjectPostProcessor}.
     * @param object the Object to post process
     * @return the possibly modified Object to use
     */
    @SuppressWarnings("unchecked")
    protected <T> T postProcess(T object) {
        return (T) this.objectPostProcessor.postProcess(object);
    }

    /**
     * Adds an {@link ObjectPostProcessor} to be used for this
     * {@link QueryConfigurerAdapter}. The default implementation does nothing to the
     * object.
     * @param objectPostProcessor the {@link ObjectPostProcessor} to use
     */
    public void addObjectPostProcessor(ObjectPostProcessor<?> objectPostProcessor) {
        this.objectPostProcessor.addObjectPostProcessor(objectPostProcessor);
    }

    /**
     * Sets the {@link QueryBuilder} to be used. This is automatically set when using
     * {@link AbstractConfiguredQueryBuilder#apply(QueryConfigurerAdapter)}
     * @param builder the {@link QueryBuilder} to set
     */
    public void setBuilder(B builder) {
        this.queryBuilder = builder;
    }


    /**
     * An {@link ObjectPostProcessor} that delegates work to numerous
     * {@link ObjectPostProcessor} implementations.
     *
     * @author Rob Winch
     */
    private static final class CompositeObjectPostProcessor implements ObjectPostProcessor<Object> {

        private List<ObjectPostProcessor<?>> postProcessors = new ArrayList<>();

        @Override
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Object postProcess(Object object) {
            for (ObjectPostProcessor opp : this.postProcessors) {
                Class<?> oppClass = opp.getClass();
//                Class<?> oppType = GenericTypeResolver.resolveTypeArgument(oppClass, ObjectPostProcessor.class);
//                if (oppType == null || oppType.isAssignableFrom(object.getClass())) {
//                    object = opp.postProcess(object);
//                }
            }
            return object;
        }

        /**
         * Adds an {@link ObjectPostProcessor} to use
         * @param objectPostProcessor the {@link ObjectPostProcessor} to add
         * @return true if the {@link ObjectPostProcessor} was added, else false
         */
        private boolean addObjectPostProcessor(ObjectPostProcessor<?> objectPostProcessor) {
            boolean result = this.postProcessors.add(objectPostProcessor);
//            this.postProcessors.sort(AnnotationAwareOrderComparator.INSTANCE);
            return result;
        }

    }
}
