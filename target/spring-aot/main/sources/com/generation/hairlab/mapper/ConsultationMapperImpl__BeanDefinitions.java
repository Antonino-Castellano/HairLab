package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ConsultationMapperImpl}.
 */
@Generated
public class ConsultationMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'consultationMapperImpl'.
   */
  public static BeanDefinition getConsultationMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ConsultationMapperImpl.class);
    beanDefinition.setInstanceSupplier(ConsultationMapperImpl::new);
    return beanDefinition;
  }
}
