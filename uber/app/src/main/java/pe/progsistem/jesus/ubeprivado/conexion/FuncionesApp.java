package pe.progsistem.jesus.ubeprivado.conexion;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.IP;

public class FuncionesApp {

    //segunda opcion
    public static final String LINK_URL = "http://"+ IP+"/ubeprivate/controller/Publicacion.php?funcion=";

    public static final String URL_READ_SEDES = LINK_URL + "getSedes";
    public static final String URL_READ_NACIONALIDAD = LINK_URL + "getNacionalidad";


    public static final String URL_CREATE_PUBLICACION = LINK_URL + "savePublicacion";
    public static final String URL_GET_PUBLICACION = LINK_URL + "getPublicacion";
    public static final String URL_GET_PUBLICACIONES = LINK_URL + "getPublicaciones";

    public static final String URL_CREATE_FOTOS = LINK_URL + "saveFotos";
    public static final String URL_GET_FOTOS = LINK_URL + "getFotos";
    public static final String URL_DELETE_FOTO = LINK_URL + "deleteFoto&id=";

    public static final String URL_GET_USUARIOS = LINK_URL + "getUsuarios";

    public static final String URL_CREATE_ATENCION = LINK_URL + "saveAtencion";
    public static final String URL_READ_ATENCION = LINK_URL + "getAtenciones";

    public static final String URL_CREATE_RATING = LINK_URL + "updateAtencion";

    public static final String URL_GET_USUARIO = LINK_URL + "getUsuario";
    public static final String URL_UPDATE_USUARIO = LINK_URL + "updateUsuario";

    public static final String URL_GET_PROMOCION = LINK_URL + "getPromocion";
    public static final String URL_CREATE_PROMOCION = LINK_URL + "savePromocion";

    public static final String URL_UPDATE_CLAVE = LINK_URL + "updateClave";

    public static final String URL_UPDATE_PERFIL = LINK_URL + "updatePerfil";
    public static final String URL_GET_PERFIL = LINK_URL + "getPerfil";


    public static final String URL_GET_ID_USER = LINK_URL + "getIdUser";
    public static final String URL_UPDATE_CLAVE_REC = LINK_URL + "updateClaveRec";

    public static final String URL_GET_SEDES = LINK_URL + "getSedesA";
    public static final String URL_UPDATE_SEDES = LINK_URL + "updateSede";
    public static final String URL_DELETE_SEDES = LINK_URL + "deleteSede";
    public static final String URL_CREATE_SEDE = LINK_URL + "createSede";

    //api goggle maps
    public static final String URL_GET_DIR = "https://maps.googleapis.com/maps/api/place/textsearch/json?fields=formatted_address,name,icon,id,name,geometry,rating&key=AIzaSyAL45pZ1teF9lRCzKSIK1VjP2Fw1N6dH4c";

    public static final String URL_GET_CATEGORIAS = LINK_URL + "getCategorias";

    public static final String URL_ENVIAR_MENSAJE = LINK_URL + "enviarMensaje";
    public static final String URL_GET_USER_BY_ID = LINK_URL + "getUserbyId";



    //login and register are diferent
    public static final String LOGIN_URL ="http://"+IP+"/ubeprivate/imagen/login.php";
    public static final String REGISTER_URL ="http://"+IP+"/ubeprivate/imagen/upload2.php";

    //categoria
    public static final String LINK_URLCAT = "http://"+ IP+"/ubeprivate/controller/Categoria.php?funcion=";
    public static final String URL_CREATE_CATEGORIA = LINK_URLCAT + "saveCategoria";
    public static final String URL_READ_CATEGORIA = LINK_URLCAT + "readCategoria";
    public static final String URL_UPDATE_CATEGORIA = LINK_URLCAT + "updateCategoria";
    public static final String URL_DELETE_CATEGORIA = LINK_URLCAT + "deleteCategoria";


}
