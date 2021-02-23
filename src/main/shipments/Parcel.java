package main.shipments;

import main.persons.Citizen;

public class Parcel extends Shipment{
    private int length;
    private int width;
    private int height;
    private boolean fragile;

    public Parcel(ShipmentType type, Citizen sender, Citizen receiver, int length, int width, int height, boolean fragile) {
        super(type, sender, receiver);

        if(height>0){
            this.height = height;
        }
        if(length>0){
            this.length = length;
        }
        if(width>0){
            this.width = width;
        }
        this.fragile = fragile;
    }

    @Override
    public double calculatePrice() {
        double price = 2;
        if(height>60 || length>60 || width>60){
            price*=1.5;
            if(fragile){
                price*=1.5;
            }
        }
        else {
            if(fragile){
                price*=1.5;
            }
        }
        return price;
    }

    @Override
    public int timeToDeliver() {
        return 1500;
    }
}
