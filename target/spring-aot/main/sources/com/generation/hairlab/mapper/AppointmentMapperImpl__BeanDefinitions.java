package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AppointmentMapperImpl}.
 */
@Generated
public class AppointmentMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'appointmentMapperImpl'.
   */
  public static BeanDefinition getAppointmentMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AppointmentMapperImpl.class);
    beanDefinition.setInstanceSupplier(AppointmentMapperImpl::new);
    return beanDefinition;
  }
}
