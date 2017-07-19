import java.io.Serializable;

import java.util.ArrayList;
import java.util.Scanner;


public class Student extends User implements StudentInterface, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String ID;
	
	//allows student to view all courses
	public void viewAll(CRS dtb) { //tested
		
		System.out.printf("%-45s%-15s%-25s%s\n\n", "Course Name", "Course ID", "Instructor", "Location");
		
		for (Course crs: dtb.courses) {
			
			System.out.printf("%-45s%-15s%-25s%s\n", crs.name, crs.courseID, crs.instuctor, crs.location);
			
		}
		
	}
	
	//allows student to view all courses that arent full 
	public void viewNotFull(CRS dtb) { //TESTED
		
		System.out.println("Printing all courses that are not full...");
		System.out.println();
		
		//Header of table
		System.out.printf("%-45s%-15s%-25s%s\n\n", "Course Name", "Course ID", "Instructor", "Location");
		
		//iterate through courses, printing info
		for (Course crs: dtb.courses) {	
			if (crs.currentStudents < crs.maxStudents) {
				System.out.printf("%-45s%-15s%-25s%s\n", crs.name, crs.courseID, crs.instuctor, crs.location);
			}	
		}
	}
	
	//allows student to register themselves to a certain class if they are not already
	public void register(CRS dtb, Scanner scanner) {// TESTED
		
		Boolean flag = false; 
		
		while (!flag) {
			
			Course crs = dtb.findCourse(scanner);
			
			if (crs instanceof Course) {
				//ensures that student isnt already enrolled in the class
				if (crs.enrolled.contains(this)) {
					System.out.println("Already enrolled.");
					break;
				}
				
				//if student not in class
				else {
					crs.enrolled.add(this); //add this object to class list
					crs.currentStudents++;
					System.out.println("Successfully added to course " + crs.name + ".");
					break;
				}	
			}
			
			else {
				System.out.println("Course not found. Try again.");
				continue;
			}
			
		}

	}
	
	//allows student to withdraw from a class if they are enrolled in given class
	public void withdraw(CRS dtb, Scanner scanner) { //tested
		
		Boolean flag = false; 
		
		ArrayList<Course> classes = new ArrayList<Course>(); 
		//hold classes that the student is currently enrolled in
		
		for (Course crs: dtb.courses) { //for all courses
			for (Student std: crs.enrolled) { //for all students in given course
				if (this.equals(std)) { //if student in the class
					classes.add(crs); //add course to list
				}
			}
		}
		
		//Print current enrollments so student can tell which classes from which they can withdraw. 
		System.out.println("The classes you are currently enrolled in:");
		for (Course crs: classes) {
			System.out.println(crs.name);
		}
		
		System.out.println("Enter name of class to withdraw from");
		
		while (!flag) {
		
		Course crs1 = dtb.findCourse(scanner); //exported search function
			
			if (crs1 instanceof Course) { //if course successfully found
				if (crs1.enrolled.contains(this)) { //if student is enrolled in course
					crs1.enrolled.remove(this); //remove from course
					
					crs1.currentStudents--;
					
					System.out.println("Successfully removed from course "+crs1.name+ ".");
					break;
				}
				else {//if not enrolled
					System.out.println("Not enrolled in "+crs1.name+".");
					break;
				}	
			}
			
			else {//if not found, try again.
				continue;
			}
		}
	}
	
	//allow student to view current registrations
	public void currentRegistrations(CRS dtb) { //TESTED
		
		ArrayList<Course> classes = new ArrayList<Course>(); //hold classes that the student is currently enrolled in
		
		for (Course crs: dtb.courses) { //for all courses
			for (Student std: crs.enrolled) { //for all students in given course
				if (this.equals(std)) { //if student in the class
					classes.add(crs); //add course to list
				}
			}
		}
		
		System.out.println("The classes you are currently enrolled in:");
		for (Course crs: classes) {
			System.out.println(crs.name);
		}
		
	}
	
	public void exit() {
		System.exit(0);
	}

}
