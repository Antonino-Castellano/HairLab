package com.generation.hairlab.controller;

import com.generation.hairlab.service.HairDyeService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HairDyeController}.
 */
@Generated
public class HairDyeController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'hairDyeController'.
   */
  private static BeanInstanceSupplier<HairDyeController> getHairDyeControllerInstanceSupplier() {
    return BeanInstanceSupplier.<HairDyeController>forConstructor(HairDyeService.class)
            .withGenerator((registeredBean, args) -> new HairDyeController(args.get(0)));
  }

  /**
   * Get the bean definition for 'hairDyeController'.
   */
  public static BeanDefinition getHairDyeControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HairDyeController.class);
    beanDefinition.setInstanceSupplier(getHairDyeControllerInstanceSupplier());
    return beanDefinition;
  }
}
