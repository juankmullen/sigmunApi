/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Respuestas;


import Entidad.TipoConsultas;
import java.util.List;

/**
 *
 * @author JCastillo
 */
public class RespuestaTipoConsulta {

    /**
     * @return the result
     */
    public List<TipoConsultas> getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(List<TipoConsultas> result) {
        this.result = result;
    }
    private List<TipoConsultas> result;
}
