package main;

import main.persons.Citizen;
import main.persons.JuniorPostman;
import main.persons.SeniorPostman;
import main.postBoxes.PostBox;
import main.postOffice.PostOffice;
import main.postOffice.Statistics;
import main.util.Constants;
import main.util.Randomizer;

import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        PostOffice postOffice = new PostOffice("IT Post");
        Statistics stat = new Statistics(postOffice);
        ArrayList<Citizen> citizens = new ArrayList<>();
        stat.setDaemon(true);
        for(int i = 0; i<25; i++){
            postOffice.addPostBox(new PostBox(postOffice));
        }

        for(int i = 0; i<100; i++){
            String firstName = Constants.firstNames[Randomizer.getRandomInt(0,Constants.firstNames.length-1)];
            String lastName = Constants.lastNames[Randomizer.getRandomInt(0,Constants.lastNames.length-1)];
            String address = Constants.address[Randomizer.getRandomInt(0,Constants.address.length-1)];
            citizens.add(new Citizen(firstName,lastName,address,postOffice));
        }
        for(int i = 0; i<5; i++){
            String firstName = Constants.firstNames[Randomizer.getRandomInt(0,Constants.firstNames.length-1)];
            String lastName = Constants.lastNames[Randomizer.getRandomInt(0,Constants.lastNames.length-1)];
            postOffice.addCollector(new JuniorPostman(firstName,lastName,postOffice));
            firstName = Constants.firstNames[Randomizer.getRandomInt(0,Constants.firstNames.length-1)];
            lastName = Constants.lastNames[Randomizer.getRandomInt(0,Constants.lastNames.length-1)];
            postOffice.addDistributor(new SeniorPostman(firstName,lastName,postOffice));
        }


        for(Citizen c: citizens){
            c.start();
        }
        postOffice.work();
        stat.start();

    }
}
