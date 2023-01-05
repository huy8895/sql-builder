package org.huytvdev.utils.httpsecurityclone;

/**
 * @param <H>
 * @author Rob Winch
 */
public interface HttpSecurityBuilder<H extends HttpSecurityBuilder<H>>
        extends SecurityBuilder<DefaultSecurityFilterChain> {
}
