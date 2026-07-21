package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SalonProductMapperImpl}.
 */
@Generated
public class SalonProductMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'salonProductMapperImpl'.
   */
  public static BeanDefinition getSalonProductMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SalonProductMapperImpl.class);
    beanDefinition.setInstanceSupplier(SalonProductMapperImpl::new);
    return beanDefinition;
  }
}
