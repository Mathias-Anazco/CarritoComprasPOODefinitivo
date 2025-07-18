package ec.edu.ups.dao.DAOArchivos;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Preguntas;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de CuestionarioDAO usando RandomAccessFile.
 * Cada pregunta tiene una ID (String de 2 caracteres) y un enunciado de longitud fija.
 * Se cargan preguntas internacionalizadas por defecto.
 *
 * @author Mathias Añazco
 * @since 18/07/2025
 */
public class CuestionarioDAOArchivo implements CuestionarioDAO {

    private final String rutaArchivo;
    private final int ENUNCIADO_LENGTH = 100; // caracteres
    private final int BYTES_POR_REGISTRO = 4 + (ENUNCIADO_LENGTH * 2); // int + String
    private final MensajeInternacionalizacionHandler mi;

    public CuestionarioDAOArchivo(File archivo, MensajeInternacionalizacionHandler mi) {
        this.rutaArchivo = archivo.getAbsolutePath();
        this.mi = mi;

        File f = new File(rutaArchivo);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }

            // ✅ Verificamos si el archivo está vacío
            if (f.length() == 0) {
                cargarPreguntasIniciales(); // se llama si está vacío
            }

        } catch (IOException e) {
            System.err.println("Error al crear archivo de cuestionario: " + e.getMessage());
        }
    }

    private void cargarPreguntasIniciales() {
        crear(new Preguntas("1", mi.get("pregunta.color_favorito")));
        crear(new Preguntas("2", mi.get("pregunta.instrumento_musical_favorito")));
        crear(new Preguntas("3", mi.get("pregunta.comida_favorita")));
        crear(new Preguntas("4", mi.get("pregunta.pais_que_visitaste_por_primera_vez")));
        crear(new Preguntas("5", mi.get("pregunta.segundo_nombre_de_tu_padre")));
        crear(new Preguntas("6", mi.get("pregunta.cancion_favorita")));
        crear(new Preguntas("7", mi.get("pregunta.tu_libro_favorito")));
    }

    @Override
    public void crear(Preguntas preguntas) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.seek(raf.length());
            raf.writeInt(Integer.parseInt(preguntas.getId()));
            escribirStringFijo(raf, preguntas.getEnunciado());
        } catch (IOException e) {
            System.err.println("Error al guardar pregunta: " + e.getMessage());
        }
    }

    @Override
    public List<Preguntas> listarPreguntas() {
        List<Preguntas> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                String enunciado = leerStringFijo(raf);
                lista.add(new Preguntas(String.valueOf(id), enunciado));
            }
        } catch (IOException e) {
            System.err.println("Error al leer preguntas: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Preguntas> listarPreguntasEnunciado() {
        return listarPreguntas(); // misma lista
    }

    private void escribirStringFijo(RandomAccessFile raf, String texto) throws IOException {
        StringBuilder sb = new StringBuilder(texto);
        sb.setLength(ENUNCIADO_LENGTH); // rellena con espacios
        raf.writeChars(sb.toString());
    }

    private String leerStringFijo(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ENUNCIADO_LENGTH; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }
}
