import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
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
    this.setDefaultDates();
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

  setDefaultDates() {
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    this.bookingData.checkInDate = today.toISOString().split('T')[0];
    this.bookingData.checkOutDate = tomorrow.toISOString().split('T')[0];
  }

  onSubmit() {
    // 1. Reset Status
    this.message = 'Processing...';
    this.isError = false;

    // 2. Send Request
    this.bookingService.createBooking(this.bookingData).subscribe({
      next: (response: any) => {
        console.log('Success:', response);

        // Handle response whether it's a simple string or a JSON object
        this.message = response.message || response || 'Booking Successful!';
        this.isError = false;

        // Refresh rooms
        this.loadData();

        // Optional: clear selection but keep dates
        this.bookingData.guestId = '';
        this.bookingData.roomId = '';

        this.cdr.detectChanges();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error:', error);
        this.isError = true;

        // 3. ROBUST ERROR EXTRACTION
        // Priority 1: Custom backend error message (e.g. "Transaction Failed...")
        if (error.error && error.error.error) {
          this.message = error.error.error;
        }
        // Priority 2: Standard Spring Boot "message" field
        else if (error.error && error.error.message) {
          this.message = error.error.message;
        }
        // Priority 3: Fallback to generic status text
        else {
          this.message = `Transaction Failed (${error.statusText})`;
        }

        this.cdr.detectChanges();
      }
    });
  }
}
