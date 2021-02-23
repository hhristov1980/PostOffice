package main.shipments;

import main.persons.Citizen;

public class Letter extends Shipment{
    public Letter(ShipmentType type, Citizen sender, Citizen receiver) {
        super(type, sender, receiver);
    }

    @Override
    public double calculatePrice() {
        return 0.5;
    }

    @Override
    public int timeToDeliver() {
        return 1000;
    }
}
