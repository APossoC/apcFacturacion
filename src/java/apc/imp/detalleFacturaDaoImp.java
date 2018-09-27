/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.imp;

import apc.dao.detalleFacturaDao;
import apc.model.Detallefactura;
import org.hibernate.Session;

/**
 *
 * @author Alberto Posso
 */
public class detalleFacturaDaoImp implements detalleFacturaDao{

    @Override
    public boolean guardarVentaDetalleFactura(Session session, Detallefactura detallefactura) throws Exception {
        session.save(detallefactura);
        return true;
    }
    
}
