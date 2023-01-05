package org.huytvdev.utils.httpsecurityclone;

import org.huytvdev.utils.httpsecurityclone.configurer.HeadersConfigurer;

public class Main {
    public static void main(String[] args) throws Exception {
        final var httpSecurity = new HttpSecurity();
        httpSecurity.csrf()
                .and()
                .headers(new Customizer<HeadersConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(HeadersConfigurer<HttpSecurity> httpSecurityHeadersConfigurer) {

                    }
                })
                    .build();

    }
}
