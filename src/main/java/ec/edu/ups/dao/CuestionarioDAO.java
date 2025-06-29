package ec.edu.ups.dao;

import ec.edu.ups.modelo.Cuestionario;

public interface CuestionarioDAO {
    void guardar(Cuestionario cuestionario);
    Cuestionario buscarPorUsername(String username);
}
