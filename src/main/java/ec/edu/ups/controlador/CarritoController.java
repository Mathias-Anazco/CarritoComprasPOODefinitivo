package ec.edu.ups.controlador;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.vista.CarritoAnadirView;
import ec.edu.ups.vista.CarritoListarView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.List;


public class CarritoController {
    private final CarritoDAO carritoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final Carrito carritoActual;
    private final Usuario usuario;
    private final ProductoDAO productoDAO;
    private final CarritoListarView carritoListarView;


    private DefaultTableModel modelo;

    public CarritoController(CarritoDAO carritoDAO, CarritoAnadirView carritoAnadirView, ProductoDAO productoDAO, CarritoListarView carritoListarView, Usuario usuario) {
        this.carritoDAO = carritoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoActual = new Carrito();
        this.productoDAO = productoDAO;
        this.usuario = new Usuario(); // Aquí deberías obtener el usuario actual de tu aplicación
        this.carritoListarView = carritoListarView;
        configurarEventos();
    }

    public void configurarEventos(){
        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProductoAlCarrito();
            }
        });
        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });
        carritoAnadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vaciarCarrito();
            }
        });
        carritoListarView.getBtnMostrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritos();
            }
        });
        carritoListarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTodosLosCarritos();
            }
        });

    }
    private void guardarCarrito(){
        if(carritoActual.estaVacio()){
            carritoAnadirView.mostrarMensaje("El carrito está vacío, no se puede guardar.");
            return;
        }
        carritoActual.setUsuario(usuario);
        carritoActual.setFechaCreacion(new GregorianCalendar());
        carritoDAO.crear(carritoActual);
        carritoAnadirView.mostrarMensaje("Carrito guardado exitosamente con código: " + carritoActual.getCodigo());

        carritoActual.vaciarCarrito();
        agregarProductoAlCarrito();
        cargarProductos();
        carritoAnadirView.limpiarCampos();
    }
    private void agregarProductoAlCarrito(){
        int codigoProducto = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigoProducto);
        int cantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        carritoActual.agregarProducto(producto, cantidad);

        cargarProductos();
        mostrarTotal();

    }
    private void cargarProductos(){
        List<ItemCarrito> items = carritoActual.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items){
            modelo.addRow(new Object[]{ item.getProducto().getCodigo(),
                                         item.getProducto().getNombre(),
                                         item.getProducto().getPrecio(),
                                         item.getCantidad(),
                                        item.getProducto().getPrecio() * item.getCantidad()});
        }
    }
    private  void mostrarTotal(){
        String subtotal = String.valueOf(carritoActual.calcularTotal());
        String iva = String.valueOf(carritoActual.calcularIVA());
        String total = String.valueOf(carritoActual.calcularTotalConIVA());
        carritoAnadirView.getTxtSubtotal().setText(subtotal);
        carritoAnadirView.getTxtIva().setText(iva);
        carritoAnadirView.getTxtTotal().setText(total);

    }
    public void vaciarCarrito() {
        carritoActual.vaciarCarrito();
        cargarProductos();
        mostrarTotal();
    }
    public void buscarCarritos() {
        String codigo = carritoListarView.getTxtCarrito().getText();
        if( !codigo.isEmpty()){
            int codigoCarrito = Integer.parseInt(carritoListarView.getTxtCarrito().getText());
            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null){
                carritoListarView.cargarDatos(List.of(carrito));
            } else {
                carritoListarView.mostrarMensaje("No se encontró el carrito");
                carritoListarView.limpiarCampos();
            }
        }
    }
    public void mostrarTodosLosCarritos() {
        List<Carrito> carritos = carritoDAO.listarTodos();
        if (carritos.isEmpty()) {
            carritoListarView.mostrarMensaje("No hay carritos registrados.");
            carritoListarView.limpiarCampos();
        } else {
            carritoListarView.cargarDatos(carritos);
        }
    }
}
