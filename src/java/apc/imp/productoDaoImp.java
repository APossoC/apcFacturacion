/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.imp;

import apc.model.Producto;
import apc.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Alberto Posso
 */
public class productoDaoImp implements apc.dao.productoDao {

    @Override
    public List<Producto> listarProducto() {
        List<Producto> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Producto";
        try {
            lista = session.createQuery(hql).list();
            t.commit();
            session.close();
        } catch (Exception e) {
            t.rollback();
        }
        return lista;
    }

    @Override
    public void newProducto(Producto producto) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(producto);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void updateProducto(Producto producto) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(producto);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteProducto(Producto producto) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(producto);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Producto obtenerProductoPorcodBarra(Session session, String codBarra) throws Exception {
        String hql = "FROM Producto WHERE codBarra=:codBarra";
        Query q = session.createQuery(hql);
        q.setParameter("codBarra", codBarra);
        return (Producto) q.uniqueResult();
    }

    @Override
    public Producto obtenerProductoPornombreProducto(Session session, Integer nombreProducto) throws Exception {
        String hql="FROM Cliente WHERE nombreProducto=:nombreProducto";
        Query q = session.createQuery(hql);
        q.setParameter("nombreProducto", nombreProducto);
        return (Producto) q.uniqueResult();
    }

}
