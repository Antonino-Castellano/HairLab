package com.generation.hairlab.controller;

import com.generation.hairlab.service.EmployeeService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link EmployeeController}.
 */
@Generated
public class EmployeeController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'employeeController'.
   */
  private static BeanInstanceSupplier<EmployeeController> getEmployeeControllerInstanceSupplier() {
    return BeanInstanceSupplier.<EmployeeController>forConstructor(EmployeeService.class)
            .withGenerator((registeredBean, args) -> new EmployeeController(args.get(0)));
  }

  /**
   * Get the bean definition for 'employeeController'.
   */
  public static BeanDefinition getEmployeeControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(EmployeeController.class);
    beanDefinition.setInstanceSupplier(getEmployeeControllerInstanceSupplier());
    return beanDefinition;
  }
}
