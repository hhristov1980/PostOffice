package main.persons;

import main.postOffice.PostOffice;

import java.util.Objects;

public abstract class Person extends Thread{
    private static int uniqueId = 1;
    private int personId;
    protected String firstName;
    protected String lastName;
    protected PostOffice postOffice;

    public Person(String firstName, String lastName, PostOffice postOffice){
        if(firstName.length()>0){
            this.firstName = firstName;
        }
        if(lastName.length()>0){
            this.lastName = lastName;
        }
        personId = uniqueId++;
        this.postOffice = postOffice;
    }

    @Override
    public abstract void run();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId);
    }
}
