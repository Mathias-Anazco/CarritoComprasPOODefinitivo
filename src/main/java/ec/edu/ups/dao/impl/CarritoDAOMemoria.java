package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria de la interfaz CarritoDAO.
 * Gestiona una lista de carritos de compra de forma volátil. Los datos
 * se perderán al finalizar la ejecución del programa.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class CarritoDAOMemoria implements CarritoDAO {

    private List<Carrito> carritos;

    /**
     * Constructor que inicializa la lista en memoria para almacenar los carritos.
     */
    public CarritoDAOMemoria() {
        this.carritos = new ArrayList<Carrito>();
    }

    /**
     * Crea un nuevo carrito en la lista. Asigna un código secuencial
     * y guarda una copia del objeto para evitar modificaciones externas no deseadas.
     *
     * @param carrito El carrito a ser creado.
     */
    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(carritos.size() + 1);
        Carrito copia = carrito.copiar();
        carritos.add(copia);
        System.out.println("Carrito creado con código: " + copia.getCodigo());
    }

    /**
     * Busca un carrito en la lista por su código.
     *
     * @param codigo El código del carrito a buscar.
     * @return El carrito encontrado, o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Actualiza un carrito existente en la lista, localizándolo por su código.
     *
     * @param carrito El carrito con los datos actualizados.
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
    }

    /**
     * Elimina un carrito de la lista usando su código.
     *
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Devuelve una lista con todos los carritos almacenados en memoria.
     *
     * @return Una lista de todos los carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return carritos;
    }

    /**
     * Busca y devuelve todos los carritos asociados a un usuario específico.
     *
     * @param usuario El usuario cuyos carritos se desean encontrar.
     * @return Una lista de carritos pertenecientes al usuario.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> carritosUsuario = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                carritosUsuario.add(carrito);
            }
        }
        return carritosUsuario;
    }
}