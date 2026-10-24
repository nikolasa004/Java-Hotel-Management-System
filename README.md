# Java Hotel Management System

A desktop hotel management application I built in **Java Swing** as a university project. It was one of my earlier, larger object-oriented programming assignments, and the goal was to bring several connected hotel operations into a single application rather than to build a production-ready system.

The application has four user roles-**administrator, receptionist, guest, and housekeeper**-each with its own interface and available actions. It uses local text files for persistence, with separate classes for the domain model, business logic, and GUI.

**[Watch the full project walkthrough on YouTube](https://youtu.be/NMa7n5CEZ0g)**

## About the project

This wasn't a particularly unusual idea for a university assignment: a hotel system with rooms, reservations, employees, and guests. What made it an interesting exercise for me was the amount of functionality that had to work together.

I had to think about what each type of user should be allowed to do, how a reservation affects room availability, how check-in and check-out change room status, and how to keep those changes saved between application runs. I also spent a considerable amount of time working with Swing forms, tables, input validation, and reports.

The result is a fairly traditional Java desktop application. The interface is simple and some of the code reflects my experience at the time, but it was a useful project for practicing **OOP, GUI development, file handling, and testing** on something larger than an isolated programming exercise.

## Features

### Administrator

- View and manage administrators, receptionists, housekeepers, guests, rooms, room types, reservations, additional services, and price lists through CRUD interfaces.
- Add employees and manage their information.
- Review hotel activity, revenue, reservations, room usage, and housekeeper activity.
- View reports and charts, including revenue by room type.

### Receptionist

- Register guests and create reservations on their behalf.
- Review and filter reservations.
- Handle guest **check-in**, including room assignment and additional services.
- Handle **check-out**, changing a room's status and sending it into the cleaning workflow.
- Access guest, room occupancy, and daily arrival/departure reports.

### Guest

- Create reservations by choosing dates, room type, number of guests, and optional services or room features.
- Review existing reservations and cancel them where supported.

### Housekeeper

- See rooms assigned for cleaning.
- Mark a room as cleaned, updating its status and recording the completed work.

### Shared application logic

- Reservation status tracking and room-availability checks.
- Price calculations involving room types and additional services.
- Text-file persistence for users, rooms, reservations, pricing, and reports.
- JUnit 5 tests for the manager and business-logic classes.

## Video walkthrough

**[Java Swing Hotel Management System - Full Project Showcase](https://youtu.be/NMa7n5CEZ0g)**

The video goes through the application from the perspectives of all four user roles, demonstrating the administrative CRUD interfaces, guest and reservation management, check-in/check-out, housekeeping, and reporting features.

## Screenshots

### User interfaces

| Administrator dashboard | Receptionist dashboard |
| :---: | :---: |
| ![Administrator dashboard with entity management, reports, and employee management menus](docs/images/Screenshot%202026-10-24%20154808.png) | ![Receptionist dashboard with guest management, reservations, check-in, check-out, and reports](docs/images/Screenshot%202026-10-24%20154755.png) |
| **Guest dashboard** | **Housekeeper view** |
| ![Guest dashboard with options to create and review reservations](docs/images/Screenshot%202026-10-24%20154819.png) | ![Housekeeper view showing a room assigned for cleaning](docs/images/Screenshot%202026-10-24%20154934.png) |

### Hotel operations

| Guest management - CRUD | Editing guest information |
| :---: | :---: |
| ![CRUD table for guest records with add, edit, and delete actions](docs/images/Screenshot%202026-10-24%20154420.png) | ![Guest information form with personal details and associated reservations](docs/images/Screenshot%202026-10-24%20154453.png) |
| **Creating a reservation** | **Viewing reservations** |
| ![Reservation form with dates, room type, additional services, and room features](docs/images/Screenshot%202026-10-24%20154530.png) | ![Table of reservations showing status, dates, prices, rooms, and guests](docs/images/Screenshot%202026-10-24%20154544.png) |
| **Guest check-in** | **Revenue chart** |
| ![Check-in workflow with room selection and confirmation of additional services](docs/images/Screenshot%202026-10-24%20154743.png) | ![Monthly revenue chart broken down by room type](docs/images/Screenshot%202026-10-24%20154509.png) |

## Technologies

- **Java 17** - application and business logic
- **Java Swing** - desktop user interface
- **MigLayout 5.3** - GUI layout management
- **XChart 3.8.8** - charts and visual reports
- **JUnit 5** - unit tests
- **Text files** - local data storage (no database server required)
- **Eclipse** - original project structure and configuration

The third-party runtime JARs used by the application are included under `Projekat/mig/` and `Projekat/xChart/`.

## Running the project

This repository contains the **Eclipse project source code**, not a packaged desktop installer. The easiest way to run it is through Eclipse:

1. Install **JDK 17** and a version of Eclipse with Java development support.
2. Clone or download this repository.
3. In Eclipse, choose **File → Import → General → Existing Projects into Workspace**.
4. Select the repository's **`Projekat/`** directory as the root directory and import the detected project.
5. Check that the Java 17 JRE and the included MigLayout/XChart JAR dependencies are resolved in the build path. JUnit 5 is configured through Eclipse's JUnit container.
6. Run **`projekat.Hotel`** as a Java application.

The program loads its sample records from the `datoteke/` folder at startup. **Keep the working directory set to `Projekat/`** so the relative paths to these files resolve correctly. The included data files contain the example accounts and records used by the application.

> **Note:** The application writes changes back to the local text files. Consider keeping a backup of `Projekat/datoteke/` if you want to reset the demo data after experimenting. Tests may also read from or write to local files.

## Project structure

```text
Java-Hotel-Management-System/
├── Projekat/
│   ├── src/projekat/
│   │   ├── enumi/             # Statuses and other enums
│   │   ├── gui/               # Swing windows for the four user roles
│   │   ├── korisnici/         # User and employee models
│   │   ├── menadzeri/         # Business logic and file operations
│   │   ├── rezervacije/       # Reservation model
│   │   ├── sobe/              # Room models
│   │   ├── usluge/            # Prices and additional services
│   │   ├── testovi/           # JUnit tests
│   │   └── Hotel.java         # Application entry point
│   ├── datoteke/              # Sample data and persistent records
│   ├── mig/                   # MigLayout dependencies
│   ├── xChart/                # XChart dependency
│   ├── .classpath
│   └── .project
├── docs/images/               # Screenshots
└── README.md
```

## Limitations and notes

This is an **educational project**, not software intended for real hotel operations. Authentication and data storage are intentionally simple: credentials are stored in plain text, access control is implemented within the desktop application, and there is no database server or multi-user backend. The application should **not** be used with real guest information or passwords.

I have kept the project close to the version developed for university, including its original GUI approach and architecture, rather than rewriting it to look like a modern production system. It represents an earlier stage of my programming experience and the work I put into learning how to build a larger Java application.

The project uses third-party **MigLayout** and **XChart** libraries; those libraries belong to their respective authors and are not my original work.
