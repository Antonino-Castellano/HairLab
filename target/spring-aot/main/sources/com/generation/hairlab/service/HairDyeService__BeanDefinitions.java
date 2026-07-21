package com.generation.hairlab.service;

import com.generation.hairlab.mapper.HairDyeMapper;
import com.generation.hairlab.repository.HairDyeRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HairDyeService}.
 */
@Generated
public class HairDyeService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'hairDyeService'.
   */
  private static BeanInstanceSupplier<HairDyeService> getHairDyeServiceInstanceSupplier() {
    return BeanInstanceSupplier.<HairDyeService>forConstructor(HairDyeRepository.class, HairDyeMapper.class)
            .withGenerator((registeredBean, args) -> new HairDyeService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'hairDyeService'.
   */
  public static BeanDefinition getHairDyeServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HairDyeService.class);
    beanDefinition.setInstanceSupplier(getHairDyeServiceInstanceSupplier());
    return beanDefinition;
  }
}
