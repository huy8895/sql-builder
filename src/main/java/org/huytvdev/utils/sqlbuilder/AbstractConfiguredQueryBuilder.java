package org.huytvdev.utils.sqlbuilder;


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
 * @see WebSecurity
 */
public abstract class AbstractConfiguredQueryBuilder<O, B extends QueryBuilder<O>>
        extends AbstractQueryBuilder<O> {

}
