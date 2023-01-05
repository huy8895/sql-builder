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
        if (this.securityBuilder == null) {
            throw new RuntimeException("securityBuilder cannot be null");
        }
        return this.securityBuilder;
    }

    /**
     * Performs post processing of an object. The default is to delegate to the
     * {@link ObjectPostProcessor}.
     * @param object the Object to post process
     * @return the possibly modified Object to use
     */
    @SuppressWarnings("unchecked")
    protected <T> T postProcess(T object) {
        return (T) this.objectPostProcessor.postProcess(object);
    }

    /**
     * Adds an {@link ObjectPostProcessor} to be used for this
     * {@link SecurityConfigurerAdapter}. The default implementation does nothing to the
     * object.
     * @param objectPostProcessor the {@link ObjectPostProcessor} to use
     */
    public void addObjectPostProcessor(ObjectPostProcessor<?> objectPostProcessor) {
        this.objectPostProcessor.addObjectPostProcessor(objectPostProcessor);
    }

    /**
     * Sets the {@link SecurityBuilder} to be used. This is automatically set when using
     * {@link AbstractConfiguredSecurityBuilder#apply(SecurityConfigurerAdapter)}
     * @param builder the {@link SecurityBuilder} to set
     */
    public void setBuilder(B builder) {
        this.securityBuilder = builder;
    }

    /**
     * An {@link ObjectPostProcessor} that delegates work to numerous
     * {@link ObjectPostProcessor} implementations.
     *
     * @author Rob Winch
     */
    private static final class CompositeObjectPostProcessor implements ObjectPostProcessor<Object> {

        private List<ObjectPostProcessor<?>> postProcessors = new ArrayList<>();

        @Override
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Object postProcess(Object object) {
//            for (ObjectPostProcessor opp : this.postProcessors) {
//                Class<?> oppClass = opp.getClass();
//                Class<?> oppType = GenericTypeResolver.resolveTypeArgument(oppClass, ObjectPostProcessor.class);
//                if (oppType == null || oppType.isAssignableFrom(object.getClass())) {
//                    object = opp.postProcess(object);
//                }
//            }
            return object;
        }

        /**
         * Adds an {@link ObjectPostProcessor} to use
         * @param objectPostProcessor the {@link ObjectPostProcessor} to add
         * @return true if the {@link ObjectPostProcessor} was added, else false
         */
        private boolean addObjectPostProcessor(ObjectPostProcessor<?> objectPostProcessor) {
            boolean result = this.postProcessors.add(objectPostProcessor);
//            this.postProcessors.sort(AnnotationAwareOrderComparator.INSTANCE);
            return result;
        }

    }

}