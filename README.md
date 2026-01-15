# Hotel Booking Platform

This project is a fully containerized microservices application using **Spring Boot**, **Angular**, **MongoDB**, and **Docker**. It implements the **Saga Pattern** to handle distributed transactions across services.

---

## 🏗️ Project Architecture

The system is designed as a set of decoupled microservices, each with its own database, communicating via REST APIs.

### 1. Infrastructure Services
* **Discovery Service (Eureka):** Acts as the phonebook. All services register here so they can find each other dynamically without hardcoded IPs.
* **API Gateway (Spring Cloud Gateway):** The single entry point (Port `8080`). It routes external traffic from the frontend to the correct microservice and handles CORS.

### 2. Domain Microservices
* **Guest Service:** Manages guest profiles.
   * *Database:* MongoDB (guest-db)
* **Room Service:** Manages room inventory and availability status.
   * *Database:* MongoDB (room-db)
* **Booking Service:** The "Orchestrator". It coordinates the booking process between guests and rooms.
   * *Database:* MongoDB (booking-db)

### 3. Frontend
* **Angular:** A modern web interface served via **Nginx** inside a Docker container. It communicates solely with the API Gateway.

---

## ⚙️ The Booking Saga (Core Logic)

The core complexity of this application lies in the **Booking Process**. Since each service has its own database, we cannot use a standard ACID database transaction. Instead, we use the **Saga Pattern (Orchestration)** to ensure data consistency.

### How a Booking Works:
When a user clicks "Book Room", the `BookingService` executes these steps sequentially:

1.  **Validation (Guest Service):** Checks if the `guestId` exists. If not, the process aborts.
2.  **Reserve Room (Room Service):**
   * Sends a request to lock the selected room (`isAvailable: false`).
   * If the room is already taken, the transaction fails immediately.
3.  **Process Payment (Mock Logic):**
   * Simulates a payment gateway.
   * **Logic:** If the Guests Last Name is `BadPayer`, this step **intentionally crashes** to simulate "Insufficient Funds".
4.  **Finalize Booking:**
   * If all steps above pass, the booking is saved as `CONFIRMED`.

### 🔄 The Rollback (Compensation)
If **Step 3 (Payment)** fails, the system is in an invalid state: the Room is locked, but the Booking failed. The Saga Orchestrator catches the error and triggers a **Compensating Transaction**:

* **Action:** The `BookingService` calls the `RoomService` again to **Release the Room** (set `isAvailable: true`).
* **Result:** The system returns to its original clean state, ensuring no rooms are "stuck" as reserved for failed bookings.

---

## 🚀 Starting the Application

The entire platform runs via Docker Compose.

1.  **Prerequisites**
   * Install Docker Desktop.
   * Ensure ports `8080`, `4200`, `8761`, and `27017-27019` are free.

2.  **Start the System**
    Open a terminal in the project root and run:
    ```bash
    docker compose up -d --build
    ```

3.  **Verify & Access**
   * **Frontend (UI):** [http://localhost:4200](http://localhost:4200)
   * **API Gateway:** [http://localhost:8080](http://localhost:8080)
   * **Eureka Dashboard:** [http://localhost:8761](http://localhost:8761)

---

## 🧪 Testing the Saga Rollback
To see the custom logic in action:
1.  Go to the UI ([http://localhost:4200](http://localhost:4200)).
2.  Create a Guest with Last Name `BadPayer`
3.  Create a Room.
4.  Go to the Bookings page.
4.  Select the **Guest** (BadPayer) and **Available Room** and try to book it.
5.  **Observation:**
   * The UI shows an error.
   * The Booking is NOT created.
   * The Room remains **Available** (because the Saga successfully rolled back the lock).