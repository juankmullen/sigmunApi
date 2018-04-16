package com.mycompany.sismun;

import Entidad.EstGenerales;
import Entidad.ExitoInsert;
import Entidad.TipoConsultas;
import Entidad.consultas;
import Entidad.vistas;
import Respuestas.RespuestaEstGenerales;
import Respuestas.RespuestasConsultas;
import com.google.gson.Gson;
import java.sql.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class API {
    
    public Connection crear_conexion() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException
    {
       Class.forName("com.mysql.jdbc.Driver").newInstance();
       
        Connection con = null;
       
         String sURL = "jdbc:mysql://localhost:3306";
         con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sigmun","root","");   
         return con;
    }

  
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/acceso/nr_rol/{in_nr_rol}/nr_vista/{in_nr_vista}")
    public String getAcceso(
            @PathParam("in_nr_rol")    int  in_nr_rol, 
            @PathParam("in_nr_vista")  int  in_nr_vista) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
     
         Connection con= crear_conexion();
         String resultado="";
            CallableStatement proc = con.prepareCall("{call sp_valida_rol_vista(?,?,?)}");
            //se cargan los parametros de entrada
            
            proc.setInt(1, in_nr_rol);//Tipo String
            proc.setInt(2, in_nr_vista);//Tipo entero
            proc.registerOutParameter(3, java.sql.Types.INTEGER);//Tipo String
            // parametros de salida
          
            // Se ejecuta el procedimiento almacenado
            proc.execute();            
            // devuelve el valor del parametro de salida del procedimiento
           
            resultado = proc.getString("total");
            resultado="[{\"total\":\""+resultado+"\"}]";
         con.close();
        return resultado;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/vistas/nr_rol/{in_nr_rol}")
    public String getVistas(
            @PathParam("in_nr_rol")    int  in_nr_rol) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
     
         Connection con= crear_conexion();
         String respuesta="";
         PreparedStatement ps=null;
         ResultSet rs=null;
         List<vistas> listaVistas = new ArrayList<vistas>();
         Gson gson = new Gson();
          
         ps = (PreparedStatement) con.prepareStatement("call sp_get_vistas(?)");
         ps.setInt(1, in_nr_rol);
         rs=ps.executeQuery(); 
         

         while(rs.next())
         {    
           vistas vista = new vistas ();
           vista.setUrl(rs.getString("url"));
           vista.setDescripcion(rs.getString("descripcion"));
           vista.setAlias(rs.getString("alias"));
           vista.setIcon(rs.getString("icon"));
           listaVistas.add(vista);
         }
         con.close();
         
         respuesta = gson.toJson(listaVistas); 
         return respuesta;
    }
    
    //Devuelve las consultas segun usuario 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/consultas/id_usuario/{in_nr_id_usuario}")
    public Response getConsultas(
            @PathParam("in_nr_id_usuario")    int  in_nr_id_usuario,
            @DefaultValue("0")@QueryParam("in_tp_consulta") int in_tp_consulta
            ) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
     
         Connection con= crear_conexion();
         String respuesta="";
         PreparedStatement ps=null;
         ResultSet rs=null;
         List<consultas> listaConsultas = new ArrayList<consultas>();
         RespuestasConsultas lista=new RespuestasConsultas();
         Gson gson = new Gson();
          
         ps = (PreparedStatement) con.prepareStatement("call sp_obt_consultas(?,?)");
         ps.setInt(1, in_nr_id_usuario);
         ps.setInt(2, in_tp_consulta);
         rs=ps.executeQuery(); 
         
         while(rs.next())
         {    
           consultas consultas = new consultas ();
           consultas.setId_consulta(rs.getInt("id_consulta"));
           consultas.setId_autor(rs.getInt("id_autor"));
           consultas.setNr_estado_consulta(rs.getInt("nr_estado_consulta"));
           consultas.setAsunto_consulta(rs.getString("asunto_consulta"));
           consultas.setFc_creacion_consulta(rs.getString("fc_creacion_consulta"));
           consultas.setStr_mensaje(rs.getString("str_mensaje"));
           consultas.setNr_tp_consulta(rs.getInt("nr_tp_consulta"));
           consultas.setNr_comuna(rs.getInt("nr_comuna"));
           consultas.setNr_sector(rs.getInt("nr_sector"));
           consultas.setStr_correo_autor(rs.getString("str_correo_autor"));
        
           listaConsultas.add(consultas);   
         
         
         
        }
         lista.setResult(listaConsultas);
         respuesta = gson.toJson(lista); 
        con.close();
        return Response.status(200).entity(respuesta)
               .header("Access-Control-Allow-Origin", "*")
               .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
               .allow("OPTIONS").build();
         
    }
    
    
     //Devuelve estadisticas generales
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/estGeneral/id_comuna/{in_nr_comuna}")
    public Response getEstGenerales(
            @PathParam("in_nr_comuna")    int  in_nr_comuna
            ) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
     
         Connection con= crear_conexion();
         String respuesta="";
         PreparedStatement ps=null;
         ResultSet rs=null;
         List<EstGenerales> listaEst = new ArrayList<EstGenerales>();
       
         Gson gson = new Gson();
          
         ps = (PreparedStatement) con.prepareStatement("call sp_obt_est_generales(?)");
         ps.setInt(1, in_nr_comuna);

         rs=ps.executeQuery(); 
         
         while(rs.next())
         {    
           EstGenerales est = new EstGenerales ();
           est.setEstado(rs.getInt("estado"));
           est.setTp_consulta(rs.getInt("tp_consulta"));
                  
           listaEst.add(est);   
        }
        
         respuesta = gson.toJson(listaEst); 
         con.close();
        return Response.status(200).entity(respuesta)
               .header("Access-Control-Allow-Origin", "*")
               .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
               .allow("OPTIONS").build();
         
    }
    
    //Devuelve todos los tipos de consultas existentes
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/consultas/tp_consultas")
    public Response getTipoConsultas() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
     
         Connection con= crear_conexion();
         String respuesta="";
         PreparedStatement ps=null;
         ResultSet rs=null;
         List<TipoConsultas> listaTipoConsultas = new ArrayList<TipoConsultas>();
         Gson gson = new Gson();
          
         ps = (PreparedStatement) con.prepareStatement("call sp_obt_tipos_consultas()");
        
         
         rs=ps.executeQuery();         

         while(rs.next())
         {    
           TipoConsultas TipoConsulta= new TipoConsultas ();
           TipoConsulta.setId_consulta(rs.getInt("id_consulta"));
           TipoConsulta.setNombre_tipo(rs.getString("nombre_tipo"));
           listaTipoConsultas.add(TipoConsulta);
         }
         con.close();
         
         respuesta = gson.toJson(listaTipoConsultas); 
           return Response.status(200).entity(respuesta)
               .header("Access-Control-Allow-Origin", "*")
               .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
               .allow("OPTIONS").build();
    }
    
    //Ingreso de nueva consulta al sistema
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/setconsultas/id_autor/{id_autor}/nr_comuna/{nr_comuna}/nr_sector/{nr_sector}/str_correo/{str_correo}")
    public Response setConsultas(
     
     @PathParam("id_autor")    int  id_autor,
     @PathParam("nr_comuna")   int  nr_comuna,      
     @PathParam("nr_sector")   int  nr_sector,
     @PathParam("str_correo")  String  str_correo,
     @FormParam("str_asunto")  String  str_asunto,  
     @FormParam("str_mensaje") String  str_mensaje,     
     @FormParam("nr_tipo_consulta") int  nr_tipo_consulta,     
     @FormParam("str_ruta_adjunto") String  str_ruta_adjunto)
            throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
     
         Connection con= crear_conexion();
         String respuesta="";
         PreparedStatement ps=null;
         ResultSet rs=null;
         List<ExitoInsert> listaExitoInsert = new ArrayList<ExitoInsert>();
         Gson gson = new Gson();
          
         ps = (PreparedStatement) con.prepareStatement("call sigmun.sp_ins_consulta(?,?,?,?,?,?,?,?)");
         ps.setInt(1, id_autor);
         ps.setInt(2, nr_comuna);
         ps.setInt(3, nr_sector);
          ps.setString(4, str_correo);         
         ps.setString(5, str_asunto);         
         ps.setString(6, str_mensaje);
         ps.setInt(7, nr_tipo_consulta);
         ps.setString(8, str_ruta_adjunto);
         rs=ps.executeQuery();         

         while(rs.next())
         {    
           ExitoInsert insert= new ExitoInsert ();
           insert.setError(rs.getInt("total"));
           insert.setMsgError(rs.getString("msgError"));
           listaExitoInsert.add(insert);
         }
         con.close();
         
         respuesta = gson.toJson(listaExitoInsert); 
           return Response.status(200).entity(respuesta)
               .header("Access-Control-Allow-Origin", "*")
               .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
               .allow("OPTIONS").build();
    }
    
    
}
