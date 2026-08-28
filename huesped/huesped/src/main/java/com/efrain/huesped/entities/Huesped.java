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
    @Column(name = "ID_HUESPED")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Column(name = "TELEFONO", nullable = false, length = 10)
    private String telefono;

    @Column(name = "DOCUMENTO", nullable = false, length = 30)
    private String documento;

    @Column(name = "NACIONALIDAD", nullable = false, length = 50)
    private String nacionalidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private EstadoRegistro estadoRegistro;

    private void validarDatos(
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String email,
            String telefono,
            String documento,
            String nacionalidad) {

        StringCustomUtils.validarTamanio(
                nombre, 2, 50,
                "El nombre es requerido y debe contener entre 2 y 50 caracteres"
        );

        StringCustomUtils.validarTamanio(
                apellidoPaterno, 2, 50,
                "El apellido paterno es requerido y debe contener entre 2 y 50 caracteres"
        );

        StringCustomUtils.validarTamanio(
                apellidoMaterno, 2, 50,
                "El apellido materno es requerido y debe contener entre 2 y 50 caracteres"
        );

        StringCustomUtils.validarTamanio(
                email, 5, 100,
                "El email es requerido y debe tener un formato válido"
        );

        StringCustomUtils.validarTamanio(
                telefono, 10, 10,
                "El teléfono es requerido y debe contener exactamente 10 dígitos"
        );

        StringCustomUtils.validarTamanio(
                documento, 1, 30,
                "El documento es requerido y debe contener máximo 30 caracteres"
        );

        StringCustomUtils.validarTamanio(
                nacionalidad, 1, 50,
                "La nacionalidad es requerida y debe contener máximo 50 caracteres"
        );
    }

    public void validarNoEliminado() {
        if (this.estadoRegistro == EstadoRegistro.ELIMINADO) {
            throw new IllegalArgumentException(
                    "El huésped ya se encuentra eliminado"
            );
        }
    }

    public void eliminar() {
        validarNoEliminado();
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }

    public void actualizar(
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String email,
            String telefono,
            String documento,
            String nacionalidad) {

        validarNoEliminado();

        validarDatos(
                nombre,
                apellidoPaterno,
                apellidoMaterno,
                email,
                telefono,
                documento,
                nacionalidad
        );

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
        this.email = email.trim().toLowerCase();
        this.telefono = telefono.trim();
        this.documento = documento.trim();
        this.nacionalidad = nacionalidad.trim();
    }
}

