/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.bean;

import apc.dao.clienteDao;
import apc.imp.clienteDaoImp;
import apc.model.Cliente;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Alberto Posso
 */
@Named(value = "clienteBean")
@ViewScoped
public class clienteBean implements Serializable {

    private List<Cliente> listaClientes;
    private Cliente cliente;

    public clienteBean() {
        cliente = new Cliente();
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaClientes() {
        clienteDao cDao = new clienteDaoImp();
        listaClientes = cDao.listarCliente();
        return listaClientes;
    }

    public void prepararNuevoCliente() {
        cliente = new Cliente();
    }

    public void nuevoCliente() {
        clienteDao cDao = new clienteDaoImp();
        cDao.newCliente(cliente);
    }

    public void modificarCliente() {
        clienteDao cDao = new clienteDaoImp();
        cDao.updateCliente(cliente);
        cliente = new Cliente();
    }

    public void eliminarCliente() {
        clienteDao cDao = new clienteDaoImp();
        cDao.deleteCliente(cliente);
        cliente = new Cliente();
    }

}
