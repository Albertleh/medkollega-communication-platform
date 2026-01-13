# Medkollega Communication Platform

Medkollega is a full-stack web application for healthcare professionals to coordinate around patients. The frontend centers on fast access to patient context, streamlined documentation, and secure clinician-to-clinician communication.

## Project description
Medkollega is designed to streamline interdisciplinary communication in clinical settings. It provides patient records, 1:1 and group chats, and structured documentation entries so medical colleagues can share updates in context and keep care plans aligned.

## From Conception to finished Application
<img width="1079" height="571" alt="image" src="https://github.com/user-attachments/assets/03581007-3a2d-4274-b396-0ebeca72d1b8" />
<img width="3024" height="1964" alt="image" src="https://github.com/user-attachments/assets/f039f15c-c52d-465b-af8f-2093185685e7" />
<img width="3024" height="1964" alt="image" src="https://github.com/user-attachments/assets/30a08e79-4f55-4568-ac54-d5ce431702e3" />
<img width="3024" height="1964" alt="image" src="https://github.com/user-attachments/assets/7abbd7fc-d38a-4e22-83b7-f25e406d5d25" />


## Frontend highlights
- **Angular 19 UI architecture** with feature-driven components for patient workflows, chat, and account management.
- **Responsive layout** built with a hybrid utility/semantic approach (Tailwind CSS).
- **Workflow-centric UX**: patient overview, 1:1 and group chat threads, and structured documentation panels.
- **Secure authentication flows** using JWT-based session handling.

## Product overview
Medkollega streamlines interdisciplinary communication in clinical settings. It provides patient records, secure messaging, and structured documentation entries so medical colleagues can share updates in context and keep care plans aligned.

## Key capabilities
- Patient management (create, update, list, and search patient records).
- Patient-specific 1:1 chats between professionals.
- Group chats for team-based collaboration on a patient.
- Documentation entries, diagnoses (active and past), and medications per patient.
- JWT-based authentication and user context handling.

## Tech stack
**Frontend:** Angular 19, Tailwind CSS.
**Backend:** Spring Boot (Java), H2 database, JWT security.

## Repository structure
- `frontend/` — Angular client application.
- `backend/` — Spring Boot API and business logic.
- `database/` — Local H2 database files (dev only).
- `log/` — Application logs.

## Getting started

### Prerequisites
- Node.js and npm (for the frontend).
- Java 17+ and Maven (for the backend).

### Backend
From the repository root:
```bash
cd backend
mvn spring-boot:run
```
The backend runs on `http://localhost:8080` by default.

### Frontend
From the repository root:
```bash
cd frontend
npm install
npm install -g @angular/cli
npm start
```
The frontend runs on `http://localhost:4200` by default.

## API overview (selected)
- `GET /api/v1/patients` — List/search patients.
- `POST /api/v1/patients` — Create a patient.
- `GET /api/v1/patient/{patientId}/chat` — Patient chats.
- `POST /api/v1/patient/{patientId}/groupChat` — Create a group chat.
- `GET /api/v1/patient/{patientId}/textEntry` — Documentation entries.
- `GET /api/v1/patient/{patientId}/diagnosis` — Diagnoses (active/past).
- `GET /api/v1/patient/{patientId}/medication` — Medication entries.

## Contributing
1. Create a feature branch.
2. Keep changes scoped and well tested.
3. Open a pull request with a clear description.

## License
this project is currently distributed under the MIT liscense.
