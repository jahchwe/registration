
import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Admin extends User implements AdminInterface, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Admin() {
		
	}
	
	//allows admin to create new course by inputting all relevant data fields
	public void createCourse(CRS dtb, Scanner scanner) { //TESTED
		
		//use scanner to gather info about new class and assign inputs to temporary variables
		
		System.out.println("Enter name of new course:");
		String name = scanner.nextLine();
		
		System.out.println("Enter course ID: ");
		String id = scanner.nextLine();
		
		System.out.println("Enter maximum number of students: ");
		String max = scanner.nextLine();
		
		System.out.println("Enter instructor name: ");
		String instructor = scanner.nextLine();
		
		System.out.println("Enter course section: ");
		String section= scanner.nextLine();
		
		System.out.println("Enter course location: ");
		String location = scanner.nextLine();
		
		Course a = new Course();
		
		//populate data fields of new course
		
		a.name = name; 
		a.courseID = id;
		a.maxStudents =Integer.parseInt(max);
		a.instuctor = instructor;
		a.section =Integer.parseInt(section);
		a.location = location;
		
		dtb.courses.add(a);
		
		System.out.println("New course "+a.name+" has been successfully created.");
	}
	
	public void createCourse (CRS dtb, String name) {
		
		Course a = new Course();
		
		a.name = name; 
		
		dtb.courses.add(a);
		
	}
	
	//allows admin to delete course using exported course search. 
	public void deleteCourse(CRS dtb, Scanner scanner) { //TESTED
		
		Boolean flag = false; //used for while loop
		
		while (!flag) { //keep searching until correct course found
			
			Course a = dtb.findCourse(scanner); //export search functionality to CRS.
			
			if (a instanceof Course) { // if course successfully found, delete
				dtb.courses.remove(a);
				flag = true;
				System.out.println("Course " + a.name + " successfully deleted.");
			}
			else { // if not, ask again. 
				System.out.println("Course not found. Please try again.");
				continue;
			}	
		}
	}
	
	//allows admin to edit all aspects of a course.
	
	public void editCourse(CRS dtb, Scanner scanner) {
				
		Boolean flag = false; //used for while loop
		
		while (!flag) { //keep searching until correct course found
			
			Course a = dtb.findCourse(scanner); //export search functionality 
			
			if (a instanceof Course) { // if course successfully found, edit options
				System.out.println("What would you like to edit?");
				System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n", 
						"1 - Course Name", "2 - Course ID", "3 - Maximum Permitted Students", "4 - Manage Students", "5 - Instructor", "6 - Course Section", "7 - Location"); 
				
				int selection = Integer.parseInt(scanner.nextLine());
				
				switch (selection) { //use switch to change function based on selection
					case 1: //TESTED
						
						//take input and alter name
						System.out.println("Current Name: "+a.name);
						System.out.print("Enter new name:");
						
						String name = scanner.nextLine();
						
						a.name = name;
						
						System.out.println("Course Name successfully changed.");
						flag = true;
						break; 
						
					case 2: //TESTED
						
						//take id and alter id
						System.out.println("Current Course ID:" + a.courseID);
						System.out.println("Enter new course ID:");
						
						String id = scanner.nextLine();
						a.courseID = id; 
						System.out.println("Course ID successfully changed.");
						flag = true;
						
						break;
						
					case 3: //TESTED
						
						//take new max and change max
						System.out.println("Current Maximum Students: "+a.maxStudents);
						System.out.print("Enter new max:");
						
						String max = scanner.nextLine();
						a.maxStudents = Integer.parseInt(max); 
						System.out.println("Max students successfully changed.");
						flag = true;
						
						break;
					
					case 4: //TESTED
						
						//display all students, then ask if deleting or adding. 
						
						System.out.println("Enrolled students:"); //show enrolled students 
						
						int counter = 0;  //print 3 per line
						
						for (Student stdnt: a.enrolled) {
							if (counter==3) { //print 3 per line
								System.out.println("");
								counter = 0;
							}
							System.out.println(stdnt.fname + " " + stdnt.lname); //no ln, print 3 per line
							flag = true;
							break;
						}
						
						System.out.println("");
						
						
						//ask for intended operation, adding or deleting
						System.out.printf("%s\n%s\n", "1 - Add Student", "2 - Remove Student");
						
						int selection1 = Integer.parseInt(scanner.nextLine());
						
						Boolean flag2 = false;
						
						while (!flag2) {
							
						
							Student s = dtb.findStudent(scanner); //export finding student to CRS
							
							if (s instanceof Student) {
								
								if (selection1 == 1 && !a.enrolled.contains(s)) { //if adding and course doesnt already have student
									if (a.currentStudents<a.maxStudents) { //if course not at capacity
										a.enrolled.add(s);
										
										a.currentStudents++;
										
										System.out.println("Student " + s.fname + " " + s.lname + " successfully added to course " + a.name + ".");
										
										flag2=true;
										break;
									}
									else { //if course full
										System.out.println("Course at capacity. Cannot add.");
										break;
									}
								}
								
								if (selection1 == 2 && a.enrolled.contains(s)) { //if deleting and course has student 
									//student must be in class to be removed
									a.enrolled.remove(s);
									
									a.currentStudents--;
									
									System.out.println("Student " + s.fname + " " + s.lname + " successfully removed from course " + a.name + ".");
									
									flag2 = true;
									break;
									
								}
							
							else  {
								continue; //if student not found, try again 
							}
							}
						}
						
						flag = true;
						break;
						
					case 5: //TESTED
						
						//take input and change instructor
						System.out.println("Current Instructor: "+a.instuctor);
						System.out.println("Enter new instructor:");
						
						String inst = scanner.nextLine();//nextLine not nextInt because input may be double digits

						a.instuctor = inst; 
						System.out.println("Instructor successfully changed.");
						
						flag = true;
						break;
						
					case 6: //TESTED
						
						//take input and change section
						System.out.println("Current section: "+a.section);
						System.out.print("Enter new section:");
						
						String sec = scanner.nextLine(); //nextLine not nextInt because input may be double digits
						a.section = Integer.parseInt(sec); 
						System.out.println("Section successfully changed.");
						flag = true;
						
						break;
					
					case 7: //TESTED
						
						//take input and change location
						System.out.println("Current Location: "+a.location);
						System.out.print("Enter new location:");
						
						String loc = scanner.nextLine(); //nextLine not nextInt because input may be double digits
						a.location = loc; 
						System.out.println("Course Location successfully changed.");
						flag = true;
						
						break;
						
					default: //if selection code invalid
						System.out.println("Invalid selection code.");
						}
			}
			else {
				System.out.println("Course not found. Please try again.");
				continue; //if course not found, try again 
			}	
		}	
	}
	
	//displays all data fields for a given course
	public void displayInfo(CRS dtb, Scanner scanner) { //TESTED
		
		Boolean flag = false; 
		
		while (!flag) {
			
			Course a = dtb.findCourse(scanner); //export search course
		
			if (a instanceof Course) {
				//header for info
				System.out.printf("%-45s%-15s%-20s%-20s%-20s%s\n", "Course Name", "Course ID", "Maximum Students", "Current Students",  "Instructor", "Location");
				
				//print info
				System.out.printf("%-45s%-15s%-20s%-20s%-20s%s\n", a.name, a.courseID, a.maxStudents, a.currentStudents, a.instuctor, a.location);
			
				System.out.println("Enrolled students:");
				
				int counter = 0;  //print 3 per line
				
				for (Student stdnt: a.enrolled) {
					if (counter==3) { //print 3 per line
						System.out.println("");
						counter = 0;
					}
					System.out.print(stdnt.fname + " " + stdnt.lname); //no ln, print 3 per line
				}
				
				flag = true; 
			}
			
			else {
				System.out.println("Course not found. Please try again.");
				continue;
			}
		}
		
		
	}
	
	//allows admin to create new student object and populate data fields 
	public void registerStudent(CRS dtb, Scanner scanner) {//TESTED
		
		System.out.println("Enter new student first name:"); //gather info using scanner and System.out
		String fname = scanner.nextLine();
		
		System.out.println("Enter new student last name:");
		String lname = scanner.nextLine();
		
		System.out.println("Enter new student username:");
		String username = scanner.nextLine();
		
		System.out.println("Enter new student password:");
		String pass = scanner.nextLine();
		
		Random rando = new Random();
		String id = Integer.toString(rando.nextInt(1000000));
		System.out.println("Autogenerated student ID: "+id);
		
		Student a = new Student(); //populate new Student object using collected info
		
		a.fname = fname;
		a.lname = lname;
		a.username = username; 
		a.password = pass; 
		a.ID = id;
		
		dtb.students.add(a);
		
		System.out.println("New student "+ fname + " " + lname+ " has been successfully created.");
		
	}
	
	//allows admin to print all courses and their information
	public void viewAll(CRS dtb) { //TESTED
		
		System.out.println("Printing all courses...");
		System.out.println();
		
		//Header of table
		System.out.printf("%-45s%-15s%-20s%s\n\n", "Course Name", "Course ID", "Max Students", "Current Students");
		
		//iterate through courses, printing info
		for (Course crs: dtb.courses) {	
			System.out.printf("%-45s%-15s%-20d%s\n", crs.name, crs.courseID, crs.maxStudents, crs.currentStudents);
		}	
	}
	
	//allows admin to print all courses taht are full 
	public void viewFull(CRS dtb) { //TESTED
		
		System.out.println("Printing all courses that are full...");
		System.out.println();
		
		//Header of table
		System.out.printf("%-45s%-15s%-20s%s\n\n", "Course Name", "Course ID", "Max Students", "Current Students");
		
		//iterate through courses, printing info
		for (Course crs: dtb.courses) {	
			if (crs.currentStudents >= crs.maxStudents) {
				System.out.printf("%-45s%-15s%-20d%s\n", crs.name, crs.courseID, crs.maxStudents, crs.currentStudents);
			}	
		}
	}
	
	//allows admin to write full courses to a csv file. 
	public void exportFull(CRS dtb) throws FileNotFoundException { 
		
		PrintWriter pw = new PrintWriter(new File("fullClasses.csv")); //writer for file
		StringBuilder sb = new StringBuilder();
		
		for (Course crs: dtb.courses) { //for all courses
			if (crs.currentStudents >= crs.maxStudents) { //if full
			
				sb.append(crs.name); //add all data fields of course (excluding students)
				sb.append(",");
				sb.append(crs.courseID);
				sb.append(",");
				sb.append(crs.maxStudents);
				sb.append(",");
				sb.append(crs.currentStudents);
				sb.append(",");
				sb.append(crs.instuctor);
				sb.append(",");
				sb.append(crs.section);
				sb.append(",");
				sb.append(crs.location);
				sb.append("\n");
				
			}
		}
		
		pw.write(sb.toString());
		pw.close();
		
		System.out.println("Full classes exported to: 'fullClasses.csv'.");
		
	}
	
	//allows admin to view students for a course.
	public void viewStudents(CRS dtb, Scanner scanner) { //looks right, check using examples
		
		Boolean flag = false; 
		
		while (!flag) {
			Course crs = dtb.findCourse(scanner);
			
			if (crs instanceof Course) {
			
				System.out.println("Printing students for course " + crs.name + ".");
				
				int counter = 0;  //print 3 per line
				
				for (Student stdnt: crs.enrolled) {
					if (counter==3) { //print 3 per line
						System.out.println("");
						counter = 0;
					}
				System.out.println(stdnt.fname + " " + stdnt.lname); //no ln, print 3 per line
				
				}
				flag = true;
			}
			
			else {
				System.out.println("Course not found. Please try again.");
				continue;
			}
		}
	}
	
	//allows admin to view all courses a student is in. 
	public void viewCourses(CRS dtb, Scanner scanner) { //looks right, check using examples
		
		Boolean flag = false; 
		
		//decided to have a search instead of storing courses with each student. 
		//Then I do not have to worry about updating both the Course enrolled ArrayList and a Student enrolled arraylist.
		while (!flag) {
			
			ArrayList<Course> stdcrs = new ArrayList<Course>(); //list of student's courses
		
			Student std = dtb.findStudent(scanner);
			
			if (std instanceof Student) { //if std is a student i.e. the search worked
				for (Course crs: dtb.courses) { //for all courses
					for (Student s: crs.enrolled) { //for all students in a given course
						if (std.equals(s)) { //if student is in the course
							stdcrs.add(crs); //add course to students list
						}
					}
				}
				
				System.out.println("Printing courses for student " + std.fname + " " + std.lname + ".");
				
				for (Course crs1: stdcrs) { //for all courses in students list, print name
					System.out.println(crs1.name);
				}
				
				flag = true;
				break;	
			}
			
			else { //if std is not a student object, try the search again
				System.out.print("Student not found. Please try again.");
				continue;
			}
		}
	}
	
	//allows admin to sort classes descending based on current size. 
	//Utilizes custom comparator to put full classes first.
	public void sortBySize(CRS dtb) { 
		
		
		dtb.courses.sort(new CourseComparator()); //using custom comparator
		
	}
	
	public void exit() {
		System.exit(0);
	}
	
	
	
	
	
	
	
	
	

}
