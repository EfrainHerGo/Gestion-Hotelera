package com.efrain.huesped.entities;

import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.Common.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HUESPEDES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Huesped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEDICO")
    private Long id;
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Column(name = "TELEFONO", nullable = false, length = 10)
    private String telefono;

    @Column(name = "DOCUMENTO", length = 12, nullable = false)
    private String documento;

    @Column(name = "Nacionalidad", nullable = false)
    private String nacionalidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private EstadoRegistro estadoRegistro;

    private void validadDatos(String nombre, String apellidoPaterno,
                              String apellidoMaterno, String email,
                              String telefono, String documento,String nacionalidad) {
        StringCustomUtils.validarTamanio(nombre, 1, 50,
                "El nombre es requerido y debe contener entre 1 y 50 caracteres");
        StringCustomUtils.validarTamanio(apellidoPaterno, 1, 50,
                "El apellido paterno es requerido y debe contener entre 1 y 50 caracteres");
        StringCustomUtils.validarTamanio(apellidoMaterno, 1, 50,
                "El apellido materno es requerido y debe contener entre 1 y 50 caracteres");
        StringCustomUtils.validarTamanio(email, 1, 100,
                "El email es requerido y debe contener entre 1 y 50 caracteres");
        StringCustomUtils.validarTamanio(telefono, 10, 10,
                "El telefono es requerido y debe contener 10 caracteres");
        StringCustomUtils.validarTamanio(documento, 1, 20,
                "La documento es requerido y debe contener entre 1 a 20 caracteres");
        StringCustomUtils.validarTamanio(nacionalidad, 1, 50,
                "La nacionalidad es requerido y debe contener entre 1 a 50 caracteres");


    }
    public void validarNoEliminado(){
        if (this.estadoRegistro == EstadoRegistro.ELIMINADO)
            throw new IllegalArgumentException("El medico ya est eliminado");
    }
    public  void eliminar (){
        validarNoEliminado();
        this.estadoRegistro = EstadoRegistro.ELIMINADO;

    }

    public void actualizar(String nombre, String apellidoPaterno,
                           String apellidoMaterno, String email,
                           String telefono, String documento,String nacionalidad) {
        validarNoEliminado();
        validadDatos(nombre, apellidoPaterno, apellidoMaterno,
                email, telefono, documento, nacionalidad);
        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
        this.email = email.trim().toLowerCase();
        this.telefono = telefono.trim();
        this.documento = documento.trim();
        this.nacionalidad = nacionalidad.trim();
    }

}
