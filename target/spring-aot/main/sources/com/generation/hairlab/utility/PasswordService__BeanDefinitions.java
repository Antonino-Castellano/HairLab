package com.generation.hairlab.utility;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PasswordService}.
 */
@Generated
public class PasswordService__BeanDefinitions {
  /**
   * Get the bean definition for 'passwordService'.
   */
  public static BeanDefinition getPasswordServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PasswordService.class);
    beanDefinition.setInstanceSupplier(PasswordService::new);
    return beanDefinition;
  }
}
