package Persons;

public class Student {
    private String name, surname;
    private Integer age, no;

    public Student(String name, String surname, int age, int no) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.no = no;
    }

    @Override
    public String toString() {
        return "Etudiant " + name +
                " " + surname +
                ", (noEt=" + no + ")" +
                " " + age + " ans";
    }
}
