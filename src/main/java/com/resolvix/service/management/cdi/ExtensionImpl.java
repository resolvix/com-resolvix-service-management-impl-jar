package com.resolvix.service.management.cdi;


import com.resolvix.lib.javaee.BeanUtils;
import com.resolvix.service.management.api.ServiceComponent;
import com.resolvix.service.management.api.event.Shutdown;
import com.resolvix.service.management.api.event.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import java.util.HashSet;
import java.util.Set;

public class ExtensionImpl
    implements Extension
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionImpl.class);

    private Event<Startup> startupEvent;

    private Event<Shutdown> shutdownEvent;

    private Set<ServiceComponent> serviceComponents;

    public ExtensionImpl() {
        this.serviceComponents = new HashSet<>();
    }

    private void postConstruct() {
        LOGGER.info("Extension::postConstruct invoked.");
    }

    private void initialiseCDI(
        BeanManager beanManager, @Observes BeforeBeanDiscovery beforeBeanDiscovery) {
        LOGGER.info("ExtensionImpl::initialiseCDI invoked.");
    }

    private void startCDI(
        BeanManager beanManager, @Observes AfterDeploymentValidation afterDeploymentValidation) {
        LOGGER.info("ExtensionImpl::startCDI invoked.");
    }

    private ServiceComponent start(BeanManager beanManager, Bean<ServiceComponent> bean) {
        ServiceComponent serviceComponent
            = BeanUtils.get(beanManager, bean, ServiceComponent.class);
        serviceComponent.start();
        return serviceComponent;
    }

    @SuppressWarnings("unchecked")
    private void initialisedApplicationScopedContext(
        BeanManager beanManager, @Observes @Initialized(ApplicationScoped.class) Object initialized) {
        LOGGER.info("ExtensionImpl::initialisedApplicationScopedContext invoked.");
        startupEvent = (Event<Startup>) BeanUtils.get(beanManager, Event.class);
        startupEvent.fire(new Startup() { });
        Set<Bean<?>> beans = beanManager.getBeans(ServiceComponent.class);
        for (Bean<?> bean : beans)
            serviceComponents.add(
                start(beanManager, (Bean<ServiceComponent>) bean));
    }

    private void stop(ServiceComponent serviceComponent) {
        serviceComponent.stop();
    }

    @SuppressWarnings("unchecked")
    private void destroyedApplicationScopedContext(
        BeanManager beanManager, @Observes @Destroyed(ApplicationScoped.class) Object destroyed) {
        LOGGER.info("ExtensionImpl::destroyedApplicationScopedContext invoked.");
        for (ServiceComponent serviceComponent : serviceComponents)
            serviceComponent.stop();
        shutdownEvent = (Event<Shutdown>) BeanUtils.get(beanManager, Event.class);
        shutdownEvent.fire(new Shutdown() { });
    }

    private void stopCDI(
        BeanManager beanManager, @Observes BeforeShutdown beforeShutdown) {
        LOGGER.info("ExtensionImpl::stopCDI invoked.");
    }

    @PreDestroy
    private void preDestroy() {
        LOGGER.info("ExtensionImpl::preDestroy invoked.");
    }
}
