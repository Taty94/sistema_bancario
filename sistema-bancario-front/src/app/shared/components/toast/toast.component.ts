import { Component } from '@angular/core';

type ToastType = 'success' | 'error' | 'info' | 'warning';

@Component({
    selector: 'app-toast',
    templateUrl: './toast.component.html',
    styleUrls: ['./toast.component.css'],
    standalone: false
})
export class ToastComponent {
    visible = false;
    message = '';
    code: string | null = null;
    type: ToastType = 'info';
    timeout: any;

    show(message: string, type: ToastType = 'info', code?: string, duration: number = 4000) {
        this.message = message;
        this.type = type;
        this.code = code || null;
        this.visible = true;
        if (this.timeout) clearTimeout(this.timeout);
        this.timeout = setTimeout(() => this.hide(), duration);
    }

    hide() {
        this.visible = false;
        this.message = '';
        this.code = null;
    }
}
