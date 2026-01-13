import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookingService, BookingRequest, Guest, Room } from '../../services/booking.service';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './booking.html',
  styleUrls: ['./booking.css']
})
export class BookingComponent implements OnInit {

  guests: Guest[] = [];
  rooms: Room[] = [];

  bookingData: BookingRequest = {
    guestId: '',
    roomId: '',
    checkInDate: '',
    checkOutDate: ''
  };

  message: string = '';
  isError: boolean = false;

  constructor(private bookingService: BookingService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    // Fetch Guests
    this.bookingService.getGuests().subscribe(data => {
      this.guests = data;
      this.cdr.detectChanges();
    });
    // Fetch Rooms
    this.bookingService.getRooms().subscribe(data => {
      this.rooms = data;
      this.cdr.detectChanges();
    });
  }

  onSubmit() {
    this.message = 'Processing...';
    this.isError = false;

    this.bookingService.createBooking(this.bookingData).subscribe({
      next: (response) => {
        console.log(response.message);
        this.message = response.message || 'Booking Successful!';
        this.isError = false;
        // Refresh rooms to update availability status
        this.loadData();
        this.cdr.detectChanges();
      },
      error: (error) => {
        this.isError = true;
        this.message = error.error.error || 'Transaction Failed & Rolled Back!';
      }
    });
  }
}
