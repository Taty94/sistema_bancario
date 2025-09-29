import { Component } from '@angular/core';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css'],
    standalone: false
})
export class HomeComponent {
    seccion: 'clientes' | 'cuentas' | 'movimientos' | 'reportes' = 'clientes';
}
