package com.generation.hairlab.service;

import com.generation.hairlab.mapper.AppointmentMapper;
import com.generation.hairlab.repository.AppointmentRepository;
import com.generation.hairlab.repository.CustomerRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AppointmentService}.
 */
@Generated
public class AppointmentService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'appointmentService'.
   */
  private static BeanInstanceSupplier<AppointmentService> getAppointmentServiceInstanceSupplier() {
    return BeanInstanceSupplier.<AppointmentService>forConstructor(AppointmentRepository.class, CustomerRepository.class, AppointmentMapper.class)
            .withGenerator((registeredBean, args) -> new AppointmentService(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'appointmentService'.
   */
  public static BeanDefinition getAppointmentServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AppointmentService.class);
    beanDefinition.setInstanceSupplier(getAppointmentServiceInstanceSupplier());
    return beanDefinition;
  }
}
