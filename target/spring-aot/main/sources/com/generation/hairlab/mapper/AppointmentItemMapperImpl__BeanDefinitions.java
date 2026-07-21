package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AppointmentItemMapperImpl}.
 */
@Generated
public class AppointmentItemMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'appointmentItemMapperImpl'.
   */
  public static BeanDefinition getAppointmentItemMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AppointmentItemMapperImpl.class);
    beanDefinition.setInstanceSupplier(AppointmentItemMapperImpl::new);
    return beanDefinition;
  }
}
