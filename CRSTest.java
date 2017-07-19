import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class CRSTest {
	
	/* Hello,
	 * 
	 * Main points:
	 * 		
		 * DATA MANAGEMENT:
		 * The class CRS (course registration system) is used to hold the arraylists of data for the program. 
		 * The CRS class also includes exported course and student search functions that loop until the correct 
		 * class or student is found. 
	 * 
		 * ADMIN.SORT
		 * I also decided to utilize the .sort function to sort my classes in the admin class. 
		 * I've implemented a custom comparator to do so. 
	 * 
		 * UNIVERSAL SCANNER
		 * My code implements a universalScanner that receives info from System.in. 
		 * This scanner object is used by all classes and methods to ensure that the scanner doesn't get tangled up.
	 * 
		 * TYPE CONTROL
		 * I've done my best to incorporate type control using try/catches. This should work for all user inputs. 
		 * These try/catches are paired with while loops so a user can keep trying until they get it right. 
		 * For example, Admin.createCourse will ask for another input if a number is given for instructor name. 
		 * I've implemented something similar for both the course search and student search. 
	 * 
	 * Thanks!
	 * 
	 * John Andrew Chwe
	 */

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner universalScanner = new Scanner(System.in); 
		//ran into scanner troubles by instantiating multiple scanner objects in main and in methods in other classes.
		//from my understanding, once scanner closes, it can't be reopened.
		//I read about solution where scanner is kept in main method and passed to other methods.
		//otherwise i get NoSuchElementExceptions
		//only one scanner per input stream
		
		//check if .ser file exists. If so, deserialize database CRS.
		File tester = new File("crs.ser");
		
		CRS nyu = null;
		
		
		//if file exists, deserialize 
		if (tester.exists()) {
			try {
				
				System.out.println("Used ser");
				
				FileInputStream fis = new FileInputStream("crs.ser");
				
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				nyu = (CRS) ois.readObject();
				
				ois.close();
				fis.close();
			
			}
			catch (ClassNotFoundException expt) {
				expt.printStackTrace();
				
			}
			catch (IOException io) {
				io.printStackTrace();
				
			}
		}
	
		//if .ser does not exist, read from csv and create default admin
		else {
			
			System.out.println("Ser not available.");
			
			nyu = new CRS();
			nyu.getCourses(); //read from csv. 
			
			//create default admin
			Admin dft = new Admin();
			
			dft.username = "Admin";
			dft.password = "Admin001";
			
			nyu.admins.add(dft);
			
		}

		
		while (true) {	//allow user to repeat input if they dont select 1 or 2
			
			try { 
				
				System.out.println("Choose a type of user:");
				System.out.printf("%s\n%s\n", "1 - Admin", "2 - Student");
				
				int slct = Integer.parseInt(universalScanner.nextLine());
				
				if (slct == 1) { //admin selected
					
					Boolean flag = false;
					
					while (!flag) { //outer loop for username and password verification
						System.out.println("Please enter a username:");
						String uname = universalScanner.nextLine();
						
						System.out.println("Please enter a password:");
						String pword = universalScanner.nextLine();
						
						//verification outsourced to static method
						Admin a = adminSearch(nyu, uname, pword);

						
						if (a instanceof Admin) { //if admin successfully logged in
							flag = true;
							
							while (true) { //allow looping until done with actions
								
								//menu formatting
								System.out.println();
								System.out.println("Actions:");
								System.out.println();
								System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n", 
										"1 - Create a new course.", 
										"2 - Delete a course.", 
										"3 - Edit a course.", 
										"4 - Display info for a course.", 
										"5 - Register a student.",
										"6 - View all courses.", 
										"7 - View full courses.", 
										"8 - Write full classes to file.",
										"9 - View students in certain class.",
										"10 - View a student's enrolled classes.",
										"11 - Exit.");
								
								try { //use switch to execute selection
									
									int slct1 = Integer.parseInt(universalScanner.nextLine());
									
									switch (slct1) { //use switch to implement functions
									
									case 1: 
										a.createCourse(nyu, universalScanner);
										break;
									case 2: 
										a.deleteCourse(nyu, universalScanner);
										break;
									case 3:
										a.editCourse(nyu, universalScanner);
										break;
									case 4: 
										a.displayInfo(nyu, universalScanner);
										break;
									case 5:
										a.registerStudent(nyu, universalScanner);
										break;
									case 6:
										a.viewAll(nyu);
										break;
									case 7:
										a.viewFull(nyu);
										break;
									case 8: 
										a.exportFull(nyu);
										break;
									case 9:
										a.viewStudents(nyu, universalScanner);
										break;
									case 10:
										a.viewCourses(nyu, universalScanner);
										break;
									case 11:
										CRSTest.saveInfo(nyu); //save info if closing
										a.exit();
										break;
									default:
										System.out.println("Invalid selection code. Please try again.");
									}	
								}
								catch (NumberFormatException err) {
									System.out.println("Invalid entry. Try again.");
									continue;
								}
							}
						}
						
						else { //if wrong info, try again. 
							System.out.println("Invalid username or password. Try again.");
						}
					}
				}
				
				else if (slct == 2) { //student selected
					
					Boolean flag = false;
					
					while (!flag) { //outer loop for id verification
						
						System.out.println("Please enter a username:");
						String uname = universalScanner.nextLine();
						
						System.out.println("Please enter a password:");
						String pword = universalScanner.nextLine();
						
						Student s = studentSearch(nyu, uname, pword);
						
						if (s instanceof Student) {
							
							flag = true; 
							
							while (true) { //inner loop: keep going until user wants to stop 
								
								//menu formatting
								System.out.println();
								System.out.println("Actions:");
								System.out.println();
								
								System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n", 
										"1 - View all courses.", 
										"2 - View all non-full courses.",
										"3 - Register on a course.",
										"4 - Withdraw from a course.",
										"5 - View all courses you are enrolled in.",
										"6 - Exit");
								
								try { 
									//switch used to execute selection
									int slct2 = Integer.parseInt(universalScanner.nextLine());
									
									switch (slct2) {
									
									case 1: 
										s.viewAll(nyu);
										break;
									case 2:
										s.viewNotFull(nyu);
										break;
									case 3: 
										s.register(nyu, universalScanner);
										break;
									case 4: 
										s.withdraw(nyu, universalScanner);
										break;
									case 5: 
										s.currentRegistrations(nyu);
										break;
									case 6: 
										CRSTest.saveInfo(nyu);
										s.exit();
									default:
										System.out.println("Invalid selection code. Please try again.");
									}	
								}
								
								catch (NumberFormatException err){
									System.out.println("Invalid entry. Try again.");
									continue;	
								}
							}
						}
						
						else {
							System.out.print("Invalid username or password. Try again.");
						}
					}
				}
			}
		 //END OF TRY 
		
			catch (NumberFormatException err) {
				System.out.println("Invalid entry, try again");
			}
		
		}
	}

	public static void saveInfo(CRS dtb) { //serialization function
		
		try {
		
			FileOutputStream fos = new FileOutputStream("crs.ser");
			
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(dtb);
			
			fos.close();
			oos.close();
		}
		
		catch (IOException err) {
			err.printStackTrace();
		}
	}
	
	public static Admin adminSearch(CRS dtb, String uname, String pword) { //validates username and password
		for (Admin a: dtb.admins) {
			if ((uname.equals(a.username)) && (pword.equals(a.password))) {
				return a;
			}
		}
		return null;
	}
	public static Student studentSearch(CRS dtb, String uname, String pword) { //validates username and password
		for (Student s: dtb.students) {
			if ((uname.equals(s.username)) && (pword.equals(s.password))) {
				return s;
			}
		}
		return null;
	}
	
	
	
	
	
}
