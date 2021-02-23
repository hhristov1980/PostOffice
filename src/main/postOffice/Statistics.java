package main.postOffice;

public class Statistics extends Thread{
    private PostOffice postOffice;

    public Statistics(PostOffice postOffice){
        this.postOffice = postOffice;
    }
}
