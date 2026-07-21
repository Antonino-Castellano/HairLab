package com.generation.hairlab.service;

import com.generation.hairlab.mapper.ColorFormulaMapper;
import com.generation.hairlab.repository.AppointmentItemRepository;
import com.generation.hairlab.repository.ColorFormulaRepository;
import com.generation.hairlab.repository.ConsultationRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ColorFormulaService}.
 */
@Generated
public class ColorFormulaService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'colorFormulaService'.
   */
  private static BeanInstanceSupplier<ColorFormulaService> getColorFormulaServiceInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ColorFormulaService>forConstructor(ColorFormulaRepository.class, ConsultationRepository.class, AppointmentItemRepository.class, ColorFormulaMapper.class)
            .withGenerator((registeredBean, args) -> new ColorFormulaService(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'colorFormulaService'.
   */
  public static BeanDefinition getColorFormulaServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ColorFormulaService.class);
    beanDefinition.setInstanceSupplier(getColorFormulaServiceInstanceSupplier());
    return beanDefinition;
  }
}
