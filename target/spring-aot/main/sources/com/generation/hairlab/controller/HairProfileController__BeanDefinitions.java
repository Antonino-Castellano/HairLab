package com.generation.hairlab.controller;

import com.generation.hairlab.service.HairProfileService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HairProfileController}.
 */
@Generated
public class HairProfileController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'hairProfileController'.
   */
  private static BeanInstanceSupplier<HairProfileController> getHairProfileControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<HairProfileController>forConstructor(HairProfileService.class)
            .withGenerator((registeredBean, args) -> new HairProfileController(args.get(0)));
  }

  /**
   * Get the bean definition for 'hairProfileController'.
   */
  public static BeanDefinition getHairProfileControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HairProfileController.class);
    beanDefinition.setInstanceSupplier(getHairProfileControllerInstanceSupplier());
    return beanDefinition;
  }
}
