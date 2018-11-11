package org.myorg.utils.integration;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A CDI producer bean implementation to create injectable {@link Logger} instances.
 */
public class LoggerProducer {

    /**
     * Create a suitable {@link Logger} instance depending on the actual
     * bean the instance is injected into.
     *
     * @param injectionPoint the injection point
     * @return the instance
     */
    @Produces
    @Dependent
    public Logger createLogger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getBean().getBeanClass().getName());
    }
}
