package com.resolvix.service.management.cdi.bean;

import com.resolvix.service.management.api.event.Shutdown;
import com.resolvix.service.management.api.event.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;

@Dependent
public class DependentBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DependentBean.class);

    @PostConstruct
    private void postConstruct() {
        LOGGER.info("DependentBean::postConstruct invoked.");
    }

    private void startup(
        @Observes Startup startup) {
        LOGGER.info("DependentBean::startup invoked");
    }

    private void shutdown(
        @Observes Shutdown shutdown) {
        LOGGER.info("DependentBean::shutdown invoked");
    }

    @PreDestroy
    private void preDestroy() {
        LOGGER.info("DependentBean::preDestroy invoked");
    }
}
