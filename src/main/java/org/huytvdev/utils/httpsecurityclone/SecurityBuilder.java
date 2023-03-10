package org.huytvdev.utils.httpsecurityclone;

/**
 * Interface for building an Object
 *
 * @param <O> The type of the Object being built
 * @author Rob Winch
 * @since 3.2
 */
public interface SecurityBuilder<O> {

    /**
     * Builds the object and returns it or null.
     * @return the Object to be built or null if the implementation allows it.
     * @throws Exception if an error occurred when building the Object
     */
    O build() throws Exception;

}