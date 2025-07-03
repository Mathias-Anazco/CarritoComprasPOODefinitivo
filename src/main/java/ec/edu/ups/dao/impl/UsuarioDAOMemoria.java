package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;
    private CuestionarioDAO cuestionarioDAO;


    public UsuarioDAOMemoria( CuestionarioDAO cuestionarioDAO) {
        this.usuarios = new ArrayList<>();
        this.cuestionarioDAO = cuestionarioDAO;

        crear(new Usuario("", "", Rol.ADMINISTRADOR));
        Usuario admin = new Usuario("admin", "12345", Rol.ADMINISTRADOR);
        admin.setNombreCompleto("Administrador Principal");
        admin.setCorreo("admin@gmail.com");
        admin.setCelular("0999999999");
        admin.setFechaNacimiento("1/Enero/1990");
        crear(admin);


        Cuestionario cuestionarioAdmin = new Cuestionario("");
        List<Respuesta> preguntas = cuestionarioAdmin.preguntasPorDefecto();

        preguntas.get(0).setRespuesta("Negro");
        preguntas.get(1).setRespuesta("Kobu");
        preguntas.get(2).setRespuesta("Churrasco");

        cuestionarioAdmin.agregarRespuesta(preguntas.get(0));
        cuestionarioAdmin.agregarRespuesta(preguntas.get(1));
        cuestionarioAdmin.agregarRespuesta(preguntas.get(2));

        cuestionarioDAO.guardar(cuestionarioAdmin);
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if(usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)){
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        for(int i = 0; i < usuarios.size(); i++){
            Usuario usuarioAux = usuarios.get(i);
            if(usuarioAux.getUsername().equals(usuario.getUsername())){
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    @Override
    public  List<Usuario> listarPorUsername(String username) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().startsWith(username)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }
}