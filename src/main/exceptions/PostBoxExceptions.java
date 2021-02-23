package main.exceptions;

public class PostBoxExceptions extends Exception{
    public PostBoxExceptions(){
        super("You cannot put parcel at post box! Please go to the Post Ofice!");
    }
}
