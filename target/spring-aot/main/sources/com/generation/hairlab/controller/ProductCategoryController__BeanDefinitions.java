package com.generation.hairlab.controller;

import com.generation.hairlab.service.ProductCategoryService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ProductCategoryController}.
 */
@Generated
public class ProductCategoryController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'productCategoryController'.
   */
  private static BeanInstanceSupplier<ProductCategoryController> getProductCategoryControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ProductCategoryController>forConstructor(ProductCategoryService.class)
            .withGenerator((registeredBean, args) -> new ProductCategoryController(args.get(0)));
  }

  /**
   * Get the bean definition for 'productCategoryController'.
   */
  public static BeanDefinition getProductCategoryControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ProductCategoryController.class);
    beanDefinition.setInstanceSupplier(getProductCategoryControllerInstanceSupplier());
    return beanDefinition;
  }
}
