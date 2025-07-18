package ec.edu.ups.modelo;

import ec.edu.ups.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Modela un usuario en el sistema.
 * Contiene información personal, credenciales, rol y preguntas de seguridad.
 * Esta clase encapsula la lógica de validación para sus atributos principales,
 * lanzando excepciones personalizadas en caso de error. También proporciona
 * métodos para la serialización a un formato de texto para persistencia.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class Usuario {
    private String username;
    private String contrasenia;
    private Rol rol;
    private String nombreCompleto;
    private String fechaNacimiento;
    private String celular;
    private String correo;
    private List<PreguntasRespuestas> preguntasRespuestas;
    private MensajeInternacionalizacionHandler mi;


    /**
     * Constructor por defecto. Inicializa la lista de preguntas y respuestas.
     */
    public Usuario() {
        this.preguntasRespuestas = new ArrayList<>();
    }

    /**
     * Constructor para crear un usuario con datos básicos de credenciales y rol.
     *
     * @param username    El nombre de usuario (cédula).
     * @param contrasenia La contraseña del usuario.
     * @param rol         El rol del usuario (ADMINISTRADOR o USUARIO).
     */
    public Usuario(String username, String contrasenia, Rol rol) {
        this.username = username;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.preguntasRespuestas = new ArrayList<>();
    }

    /**
     * Constructor completo para crear un usuario con todos sus datos.
     *
     * @param username        El nombre de usuario (cédula).
     * @param contrasenia     La contraseña.
     * @param rol             El rol del usuario.
     * @param nombreCompleto  El nombre completo.
     * @param fechaNacimiento La fecha de nacimiento.
     * @param celular         El número de celular.
     * @param correo          La dirección de correo electrónico.
     * @param mi              El manejador de internacionalización para mensajes de error.
     */
    public Usuario(String username, String contrasenia, Rol rol, String nombreCompleto,
                   String fechaNacimiento, String celular, String correo, MensajeInternacionalizacionHandler mi) {
        this.username = username;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.correo = correo;
        this.mi = mi;
        this.preguntasRespuestas = new ArrayList<>();

    }

    /**
     * Serializa el objeto Usuario a un formato de cadena de texto para su almacenamiento.
     *
     * @return Una cadena de texto con los atributos del usuario separados por punto y coma.
     */
    public String toArchivoTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append("|")
                .append(contrasenia).append("|")
                .append(nombreCompleto).append("|")
                .append(correo).append("|")
                .append(celular).append("|")
                .append(fechaNacimiento).append("|")
                .append(rol.name()).append("|");

        for (int i = 0; i < preguntasRespuestas.size(); i++) {
            PreguntasRespuestas pr = preguntasRespuestas.get(i);
            sb.append(pr.getPreguntas().getId()).append(":").append(pr.getRespuesta().getTexto());
            if (i < preguntasRespuestas.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }



    /**
     * Método de fábrica estático que deserializa un Usuario desde una cadena de texto.
     *
     * @param linea La línea de texto del archivo que representa un usuario.
     * @return una nueva instancia de {@code Usuario}, o {@code null} si ocurre un error.
     */
    public static Usuario fromArchivoTexto(String linea) {
        String[] partes = linea.split("\\|");
        if (partes.length < 7) return null;

        Usuario u = new Usuario();
        u.username = partes[0];
        u.contrasenia = partes[1];
        u.nombreCompleto = partes[2];
        u.correo = partes[3];
        u.celular = partes[4];
        u.fechaNacimiento = partes[5];
        u.rol = Rol.valueOf(partes[6]);

        u.preguntasRespuestas = new ArrayList<>();
        if (partes.length == 8) {
            String[] preguntas = partes[7].split(",");
            for (String par : preguntas) {
                String[] codResp = par.split(":");
                if (codResp.length == 2) {
                    Preguntas pregunta = new Preguntas(codResp[0], ""); // solo código
                    Respuesta respuesta = new Respuesta(codResp[0]); // código y texto de respuesta
                    u.preguntasRespuestas.add(new PreguntasRespuestas(pregunta, respuesta));
                }
            }
        }

        return u;
    }


    /**
     * Obtiene el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Valida y establece el nombre de usuario (cédula).
     *
     * @param username La cédula a validar y establecer.
     * @throws SecondExcepcion si la cédula no es válida.
     */
    public void setUsername(String username) throws SecondExcepcion {
        if (!esCedulaValida(username)) {
            throw new SecondExcepcion(mi.get("mensaje.error.cedula.verificador"));
        }
        this.username = username;
    }

    /**
     * Valida una cédula ecuatoriana usando el algoritmo de módulo 10.
     *
     * @param cedula La cadena de 10 dígitos a validar.
     * @return {@code true} si la cédula es válida, {@code false} en caso contrario.
     */
    private static boolean esCedulaValida(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) {
            return false;
        }
        int suma = 0;
        int[] coef = {2,1,2,1,2,1,2,1,2};
        try {
            for (int i = 0; i < 9; i++) {
                int num = Character.getNumericValue(cedula.charAt(i));
                int prod = num * coef[i];
                if (prod > 9) prod -= 9;
                suma += prod;
            }
            int ultimoDigito = Character.getNumericValue(cedula.charAt(9));
            int decenaSuperior = ((suma / 10) + 1) * 10;
            int digitoValidador = decenaSuperior - suma;
            if (digitoValidador == 10) digitoValidador = 0;
            return digitoValidador == ultimoDigito;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return La contraseña.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Valida y establece la contraseña del usuario.
     *
     * @param contrasenia La contraseña a validar y establecer.
     * @throws FirstException si la contraseña no cumple los requisitos de seguridad.
     */
    public void setContrasenia(String contrasenia) throws FirstException {
        char[] contraseniaChars = contrasenia.toCharArray();
        boolean tieneMayuscula = false;
        boolean especial = false;

        for (char c : contraseniaChars) {
            if (Character.isUpperCase(c)) tieneMayuscula = true;
            if (c == '@' || c == '_' || c == '-' || c == '.') especial = true;
        }
        if (!tieneMayuscula) {
            throw new FirstException(mi.get("mensaje.error.contrasena.mayuscula"));
        }
        if (contrasenia.length() < 6) {
            throw new FirstException(mi.get("mensaje.error.contrasena.longa"));
        }
        if (!especial) {
            throw new FirstException(mi.get("mensaje.error.contrasena.especial"));
        }
        this.contrasenia = contrasenia;
    }

    /**
     * Obtiene el rol del usuario.
     * @return El rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * @param rol El nuevo rol.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el nombre completo del usuario.
     * @return El nombre completo.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo del usuario.
     * @param nombreCompleto El nuevo nombre.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     * @return La fecha de nacimiento.
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del usuario.
     * @param fechaNacimiento La nueva fecha.
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el celular del usuario.
     * @return El número de celular.
     */
    public String getCelular() {
        return celular;
    }

    /**
     * Valida y establece el número de celular del usuario.
     *
     * @param celular El número de 10 dígitos a validar y establecer.
     * @throws CelularException si el celular no es válido.
     */
    public void setCelular(String celular) throws CelularException {
        if (celular == null || !celular.matches("\\d{10}")) {
            throw new CelularException(mi.get("mensaje.error.celular"));
        }
        this.celular = celular;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return El correo electrónico.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Valida y establece la dirección de correo electrónico del usuario.
     *
     * @param correo La dirección de correo a validar y establecer.
     * @throws CorreoException si el correo no tiene un formato válido.
     */
    public void setCorreo(String correo) throws CorreoException {
        if (correo == null || !correo.matches("^[\\w.-]+@gmail\\.com$")) {
            throw new CorreoException(mi.get("mensaje.error.correo"));
        }
        this.correo = correo;
    }

    /**
     * Obtiene la lista de preguntas y respuestas de seguridad del usuario.
     * @return Una lista de {@code PreguntasRespuestas}.
     */
    public List<PreguntasRespuestas> getPreguntasRespuestas() {
        return preguntasRespuestas;
    }

    /**
     * Establece la lista de preguntas y respuestas de seguridad.
     * @param preguntasRespuestas La nueva lista.
     */
    public void setPreguntasRespuestas(List<PreguntasRespuestas> preguntasRespuestas) {
        this.preguntasRespuestas = preguntasRespuestas;
    }

    /**
     * Agrega una lista de preguntas y respuestas al perfil del usuario.
     * @param preguntasRes La lista de {@code PreguntasRespuestas} a añadir.
     */
    public void agregarPreguntas(List<PreguntasRespuestas> preguntasRes) {
        preguntasRespuestas.addAll(preguntasRes);
    }

    /**
     * Establece el manejador de internacionalización para esta instancia de usuario.
     * @param mi El manejador de internacionalización.
     */
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
    }

    /**
     * Devuelve una representación en cadena del objeto Usuario.
     * @return Una cadena con los datos del usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", celular='" + celular + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}