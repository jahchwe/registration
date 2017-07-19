import java.io.Serializable;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String username;
	protected String password;
	protected String fname; 
	protected String lname; 
	
	public void viewAll(CRS dtb) {
		
		for (Course crs: dtb.courses) {
			System.out.println("Courses:");
			System.out.println(crs.name);
		}
		
	}
	
	

}
