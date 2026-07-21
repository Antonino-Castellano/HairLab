package com.generation.hairlab.controller;

import com.generation.hairlab.service.ColorFormulaService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ColorFormulaController}.
 */
@Generated
public class ColorFormulaController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'colorFormulaController'.
   */
  private static BeanInstanceSupplier<ColorFormulaController> getColorFormulaControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ColorFormulaController>forConstructor(ColorFormulaService.class)
            .withGenerator((registeredBean, args) -> new ColorFormulaController(args.get(0)));
  }

  /**
   * Get the bean definition for 'colorFormulaController'.
   */
  public static BeanDefinition getColorFormulaControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ColorFormulaController.class);
    beanDefinition.setInstanceSupplier(getColorFormulaControllerInstanceSupplier());
    return beanDefinition;
  }
}
