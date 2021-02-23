package main.postOffice;

import main.persons.JuniorPostman;
import main.persons.SeniorPostman;
import main.postBoxes.PostBox;
import main.shipments.Shipment;
import main.util.Randomizer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.*;


public class PostOffice {
    private String name;
    private volatile int shipmentsInOffice;
    private CopyOnWriteArraySet<PostBox> postBoxes;
    private CopyOnWriteArraySet<JuniorPostman> collectors;
    private CopyOnWriteArraySet<SeniorPostman> distributors;
    private ConcurrentSkipListMap<LocalDate, ConcurrentSkipListMap<Integer,LocalTime>> archive;
    private BlockingQueue<Shipment> postOfficeStorage;

    public PostOffice(String name){
        if(name.length()>0){
            this.name = name;
        }
        postBoxes = new CopyOnWriteArraySet<>();
        collectors = new CopyOnWriteArraySet<>();
        distributors = new CopyOnWriteArraySet<>();
        archive = new ConcurrentSkipListMap<>();
        postOfficeStorage = new LinkedBlockingQueue<>();

    }

    public void addPostBox(PostBox p){
        postBoxes.add(p);
    }

    public void addCollector(JuniorPostman j){
        collectors.add(j);
    }

    public void addDistributor(SeniorPostman s){
        distributors.add(s);
    }
    public void work(){
        for(JuniorPostman j: collectors){
            j.start();
        }
        for(SeniorPostman s: distributors){
            s.start();
        }
    }

    public synchronized void addShipmentInStorage(Shipment shipment){
        shipment.setDateOfComingAtPostOffice(LocalDate.now());
        shipment.setTimeOfComingAtPostOffice(LocalTime.now());
        if(!archive.containsKey(shipment.getDateOfComingAtPostOffice())){
            archive.put(shipment.getDateOfComingAtPostOffice(),new ConcurrentSkipListMap<>());
        }
        archive.get(shipment.getDateOfComingAtPostOffice()).put(shipment.getShipmentId(),shipment.getTimeOfComingAtPostOffice());
        try {
            postOfficeStorage.put(shipment);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        shipmentsInOffice++;
        if(shipmentsInOffice>=50){
            notifyAll();
        }
    }

    public synchronized void sendShipment(Shipment shipment){
        Random rnd = new Random();
        boolean sendInPostBox = rnd.nextBoolean();
        if(sendInPostBox){
            CopyOnWriteArrayList<PostBox> boxes = new CopyOnWriteArrayList<>();
            boxes.addAll(postBoxes);
            PostBox postBox = boxes.get(Randomizer.getRandomInt(0,boxes.size()-1));
            postBox.addLetter(shipment);
        }
        else {
            addShipmentInStorage(shipment);
            shipment.getSender().setSent(true);
        }
    }

    public synchronized void collectLetters(JuniorPostman juniorPostman){
        if(shipmentsInOffice>=50){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(PostBox pb: postBoxes){
            juniorPostman.getShipments().addAll(pb.emptyBox());
            System.out.println(juniorPostman.getFirstName()+" "+juniorPostman.getLastName()+" just checked "+pb.getPostBoxId());
        }
    }

    public synchronized void dropLetters(JuniorPostman juniorPostman){
        if(!juniorPostman.getShipments().isEmpty()){
            for(Shipment sh: juniorPostman.getShipments()){
                addShipmentInStorage(sh);
            }
            System.out.println(juniorPostman.getFirstName()+" "+juniorPostman.getLastName()+" collected "+juniorPostman.getShipments().size()+" letters!");
        }
        else {
            System.out.println(juniorPostman.getFirstName()+" "+juniorPostman.getLastName()+" hasn't collected any letters");
        }
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void takeShipmentsFromStorageAndDeliver(SeniorPostman seniorPostman){
            for(int i = 0; i<50/ distributors.size(); i++){
                if(!postOfficeStorage.isEmpty()){
                    try {
                        seniorPostman.getShipments().offer(postOfficeStorage.take());
                        shipmentsInOffice--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(seniorPostman.getFirstName()+" "+seniorPostman.getLastName()+" took "+seniorPostman.getShipments().size()+" shipments!");
            notifyAll();
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ConcurrentSkipListMap<LocalDate, ConcurrentSkipListMap<Integer, LocalTime>> getArchive() {
        return archive;
    }
}
