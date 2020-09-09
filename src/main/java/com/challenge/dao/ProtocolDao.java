package com.challenge.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;

import com.challenge.entitys.Protocol;
import com.challenge.util.HibernateUtil;

/**
 * 
 * @author Ricardo Braga
 *
 */
public class ProtocolDao {
	public void saveProtocol(Protocol protocol) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			session.save(protocol);
			transaction.commit();
			
			session.close();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	
	public List<Protocol> getAllProtocols() {
		List<Protocol> list;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			list = session.createQuery("from Protocol", Protocol.class).list();
			session.close();
			return list;
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
			list = new ArrayList<Protocol>();
			return list;
		}
	}
}
