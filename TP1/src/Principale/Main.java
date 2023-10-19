package Principale;

import Persons.External;
import Persons.Professors;
import Persons.Student;
import Groups.Group;
public class Main {
    public static void main(String[] args) {

        Student S1 = new Student("jean-francois","petin",57,1);
        System.out.println("\n" + S1 + "\n");
        Student S2 = new Student("martin","matin",11,2);
        System.out.println("\n" + S2 + "\n");
        Student S3 = new Student("Grand","Sechem",1025,3);
        System.out.println("\n" + S3 + "\n");

        Professors P1 = new Professors("jean","pierre","math",1200);
        System.out.println(P1 + "\n");

        External E1 = new External("pierre","paul","anglais",15);
        System.out.println(E1 + "\n");

        Group ISN = new Group();
        ISN.AddStudent(S1);
        ISN.AddStudent(S2);
        System.out.println(ISN);

    }
}