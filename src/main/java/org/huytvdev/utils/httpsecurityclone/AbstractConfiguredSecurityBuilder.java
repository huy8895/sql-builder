package org.huytvdev.utils.httpsecurityclone;

/**
 * <p>
 * A base {@link SecurityBuilder} that allows {@link SecurityConfigurer} to be applied to
 * it. This makes modifying the {@link SecurityBuilder} a strategy that can be customized
 * and broken up into a number of {@link SecurityConfigurer} objects that have more
 * specific goals than that of the {@link SecurityBuilder}.
 * </p>
 *
 * <p>
 * For example, a {@link SecurityBuilder} may build an {@link DelegatingFilterProxy}, but
 * a {@link SecurityConfigurer} might populate the {@link SecurityBuilder} with the
 * filters necessary for session management, form based login, authorization, etc.
 * </p>
 *
 * @param <O> The object that this builder returns
 * @param <B> The type of this builder (that is returned by the base class)
 * @author Rob Winch
 * @see WebSecurity
 */
public abstract class AbstractConfiguredSecurityBuilder<O, B extends SecurityBuilder<O>>
        extends AbstractSecurityBuilder<O> {
}
