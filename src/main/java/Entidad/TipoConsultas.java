
package Entidad;

/**
 *
 * @author JCastillo
 */
public class TipoConsultas {

    
    public int getId_consulta() {
        return id_consulta;
    }

   
    public void setId_consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }

  
    public String getNombre_tipo() {
        return nombre_tipo;
    }

   
    public void setNombre_tipo(String nombre_tipo) {
        this.nombre_tipo = nombre_tipo;
    }
    private int id_consulta;
    private String nombre_tipo;
}
