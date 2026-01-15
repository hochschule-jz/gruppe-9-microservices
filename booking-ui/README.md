# 🏨 Hotel Booking Platform - Frontend

This is the user interface for the Hotel Booking Microservices Platform. It is built with **Angular** and designed to interact with the backend services via the API Gateway.

The application allows users to manage guests, rooms, and perform bookings with real-time validation and error handling.

---

## 🚀 Features

### 1. Booking Management (Home)
* **Booking Process:** Select a Guest and a Room to create a booking.
* **Saga Pattern Feedback:** The UI handles complex distributed transaction errors.
  * *Example:* If a guest named "BadPayer" tries to book, the backend rolls back the transaction, and the UI displays the specific error: `❌ Transaction Failed: Payment Rejected`.
* **Availability:** Automatically updates room availability after a successful booking.

### 2. Guest Management
* **Create Guests:** Registration form with strict validation.
  * **Email:** Validates format (Angular built-in).
  * **Phone:** Validates international formats using Regex: `^[0-9+\s()-]{7,15}$`.
* **Directory:** Lists all guests.
  * Displays "No email/phone provided" placeholders for legacy data.
  * Visual status badges for missing contact info.

### 3. Room Management
* **Inventory Control:** Create new rooms with Types (e.g., "Deluxe") and Prices.
* **Status Tracking:** Visual indicators for `Available` vs `Booked` rooms.

---

## 🛠️ Technology Stack

* **Framework:** Angular (Standalone Components)
* **Styling:** CSS3 (Flexbox, Card Layouts, Responsive Design)
* **State Management:** RxJS (Observables)
* **Production Server:** Nginx (via Docker)

---

## 🏃‍♂️ How to Run

### Option 1: Via Docker (Recommended)
This frontend is part of the main `docker-compose.yml`. It runs automatically when you start the full platform.

```bash
# From the project root
docker compose up -d --build
