import { Component, Input } from '@angular/core';
import { Cuenta } from '../../models/cuenta.model';

@Component({
    selector: 'app-cuenta-detail',
    templateUrl: './cuenta-detail.component.html',
    styleUrls: ['./cuenta-detail.component.css'],
    standalone: false
})
export class CuentaDetailComponent {
    @Input() cuenta: Cuenta | null = null;
}
