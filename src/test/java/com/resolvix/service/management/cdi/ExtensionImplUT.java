package com.resolvix.service.management.cdi;

import com.resolvix.lib.javaee.BeanUtils;
import org.hamcrest.Matchers;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class ExtensionImplUT {

    @Rule
    public WeldInitiator weld = WeldInitiator.performDefaultDiscovery();

    @Test
    public void testExtension() {

    }
}
