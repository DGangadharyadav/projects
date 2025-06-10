package student;

import java.util.Scanner;
import java.util.Scanner;

//Class to represent a Student
class Student {
 String name;
 int[] marks = new int[3];
 double average;
 char grade;

 // Constructor
 Student(String name, int[] marks) {
     this.name = name;
     this.marks = marks;
     this.calculateAverage();
     this.assignGrade();
 }

 // Method to calculate average
 public void calculateAverage() {
     int sum = 0;
     for (int mark : marks) {
         sum += mark;
     }
     average = sum / 3.0;
 }

 // Method to assign grade
 public  void assignGrade() {
     if (average >= 90)
         grade = 'A';
     else if (average >= 80)
         grade = 'B';
     else if (average >= 70)
         grade = 'C';
     else if (average >= 60)
         grade = 'D';
     else
         grade = 'F';
 }

 // Method to display student details
 public void display() {
     System.out.println("Name: " + name);
     System.out.print("Marks: ");
     for (int mark : marks) {
         System.out.print(mark + " ");
     }
     System.out.println("\nAverage: " + average);
     System.out.println("Grade: " + grade);
     System.out.println();
 }
}

//Main class
public class StudentReport {
 public static void main(String[] args) {
     Scanner sc = new Scanner(System.in);

     System.out.print("Enter number of students: ");
     int n = sc.nextInt();
     sc.nextLine();  // consume newline

     Student[] students = new Student[n];

     for (int i = 0; i < n; i++) {
         System.out.println("\nEnter details for Student " + (i + 1));
         System.out.print("Name: ");
         String name = sc.nextLine();

         int[] marks = new int[3];
         for (int j = 0; j < 3; j++) {
             System.out.print("Enter marks for subject " + (j + 1) + ": ");
             marks[j] = sc.nextInt();
         }
         sc.nextLine();  // consume newline

         students[i] = new Student(name, marks);
     }

     System.out.println("\n--- Student Report ---");
     for (Student student : students) {
         student.display();
     }

     sc.close();
 }
}


