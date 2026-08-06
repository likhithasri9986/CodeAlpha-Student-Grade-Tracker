# Student Grade Tracker

## Project Description

Student Grade Tracker is a console-based Java application designed for managing student assessments and academic progress. It provides a professional interface to add, update, search, sort, and report student grades while preserving data in a text file.

## Features

- Add and manage student records
- View all students in a formatted table
- Search students by ID or name
- Update marks and recalculate grades automatically
- Delete student records
- Display academic statistics and top performers
- Sort students by marks or name
- View passed / failed students
- Export a formatted report to a text file
- Automatic data load from `students.txt`
- Save student data to `students.txt`

## Technologies Used

- Java 17
- Maven
- Object-oriented design
- Collections API (`ArrayList`)

## Folder Structure
```
StudentGradeTracker/
├── pom.xml
├── README.md
└── src/main/java/com/codealpha/studenttracker
    ├── Main.java
    ├── model/Student.java
    ├── service/StudentService.java
    └── util
        ├── FileManager.java
        ├── InputValidator.java
        └── ReportGenerator.java
```

## How to Run

1. Open a terminal in the project root.
2. Build the application with Maven:
   ```bash
   mvn clean package
   ```
3. Run the application:
   ```bash
   java -jar target/student-grade-tracker-1.0.0-jar-with-dependencies.jar
   ```

## Screenshots

> Screenshots will be added after implementation.

## Future Enhancements

- Add support for multiple subjects per student
- Add persistence with a database
- Add user authentication and role-based permissions
- Add CSV import/export support
- Add reports for failing students and grade distributions

## Author

CodeAlpha Internship Candidate
