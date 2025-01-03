# Citronix

## Project Description
Citronix is a Java-based management application designed for lemon farm operations. It provides farmers with tools to efficiently track production, harvests, and sales of lemons while optimizing productivity monitoring. The application supports the management of farms, fields, trees, harvests, and sales, ensuring a streamlined and user-friendly experience for managing all aspects of a lemon farm.

## General Objective
The primary goal of Citronix is to empower lemon farmers by allowing them to:
- Manage farms, fields, and individual lemon trees.
- Track harvest details, including quantities and associated sales.
- Dynamically calculate and monitor productivity per tree based on its age.
- Manage sales and calculate revenues efficiently.

## Technologies Used
- **Java 17**: Core programming language for the application.
- **Spring Boot**: Framework for developing the backend.
- **Spring Data JPA**: For interacting with the database.
- **PostgreSQL**: Database management system.
- **Lombok**: Simplifies boilerplate code in Java.
- **Maven**: Build automation tool for managing dependencies and project structure.
- **Git**: Version control system to track changes.

## Project Structure
- **Controller/**: Handles HTTP requests and maps them to service methods.
- **Service/**: Contains business logic and service methods.
- **Repository/**: Manages data retrieval and interaction with the database.
- **Model/**: Defines entities like Farm, Field, Tree, Harvest, Sale, etc.
- **DTO/**: Contains data transfer objects for cleaner communication between layers.
- **Mapper/**: Maps entities to DTOs and vice versa.
- **Exception/**: Handles custom exceptions and global error handling.

## Features
- **Farm Management**: Add, update, and delete farms with associated fields and trees.
- **Harvest Management**: Record harvests, dynamically calculate total quantities and revenues, and ensure one harvest per season per tree.
- **Sales Management**: Manage sales, calculate remaining quantities, and dynamically compute revenues.
- **Productivity Monitoring**: Track the productivity of each tree and identify areas for improvement.

## Diagrams
- **Use Case Diagram**: ![Use Case Diagram](/project_resources/UML/classDiagram.png)

## Installation and Usage

### Prerequisites
- **Java 17** or later.
- **PostgreSQL** installed and running.
- **Maven** for dependency management and project builds.

### Database Setup
1. Create a PostgreSQL database named `citronix`.

### Running the Application
1. Clone this repository:
   ```bash
   git clone https://github.com/your-repo/Citronix.git
   ```
   
2. Navigate to the project directory:
   ```bash
   cd Citronix
   ```
   
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
   
4. Run the application:
   ```bash
   java -jar target/citronix.jar
   ```

## Project Management
- [Project Planning in Jira](https://meskinemsoatafa.atlassian.net/jira/software/projects/CT/boards/15/backlog?epics=visible)

## Project Presentation
- [Canva Presentation](https://www.canva.com/design/DAGXr4W5iv8/_tV43mLmFRBZQDqwBKJDWQ/edit?utm_content=DAGXr4W5iv8&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

## Author
- **Meskine Mostafa**
    - Email: meskinemostafa4@gmail.com
    - GitHub: [Meskine Mostafa](https://github.com/MesVortex)