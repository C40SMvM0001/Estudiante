package com.android.firebase.estudiante.Modelo;

public class Estudiante {

    private String IdEstudiante;
    private String Nombre;
    private String Apellido;
    private String Correo;
    private String Telefono;
    private String Ci;

    public Estudiante() {
    }

    public String getIdEstudiante() {
        return IdEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        IdEstudiante = idEstudiante;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getCi() {
        return Ci;
    }

    public void setCi(String ci) {
        Ci = ci;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
