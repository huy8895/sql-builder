package org.huytvdev.utils.sqlbuilder;

import org.huytvdev.utils.httpsecurityclone.SecurityConfigurer;

/**
 * Allows for configuring a {@link QueryBuilder}. All {@link SecurityConfigurer} first
 * have their {@link #init(QueryBuilder)} method invoked. After all
 * {@link #init(QueryBuilder)} methods have been invoked, each
 * {@link #configure(QueryBuilder)} method is invoked.
 *
 * @param <O> The object being built by the {@link QueryBuilder} B
 * @param <B> The {@link QueryBuilder} that builds objects of type O. This is also the
 * {@link QueryBuilder} that is being configured.
 * @author huy8895
 * @see AbstractConfiguredQueryBuilder
 */
public interface QueryConfigurer<O, B extends QueryBuilder<O>> {
    /**
     * Initialize the {@link QueryConfigurer}. Here only shared state should be created
     * and modified, but not properties on the {@link QueryConfigurer} used for building
     * the object. This ensures that the {@link #configure(QueryBuilder)} } method uses
     * the correct shared objects when building. Configurers should be applied here.
     * @param builder
     * @throws Exception
     */
    void init(B builder) throws Exception;

    /**
     * Configure the {@link QueryConfigurer} by setting the necessary properties on the
     * {@link QueryConfigurer}.
     * @param builder
     * @throws Exception
     */
    void configure(B builder) throws Exception;

}
