/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.imp;

import apc.dao.facturaDao;
import apc.model.Factura;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Alberto Posso
 */
public class facturaDaoImp implements facturaDao{

    @Override
    public Factura obtenerUltimoRegristro(Session session) throws Exception {
        String hql="FROM Factura ORDER BY codFactura DESC";
        Query q = session.createQuery(hql).setMaxResults(1);
        return (Factura) q.uniqueResult();        
    }

    @Override
    public Long obtenerTotalRegistrosEnFactura(Session session) {
       String hql="SELECT COUNT(*) FROM Factura";
       Query q = session.createQuery(hql);
       return (Long) q.uniqueResult();
    }

    @Override
    public boolean guardarVentaFactura(Session session, Factura factura) throws Exception {
        session.save(factura);
        return true;
    }
    
}
