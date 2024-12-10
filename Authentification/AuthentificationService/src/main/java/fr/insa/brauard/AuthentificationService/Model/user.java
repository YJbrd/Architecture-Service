package Model.Users;

import java.util.ArrayList;

public class user {

    private static int id;
    private static String pseudo;
    private static String password;
    private ArrayList<?> review;
    private String type;
    
    public user(int id, String pseudo, String password, ArrayList<?> review, String type) {
    	this.id=id;
    	this.pseudo=pseudo;
    	this.password=password;
    	this.review=new ArrayList<>();
    	this.type=type;
    }
    
    public int getID (String pseudo) {
    	return this.id;
    }
    
    public int getPseudo(int ID) {
    	return this.id;
    }
    
    public String getType(){
    	return this.type;
    }
    
    public ArrayList<String> getReviex(){
    	return this.getReviex();
    }
    
}