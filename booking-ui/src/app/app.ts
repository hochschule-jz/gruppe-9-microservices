import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookingComponent } from './components/booking/booking';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, BookingComponent],
  template: `<app-booking></app-booking>`,
  styleUrls: ['./app.css']
})
export class AppComponent {
  title = 'booking-frontend';
}
