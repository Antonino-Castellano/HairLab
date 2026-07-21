package com.generation.hairlab.controller;

import com.generation.hairlab.service.AppointmentService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AppointmentController}.
 */
@Generated
public class AppointmentController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'appointmentController'.
   */
  private static BeanInstanceSupplier<AppointmentController> getAppointmentControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AppointmentController>forConstructor(AppointmentService.class)
            .withGenerator((registeredBean, args) -> new AppointmentController(args.get(0)));
  }

  /**
   * Get the bean definition for 'appointmentController'.
   */
  public static BeanDefinition getAppointmentControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AppointmentController.class);
    beanDefinition.setInstanceSupplier(getAppointmentControllerInstanceSupplier());
    return beanDefinition;
  }
}
