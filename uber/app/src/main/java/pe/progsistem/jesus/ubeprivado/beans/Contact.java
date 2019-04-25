package pe.progsistem.jesus.ubeprivado.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Contact {
    public String name;
    public Date birthday;

    public Contact(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }
    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    private static Date GetRandomDate(){
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1900, 2010);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }
    public static ArrayList<Contact> GenerateContacts() {
        ArrayList<Contact> dummyContacts = new ArrayList<Contact>();
        for (int i = 0; i < 20; i++) {
            Contact contact = new Contact("Contacto " + (i+1), GetRandomDate());
            dummyContacts.add(contact);
        }
        return dummyContacts;

    }
}