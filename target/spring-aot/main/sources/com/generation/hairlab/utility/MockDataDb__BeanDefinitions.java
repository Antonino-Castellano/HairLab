package com.generation.hairlab.utility;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link MockDataDb}.
 */
@Generated
public class MockDataDb__BeanDefinitions {
  /**
   * Get the bean definition for 'mockDataDb'.
   */
  public static BeanDefinition getMockDataDbBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(MockDataDb.class);
    InstanceSupplier<MockDataDb> instanceSupplier = InstanceSupplier.using(MockDataDb::new);
    instanceSupplier = instanceSupplier.andThen(MockDataDb__PersistenceInjection::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
