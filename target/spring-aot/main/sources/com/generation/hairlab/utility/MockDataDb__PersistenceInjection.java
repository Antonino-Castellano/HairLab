package com.generation.hairlab.utility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.lang.reflect.Field;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.RegisteredBean;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.util.ReflectionUtils;

/**
 * Persistence injection for {@link MockDataDb}.
 */
@Generated
public class MockDataDb__PersistenceInjection {
  /**
   * Get the 'default' {@link EntityManager}.
   */
  public static EntityManager getEntityManager(RegisteredBean registeredBean) {
    EntityManagerFactory entityManagerFactory = EntityManagerFactoryUtils.findEntityManagerFactory((ListableBeanFactory) registeredBean.getBeanFactory(), "");
    return SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory, null, true);
  }

  /**
   * Apply the persistence injection.
   */
  public static MockDataDb apply(RegisteredBean registeredBean, MockDataDb instance) {
    Field field = ReflectionUtils.findField(MockDataDb.class, "entityManager");
    ReflectionUtils.makeAccessible(field);
    ReflectionUtils.setField(field, instance, getEntityManager(registeredBean));
    return instance;
  }
}
