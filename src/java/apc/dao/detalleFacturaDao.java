/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.dao;

import apc.model.Detallefactura;
import apc.model.Factura;
import org.hibernate.Session;

/**
 *
 * @author Alberto Posso
 */
public interface detalleFacturaDao {
    
    //Guardar registro en factura
    public boolean guardarVentaDetalleFactura(Session session,Detallefactura detallefactura) throws Exception;
    
}
