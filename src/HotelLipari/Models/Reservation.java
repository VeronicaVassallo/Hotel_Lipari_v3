package HotelLipari.Models;

import java.util.Date;

public class Reservation {
	String idReservation;
   String idCamera;
   String idUser;
   Date startDate;
   Date endDate;
	   
   public Reservation(String idReservation, String idCamera, String idUser, Date startDate, Date endDate ) 
   {
	   this.idReservation = idReservation;
	   this.idCamera = idCamera;
	   this.idUser = idUser ;
	   this.startDate = startDate;
	   this.endDate = endDate;
   }
   
   public String getIdReservation()
   {
	   return this.idReservation;
   }
   
   public String getIdCamera()
   {
	   return this.idCamera;
   }
   
   public String getIdUser()
   {
	   return this.idUser;
   }
   
   public Date getStartDate()
   {
	   return this.startDate;
   }
   
   public Date getEndDate()
   {
	   return this.endDate;
   }
}
