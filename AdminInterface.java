import java.io.FileNotFoundException;
import java.util.Scanner;

public interface AdminInterface {
	
	//Course Management
	
	public void createCourse(CRS dtb, Scanner scanner);
	public void deleteCourse(CRS dtb,  Scanner scanner);
	public void editCourse(CRS dtb,  Scanner scanner);
	
	public void displayInfo(CRS dtb,  Scanner scanner);
	
	public void registerStudent(CRS dtb,  Scanner scanner);
	
	//Reports
		
	public void viewAll(CRS dtb);
	public void viewFull(CRS dtb);
	public void exportFull(CRS dtb) throws FileNotFoundException;	
	
	public void viewStudents(CRS dtb,  Scanner scanner);
	public void viewCourses(CRS dtb, Scanner scanner);
	
	public void sortBySize(CRS dtb);
	
	public void exit();
	

}
