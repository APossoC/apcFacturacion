/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.bean;

import apc.dao.clienteDao;
import apc.dao.productoDao;
import apc.imp.clienteDaoImp;
import apc.imp.productoDaoImp;
import apc.model.Cliente;
import apc.model.Detallefactura;
import apc.model.Factura;
import apc.model.Producto;
import apc.util.HibernateUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Alberto Posso
 */
@Named(value = "facturaBean")
@ViewScoped
public class facturaBean implements Serializable {

    Session session = null;
    Transaction transaction;

    //Variables para busquedas de cliente
    private Cliente cliente;
    private String dui;

    //Variables para busquedas de producto
    private Producto producto;
    private String codBarra;

    private Integer cantidadProducto;
    private String productoSeleccionado;
    private Factura factura;

    private List<Detallefactura> listaDetalleFactura;

    public facturaBean() {
        this.factura = new Factura();
        this.listaDetalleFactura = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;

    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<Detallefactura> getListaDetalleFactura() {
        return listaDetalleFactura;
    }

    public void setListaDetalleFactura(List<Detallefactura> listaDetalleFactura) {
        this.listaDetalleFactura = listaDetalleFactura;
    }

    // metodo para buscar los datos de los clientes buscado por  dialogCliente
    public void agregarDatosClientes(String dui) {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            clienteDao cDao = new clienteDaoImp();
            this.transaction = this.session.beginTransaction();

            //obtener los datos del clente en la variable objeto cliente, segun codigo cliente
            this.cliente = cDao.obtenerClientePorDui(this.session, dui);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del cliente agregados"));

        } catch (Exception e) {
            if (this.transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    //Metodo para pedir cantidad de producto.
    public void pedirCantidadProducto(String codBarra) {
        this.productoSeleccionado = codBarra;
    }

    // metodo para agregar los datos de los clientes buscado por dui
    public void agregarDatosClientesPorDui() {
        this.session = null;
        this.transaction = null;
        try {
            if (this.dui.equals("")) {
                return;
            }
            this.session = HibernateUtil.getSessionFactory().openSession();
            clienteDao cDao = new clienteDaoImp();
            this.transaction = this.session.beginTransaction();

            //obtener los datos del clente en la variable objeto cliente, segun codigo cliente
            this.cliente = cDao.obtenerClientePorDui(this.session, this.dui);
            //Evaluar si se lleno el objeto cliente
            if (this.cliente != null) {
                this.dui = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del cliente agregados"));
            } else {
                this.dui = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrecto", "Cliente no encontrado"));
            }
            this.transaction.commit();
        } catch (Exception e) {
            if (this.transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    // metodo para buscar los datos de los productos buscado por  dialogProducto
    public void agregarDatosProductos() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            productoDao pDao = new productoDaoImp();
            this.transaction = this.session.beginTransaction();

            //obtener los datos del producto en la variable objeto prodcuto, segun codbarra
            this.producto = pDao.obtenerProductoPorcodBarra(this.session, this.productoSeleccionado);

            //asgnacion de valores a datelle factura
            this.listaDetalleFactura.add(new Detallefactura(null, null, this.producto.getCodBarra(),
                    this.producto.getNombreProducto(), this.cantidadProducto, this.producto.getPrecioVenta(),
                    this.cantidadProducto * this.producto.getPrecioVenta()));
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto agregados al detalle"));
            
            //Llamada al metodo totalFacturaventa
            this.totalFacturaVenta();
            
        } catch (Exception e) {
            if (this.transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    // metodo para agregar los datos de los productos buscado por codbarra
    public void agregarDatosProdcutoPorcodBarra() {
        this.session = null;
        this.transaction = null;
        try {
            if (this.codBarra.equals("")) {
                return;
            }
            this.session = HibernateUtil.getSessionFactory().openSession();
            productoDao pDao = new productoDaoImp();
            this.transaction = this.session.beginTransaction();

            //obtener los datos del prolucto en la variable objeto producto, segun codBarra
            this.producto = pDao.obtenerProductoPorcodBarra(this.session, this.productoSeleccionado);

            //Evaluar si se lleno el objeto cliente
            if (this.producto != null) {
                this.listaDetalleFactura.add(new Detallefactura(null, null, this.producto.getCodBarra(),
                        this.producto.getNombreProducto(), this.cantidadProducto, this.producto.getPrecioVenta(),
                        this.cantidadProducto* this.producto.getPrecioVenta()));

                this.codBarra = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto agregados al detalle"));
                this.cantidadProducto = null;

            } else {
                this.codBarra = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrecto", "Producto no encontrado"));
            }
            this.transaction.commit();
              this.totalFacturaVenta();
        } catch (Exception e) {
            if (this.transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }

    }
    
    //Metodo para calcualr el total a vender en factura
    public void totalFacturaVenta(){
    Float totalFacturaVenta = new Float("0");
    
        try {
            for(Detallefactura item :listaDetalleFactura){
            Float totalVentaPorProducto = new BigDecimal(item.getCantidad()).
                                                    multiply((item.getPrecioVenta()));
            item.setTotal(totalVentaPorProducto);
            totalFacturaVenta= totalFacturaVenta + totalVentaPorProducto;
            }
            this.factura.setTotalVenta(totalFacturaVenta);
            
        } catch (Exception e) {            
            System.out.println(e.getMessage());
        }
    }
    
    
}
