package org.huytvdev.utils.httpsecurityclone;

import org.huytvdev.utils.httpsecurityclone.configurer.HeadersConfigurer;

public class Main {
    public static void main(String[] args) throws Exception {
        final var httpSecurity = new HttpSecurity();
        httpSecurity.csrf()
                    .and()
                    .headers(headersConfigurer -> {
                        headersConfigurer.defaultsDisabled()
                                         .frameOptions();
                        })
                        .headers()
                    .and()
                        .headers()
                        .defaultsDisabled()
                        .frameOptions()
                    .and()
                    .build();

    }
}
