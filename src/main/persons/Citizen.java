package main.persons;

import main.postOffice.PostOffice;
import main.shipments.Letter;
import main.shipments.Parcel;
import main.shipments.Shipment;
import main.util.Constants;
import main.util.Randomizer;

import java.util.Random;

public class Citizen extends Person{
    private String address;
    private boolean sent;


    public Citizen(String firstName, String lastName, String address, PostOffice postOffice) {
        super(firstName, lastName,postOffice);

        if(address.length()>0){
            this.address = address;
        }
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public void run() {
        String firstName = Constants.firstNames[Randomizer.getRandomInt(0,Constants.firstNames.length-1)];
        String lastName = Constants.lastNames[Randomizer.getRandomInt(0,Constants.lastNames.length-1)];
        String address = Constants.address[Randomizer.getRandomInt(0,Constants.address.length-1)];
        Citizen receiver = new Citizen(firstName,lastName,address,postOffice);
        int choice = Randomizer.getRandomInt(1,10);
        Shipment shipment;
        if(choice>=3){
            shipment = new Letter(Shipment.ShipmentType.LETTER, this, receiver);
            postOffice.sendShipment(shipment);
        }
        else {
            shipment = new Parcel(Shipment.ShipmentType.PARCEL, this,receiver, Randomizer.getRandomInt(10,100)
                    , Randomizer.getRandomInt(10,100),Randomizer.getRandomInt(10,100), new Random().nextBoolean());
            postOffice.sendShipment(shipment);
        }
        if(sent){
            System.out.println(this.firstName+" "+this.lastName+" sent a "+shipment.getType()+" to "+firstName+" "+lastName+" for "+shipment.getPrice());
        }
        else {
            System.out.println(this.firstName+" "+this.lastName+" was unable to sent a "+shipment.getType()+" to "+firstName+" "+lastName+" for "+shipment.getPrice());

        }

    }
}
