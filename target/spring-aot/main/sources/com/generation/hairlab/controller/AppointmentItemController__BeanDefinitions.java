package com.generation.hairlab.controller;

import com.generation.hairlab.service.AppointmentItemService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AppointmentItemController}.
 */
@Generated
public class AppointmentItemController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'appointmentItemController'.
   */
  private static BeanInstanceSupplier<AppointmentItemController> getAppointmentItemControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AppointmentItemController>forConstructor(AppointmentItemService.class)
            .withGenerator((registeredBean, args) -> new AppointmentItemController(args.get(0)));
  }

  /**
   * Get the bean definition for 'appointmentItemController'.
   */
  public static BeanDefinition getAppointmentItemControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AppointmentItemController.class);
    beanDefinition.setInstanceSupplier(getAppointmentItemControllerInstanceSupplier());
    return beanDefinition;
  }
}
