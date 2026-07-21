package com.generation.hairlab.mapper;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ProductCategoryMapperImpl}.
 */
@Generated
public class ProductCategoryMapperImpl__BeanDefinitions {
  /**
   * Get the bean definition for 'productCategoryMapperImpl'.
   */
  public static BeanDefinition getProductCategoryMapperImplBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ProductCategoryMapperImpl.class);
    beanDefinition.setInstanceSupplier(ProductCategoryMapperImpl::new);
    return beanDefinition;
  }
}
