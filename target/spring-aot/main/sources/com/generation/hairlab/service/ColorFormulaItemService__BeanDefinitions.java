package com.generation.hairlab.service;

import com.generation.hairlab.mapper.ColorFormulaItemMapper;
import com.generation.hairlab.repository.ColorFormulaItemRepository;
import com.generation.hairlab.repository.ColorFormulaRepository;
import com.generation.hairlab.repository.HairDyeRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ColorFormulaItemService}.
 */
@Generated
public class ColorFormulaItemService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'colorFormulaItemService'.
   */
  private static BeanInstanceSupplier<ColorFormulaItemService> getColorFormulaItemServiceInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ColorFormulaItemService>forConstructor(ColorFormulaItemRepository.class, ColorFormulaRepository.class, HairDyeRepository.class, ColorFormulaItemMapper.class)
            .withGenerator((registeredBean, args) -> new ColorFormulaItemService(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'colorFormulaItemService'.
   */
  public static BeanDefinition getColorFormulaItemServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ColorFormulaItemService.class);
    beanDefinition.setInstanceSupplier(getColorFormulaItemServiceInstanceSupplier());
    return beanDefinition;
  }
}
