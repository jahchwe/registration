import java.util.Comparator;

public class CourseComparator implements Comparator<Course> {
	
	//custom comparator used for sort function in admin class
	
	public int compare(Course c1, Course c2) {
		if (c1.currentStudents>c2.currentStudents) { 
			//swapped the returns. Instead of > returning 1, it returns -1 so that full classes rise to the top
			return -1;
		}
		 
		else if (c1.currentStudents<c2.currentStudents) {
			
			//swapped so full classes rise to the top 
			return 1;
		}
		
		else {
			return 0;
		}
	}
	

}
