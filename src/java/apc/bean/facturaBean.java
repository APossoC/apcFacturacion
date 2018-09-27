/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apc.bean;

import apc.dao.clienteDao;
import apc.dao.detalleFacturaDao;
import apc.dao.facturaDao;
import apc.dao.productoDao;
import apc.imp.clienteDaoImp;
import apc.imp.detalleFacturaDaoImp;
import apc.imp.facturaDaoImp;
import apc.imp.productoDaoImp;
import apc.model.Cliente;
import apc.model.Detallefactura;
import apc.model.Factura;
import apc.model.Producto;
import apc.model.Vendedor;
import apc.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.RowEditEvent;

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
    private String codigoBarra;

    private String cantidadProducto;
    private String productoSeleccionado;
    private Factura factura;

    private Long numeroFactura;

    private Float totalVentaFactura;

    private Vendedor vendedor;

    private List<Detallefactura> listaDetalleFactura;

    public facturaBean() {
        this.factura = new Factura();
        this.listaDetalleFactura = new ArrayList<>();
        this.vendedor = new Vendedor();
        this.cliente = new Cliente();
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
        return codigoBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codigoBarra = codBarra;

    }

    public String getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(String cantidadProducto) {
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

    public Long getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Float getTotalVentaFactura() {
        return totalVentaFactura;
    }

    public void setTotalVentaFactura(Float totalVentaFactura) {
        this.totalVentaFactura = totalVentaFactura;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
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

            if (!(cantidadProducto.matches("[0-9]*")) || this.cantidadProducto.equals("0") || this.cantidadProducto.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cantidad incorrecta"));
                this.cantidadProducto = "";
            } else {
                this.session = HibernateUtil.getSessionFactory().openSession();
                productoDao pDao = new productoDaoImp();
                this.transaction = this.session.beginTransaction();

                //obtener los datos del producto en la variable objeto prodcuto, segun codbarra
                this.producto = pDao.obtenerProductoPorcodBarra(this.session, this.productoSeleccionado);

                //asgnacion de valores a datelle factura
                this.listaDetalleFactura.add(new Detallefactura(null, null, this.producto.getCodBarra(),
                        this.producto.getNombreProducto(), Integer.parseInt(this.cantidadProducto), this.producto.getPrecioVenta(),
                        Integer.parseInt(this.cantidadProducto) * this.producto.getPrecioVenta()));
                this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto agregados al detalle"));
                this.cantidadProducto = "";
                //Llamada al metodo totalFacturaventa
                this.totalFacturaVenta();
            }
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
    public void totalFacturaVenta() {
        this.totalVentaFactura = new Float("0");

        try {
            for (Detallefactura item : listaDetalleFactura) {
                Float totalVentaPorProducto = item.getCantidad() * item.getPrecioVenta();
                item.setTotal(totalVentaPorProducto);
                totalVentaFactura = totalVentaFactura + totalVentaPorProducto;
            }
            this.factura.setTotalVenta(totalVentaFactura);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //metodo para quitar producto de detallefactura

    public void quitarProductoDetalleFactura(String codBarrra, Integer filaSeleccionada) {
        try {
            int i = 0;
            for (Detallefactura item : this.listaDetalleFactura) {
                if (item.getCodBarra().equals(codBarrra) && filaSeleccionada.equals(i)) {
                    this.listaDetalleFactura.remove(i);
                    break;
                }
                i++;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Correcto", "Producto retirado al detalle"));
            //invocar el metodo calcular factura, para actuliar el total a vender
            this.totalFacturaVenta();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Incorrecto", "Error"));
        }
    }

    //metodos para editar la cantidad en la tabla detalle factura
    public void onRowEdit(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se modifico la cantidad"));
        this.totalFacturaVenta();
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "No se hizo cambio"));
    }

    //metodo para generar numero facvtura
    public void numeracionFactura() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            facturaDao fDao = new facturaDaoImp();

            //validar si hay registros en tabla factura
            this.numeroFactura = fDao.obtenerTotalRegistrosEnFactura(this.session);
            if (this.numeroFactura <= 0 || this.numeroFactura == null) {
                this.numeroFactura = Long.valueOf("1");
            } else {
                //recuperar ultimo registro de tabla factura
                this.factura = fDao.obtenerUltimoRegristro(this.session);
                this.numeroFactura = Long.valueOf(this.factura.getNumeroFactura() + 1);

                //limpiar variable venta factura
                this.totalVentaFactura = new Float("0");

            }

            this.transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (this.transaction != null) {
                this.transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }

        //metodo para limpiar la factura
    }

    public void limpiarFactura() {
        this.cliente = new Cliente();
        this.factura = new Factura();
        this.listaDetalleFactura = new ArrayList<>();
        this.numeroFactura = null;
        this.totalVentaFactura = null;
        //invocar al metodo para desactivar los botone
        this.disableButton();
    }

    // metodo para guardar venta
    public void guardarVenta() {
        this.session = null;
        this.transaction = null;
        this.vendedor.setCodVendedor(2);
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            productoDao pDao = new productoDaoImp();
            facturaDao fDao = new facturaDaoImp();
            detalleFacturaDao dFDao = new detalleFacturaDaoImp();

            this.transaction = this.session.beginTransaction();

            //datos para guardan en factura
            this.factura.setNumeroFactura(this.numeroFactura.intValue());
            this.factura.setCliente(this.cliente);
            this.factura.setVendedor(this.vendedor);

            //insert en tb factura
            fDao.guardarVentaFactura(this.session, this.factura);

            //recueprar el ultimo registro de la tb factura
            this.factura = fDao.obtenerUltimoRegristro(this.session);

            //recorremos el Alist para guarda cada registro en bd
            for (Detallefactura item : listaDetalleFactura) {
                this.producto = pDao.obtenerProductoPorcodBarra(this.session, item.getCodBarra());
                item.setFactura(this.factura);
                item.setProducto(this.producto);

                //insert en tb detallefactura 
                dFDao.guardarVentaDetalleFactura(this.session, item);

            }

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Venta Registrada"));

            this.limpiarFactura();
            this.disableButton();
        } catch (Exception e) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            if (this.session != null) {
                this.session.close();

            }
        }
    }
    
    //metodos para activar y desctivar los controles en fc
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }
    public void enabledButton(){
        enabled = true;
    }
    public void disableButton(){
        enabled = false;
    }
    
}
