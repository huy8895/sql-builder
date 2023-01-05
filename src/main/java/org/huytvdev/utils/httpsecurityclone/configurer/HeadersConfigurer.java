package org.huytvdev.utils.httpsecurityclone.configurer;

import org.huytvdev.utils.httpsecurityclone.AbstractHttpConfigurer;
import org.huytvdev.utils.httpsecurityclone.HttpSecurityBuilder;

public class HeadersConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractHttpConfigurer<HeadersConfigurer<H>, H> {

//  .defaultsDisabled()
// 	.cacheControl(withDefaults())
//  .frameOptions(withDefaults())

    public HeadersConfigurer<H> defaultsDisabled() {
        // TODO: 5/1/2023
        return this;
    }

    public HeadersConfigurer<H> frameOptions() {
        return this;
    }

}
