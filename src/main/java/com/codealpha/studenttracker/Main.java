package com.codealpha.studenttracker;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.codealpha.studenttracker.model.Student;
import com.codealpha.studenttracker.service.StudentService;
import com.codealpha.studenttracker.service.StudentService.Statistics;
import com.codealpha.studenttracker.util.FileManager;
import com.codealpha.studenttracker.util.InputValidator;
import com.codealpha.studenttracker.util.ReportGenerator;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();

    public static void main(String[] args) {
        loadDataOnStart();
        while (true) {
            displayMainMenu();
            int option = readIntegerInput("Select an option");
            handleMenuOption(option);
        }
    }

    private static void loadDataOnStart() {
        try {
            List<Student> loadedStudents = FileManager.loadFromFile();
            studentService.loadStudents(loadedStudents);
            System.out.println("Data loaded successfully. " + loadedStudents.size() + " students available.");
        } catch (IOException ex) {
            System.out.println("[ERROR] Unable to load student data: " + ex.getMessage());
        }
    }

    private static void displayMainMenu() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("        Student Grade Tracker Menu        ");
        System.out.println("==========================================");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student Marks");
        System.out.println("5. Delete Student");
        System.out.println("6. Calculate Statistics");
        System.out.println("7. Save Data");
        System.out.println("8. Load Data");
        System.out.println("9. Sort Students");
        System.out.println("10. Display Top 3 Students");
        System.out.println("11. Display Passed Students");
        System.out.println("12. Display Failed Students");
        System.out.println("13. Export Report");
        System.out.println("14. Exit");
        System.out.println("------------------------------------------");
    }

    private static void handleMenuOption(int option) {
        switch (option) {
            case 1 -> addStudent();
            case 2 -> viewAllStudents(studentService.getAllStudents());
            case 3 -> searchStudent();
            case 4 -> updateStudentMarks();
            case 5 -> deleteStudent();
            case 6 -> displayStatistics();
            case 7 -> saveData();
            case 8 -> loadData();
            case 9 -> displaySortMenu();
            case 10 -> displayTopStudents();
            case 11 -> viewPassedStudents();
            case 12 -> viewFailedStudents();
            case 13 -> exportReport();
            case 14 -> exitApplication();
            default -> System.out.println("[ERROR] Invalid menu option. Please select from the displayed options.");
        }
    }

    private static void addStudent() {
        System.out.println("\n-- Add Student --");
        String studentId = promptForString("Enter student ID");
        if (!InputValidator.isValidStudentId(studentId)) {
            System.out.println("[ERROR] Student ID cannot be empty.");
            return;
        }
        if (studentService.findById(studentId).isPresent()) {
            System.out.println("[ERROR] Student ID already exists.");
            return;
        }

        String name = promptForString("Enter student name");
        if (!InputValidator.isValidName(name)) {
            System.out.println("[ERROR] Student name cannot be empty.");
            return;
        }

        String subject = promptForString("Enter subject");
        int marks = readIntegerInput("Enter student marks (0-100)");
        if (!InputValidator.isValidMarks(marks)) {
            System.out.println("[ERROR] Marks must be between 0 and 100.");
            return;
        }

        Student student = new Student(studentId, name, subject, marks);
        boolean added = studentService.addStudent(student);
        if (added) {
            System.out.println("[SUCCESS] Student added successfully.");
        } else {
            System.out.println("[ERROR] Failed to add student. Please try again.");
        }
    }

    private static void viewAllStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("[INFO] No student records available.");
            return;
        }
        System.out.println("\n==========================================");
        System.out.println("            All Students List            ");
        System.out.println("==========================================");
        System.out.printf("%-12s | %-20s | %-15s | %-5s | %-5s | %-10s | %-19s%n",
                "Student ID", "Name", "Subject", "Marks", "Grade", "Percentage", "Created At");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Student student : students) {
            System.out.printf("%-12s | %-20s | %-15s | %-5d | %-5s | %-9.2f%% | %-19s%n",
                    student.getStudentId(), student.getName(), student.getSubject(),
                    student.getMarks(), student.getGrade(), student.getPercentage(), student.getDateCreated());
        }
    }

    private static void searchStudent() {
        System.out.println("\n-- Search Student --");
        int option = readIntegerInput("1. Search by ID\n2. Search by Name\nSelect search option");
        if (option == 1) {
            String studentId = promptForString("Enter student ID");
            studentService.findById(studentId)
                    .ifPresentOrElse(student -> viewAllStudents(List.of(student)),
                            () -> System.out.println("[INFO] Student not found."));
        } else if (option == 2) {
            String nameQuery = promptForString("Enter student name or part of name");
            List<Student> found = studentService.findByName(nameQuery);
            if (found.isEmpty()) {
                System.out.println("[INFO] No matching students found.");
            } else {
                viewAllStudents(found);
            }
        } else {
            System.out.println("[ERROR] Invalid search option.");
        }
    }

    private static void updateStudentMarks() {
        System.out.println("\n-- Update Student Marks --");
        String studentId = promptForString("Enter student ID");
        if (studentService.findById(studentId).isEmpty()) {
            System.out.println("[ERROR] Student not found.");
            return;
        }

        int marks = readIntegerInput("Enter new marks (0-100)");
        if (!InputValidator.isValidMarks(marks)) {
            System.out.println("[ERROR] Marks must be between 0 and 100.");
            return;
        }
        boolean updated = studentService.updateStudentMarks(studentId, marks);
        if (updated) {
            System.out.println("[SUCCESS] Student marks updated successfully.");
        } else {
            System.out.println("[ERROR] Failed to update marks.");
        }
    }

    private static void deleteStudent() {
        System.out.println("\n-- Delete Student --");
        String studentId = promptForString("Enter student ID");
        boolean deleted = studentService.deleteStudent(studentId);
        if (deleted) {
            System.out.println("[SUCCESS] Student deleted successfully.");
        } else {
            System.out.println("[ERROR] Student not found.");
        }
    }

    private static void displayStatistics() {
        System.out.println("\n-- Calculate Statistics --");
        Statistics stats = studentService.calculateStatistics();
        System.out.println(ReportGenerator.buildStatisticsReport(stats));
    }

    private static void saveData() {
        try {
            FileManager.saveToFile(studentService.getAllStudents());
            System.out.println("[SUCCESS] Student data saved to " + FileManager.DATA_FILE + ".");
        } catch (IOException ex) {
            System.out.println("[ERROR] Unable to save data: " + ex.getMessage());
        }
    }

    private static void loadData() {
        loadDataOnStart();
    }

    private static void displaySortMenu() {
        System.out.println("\n-- Sort Students --");
        int option = readIntegerInput("1. Sort by Marks\n2. Sort by Name\nSelect sort option");
        if (option == 1) {
            viewAllStudents(studentService.sortByMarksDescending());
        } else if (option == 2) {
            viewAllStudents(studentService.sortByNameAscending());
        } else {
            System.out.println("[ERROR] Invalid sort option.");
        }
    }

    private static void displayTopStudents() {
        System.out.println("\n-- Top 3 Students --");
        List<Student> topStudents = studentService.getTopStudents(3);
        if (topStudents.isEmpty()) {
            System.out.println("[INFO] No student records available.");
            return;
        }
        viewAllStudents(topStudents);
    }

    private static void viewPassedStudents() {
        System.out.println("\n-- Passed Students --");
        List<Student> passed = studentService.getPassedStudents();
        if (passed.isEmpty()) {
            System.out.println("[INFO] No passed student records.");
            return;
        }
        viewAllStudents(passed);
    }

    private static void viewFailedStudents() {
        System.out.println("\n-- Failed Students --");
        List<Student> failed = studentService.getFailedStudents();
        if (failed.isEmpty()) {
            System.out.println("[INFO] No failed student records.");
            return;
        }
        viewAllStudents(failed);
    }

    private static void exportReport() {
        try {
            FileManager.exportReport(studentService.getAllStudents(), "student-report.txt");
            System.out.println("[SUCCESS] Report exported to student-report.txt.");
        } catch (IOException ex) {
            System.out.println("[ERROR] Unable to export report: " + ex.getMessage());
        }
    }

    private static void exitApplication() {
        System.out.println("Exiting application. Goodbye!");
        SCANNER.close();
        System.exit(0);
    }

    private static int readIntegerInput(String prompt) {
        while (true) {
            try {
                System.out.println(prompt + ":");
                String input = SCANNER.nextLine();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException ex) {
                System.out.println("[ERROR] Invalid numeric input. Please enter a valid number.");
            }
        }
    }

    private static String promptForString(String prompt) {
        System.out.println(prompt + ":");
        return SCANNER.nextLine().trim();
    }
}
