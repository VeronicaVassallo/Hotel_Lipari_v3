package HotelLipari.Models;

public class Room {
	String idCamera;
	 int postiLetto;
	 int costoCamera;
	 boolean suit;
    
	  public Room(String idCamera, int postiLetto, int costoCamera, boolean suit) 
	  {
	     this.idCamera = idCamera;
	     this.postiLetto = postiLetto;
	     this.costoCamera = costoCamera;
	     this.suit = suit;
	  }
	  
	  public String getIdCamera() 
	  {
		  return this.idCamera;
	  }
	  
	  public int getPostiLetto() 
	  {
		  return this.postiLetto;
	  }
	  
	  public int getCostoCamera() 
	  {
		  return this.costoCamera;
	  }
}
