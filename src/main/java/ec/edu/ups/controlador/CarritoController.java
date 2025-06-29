package ec.edu.ups.controlador;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.vista.*;

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
    private CarritoDetalleView carritoDetalleView;
    private final CarritoListarView carritoListarView;
    private final CarritoModificarView carritoModificarView;
    private final CarritoEliminarView carritoEliminarView;


    private DefaultTableModel modelo;

    public CarritoController(CarritoDAO carritoDAO, CarritoAnadirView carritoAnadirView, ProductoDAO productoDAO, CarritoListarView carritoListarView, Usuario usuario, CarritoModificarView carritoModificarView, CarritoEliminarView carritoEliminarView) {
        this.carritoDAO = carritoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoActual = new Carrito();
        this.productoDAO = productoDAO;
        this.usuario = new Usuario();// Aquí deberías obtener el usuario actual de tu aplicación
        this.carritoListarView = carritoListarView;
        this.carritoModificarView = carritoModificarView;
        this.carritoEliminarView = carritoEliminarView;
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
        carritoListarView.getBtnMostrarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDetalle();
            }
        });
        carritoModificarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoParaModificar();
            }
        });
        carritoModificarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarCarrito();
            }
        });
        carritoModificarView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoModificarView.limpiarCampos();
            }
        });
        carritoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoParaEliminar();
            }
        });
        carritoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCarrito();
            }
        });

    }

    private void eliminarCarrito() {
        int codigo = Integer.parseInt(carritoEliminarView.getTxtCodigo().getText());
        carritoDAO.eliminar(codigo);
        carritoEliminarView.mostrarMensaje("Carrito eliminado exitosamente.");
        carritoEliminarView.limpiarCampos();
    }



    private void buscarCarritoParaEliminar() {
        String codigo = carritoEliminarView.getTxtCodigo().getText();
        if (!codigo.isEmpty()) {
            int codigoCarrito = Integer.parseInt(codigo);
            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                carritoEliminarView.cargarDatos(carrito);
                carritoEliminarView.setVisible(true);
                carritoEliminarView.moveToFront();
                carritoEliminarView.requestFocusInWindow();

            } else {
                carritoEliminarView.mostrarMensaje("No se encontró el carrito con código: " + codigoCarrito);
                carritoEliminarView.limpiarCampos();
            }
        } else {
            carritoEliminarView.mostrarMensaje("Ingrese un código de carrito válido");
        }
    }

    private void modificarCarrito() {
        int filaSeleccionada = carritoModificarView.getTblProductos().getSelectedRow();

        if (filaSeleccionada == -1) {
            carritoModificarView.mostrarMensaje("Selecciona un producto de la tabla.");
            return;
        }

        String textoCantidad = carritoModificarView.getCbxCantidad().getSelectedItem().toString();

        if (!textoCantidad.matches("\\d+")) {
            carritoModificarView.mostrarMensaje("Ingrese un número válido.");
            return;
        }

        int cantidad = Integer.parseInt(textoCantidad);
        if (cantidad < 1 || cantidad > 20) {
            carritoModificarView.mostrarMensaje("La cantidad debe estar entre 1 y 20.");
            return;
        }

        int codigoCarrito = Integer.parseInt(carritoModificarView.getTxtCarrito().getText());
        Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);

        if (carrito == null) {
            carritoModificarView.mostrarMensaje("No se encontró el carrito.");
            return;
        }

        int codigoProducto = (int) carritoModificarView.getModelo().getValueAt(filaSeleccionada, 0);

        for (ItemCarrito item : carrito.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                item.setCantidad(cantidad);
                break;
            }
        }

        carritoModificarView.cargarDatos(carrito);

        carritoModificarView.getTxtSubtotal().setText(String.format("%.2f", carrito.calcularTotal()));
        carritoModificarView.getTxtIVA().setText(String.format("%.2f", carrito.calcularIVA()));
        carritoModificarView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotalConIVA()));

        carritoModificarView.mostrarMensaje("Cantidad actualizada correctamente.");
    }


    private void buscarCarritoParaModificar() {
        String codigo = carritoModificarView.getTxtCarrito().getText();
        if (!codigo.isEmpty()) {
            int codigoCarrito = Integer.parseInt(codigo);
            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                carritoModificarView.getTxtSubtotal().setText(String.format("%.2f", carrito.calcularTotal()));
                carritoModificarView.getTxtIVA().setText(String.format("%.2f", carrito.calcularIVA()));
                carritoModificarView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotalConIVA()));

                carritoModificarView.cargarDatos(carrito);
                carritoModificarView.setVisible(true);
                carritoModificarView.moveToFront();
                carritoModificarView.requestFocusInWindow();

            } else {
                carritoModificarView.mostrarMensaje("No se encontró el carrito con código: " + codigoCarrito);
                carritoModificarView.limpiarCampos();
            }
        } else {
            carritoModificarView.mostrarMensaje("Ingrese un código de carrito válido");
        }
    }

    private void mostrarDetalle() {
        int filaSeleccionada = carritoListarView.getTblProductos().getSelectedRow();

        if (filaSeleccionada != -1) {
            int codigoCarrito = (int) carritoListarView.getModelo().getValueAt(filaSeleccionada, 0);

            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                if (carritoDetalleView == null || carritoDetalleView.isClosed()) {
                    carritoDetalleView = new CarritoDetalleView();
                    carritoListarView.getDesktopPane().add(carritoDetalleView);
                }

                carritoDetalleView.cargarDatos(carrito);

                carritoDetalleView.getTxtSubtotal().setText(String.format("%.2f", carrito.calcularTotal()));
                carritoDetalleView.getTxtIVA().setText(String.format("%.2f", carrito.calcularIVA()));
                carritoDetalleView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotalConIVA()));

                carritoDetalleView.setVisible(true);
                carritoDetalleView.moveToFront();
                carritoDetalleView.requestFocusInWindow();

            } else {
                carritoDetalleView.mostrarMensaje("Carrito no encontrado");
            }
        } else {
            carritoDetalleView.mostrarMensaje("Seleccione un carrito de la tabla");
        }
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
        if (!codigo.isEmpty()) {
            int codigoCarrito = Integer.parseInt(codigo);
            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                carritoListarView.cargarDatos(List.of(carrito));
                carritoListarView.setVisible(true);
                carritoListarView.moveToFront();
                carritoListarView.requestFocusInWindow();
            } else {
                carritoListarView.mostrarMensaje("No se encontró el carrito con código: " + codigoCarrito);
                carritoListarView.limpiarCampos();
            }
        } else {
            carritoListarView.mostrarMensaje("Ingrese un código de carrito válido");
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
