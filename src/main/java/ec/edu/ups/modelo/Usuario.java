package ec.edu.ups.modelo;

import ec.edu.ups.util.*;

import java.util.ArrayList;
import java.util.List;

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

    public Usuario() {
        this.preguntasRespuestas = new ArrayList<>();
    }

    public Usuario(String username, String contrasenia, Rol rol) {
        this.username = username;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.preguntasRespuestas = new ArrayList<>();
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws SecondExcepcion {
        if (!esCedulaValida(username)) {
            throw new SecondExcepcion(mi.get("mensaje.error.cedula.verificador"));
        }
        this.username = username;
    }

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

    public String getContrasenia() {
        return contrasenia;
    }

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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) throws CelularException {
        if (celular == null || !celular.matches("\\d{10}")) {
            throw new CelularException(mi.get("mensaje.error.celular"));
        }
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) throws CorreoException {
        if (correo == null || !correo.matches("^[\\w.-]+@gmail\\.com$")) {
            throw new CorreoException(mi.get("mensaje.error.correo"));
        }
        this.correo = correo;
    }

    public List<PreguntasRespuestas> getPreguntasRespuestas() {
        return preguntasRespuestas;
    }

    public void setPreguntasRespuestas(List<PreguntasRespuestas> preguntasRespuestas) {
        this.preguntasRespuestas = preguntasRespuestas;
    }

    public void agregarPreguntas(List<PreguntasRespuestas> preguntasRes) {
        preguntasRespuestas.addAll(preguntasRes);
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
    }

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
