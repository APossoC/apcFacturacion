/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.imp;

import apc.dao.vendedorDao;
import apc.model.Vendedor;
import apc.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Alberto Posso
 */
public class vendedorDaoImp implements vendedorDao {

    @Override
    public List<Vendedor> listarVendedores() {
        List<Vendedor> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Vendedor";
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
    public void newVendedor(Vendedor vendedor) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(vendedor);
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
    public void updateVendedor(Vendedor vendedor) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(vendedor);
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
    public void deleteVendedor(Vendedor vendedor) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(vendedor);
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
    public Vendedor obtenerVendedorPorDui(Session session, String dui) throws Exception {
        String hql = "FROM Vendedor WHERE dui=:dui";
        Query q = session.createQuery(hql);
        q.setParameter("dui", dui);
        return (Vendedor) q.uniqueResult();
    }

    @Override
    public Vendedor obtenerVendedorPorcodVendedor(Session session, Integer codVendedor) throws Exception {
        String hql = "FROM Vendedor WHERE codVendedor=:codVendedor";
        Query q = session.createQuery(hql);
        q.setParameter("codVendedor", codVendedor);
        return (Vendedor) q.uniqueResult();
    }
}


