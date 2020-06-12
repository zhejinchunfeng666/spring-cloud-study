package com.zf.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test1 {
    public static void main(String[] args) {
        Student student1 = new Student(1,"1001","zf1","123");
        Student student2 = new Student(2,"1002","zf2","456");
        Student student3 = new Student(3,"1003","zf3","789");
        Student student4 = new Student(4,"1004","zf4","111");
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        String collect = studentList.stream().map(Student::getId).map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(collect);
    }

}
