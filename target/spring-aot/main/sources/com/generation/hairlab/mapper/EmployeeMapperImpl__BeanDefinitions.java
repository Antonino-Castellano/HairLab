package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link EmployeeMapperImpl}.
 */
@Generated
public class EmployeeMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'employeeMapperImpl'.
   */
  public static BeanDefinition getEmployeeMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(EmployeeMapperImpl.class);
    beanDefinition.setInstanceSupplier(EmployeeMapperImpl::new);
    return beanDefinition;
  }
}
