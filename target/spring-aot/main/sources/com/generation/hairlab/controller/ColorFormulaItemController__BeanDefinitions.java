package com.generation.hairlab.controller;

import com.generation.hairlab.service.ColorFormulaItemService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ColorFormulaItemController}.
 */
@Generated
public class ColorFormulaItemController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'colorFormulaItemController'.
   */
  private static BeanInstanceSupplier<ColorFormulaItemController> getColorFormulaItemControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ColorFormulaItemController>forConstructor(ColorFormulaItemService.class)
            .withGenerator((registeredBean, args) -> new ColorFormulaItemController(args.get(0)));
  }

  /**
   * Get the bean definition for 'colorFormulaItemController'.
   */
  public static BeanDefinition getColorFormulaItemControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ColorFormulaItemController.class);
    beanDefinition.setInstanceSupplier(getColorFormulaItemControllerInstanceSupplier());
    return beanDefinition;
  }
}
