package com.resolvix.service.management.cdi.bean;

import com.resolvix.service.management.api.event.Shutdown;
import com.resolvix.service.management.api.event.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class ApplicationScopedBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationScopedBean.class);

    @PostConstruct
    private void postConstruct() {
        LOGGER.info("ApplicationScopedBean::postConstruct invoked.");
    }

    private void startup(
        @Observes Startup startup) {
        LOGGER.info("ApplicationScopedBean::startup invoked");
    }

    private void shutdown(
        @Observes Shutdown shutdown) {
        LOGGER.info("ApplicationScopedBean::shutdown invoked");
    }

    @PreDestroy
    private void preDestroy() {
        LOGGER.info("ApplicationScopedBean::preDestroy invoked");
    }
}
