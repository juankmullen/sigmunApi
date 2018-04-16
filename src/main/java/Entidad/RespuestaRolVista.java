/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcastilb
 */
public class RespuestaRolVista {

    /**
     * @return the list
     */
    public List<RolVista> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<RolVista> list) {
        this.list = list;
    }
   private List<RolVista> list = new ArrayList<RolVista>();
}
