package main.postBoxes;

import main.exceptions.PostBoxExceptions;
import main.postOffice.PostOffice;
import main.shipments.Letter;
import main.shipments.Shipment;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class PostBox {
    private static int uniqueId = 1;
    private String postBoxId;
    private PostOffice postOffice;
    private BlockingQueue<Shipment> lettersInBox;

    public PostBox (PostOffice postOffice){
        this.postOffice = postOffice;
        this.postBoxId = "Post Box "+uniqueId++;
        lettersInBox = new LinkedBlockingQueue<>();
    }

    public void addLetter(Shipment shipment){
        if(shipment.getType().equals(Shipment.ShipmentType.LETTER)){
            try {
                lettersInBox.put(shipment);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shipment.getSender().setSent(true);
        }
        else {
            try {
                throw new PostBoxExceptions();
            } catch (PostBoxExceptions postBoxExceptions) {
                System.out.println(postBoxExceptions.getMessage());
                shipment.getSender().setSent(false);
            }
        }
    }
    public Shipment removeShipment(){
        Shipment shipment;
        try {
            return lettersInBox.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPostBoxId() {
        return postBoxId;
    }

    public BlockingQueue<Shipment> getLettersInBox() {
        return lettersInBox;
    }
}
