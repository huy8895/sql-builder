package org.huytvdev.utils.httpsecurityclone;

public final class HttpSecurity extends AbstractConfiguredSecurityBuilder<DefaultSecurityFilterChain, HttpSecurity>
        implements SecurityBuilder<DefaultSecurityFilterChain>, HttpSecurityBuilder<HttpSecurity>{
    @Override
    protected DefaultSecurityFilterChain doBuild() throws Exception {
        return null;
    }

    public CsrfConfigurer<HttpSecurity> csrf() throws Exception {
        ApplicationContext context = getContext();
        return getOrApply(new CsrfConfigurer<>(context));
    }
}
