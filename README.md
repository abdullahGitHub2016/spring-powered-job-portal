BdJobs Portal - Full Stack Application

A full-stack job portal clone inspired by BDJobs, built using Spring Boot (Backend) and Vue 3 (Frontend).

🚀 Features

Job Management: Create, Read, Update, and Delete job postings.

Job Applications: Users can apply for jobs with their details and cover letters.

Admin View: Specialized view to see all incoming job applications.

Responsive UI: Clean, modern interface styled with Tailwind CSS.

🛠️ Tech Stack

Backend

Java 17+

Spring Boot 3.x

Spring Data JPA (Hibernate)

MySQL (Database)

Lombok (for boilerplate reduction)

Frontend

Vue 3 (Composition API)

Vue Router

Tailwind CSS (Styling)

Lucide Icons

⚙️ Backend Setup (myjobs-backend)

Database Configuration:
Create a MySQL database named bdjobs_db (or as configured in your properties).

Configure application.properties:
Ensure your database credentials match:

spring.datasource.url=jdbc:mysql://localhost:3306/bdjobs_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update


Run the Application:
Using Maven:

mvn spring-boot:run


The API will be available at http://localhost:8080.

💻 Frontend Setup (myjobs-frontend)

Install Dependencies:

npm install


Run Development Server:

npm run dev


The frontend will be available at http://localhost:5173.

📡 API Endpoints

Jobs

GET /api/jobs - Retrieve all job postings.

GET /api/jobs/{id} - Retrieve a specific job.

POST /api/jobs - Create a new job.

PUT /api/jobs/{id} - Update an existing job.

DELETE /api/jobs/{id} - Remove a job.

Applications

POST /api/applications - Submit a new job application.

GET /api/applications - View all submitted applications (Admin).

📁 Project Structure

├── myjobs-backend
│   └── src/main/java/com/abdullah/bdjobs_backend
│       ├── controller/   # REST Controllers (Job, Application)
│       ├── entity/       # JPA Entities (JobPost, JobApplication)
│       └── repository/   # Data Access Interfaces
└── myjobs-frontend
    ├── src
    │   ├── views/        # Page components (Home, Details, Admin)
    │   └── router/       # Navigation logic
    └── index.html        # Main entry point


📝 Troubleshooting

CORS Error: Ensure @CrossOrigin(origins = "*") is present on all controllers.

405 Method Not Allowed: Restart the Spring Boot server after adding new @GetMapping or @PostMapping methods.

Table not found: Check if spring.jpa.hibernate.ddl-auto is set to update or create.