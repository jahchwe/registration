import java.util.Scanner;

public interface StudentInterface {
	
	//Course Management
	
	public void viewAll(CRS dtb);
	public void viewNotFull(CRS dtb);
	
	public void register(CRS dtb, Scanner scanner);
	
	public void withdraw(CRS dtb, Scanner scanner);
	
	public void currentRegistrations(CRS dtb);
	
	public void exit();

}
