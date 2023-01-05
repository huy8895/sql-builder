package org.huytvdev.utils.httpsecurityclone;


import java.util.ArrayList;
import java.util.List;

/**
 * A base class for {@link SecurityConfigurer} that allows subclasses to only implement
 * the methods they are interested in. It also provides a mechanism for using the
 * {@link SecurityConfigurer} and when done gaining access to the {@link SecurityBuilder}
 * that is being configured.
 *
 * @param <O> The Object being built by B
 * @param <B> The Builder that is building O and is configured by
 * {@link SecurityConfigurerAdapter}
 * @author Rob Winch
 * @author Wallace Wadge
 */
public abstract class SecurityConfigurerAdapter<O, B extends SecurityBuilder<O>> implements SecurityConfigurer<O, B> {

    private B securityBuilder;

    private CompositeObjectPostProcessor objectPostProcessor = new CompositeObjectPostProcessor();

    @Override
    public void init(B builder) throws Exception {
    }

    @Override
    public void configure(B builder) throws Exception {
    }

    /**
     * Return the {@link SecurityBuilder} when done using the {@link SecurityConfigurer}.
     * This is useful for method chaining.
     * @return the {@link SecurityBuilder} for further customizations
     */
    public B and() {
        return getBuilder();
    }

    /**
     * Gets the {@link SecurityBuilder}. Cannot be null.
     * @return the {@link SecurityBuilder}
     * @throws IllegalStateException if {@link SecurityBuilder} is null
     */
    protected final B getBuilder() {
        return this.securityBuilder;
    }

    /**
     * Sets the {@link SecurityBuilder} to be used. This is automatically set when using
     * {@link AbstractConfiguredSecurityBuilder#apply(SecurityConfigurerAdapter)}
     * @param builder the {@link SecurityBuilder} to set
     */
    public void setBuilder(B builder) {
        this.securityBuilder = builder;
    }


}