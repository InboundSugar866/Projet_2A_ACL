package Persons;

public class External extends Teachers{
    private int salary;
    final static int TH = 30;

    public External(String name, String surname, String course, int hours) {
        super(name, surname, course, hours);

        salary = hours*2*TH;
    }

    @Override
    public String toString() {
        return super.toString() + ", salaire = " + salary + " Euros par ans";
    }
}
