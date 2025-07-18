package ec.edu.ups.dao.DAOArchivos;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Preguntas;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de {@link CuestionarioDAO} que persiste los datos de las preguntas
 * en un archivo binario utilizando {@link RandomAccessFile}.
 * Cada registro de pregunta en el archivo consta de una ID (entero) y un enunciado
 * de longitud fija. Las preguntas iniciales se cargan al crear la instancia
 * si el archivo está vacío, haciendo uso de un manejador de internacionalización.
 *
 * @author Mathias Añazco
 * @since 18/07/2025
 */
public class CuestionarioDAOArchivo implements CuestionarioDAO {

    /**
     * Ruta absoluta del archivo donde se almacenarán las preguntas.
     */
    private final String rutaArchivo;
    /**
     * Longitud fija en caracteres para el enunciado de cada pregunta.
     */
    private final int ENUNCIADO_LENGTH = 100; // caracteres
    /**
     * Número de bytes por cada registro de pregunta en el archivo.
     * Calculado como el tamaño de un entero (4 bytes) más el tamaño del enunciado
     * (longitud en caracteres * 2 bytes por caracter Unicode).
     */
    private final int BYTES_POR_REGISTRO = 4 + (ENUNCIADO_LENGTH * 2); // int + String
    /**
     * Manejador para obtener mensajes internacionalizados, utilizado para cargar
     * las preguntas iniciales.
     */
    private final MensajeInternacionalizacionHandler mi;

    /**
     * Construye una nueva instancia de {@code CuestionarioDAOArchivo}.
     * Si el archivo especificado no existe, se crea. Si el archivo existe pero
     * está vacío, se cargan un conjunto de preguntas iniciales internacionalizadas.
     *
     * @param archivo El objeto {@link File} que representa el archivo de almacenamiento.
     * @param mi El {@link MensajeInternacionalizacionHandler} para la carga de preguntas iniciales.
     */
    public CuestionarioDAOArchivo(File archivo, MensajeInternacionalizacionHandler mi) {
        this.rutaArchivo = archivo.getAbsolutePath();
        this.mi = mi;

        File f = new File(rutaArchivo);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }

            // Verificamos si el archivo está vacío
            if (f.length() == 0) {
                cargarPreguntasIniciales(); // se llama si está vacío
            }

        } catch (IOException e) {
            System.err.println("Error al crear archivo de cuestionario: " + e.getMessage());
        }
    }

    /**
     * Carga un conjunto predefinido de preguntas iniciales en el archivo.
     * Este método se invoca únicamente si el archivo está vacío al inicializar la clase.
     * Las preguntas son internacionalizadas utilizando el {@link MensajeInternacionalizacionHandler}
     * proporcionado en el constructor.
     */
    private void cargarPreguntasIniciales() {
        crear(new Preguntas("1", mi.get("pregunta.color_favorito")));
        crear(new Preguntas("2", mi.get("pregunta.instrumento_musical_favorito")));
        crear(new Preguntas("3", mi.get("pregunta.comida_favorita")));
        crear(new Preguntas("4", mi.get("pregunta.pais_que_visitaste_por_primera_vez")));
        crear(new Preguntas("5", mi.get("pregunta.segundo_nombre_de_tu_padre")));
        crear(new Preguntas("6", mi.get("pregunta.cancion_favorita")));
        crear(new Preguntas("7", mi.get("pregunta.tu_libro_favorito")));
    }

    /**
     * Guarda una nueva pregunta en el archivo.
     * La pregunta se añade al final del archivo. La ID de la pregunta se convierte a entero
     * y el enunciado se guarda con una longitud fija.
     *
     * @param preguntas El objeto {@link Preguntas} a guardar.
     */
    @Override
    public void crear(Preguntas preguntas) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.seek(raf.length()); // Mueve el puntero al final del archivo
            raf.writeInt(Integer.parseInt(preguntas.getId())); // Escribe la ID como entero
            escribirStringFijo(raf, preguntas.getEnunciado()); // Escribe el enunciado con longitud fija
        } catch (IOException e) {
            System.err.println("Error al guardar pregunta: " + e.getMessage());
        }
    }

    /**
     * Recupera y devuelve una lista de todas las preguntas almacenadas en el archivo.
     * Las preguntas se leen secuencialmente desde el inicio hasta el final del archivo.
     *
     * @return Una {@link List} de objetos {@link Preguntas} que representan todas las preguntas en el archivo.
     */
    @Override
    public List<Preguntas> listarPreguntas() {
        List<Preguntas> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            while (raf.getFilePointer() < raf.length()) { // Itera mientras no se llegue al final del archivo
                int id = raf.readInt(); // Lee la ID
                String enunciado = leerStringFijo(raf); // Lee el enunciado
                lista.add(new Preguntas(String.valueOf(id), enunciado)); // Añade la pregunta a la lista
            }
        } catch (IOException e) {
            System.err.println("Error al leer preguntas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Recupera y devuelve una lista de todas las preguntas almacenadas en el archivo,
     * idéntica a {@link #listarPreguntas()}.
     * Este método se mantiene para cumplir con la interfaz {@link CuestionarioDAO}.
     *
     * @return Una {@link List} de objetos {@link Preguntas}.
     */
    @Override
    public List<Preguntas> listarPreguntasEnunciado() {
        return listarPreguntas(); // misma lista
    }

    /**
     * Escribe una cadena de texto en un {@link RandomAccessFile} asegurando que ocupe
     * una longitud fija predefinida ({@code ENUNCIADO_LENGTH}).
     * Si la cadena es más corta, se rellena con espacios. Si es más larga, se trunca.
     *
     * @param raf El {@link RandomAccessFile} en el que se va a escribir.
     * @param texto La cadena de texto a escribir.
     * @throws IOException Si ocurre un error de E/S durante la escritura.
     */
    private void escribirStringFijo(RandomAccessFile raf, String texto) throws IOException {
        StringBuilder sb = new StringBuilder(texto);
        sb.setLength(ENUNCIADO_LENGTH); // Rellena con espacios si es más corto, o trunca si es más largo
        raf.writeChars(sb.toString()); // Escribe la cadena de caracteres
    }

    /**
     * Lee una cadena de texto de longitud fija desde un {@link RandomAccessFile}.
     * Se leen {@code ENUNCIADO_LENGTH} caracteres y el resultado se recorta (trim)
     * para eliminar los espacios en blanco de relleno.
     *
     * @param raf El {@link RandomAccessFile} del que se va a leer.
     * @return La cadena de texto leída, sin espacios de relleno al final.
     * @throws IOException Si ocurre un error de E/S durante la lectura.
     */
    private String leerStringFijo(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ENUNCIADO_LENGTH; i++) {
            sb.append(raf.readChar()); // Lee caracter por caracter
        }
        return sb.toString().trim(); // Devuelve la cadena recortada
    }
}