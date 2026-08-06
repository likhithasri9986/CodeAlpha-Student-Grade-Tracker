package com.codealpha.studenttracker.util;

public final class InputValidator {

    private InputValidator() {
        // Utility class
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidMarks(int marks) {
        return marks >= 0 && marks <= 100;
    }

    public static boolean isValidStudentId(String studentId) {
        return studentId != null && !studentId.trim().isEmpty();
    }
}
