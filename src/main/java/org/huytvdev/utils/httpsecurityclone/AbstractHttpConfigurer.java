package org.huytvdev.utils.httpsecurityclone;


/**
 * Adds a convenient base class for {@link SecurityConfigurer} instances that operate on
 * {@link HttpSecurity}.
 *
 * @author Rob Winch
 */
public abstract class AbstractHttpConfigurer<T extends AbstractHttpConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, B> {

    /**
     * Disables the {@link AbstractHttpConfigurer} by removing it. After doing so a fresh
     * version of the configuration can be applied.
     * @return the {@link HttpSecurityBuilder} for additional customizations
     */
    public B disable() {
//        getBuilder().removeConfigurer(getClass());
        return getBuilder();
    }

    @SuppressWarnings("unchecked")
    public T withObjectPostProcessor(ObjectPostProcessor<?> objectPostProcessor) {
        addObjectPostProcessor(objectPostProcessor);
        return (T) this;
    }

}