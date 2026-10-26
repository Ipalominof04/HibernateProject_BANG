package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/*
 * Aquí desenvolupem les funcions que hem definit a la interfície
 */

public class GenericDao<T, ID extends Serializable> implements IGenericDao<T, ID> {

	protected SessionFactory sessionFactory;

	// Utilitzem el constructor per crear la session
	public GenericDao() {
		sessionFactory = SessionManager.getSessionFactory();
	}

	/*
	 * Ara si, desenvolupem el codi de manera genèrica ja que totes les classes
	 * necessiten aquests mètodes
	 */
	@Override
	public void saveOrUpdate(T entity) {
		Session session = sessionFactory.getCurrentSession();

		try {
			// Tota operació de modificació necessita una transacció
			session.beginTransaction();
			session.saveOrUpdate(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			handleException(session, e);
		}

	}

	@Override
	public T get(ID id) {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			// Recuperem la classe mitjançant getClass
			T entity = (T) session.get(getEntityClass(), id);
			session.getTransaction().commit();
			return entity;
		} catch (HibernateException e) {
			handleException(session, e);
			return null;
		}
	}

	@Override
	public void delete(ID id) {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			// Reciclem codi
			T entity = get(id);
			if (entity != null) {
				session.remove(entity);
			} else {
				System.out.println("No existeix aquest ID");
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			handleException(session, e);
		}

	}

	@Override
	public void delete(T entity) {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			session.remove(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			handleException(session, e);
		}

	}

	@Override
	public List<T> list() {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			// Fem el SELECT ALL
			List<T> entities = session.createQuery("FROM " + getEntityClass().getName(), getEntityClass())
					.getResultList();
			session.getTransaction().commit();
			return entities;
		} catch (HibernateException e) {
			System.err.println("Error: ");
			handleException(session, e);
			return null;
		}
	}

	/**
	 * Mètode auxiliar que utilitza reflection per obtenir la classe de l'entitat
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Mètode auxiliar per gestionar les excepcions d'Hibernate
	 */
	private void handleException(Session session, HibernateException e) {
		e.printStackTrace();
		if (session != null && session.getTransaction() != null) {
			session.getTransaction().rollback();
		}
	}
	


}
