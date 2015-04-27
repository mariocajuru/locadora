/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.locadora.util;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 * 
 * @author DESENVJAVA01
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties())
            .build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			
			/*ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);*/
			
			//http://www.guj.com.br/4797-hibernatecfgxml--hibernate-42--sql-server-2008
			
			/*SchemaExport se = new SchemaExport(configuration);  
	        se.create(true, true); */ 

			// sessionFactory = new
			// AnnotationConfiguration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Criação do SessionFactory falhou (HibernateUtil)." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
