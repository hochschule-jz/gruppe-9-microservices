import {Component, OnInit, ChangeDetectorRef, ViewChild} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, NgForm} from '@angular/forms';
import { BookingService, Guest } from '../../services/booking.service';

@Component({
  selector: 'app-guest-manager',
  imports: [CommonModule, FormsModule],
  templateUrl: './guest-manager.html',
  styleUrl: './guest-manager.css',
})
export class GuestManager implements OnInit {
  @ViewChild('guestForm') guestForm!: NgForm;

  guests: Guest[] = [];

  newGuest = {
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: ''
  };

  constructor(private bookingService: BookingService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.loadGuests();
  }

  loadGuests() {
    this.bookingService.getGuests().subscribe({
      next: (data) => {
        this.guests = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Failed to load guests', err)
    });
  }

  addGuest() {
    // Safety check using the form's status
    if (this.guestForm.invalid) return;

    this.bookingService.createGuest(this.newGuest).subscribe(() => {
      this.loadGuests();
      this.guestForm.resetForm();
    });
  }
}
