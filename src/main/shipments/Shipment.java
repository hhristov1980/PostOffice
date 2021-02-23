package main.shipments;

import main.persons.Citizen;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public abstract class Shipment {
    private static int uniqueId = 1;
    private int shipmentId;
    private ShipmentType type;
    private Citizen sender;
    private Citizen receiver;
    private double price;
    private LocalDate dateOfComingAtPostOffice;
    private LocalTime timeOfComingAtPostOffice;

    public Shipment(ShipmentType type, Citizen sender, Citizen receiver){
        this.shipmentId = uniqueId++;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.price = calculatePrice();
    }

    public enum ShipmentType{
        PARCEL, LETTER
    }
    public abstract double calculatePrice();
    public abstract int timeToDeliver();

    public void setDateOfComingAtPostOffice(LocalDate dateOfComingAtPostOffice) {
        this.dateOfComingAtPostOffice = dateOfComingAtPostOffice;
    }

    public void setTimeOfComingAtPostOffice(LocalTime timeOfComingAtPostOffice) {
        this.timeOfComingAtPostOffice = timeOfComingAtPostOffice;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public Citizen getReceiver() {
        return receiver;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDateOfComingAtPostOffice() {
        return dateOfComingAtPostOffice;
    }

    public LocalTime getTimeOfComingAtPostOffice() {
        return timeOfComingAtPostOffice;
    }

    public ShipmentType getType() {
        return type;
    }

    public Citizen getSender() {
        return sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return shipmentId == shipment.shipmentId && type == shipment.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipmentId, type);
    }
}
