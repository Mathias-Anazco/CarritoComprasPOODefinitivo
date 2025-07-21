package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.CarritoView.CarritoAnadirView;
import ec.edu.ups.vista.ProductoView.ProductoActualizarView;
import ec.edu.ups.vista.ProductoView.ProductoAnadirView;
import ec.edu.ups.vista.ProductoView.ProductoEliminarView;
import ec.edu.ups.vista.ProductoView.ProductoListaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

/**
 * Controlador que gestiona la lógica de negocio para las operaciones de productos.
 * Interactúa con las vistas de productos (CRUD) y el DAO para manejar la creación,
 * lectura, actualización y eliminación de productos.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoActualizarView productoActualizarView;
    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para el ProductoController.
     *
     * @param productoDAO DAO para el acceso a datos de productos.
     * @param productoAnadirView Vista para añadir nuevos productos.
     * @param productoListaView Vista para listar y buscar productos.
     * @param carritoAnadirView Vista para añadir productos a un carrito, utilizada para la búsqueda de productos.
     * @param productoEliminarView Vista para eliminar productos.
     * @param productoActualizarView Vista para actualizar productos.
     * @param mi Manejador de internacionalización para los mensajes.
     */
    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              CarritoAnadirView carritoAnadirView,
                              ProductoEliminarView productoEliminarView,
                              ProductoActualizarView productoActualizarView,
                              MensajeInternacionalizacionHandler mi) {

        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.carritoAnadirView = carritoAnadirView;
        this.productoEliminarView = productoEliminarView;
        this.productoActualizarView = productoActualizarView;
        this.mi = mi;
        this.configurarEventosEnVistas();
    }

    /**
     * Configura los ActionListeners para los componentes de las diferentes vistas de producto.
     */
    private void configurarEventosEnVistas() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());
        productoListaView.getBtnBuscar().addActionListener(e -> buscarProducto());
        productoListaView.getBtnListar().addActionListener(e -> listarProductos());
        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoPorCodigo());
        productoEliminarView.getBuscarButton().addActionListener(e -> buscarProductoPorCodigoEliminar());
        productoEliminarView.getEliminarButton().addActionListener(e -> eliminarProducto());
        productoActualizarView.getBuscarButton().addActionListener(e -> buscarProductoPorCodigoActualizar());
        productoActualizarView.getActualizarButton().addActionListener(e -> actualizarProducto());
    }

    /**
     * Guarda un nuevo producto obteniendo los datos desde la vista de añadir producto.
     */
    private void guardarProducto() {
        try {
            int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
            String nombre = productoAnadirView.getTxtNombre().getText();
            double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

            productoDAO.crear(new Producto(codigo, nombre, precio));
            productoAnadirView.mostrarMensaje(mi.get("producto.guardado"));
            productoAnadirView.limpiarCampos();
            productoAnadirView.mostrarProductos(productoDAO.listarTodos());
        } catch (NumberFormatException e) {
            productoAnadirView.mostrarMensaje(mi.get("mensaje.numero.invalido"));
        }
    }

    /**
     * Busca productos por nombre y actualiza la vista de lista de productos.
     */
    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    /**
     * Obtiene y muestra todos los productos en la vista de lista de productos.
     */
    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    /**
     * Busca un producto por su código y muestra su nombre y precio en la vista de añadir carrito.
     */
    private void buscarProductoPorCodigo() {
        try {
            Locale locale = mi.getLocale();
            int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                carritoAnadirView.mostrarMensaje(mi.get("producto.no_encontrado"));
                carritoAnadirView.getTxtNombre().setText("");
                carritoAnadirView.getTxtPrecio().setText("");
            } else {
                carritoAnadirView.getTxtNombre().setText(producto.getNombre());
                carritoAnadirView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(producto.getPrecio(), locale));
            }
        } catch (NumberFormatException e) {
            carritoAnadirView.mostrarMensaje(mi.get("mensaje.codigo.invalido"));
        }
    }

    /**
     * Elimina un producto basado en el código proporcionado en la vista de eliminación.
     */
    private void eliminarProducto() {
        String cod = productoEliminarView.getTextField1().getText().trim();

        if (cod.isEmpty()) {
            productoEliminarView.mostrarMensaje(mi.get("producto.error.codigo_vacio"));
            return;
        }

        try {
            int codigo = Integer.parseInt(cod);
            productoDAO.eliminar(codigo);
            productoEliminarView.mostrarMensaje(mi.get("producto.eliminado"));
            productoEliminarView.limpiarCampos();
        } catch (NumberFormatException e) {
            productoEliminarView.mostrarMensaje(mi.get("producto.error.codigo_invalido"));
        }
    }

    /**
     * Busca un producto por código y lo muestra en la vista de eliminación.
     */
    private void buscarProductoPorCodigoEliminar() {
        String code = productoEliminarView.getTextField1().getText();
        if (!code.isEmpty()) {
            try {
                int codigo = Integer.parseInt(code);
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    productoEliminarView.cargarDatos(List.of(producto));
                } else {
                    productoEliminarView.mostrarMensaje(mi.get("producto.no_encontrado"));
                    productoEliminarView.cargarDatos(List.of());
                    productoEliminarView.limpiarCampos();
                }
            } catch (NumberFormatException e) {
                productoEliminarView.mostrarMensaje(mi.get("producto.error.codigo_invalido"));
            }
        }
    }

    /**
     * Busca un producto por código y lo muestra en la vista de actualización.
     */
    private void buscarProductoPorCodigoActualizar() {
        String code = productoActualizarView.getTextField1().getText();
        if (!code.isEmpty()) {
            try {
                int codigo = Integer.parseInt(code);
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    productoActualizarView.cargarDatos(List.of(producto));
                } else {
                    productoActualizarView.mostrarMensaje(mi.get("producto.no_encontrado"));
                    productoActualizarView.cargarDatos(List.of());
                    productoActualizarView.limpiarCampos();
                }
            } catch (NumberFormatException e) {
                productoActualizarView.mostrarMensaje(mi.get("producto.error.codigo_invalido"));
            }
        }
    }

    /**
     * Actualiza un producto con los datos ingresados en la vista de actualización.
     */
    private void actualizarProducto() {
        try {
            String cod = productoActualizarView.getTextField1().getText();
            int codigo = Integer.parseInt(cod);
            String nombre = productoActualizarView.getTextField2().getText();
            double precio = Double.parseDouble(productoActualizarView.getTextField3().getText());

            Producto producto = new Producto(codigo, nombre, precio);
            productoDAO.actualizar(producto);
            productoActualizarView.mostrarMensaje(mi.get("producto.modificado"));
        } catch (NumberFormatException e) {
            productoActualizarView.mostrarMensaje(mi.get("mensaje.numero.invalido"));
        }
    }
}