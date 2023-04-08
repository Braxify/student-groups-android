package com.chnulabs.students;

import java.util.ArrayList;
import java.util.Arrays;

public class Student {
    private String name;
    private String groupNumber;

    public Student(String name, String groupNumber) {
        this.name = name;
        this.groupNumber = groupNumber;
    }

    public String getName() {
        return name;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    private final static ArrayList<Student> students = new ArrayList<Student>(
            Arrays.asList(
                    new Student("Іванов Роман", "301"),
                    new Student("Петров Федір", "301"),
                    new Student("Осадча Оксана", "308")
            )
    );

    public static ArrayList<Student> getStudents(String groupNumber) {
        ArrayList<Student> stList = new ArrayList<>();

        for (Student s : students) {
            if (s.getGroupNumber().equals(groupNumber)) {
                stList.add(s);
            }
        }

        return stList;
    }

    @Override
    public String toString(){
        return name;
    }
}
