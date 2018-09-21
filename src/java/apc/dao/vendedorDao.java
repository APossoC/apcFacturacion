/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.dao;

import apc.model.Vendedor;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Alberto Posso
 */
public interface vendedorDao {
    
    public List<Vendedor> listarVendedores();
    public void newVendedor(Vendedor vendedor);
    public void updateVendedor(Vendedor vendedor);
    public void deleteVendedor(Vendedor vendedor);
    
    // Metodo para factura
    public Vendedor obtenerVendedorPorDui(Session session, String dui) throws Exception;
    public Vendedor obtenerVendedorPorcodVendedor(Session session, Integer codVendedor) throws Exception;
    
}
