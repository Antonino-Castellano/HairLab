package com.generation.hairlab.service;

import com.generation.hairlab.mapper.SalonProductMapper;
import com.generation.hairlab.repository.ProductCategoryRepository;
import com.generation.hairlab.repository.SalonProductRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SalonProductService}.
 */
@Generated
public class SalonProductService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'salonProductService'.
   */
  private static BeanInstanceSupplier<SalonProductService> getSalonProductServiceInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<SalonProductService>forConstructor(SalonProductRepository.class, ProductCategoryRepository.class, SalonProductMapper.class)
            .withGenerator((registeredBean, args) -> new SalonProductService(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'salonProductService'.
   */
  public static BeanDefinition getSalonProductServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SalonProductService.class);
    beanDefinition.setInstanceSupplier(getSalonProductServiceInstanceSupplier());
    return beanDefinition;
  }
}
