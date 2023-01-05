package org.huytvdev.utils.httpsecurityclone;

import org.huytvdev.utils.httpsecurityclone.configurer.HeadersConfigurer;
@SuppressWarnings("unchecked")
public final class HttpSecurity extends AbstractConfiguredSecurityBuilder<DefaultSecurityFilterChain, HttpSecurity>
        implements SecurityBuilder<DefaultSecurityFilterChain>, HttpSecurityBuilder<HttpSecurity>{
    @Override
    protected DefaultSecurityFilterChain doBuild() throws Exception {
        return null;
    }

    public CsrfConfigurer<HttpSecurity> csrf() throws Exception {
        return getOrApply(new CsrfConfigurer<>());
    }

    public HttpSecurity headers(Customizer<HeadersConfigurer<HttpSecurity>> headersCustomizer) throws Exception {
        headersCustomizer.customize(getOrApply(new HeadersConfigurer<>()));
        return HttpSecurity.this;
    }

    public HeadersConfigurer<HttpSecurity> headers() throws Exception {
        return getOrApply(new HeadersConfigurer<>());
    }


    private <C extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> C getOrApply(C configurer)
            throws Exception {
        C existingConfig = (C) getConfigurer(configurer.getClass());
        if (existingConfig != null) {
            return existingConfig;
        }
        return apply(configurer);
    }
}
