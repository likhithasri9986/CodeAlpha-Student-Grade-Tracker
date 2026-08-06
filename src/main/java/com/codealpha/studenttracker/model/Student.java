package com.codealpha.studenttracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Student {

    private String studentId;
    private String name;
    private String subject;
    private int marks;
    private String grade;
    private double percentage;
    private String dateCreated;

    public Student(String studentId, String name, String subject, int marks) {
        this.studentId = studentId;
        this.name = name;
        this.subject = subject;
        this.marks = marks;
        this.percentage = marks;
        this.grade = calculateGrade(marks);
        this.dateCreated = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
        this.percentage = marks;
        this.grade = calculateGrade(marks);
    }

    public String getGrade() {
        return grade;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    private String calculateGrade(int marks) {
        if (marks >= 90) {
            return "A+";
        }
        if (marks >= 80) {
            return "A";
        }
        if (marks >= 70) {
            return "B";
        }
        if (marks >= 60) {
            return "C";
        }
        if (marks >= 50) {
            return "D";
        }
        return "F";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%d|%s|%.2f|%s", studentId, name, subject, marks, grade, percentage, dateCreated);
    }
}
