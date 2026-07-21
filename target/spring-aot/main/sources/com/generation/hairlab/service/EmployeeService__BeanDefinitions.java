package com.generation.hairlab.service;

import com.generation.hairlab.mapper.EmployeeMapper;
import com.generation.hairlab.repository.EmployeeRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link EmployeeService}.
 */
@Generated
public class EmployeeService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'employeeService'.
   */
  private static BeanInstanceSupplier<EmployeeService> getEmployeeServiceInstanceSupplier() {
    return BeanInstanceSupplier.<EmployeeService>forConstructor(EmployeeRepository.class, EmployeeMapper.class)
            .withGenerator((registeredBean, args) -> new EmployeeService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'employeeService'.
   */
  public static BeanDefinition getEmployeeServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(EmployeeService.class);
    beanDefinition.setInstanceSupplier(getEmployeeServiceInstanceSupplier());
    return beanDefinition;
  }
}
