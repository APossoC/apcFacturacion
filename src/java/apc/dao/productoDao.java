/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.dao;

import apc.model.Producto;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Alberto Posso
 */
public interface productoDao {
    
    public List<Producto> listarProducto();
    public void newProducto(Producto producto);
    public void updateProducto(Producto producto);
    public void deleteProducto(Producto producto);
    
    // Metodo para factura
    public Producto obtenerProductoPorcodBarra(Session session, String codBarra) throws Exception;
    public Producto obtenerProductoPornombreProducto(Session session, String nombreProducto) throws Exception;
    
}
