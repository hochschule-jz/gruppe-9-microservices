import { Routes } from '@angular/router';
import {BookingComponent} from './components/booking/booking';
import {RoomManager} from './components/room-manager/room-manager';
import {GuestManager} from './components/guest-manager/guest-manager';

export const routes: Routes = [
  { path: '', component: BookingComponent },
  { path: 'guests', component: GuestManager },
  { path: 'rooms', component: RoomManager }
];
