package com.chnulabs.students;

import java.util.ArrayList;
import java.util.Arrays;

public class Student {
    private final static ArrayList<Student> students = new ArrayList<Student>(
            Arrays.asList(
                    new Student("Іванов Роман", "301"),
                    new Student("Петров Федір", "301"),
                    new Student("Осадча Оксана", "308")
            )
    );
    private String name;
    private String groupNumber;

    public Student(String name, String groupNumber) {
        this.name = name;
        this.groupNumber = groupNumber;
    }

    public static ArrayList<Student> getStudents() {
        return getStudents("");
    }

    public static ArrayList<Student> getStudents(String groupNumber) {
        ArrayList<Student> stList = new ArrayList<>();

        for (Student s : students) {
            if (s.getGroupNumber().equals(groupNumber) || (groupNumber == "")) {
                stList.add(s);
            }
        }

        return stList;
    }

    public String getName() {
        return name;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    @Override
    public String toString() {
        return name;
    }
}
