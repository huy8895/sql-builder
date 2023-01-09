package org.huytvdev.utils.sqlbuilder.lib;

import java.util.ArrayList;
import java.util.List;

public abstract class QueryStatementAdapter<O, B extends QueryBuilder<O>>
        implements QueryConfigurer<O, B> {
    private B queryBuilder;
    @Override
    public void init(B builder) throws Exception {

    }

    @Override
    public void configure(B builder) throws Exception {

    }

    public B appendQuery() {
        return this.getBuilder();
    }

    protected B getBuilder() {
        Assert.state(this.queryBuilder != null, "queryBuilder cannot be null");
        return this.queryBuilder;
    }


    /**
     * Sets the {@link QueryBuilder} to be used. This is automatically set when using
     * {@link AbstractConfiguredQueryBuilder#apply(QueryStatementAdapter)}
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
