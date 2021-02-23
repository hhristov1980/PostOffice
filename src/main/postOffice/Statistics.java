package main.postOffice;

import main.shipments.Shipment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class Statistics extends Thread{
    private PostOffice postOffice;

    public Statistics(PostOffice postOffice){
        this.postOffice = postOffice;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createBackUp();


        }
    }
//public ConcurrentSkipListMap<LocalDate, ConcurrentSkipListMap<Shipment.ShipmentType, ConcurrentSkipListMap<Integer, LocalTime>>> getArchive() {
//        return archive;

    private void createBackUp(){
        File f = new File(LocalDateTime.now()+" archive.txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(PrintStream ps = new PrintStream(f)){
            ps.println("================ ARCHIVE ==============");
            for(Map.Entry<LocalDate, ConcurrentSkipListMap<Shipment.ShipmentType, ConcurrentSkipListMap<Integer, LocalTime>>> date:postOffice.getArchive().entrySet()){
                ps.print("Date: ");
                ps.println(date.getKey());
                for(Map.Entry<Shipment.ShipmentType, ConcurrentSkipListMap<Integer, LocalTime>>type:date.getValue().entrySet()){
                    ps.println("\t"+"Shipment type: ");
                    ps.println("\t"+type.getKey());
                    int counter = 0;
                    for(Map.Entry<Integer, LocalTime> time: type.getValue().entrySet()){
                        ps.println("\t\t"+"Shipment Id "+time.getKey()+" Time: "+time.getValue());
                        counter++;
                    }
                    ps.println("\t\t"+"Total "+type.getKey()+": "+counter);
                    ps.println("------------------------------------------");
                }
            }
            ps.println("================ END OF ARCHIVE ==============");
        } catch (FileNotFoundException e) {
            System.out.println("Not able to create archive!");
        }
    }


}
