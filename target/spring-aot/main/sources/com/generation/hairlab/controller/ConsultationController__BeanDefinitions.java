package com.generation.hairlab.controller;

import com.generation.hairlab.service.ConsultationService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ConsultationController}.
 */
@Generated
public class ConsultationController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'consultationController'.
   */
  private static BeanInstanceSupplier<ConsultationController> getConsultationControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ConsultationController>forConstructor(ConsultationService.class)
            .withGenerator((registeredBean, args) -> new ConsultationController(args.get(0)));
  }

  /**
   * Get the bean definition for 'consultationController'.
   */
  public static BeanDefinition getConsultationControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ConsultationController.class);
    beanDefinition.setInstanceSupplier(getConsultationControllerInstanceSupplier());
    return beanDefinition;
  }
}
