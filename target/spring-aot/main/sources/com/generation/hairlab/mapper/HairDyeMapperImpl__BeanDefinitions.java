package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HairDyeMapperImpl}.
 */
@Generated
public class HairDyeMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'hairDyeMapperImpl'.
   */
  public static BeanDefinition getHairDyeMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HairDyeMapperImpl.class);
    beanDefinition.setInstanceSupplier(HairDyeMapperImpl::new);
    return beanDefinition;
  }
}
