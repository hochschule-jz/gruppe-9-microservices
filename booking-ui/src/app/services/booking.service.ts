import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// DTOs to match your Backend
export interface Guest {
  id: string;
  firstName: string;
  lastName: string;
}

export interface Room {
  id: string;
  roomNumber: string;
  type: string;
  pricePerNight: number;
  available: boolean;
}

export interface BookingRequest {
  guestId: string;
  roomId: string;
  checkInDate: string;
  checkOutDate: string;
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  // Point to API Gateway (Port 8080)
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getGuests(): Observable<Guest[]> {
    return this.http.get<Guest[]>(`${this.baseUrl}/guests`);
  }

  getRooms(): Observable<Room[]> {
    return this.http.get<Room[]>(`${this.baseUrl}/rooms`);
  }

  createBooking(booking: BookingRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/bookings`, booking);
  }
}
