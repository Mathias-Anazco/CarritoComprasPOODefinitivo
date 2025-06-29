package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Cuestionario;

import java.util.List;
import java.util.ArrayList;

public class CuestionarioDAOMemoria implements CuestionarioDAO {

    private List<Cuestionario> cuestionarios;
    public CuestionarioDAOMemoria() {
        this.cuestionarios = new ArrayList<>();
    }

    @Override
    public void guardar(Cuestionario cuestionario) {
        cuestionarios.add(cuestionario);
    }

    @Override
    public Cuestionario buscarPorUsername(String username) {
        for (Cuestionario cuestionario : cuestionarios) {
            if (cuestionario.getUsername().equalsIgnoreCase(username)) {
                return cuestionario;
            }
        }
        return null;
    }
}
