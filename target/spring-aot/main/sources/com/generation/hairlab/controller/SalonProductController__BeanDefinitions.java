package com.generation.hairlab.controller;

import com.generation.hairlab.service.SalonProductService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SalonProductController}.
 */
@Generated
public class SalonProductController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'salonProductController'.
   */
  private static BeanInstanceSupplier<SalonProductController> getSalonProductControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<SalonProductController>forConstructor(SalonProductService.class)
            .withGenerator((registeredBean, args) -> new SalonProductController(args.get(0)));
  }

  /**
   * Get the bean definition for 'salonProductController'.
   */
  public static BeanDefinition getSalonProductControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SalonProductController.class);
    beanDefinition.setInstanceSupplier(getSalonProductControllerInstanceSupplier());
    return beanDefinition;
  }
}
