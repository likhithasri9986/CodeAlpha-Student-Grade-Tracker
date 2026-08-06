package com.codealpha.studenttracker.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.codealpha.studenttracker.model.Student;

public class StudentService {

    private final List<Student> students;

    public StudentService() {
        this.students = new ArrayList<>();
    }

    public boolean addStudent(Student student) {
        if (findById(student.getStudentId()).isPresent()) {
            return false;
        }
        students.add(student);
        return true;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Optional<Student> findById(String studentId) {
        return students.stream()
                .filter(student -> student.getStudentId().equalsIgnoreCase(studentId.trim()))
                .findFirst();
    }

    public List<Student> findByName(String nameQuery) {
        String normalizedName = nameQuery.trim().toLowerCase();
        return students.stream()
                .filter(student -> student.getName().toLowerCase().contains(normalizedName))
                .collect(Collectors.toList());
    }

    public boolean updateStudentMarks(String studentId, int marks) {
        Optional<Student> optionalStudent = findById(studentId);
        if (optionalStudent.isEmpty()) {
            return false;
        }
        optionalStudent.get().setMarks(marks);
        return true;
    }

    public boolean deleteStudent(String studentId) {
        return students.removeIf(student -> student.getStudentId().equalsIgnoreCase(studentId.trim()));
    }

    public Statistics calculateStatistics() {
        if (students.isEmpty()) {
            return new Statistics(0, 0, 0, 0, 0, 0, 0, 0, "N/A");
        }

        int totalStudents = students.size();
        int sumMarks = students.stream().mapToInt(Student::getMarks).sum();
        int highestMarks = students.stream().mapToInt(Student::getMarks).max().orElse(0);
        int lowestMarks = students.stream().mapToInt(Student::getMarks).min().orElse(0);
        long passCount = students.stream().filter(student -> student.getMarks() >= 50).count();
        long failCount = totalStudents - passCount;
        double averageMarks = (double) sumMarks / totalStudents;
        double passPercentage = totalStudents == 0 ? 0 : (passCount * 100.0) / totalStudents;
        double failPercentage = totalStudents == 0 ? 0 : (failCount * 100.0) / totalStudents;
        String topPerformer = students.stream()
                .max(Comparator.comparingInt(Student::getMarks))
                .map(student -> String.format("%s (%s)", student.getName(), student.getStudentId()))
                .orElse("N/A");

        return new Statistics(totalStudents, averageMarks, highestMarks, lowestMarks,
                passCount, failCount, passPercentage, failPercentage, topPerformer);
    }

    public List<Student> getTopStudents(int limit) {
        return students.stream()
                .sorted(Comparator.comparingInt(Student::getMarks).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Student> getFailedStudents() {
        return students.stream()
                .filter(student -> student.getMarks() < 50)
                .sorted(Comparator.comparingInt(Student::getMarks))
                .collect(Collectors.toList());
    }

    public List<Student> getPassedStudents() {
        return students.stream()
                .filter(student -> student.getMarks() >= 50)
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    public List<Student> sortByMarksDescending() {
        return students.stream()
                .sorted(Comparator.comparingInt(Student::getMarks).reversed())
                .collect(Collectors.toList());
    }

    public List<Student> sortByNameAscending() {
        return students.stream()
                .sorted(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public void loadStudents(List<Student> loadedStudents) {
        students.clear();
        students.addAll(loadedStudents);
    }

    public static class Statistics {
        private final int totalStudents;
        private final double averageMarks;
        private final int highestMarks;
        private final int lowestMarks;
        private final long passCount;
        private final long failCount;
        private final double passPercentage;
        private final double failPercentage;
        private final String topPerformer;

        public Statistics(int totalStudents, double averageMarks, int highestMarks, int lowestMarks,
                          long passCount, long failCount, double passPercentage, double failPercentage,
                          String topPerformer) {
            this.totalStudents = totalStudents;
            this.averageMarks = averageMarks;
            this.highestMarks = highestMarks;
            this.lowestMarks = lowestMarks;
            this.passCount = passCount;
            this.failCount = failCount;
            this.passPercentage = passPercentage;
            this.failPercentage = failPercentage;
            this.topPerformer = topPerformer;
        }

        public int getTotalStudents() {
            return totalStudents;
        }

        public double getAverageMarks() {
            return averageMarks;
        }

        public int getHighestMarks() {
            return highestMarks;
        }

        public int getLowestMarks() {
            return lowestMarks;
        }

        public long getPassCount() {
            return passCount;
        }

        public long getFailCount() {
            return failCount;
        }

        public double getPassPercentage() {
            return passPercentage;
        }

        public double getFailPercentage() {
            return failPercentage;
        }

        public String getTopPerformer() {
            return topPerformer;
        }
    }
}
