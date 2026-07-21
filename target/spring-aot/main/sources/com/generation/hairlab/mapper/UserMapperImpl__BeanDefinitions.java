package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link UserMapperImpl}.
 */
@Generated
public class UserMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'userMapperImpl'.
   */
  public static BeanDefinition getUserMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(UserMapperImpl.class);
    beanDefinition.setInstanceSupplier(UserMapperImpl::new);
    return beanDefinition;
  }
}
