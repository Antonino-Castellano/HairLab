package com.generation.hairlab.service;

import com.generation.hairlab.mapper.AppointmentItemMapper;
import com.generation.hairlab.repository.AppointmentItemRepository;
import com.generation.hairlab.repository.AppointmentRepository;
import com.generation.hairlab.repository.EmployeeRepository;
import com.generation.hairlab.repository.SalonProductRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AppointmentItemService}.
 */
@Generated
public class AppointmentItemService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'appointmentItemService'.
   */
  private static BeanInstanceSupplier<AppointmentItemService> getAppointmentItemServiceInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AppointmentItemService>forConstructor(AppointmentItemRepository.class, AppointmentRepository.class, SalonProductRepository.class, EmployeeRepository.class, AppointmentItemMapper.class)
            .withGenerator((registeredBean, args) -> new AppointmentItemService(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4)));
  }

  /**
   * Get the bean definition for 'appointmentItemService'.
   */
  public static BeanDefinition getAppointmentItemServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AppointmentItemService.class);
    beanDefinition.setInstanceSupplier(getAppointmentItemServiceInstanceSupplier());
    return beanDefinition;
  }
}
