package model;

public class Actividad {

    private int    idActividad;
    private String nombre;
    private String descripcion;
    private int    aforoMaximo;
    private int    duracionMin;
    private int    idInstructor;
    private String nombreInstructor; // campo auxiliar para mostrar en listados
    private boolean activa;

    public Actividad(int idActividad, String nombre, String descripcion,
                     int aforoMaximo, int duracionMin, int idInstructor,
                     String nombreInstructor, boolean activa) {
        this.idActividad      = idActividad;
        this.nombre           = nombre;
        this.descripcion      = descripcion;
        this.aforoMaximo      = aforoMaximo;
        this.duracionMin      = duracionMin;
        this.idInstructor     = idInstructor;
        this.nombreInstructor = nombreInstructor;
        this.activa           = activa;
    }

    public Actividad(String nombre, String descripcion, int aforoMaximo,
                     int duracionMin, int idInstructor) {
        this.nombre       = nombre;
        this.descripcion  = descripcion;
        this.aforoMaximo  = aforoMaximo;
        this.duracionMin  = duracionMin;
        this.idInstructor = idInstructor;
        this.activa       = true;
    }

    // Getters
    public int     getIdActividad()      { return idActividad; }
    public String  getNombre()           { return nombre; }
    public String  getDescripcion()      { return descripcion; }
    public int     getAforoMaximo()      { return aforoMaximo; }
    public int     getDuracionMin()      { return duracionMin; }
    public int     getIdInstructor()     { return idInstructor; }
    public String  getNombreInstructor() { return nombreInstructor; }
    public boolean isActiva()            { return activa; }

    // Setters
    public void setNombre(String nombre)       { this.nombre      = nombre; }
    public void setDescripcion(String desc)    { this.descripcion = desc; }
    public void setAforoMaximo(int aforo)      { this.aforoMaximo = aforo; }
    public void setActiva(boolean activa)      { this.activa      = activa; }

    @Override
    public String toString() {
        return String.format("[%d] %s | Instructor: %s | Aforo: %d | %d min",
                idActividad, nombre, nombreInstructor, aforoMaximo, duracionMin);
    }
}