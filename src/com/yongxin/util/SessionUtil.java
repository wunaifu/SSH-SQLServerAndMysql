/**
 * 
 */
package com.yongxin.util;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


/**
 * @author Lee 2017年12月22日
 *
 */
public class SessionUtil {
	private static SessionFactory factory;
	/**
	 * 创建session
	 * @session
	 */
	public static Session getSession(){
		Configuration config = new Configuration().configure();
		ServiceRegistryBuilder regbulider=new ServiceRegistryBuilder().applySettings(config.getProperties());   
	    ServiceRegistry reg=regbulider.buildServiceRegistry();  
		factory = config.buildSessionFactory(reg);
		Session session = factory.openSession();
		return session;
		
	}
	/**
	 * 关闭session
	 * @param session
	 */
	public static void closeSession(Session session){
		if(session != null){
			session.close();
		}
	}
}
