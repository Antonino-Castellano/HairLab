package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CustomerMapperImpl}.
 */
@Generated
public class CustomerMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'customerMapperImpl'.
   */
  public static BeanDefinition getCustomerMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CustomerMapperImpl.class);
    beanDefinition.setInstanceSupplier(CustomerMapperImpl::new);
    return beanDefinition;
  }
}
