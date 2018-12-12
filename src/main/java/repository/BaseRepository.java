package repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import servlets.HibernateContext;

import java.util.List;

public abstract class BaseRepository<T> {
    private final SessionFactory sessionFactory;
    private final Class<T> type;

    protected BaseRepository(Class<T> type) {
        this.sessionFactory = HibernateContext.getSessionFactory();
        this.type = type;
    }

    public T add(T objectToAdd) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(objectToAdd);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return objectToAdd;

    }

    public T update(T objectToUpdate) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(objectToUpdate);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return objectToUpdate;
    }

    public List<T> get() {
        Session session = sessionFactory.openSession();
        List<T> data = session.createSQLQuery("from " + type.getSimpleName()).getResultList();
        return data;
    }

    public T delete(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        T t = session.get(type, id);
        try {
            transaction = session.beginTransaction();
            if (t == null) {
                return null;
            }
            session.delete(t);
            transaction.commit();


        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return t;
    }


}
