import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// DTOs
export interface Guest {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
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
  // Point to API Gateway
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

  createGuest(guest: Partial<Guest>): Observable<Guest> {
    return this.http.post<Guest>(`${this.baseUrl}/guests`, guest);
  }

  createRoom(room: any): Observable<Room> {
    return this.http.post<Room>(`${this.baseUrl}/rooms`, room);
  }
}
