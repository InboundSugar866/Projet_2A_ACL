package Groups;

import Persons.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
public class Group {
    private Set<Student> students;

    //	public Group(ArrayList<Student> students) {
//		super();
//		this.students = students;
//	}
    public Group() {
        this.students = new HashSet<>();
    }

    public void AddStudent(Student student) {

        this.students.add(student);
    }

    @Override
    public String toString() {

        return "Group [students=" + students + "]";
    }

}
