import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookingService, Room } from '../../services/booking.service';

@Component({
  selector: 'app-room-manager',
  imports: [CommonModule, FormsModule],
  templateUrl: './room-manager.html',
  styleUrl: './room-manager.css',
})
export class RoomManager implements OnInit {
  rooms: Room[] = [];

  // Model for the new room form
  newRoom = {
    roomNumber: '',
    type: '',
    pricePerNight: 0,
    available: true
  };

  constructor(private bookingService: BookingService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.loadRooms();
  }

  loadRooms() {
    this.bookingService.getRooms().subscribe(data => {
      this.rooms = data;
      this.cdr.detectChanges();
    });
  }

  addRoom() {
    if (!this.newRoom.roomNumber) return;

    this.bookingService.createRoom(this.newRoom).subscribe(() => {
      this.loadRooms(); // Refresh the list
      // Reset form to defaults
      this.newRoom = { roomNumber: '', type: '', pricePerNight: 0, available: true };
    });
  }
}
