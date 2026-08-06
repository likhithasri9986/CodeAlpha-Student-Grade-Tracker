package com.codealpha.studenttracker.util;

import java.util.List;
import java.util.StringJoiner;

import com.codealpha.studenttracker.model.Student;
import com.codealpha.studenttracker.service.StudentService.Statistics;

public final class ReportGenerator {

    private ReportGenerator() {
        // Utility class
    }

    public static String buildStudentReport(List<Student> students) {
        StringBuilder builder = new StringBuilder();
        builder.append("==========================================\n");
        builder.append("           Student Grade Report           \n");
        builder.append("==========================================\n");
        builder.append(String.format("%-12s | %-20s | %-15s | %-5s | %-5s | %-10s\n",
                "Student ID", "Name", "Subject", "Marks", "Grade", "Percentage"));
        builder.append("--------------------------------------------------------------------------------\n");
        for (Student student : students) {
            builder.append(String.format("%-12s | %-20s | %-15s | %-5d | %-5s | %-9.2f%%\n",
                    student.getStudentId(), student.getName(), student.getSubject(), student.getMarks(),
                    student.getGrade(), student.getPercentage()));
        }
        builder.append("==========================================\n");
        return builder.toString();
    }

    public static String buildStatisticsReport(Statistics stats) {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Statistics Summary");
        joiner.add("------------------");
        joiner.add(String.format("Total Students : %d", stats.getTotalStudents()));
        joiner.add(String.format("Average Marks  : %.2f", stats.getAverageMarks()));
        joiner.add(String.format("Highest Marks  : %d", stats.getHighestMarks()));
        joiner.add(String.format("Lowest Marks   : %d", stats.getLowestMarks()));
        joiner.add(String.format("Pass Count     : %d", stats.getPassCount()));
        joiner.add(String.format("Fail Count     : %d", stats.getFailCount()));
        joiner.add(String.format("Pass %%         : %.2f%%", stats.getPassPercentage()));
        joiner.add(String.format("Fail %%         : %.2f%%", stats.getFailPercentage()));
        joiner.add(String.format("Top Performer  : %s", stats.getTopPerformer()));
        return joiner.toString();
    }
}
