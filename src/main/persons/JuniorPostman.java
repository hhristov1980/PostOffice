package main.persons;

import main.postOffice.PostOffice;
import main.util.Randomizer;

public class JuniorPostman extends Postman{


    public JuniorPostman(String firstName, String lastName, PostOffice postOffice) {
        super(firstName, lastName, postOffice);
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            postOffice.collectLetters(this);
            postOffice.dropLetters(this);
        }
    }

    @Override
    protected int validateYearsOfExperience() {
        return Randomizer.getRandomInt(1,3);
    }
}
