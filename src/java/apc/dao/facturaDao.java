/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.dao;

import apc.model.Factura;
import org.hibernate.Session;

/**
 *
 * @author Alberto Posso
 */
public interface facturaDao {
    
    public Factura obtenerUltimoRegristro(Session session) throws Exception;
    
    //consultar total registros si hay o no
    public Long obtenerTotalRegistrosEnFactura(Session session);
    
    //Guardar registro en factura
    public boolean guardarVentaFactura(Session session,Factura factura) throws Exception;
    
    //
}
