package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoActualizarView productoActualizarView;
    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mi;

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

    private void configurarEventosEnVistas() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigo();
            }
        });

        productoEliminarView.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigoEliminar();
            }
        });

        productoEliminarView.getEliminarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        productoActualizarView.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigoActualizar();
            }
        });

        productoActualizarView.getActualizarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje(mi.get("producto.guardado"));
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();

        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    private void buscarProductoPorCodigo() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoAnadirView.mostrarMensaje(mi.get("producto.no_encontrado"));
            carritoAnadirView.getTxtNombre().setText("");
            carritoAnadirView.getTxtPrecio().setText("");
        } else {
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        }
    }

    private void eliminarProducto() {
        String cod = productoEliminarView.getTextField1().getText();
        int codigo = Integer.parseInt(cod);
        productoDAO.eliminar(codigo);
        productoEliminarView.mostrarMensaje(mi.get("producto.eliminado"));
    }

    private void buscarProductoPorCodigoEliminar() {
        String code = productoEliminarView.getTextField1().getText();
        if (!code.isEmpty()) {
            int codigo = Integer.parseInt(code);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoEliminarView.cargarDatos(List.of(producto));
            } else {
                productoEliminarView.mostrarMensaje(mi.get("producto.no_encontrado"));
                productoEliminarView.cargarDatos(List.of());
                productoEliminarView.limpiarCampos();
            }
        }
    }

    private void buscarProductoPorCodigoActualizar() {
        String code = productoActualizarView.getTextField1().getText();
        if (!code.isEmpty()) {
            int codigo = Integer.parseInt(code);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoActualizarView.cargarDatos(List.of(producto));
            } else {
                productoActualizarView.mostrarMensaje(mi.get("producto.no_encontrado"));
                productoActualizarView.cargarDatos(List.of());
                productoActualizarView.limpiarCampos();
            }
        }
    }

    private void actualizarProducto() {
        String cod = productoActualizarView.getTextField1().getText();
        int codigo = Integer.parseInt(cod);
        String nombre = productoActualizarView.getTextField2().getText();
        double precio = Double.parseDouble(productoActualizarView.getTextField3().getText());

        Producto producto = new Producto(codigo, nombre, precio);
        productoDAO.actualizar(producto);
        productoActualizarView.mostrarMensaje(mi.get("producto.modificado"));
    }
}
