package main.persons;

import main.postOffice.PostOffice;
import main.shipments.Shipment;
import main.util.Randomizer;

public class SeniorPostman extends Postman{

    public SeniorPostman(String firstName, String lastName, PostOffice postOffice) {
        super(firstName, lastName, postOffice);
    }

    @Override
    public void run() {
        while (true){
            postOffice.takeShipmentsFromStorageAndDeliver(this);
            if(!shipments.isEmpty()) {
                for (Shipment sh : shipments) {
                    try {
                        Thread.sleep(sh.timeToDeliver());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(firstName + " " + lastName + " delivered a " + sh.getType() + " to " + sh.getReceiver().getFirstName()+" "+sh.getReceiver().getLastName()+" who lives is "+sh.getReceiver().getAddress());
                }
                shipments.clear();
            }
        }
    }

    @Override
    protected int validateYearsOfExperience() {
        return Randomizer.getRandomInt(4,45);
    }
}
