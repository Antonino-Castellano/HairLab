package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HairProfileMapperImpl}.
 */
@Generated
public class HairProfileMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'hairProfileMapperImpl'.
   */
  public static BeanDefinition getHairProfileMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HairProfileMapperImpl.class);
    beanDefinition.setInstanceSupplier(HairProfileMapperImpl::new);
    return beanDefinition;
  }
}
