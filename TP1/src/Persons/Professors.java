package Persons;

public class Professors extends Teachers{
    private int salary;
    final static int TH = 30;
    final static int BASE = 2000;

    public Professors(String name, String surname, String course, int hours) {
        super(name, surname, course, hours);

        if (hours <= 200){
            this.salary = BASE*12;
        }
        else {
            this.salary = BASE*12 + ((hours - 200 )*TH);
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", salaire = " + salary + " Euros par ans";
    }
}
