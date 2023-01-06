package org.huytvdev.utils.sqlbuilder;

public abstract class AbstractSqlConfigurer<T extends AbstractSqlConfigurer<T, B>, B extends SqlQueryBuilder<B>>
    extends QueryConfigurerAdapter<DefaultSqlQueryChain, B> {


    /**
     * Disables the {@link AbstractSqlConfigurer} by removing it. After doing so a fresh
     * version of the configuration can be applied.
     * @return the {@link SqlQueryBuilder} for additional customizations
     */
    @SuppressWarnings("unchecked")
    public B disable() {
        getBuilder().removeConfigurer(getClass());
        return getBuilder();
    }

    @SuppressWarnings("unchecked")
    public T withObjectPostProcessor(ObjectPostProcessor<?> objectPostProcessor) {
        addObjectPostProcessor(objectPostProcessor);
        return (T) this;
    }
}
