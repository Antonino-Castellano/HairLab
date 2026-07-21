package com.generation.hairlab.service;

import com.generation.hairlab.mapper.ConsultationMapper;
import com.generation.hairlab.repository.AppointmentRepository;
import com.generation.hairlab.repository.ConsultationRepository;
import com.generation.hairlab.repository.CustomerRepository;
import com.generation.hairlab.repository.EmployeeRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ConsultationService}.
 */
@Generated
public class ConsultationService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'consultationService'.
   */
  private static BeanInstanceSupplier<ConsultationService> getConsultationServiceInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ConsultationService>forConstructor(ConsultationRepository.class, CustomerRepository.class, EmployeeRepository.class, AppointmentRepository.class, ConsultationMapper.class)
            .withGenerator((registeredBean, args) -> new ConsultationService(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4)));
  }

  /**
   * Get the bean definition for 'consultationService'.
   */
  public static BeanDefinition getConsultationServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ConsultationService.class);
    beanDefinition.setInstanceSupplier(getConsultationServiceInstanceSupplier());
    return beanDefinition;
  }
}
