/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.dao;

import apc.model.Cliente;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Alberto Posso
 */
public interface clienteDao {
    
    public List<Cliente> listarCliente();
    public void newCliente(Cliente cliente);
    public void updateCliente(Cliente cliente);
    public void deleteCliente(Cliente cliente);
    
    // Metodo para factura
    public Cliente obtenerClientePorDui(Session session, String dui) throws Exception;
}
