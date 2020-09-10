package com.challenge.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;

import com.challenge.util.HibernateUtil;

/**
 * 
 * @author Ricardo Braga
 *
 */
public class GenericDao {
	
	public <E> void saveProtocol(E entity) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			session.save(entity);
			transaction.commit();

			session.close();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}


}
