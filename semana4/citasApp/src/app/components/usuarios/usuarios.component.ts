import { Component, OnInit } from '@angular/core';
import { UsuarioResponse } from '../../models/Usuario.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-usuarios',
  standalone: false,
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css'
})
export class UsuariosComponent implements OnInit{
  ngOnInit(): void {
    this.usuarios = [
      {username: 'UsuarioJP',
      roles: ['ROLE_ADMIN']
    },
    {
      username:'Administrador',
      roles:['ROLE_ADMIN']
    },
    {
      username: 'Usuario',
      roles: ['ROLE_USER']
    }
    ];
    console.info('Usuarios', this.usuarios);
  }
  usuarios:UsuarioResponse[] = [];

  eliminarUsuario(username: string): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: `El usuario ${username} sera eliminado permanetamente`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: "Si, eliminar",
      cancelButtonText: 'Cancelar'
    }).then(result => {
      if(result.isConfirmed){
            this.usuarios = this.usuarios.filter(u => u.username !== username);

      }
    })
  }

}
