/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

/**
 *
 * @author JCastillo
 */
public class EstGenerales {

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return the tp_consulta
     */
    public int getTp_consulta() {
        return tp_consulta;
    }

    /**
     * @param tp_consulta the tp_consulta to set
     */
    public void setTp_consulta(int tp_consulta) {
        this.tp_consulta = tp_consulta;
    }
    
    private int estado;
    private int tp_consulta;
    
}
