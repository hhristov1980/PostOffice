package main.postBoxes;

import main.exceptions.PostBoxExceptions;
import main.postOffice.PostOffice;
import main.shipments.Letter;
import main.shipments.Shipment;

import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostBox {
    private static int uniqueId = 1;
    private String postBoxId;
    private PostOffice postOffice;
    private CopyOnWriteArrayList<Shipment> lettersInBox;

    public PostBox (PostOffice postOffice){
        this.postOffice = postOffice;
        this.postBoxId = "Post Box "+uniqueId++;
        lettersInBox = new CopyOnWriteArrayList<>();
    }

    public void addLetter(Shipment shipment){
        if(shipment.getType().equals(Shipment.ShipmentType.LETTER)){
            lettersInBox.add(shipment);
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
    public HashSet<Shipment> emptyBox(){
        HashSet<Shipment> set = new HashSet<>();
        if(!lettersInBox.isEmpty()){
            set.addAll(lettersInBox);
            set.clear();
        }
        else {
            System.out.println(postBoxId+" is empty!");
        }
        return set;
    }

    public String getPostBoxId() {
        return postBoxId;
    }
}
