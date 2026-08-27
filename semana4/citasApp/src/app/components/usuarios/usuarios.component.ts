import { Component, ElementRef, OnInit, ViewChild, viewChild } from '@angular/core';
import { UsuarioRequest, UsuarioResponse } from '../../models/Usuario.model';
import {Validators, FormBuilder, FormGroup} from '@angular/forms'
import Swal from 'sweetalert2';
import { DescripcionesRoles, Roles } from '../../constants/Roles';
import { UsuariosService } from '../../services/usuarios.service';
import { SequenceError } from 'rxjs';

declare var bootstrap: any;


@Component({
  selector: 'app-usuarios',
  standalone: false,
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css'
})
export class UsuariosComponent implements OnInit{
  
  usuarios: UsuarioResponse[] = [];
  textoModal: string = "Registrar usuario";
  usuarioForm: FormGroup;
  roles: string[] = Object.values(Roles);

  @ViewChild('usuarioModalRef') usuariosModelEl!: ElementRef;

  private modalInstance!: any;
  isEditMode: boolean = false;
  selectedUsuario:UsuarioResponse | null = null;

  constructor(private fb: FormBuilder, private usuarioService: UsuariosService) {
    this.usuarioForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(20)] ],
      password: ['', [Validators.required, Validators.minLength(8)]],
      roles:[[], [Validators.required]]

    })
  } 
  ngOnInit(): void {

    /* this.usuarios = [
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
    ]; */
    //console.info('Usuarios', this.usuarios);
    this.listarUsuario();
  }
  listarUsuario():void {
    this.usuarioService.getUsuarios().subscribe({
      next: resp => this.usuarios = resp,
      error: (error) => {
        console.log(error);
        Swal.fire('Error', 'No se pudieron cargar los usuarios', 'error');
      }
    })
  }
  ngAfterViewInit(): void {
    this.modalInstance = new bootstrap.Modal(this.usuariosModelEl.nativeElement, {keyboard : false});
    this.usuariosModelEl.nativeElement.addEventListener('hidden.bs.modal', () => {
      this.resetForm();
    })
  }
  resetForm(): void{
    this.usuarioForm.reset();
    this.isEditMode = false;
    this.usuarioForm.get('roles')?.setValue([]);
  }

  toggleForm(): void{
    this.textoModal = "Registrar usuario";
    this.modalInstance.show();
  } 

  editarUsuario(usuario: UsuarioResponse): void{
    this.isEditMode = true;
    this.selectedUsuario = usuario;
    this.textoModal = 'Actualizado usuario' + usuario.username;

    this.usuarioForm.patchValue({... usuario});
    this.modalInstance.show();
  }
  onSubmit(): void{

    if(this.usuarioForm.invalid) return;

    const datoUsuarios: UsuarioRequest = this.usuarioForm.value;

    if(this.isEditMode && this.selectedUsuario){
      //actualizar
    }else{//Registrar
      this.usuarioService.postUsuario(datoUsuarios).subscribe({
        next: nuevoUsuario => {
          this.usuarios.push(nuevoUsuario);
          Swal.fire("Registrado", "Registrado con exito", 'success');
          this.modalInstance.hide();
        }
      });
    }


    //alert(this.usuarioForm.value)
  }

  transformarRol(rol: string): string{
    return DescripcionesRoles[rol as Roles] || 'Desconocido';
  }
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
          this.usuarioService.deleteUsuario(username).subscribe({
            next: () => {
              this.usuarios = this.usuarios.filter(u => u.username !== username);
              Swal.fire('eliminado', `usuario elimnado`);
            }
          })


      }
    })
  }

}
