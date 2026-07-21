package com.generation.hairlab.service;

import com.generation.hairlab.mapper.HairProfileMapper;
import com.generation.hairlab.repository.CustomerRepository;
import com.generation.hairlab.repository.HairProfileRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HairProfileService}.
 */
@Generated
public class HairProfileService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'hairProfileService'.
   */
  private static BeanInstanceSupplier<HairProfileService> getHairProfileServiceInstanceSupplier() {
    return BeanInstanceSupplier.<HairProfileService>forConstructor(HairProfileRepository.class, CustomerRepository.class, HairProfileMapper.class)
            .withGenerator((registeredBean, args) -> new HairProfileService(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'hairProfileService'.
   */
  public static BeanDefinition getHairProfileServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HairProfileService.class);
    beanDefinition.setInstanceSupplier(getHairProfileServiceInstanceSupplier());
    return beanDefinition;
  }
}
