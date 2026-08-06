package com.codealpha.studenttracker.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.codealpha.studenttracker.model.Student;

public final class FileManager {

    public static final String DATA_FILE = "students.txt";

    private FileManager() {
        // Utility class
    }

    public static List<Student> loadFromFile() throws IOException {
        List<Student> students = new ArrayList<>();
        Path filePath = Path.of(DATA_FILE);

        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length < 7) {
                    continue;
                }
                String id = parts[0].trim();
                String name = parts[1].trim();
                String subject = parts[2].trim();
                int marks = Integer.parseInt(parts[3].trim());
                String dateCreated = parts[6].trim();
                Student student = new Student(id, name, subject, marks);
                student.setStudentId(id);
                student.setName(name);
                student.setSubject(subject);
                student.setMarks(marks);
                students.add(student);
            }
        }
        return students;
    }

    public static void saveToFile(List<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }
        }
    }

    public static void exportReport(List<Student> students, String reportFileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFileName))) {
            writer.write(ReportGenerator.buildStudentReport(students));
        }
    }
}
