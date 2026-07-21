package com.generation.hairlab;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HairlabApplication}.
 */
@Generated
public class HairlabApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'hairlabApplication'.
   */
  public static BeanDefinition getHairlabApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HairlabApplication.class);
    beanDefinition.setInstanceSupplier(HairlabApplication::new);
    return beanDefinition;
  }
}
