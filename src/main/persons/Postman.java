package main.persons;

import main.postOffice.PostOffice;
import main.shipments.Shipment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Postman extends Person{
    private int yearsOfExperience;
    protected Queue<Shipment> shipments;

    public Postman(String firstName, String lastName, PostOffice postOffice) {
        super(firstName, lastName,postOffice);

        yearsOfExperience = validateYearsOfExperience();
        shipments = new LinkedList<>();

    }
    protected abstract int validateYearsOfExperience();

    public Queue<Shipment> getShipments() {
        return shipments;
    }
}
