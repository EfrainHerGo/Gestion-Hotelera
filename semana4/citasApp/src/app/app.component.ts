import { AfterViewInit, Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'

})
export class AppComponent {
  
  
  title = 'Hola mundo';
  
  /*
    {path: 'dashcboard', component: DashboardComponent},
otra: string = "Otro valor";
  contador: number = 0;
  limir:boolean = false 

  ngOnInit(): void {
    alert ("Se esta iniciando el componente");
  }
  ngAfterViewInit(): void {
    alert ('Se renderizo a la vista componente');
  }



  aumentarContador(): void{
    if(!this.limir){
    this.contador++;
    }
    this.limir = this.contador > 10 ? true : false;
  }*/
}
