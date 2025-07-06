package ec.edu.ups.modelo;

public class Preguntas {
    private String id;
    private String enunciado;

    public Preguntas( String id, String enunciado) {
        this.id = id;
        this.enunciado = enunciado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    @Override
    public String toString() {
        return enunciado;
    }
}
