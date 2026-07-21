package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ColorFormulaMapperImpl}.
 */
@Generated
public class ColorFormulaMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'colorFormulaMapperImpl'.
   */
  public static BeanDefinition getColorFormulaMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ColorFormulaMapperImpl.class);
    beanDefinition.setInstanceSupplier(ColorFormulaMapperImpl::new);
    return beanDefinition;
  }
}
