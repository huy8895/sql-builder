package org.huytvdev.utils.httpsecurityclone;

import java.util.LinkedHashMap;
import java.util.List;

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

    private final LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>> configurers = new LinkedHashMap<>();

    private ObjectPostProcessor<Object> objectPostProcessor;

    /**
     * Applies a {@link SecurityConfigurerAdapter} to this {@link SecurityBuilder} and
     * invokes {@link SecurityConfigurerAdapter#setBuilder(SecurityBuilder)}.
     * @param configurer
     * @return the {@link SecurityConfigurerAdapter} for further customizations
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <C extends SecurityConfigurerAdapter<O, B>> C apply(C configurer) throws Exception {
        configurer.addObjectPostProcessor(this.objectPostProcessor);
        configurer.setBuilder((B) this);
        return configurer;
    }

    /**
     * Gets the {@link SecurityConfigurer} by its class name or <code>null</code> if not
     * found. Note that object hierarchies are not considered.
     * @param clazz
     * @return the {@link SecurityConfigurer} for further customizations
     */
    @SuppressWarnings("unchecked")
    public <C extends SecurityConfigurer<O, B>> C getConfigurer(Class<C> clazz) {
        List<SecurityConfigurer<O, B>> configs = this.configurers.get(clazz);
        if (configs == null) {
            return null;
        }

        if (configs.size() == 1) throw new RuntimeException("Only one configurer expected for type " + clazz + ", but got " + configs);

        return (C) configs.get(0);
    }
}
