package Persons;

public class Teachers {
    private String name, surname, course;
    private int hours;

    public Teachers(String name, String surname, String course, int hours) {
        this.name = name;
        this.surname = surname;
        this.course = course;
        this.hours = hours;
    }



    @Override
    public String toString() {
        return name + " "
                + surname + " " +
                "en " + course;
    }

}