package HotelLipari.Services;
import HotelLipari.Models.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;




public class RoomService {
	
	public static HashMap<String, Room> populateListRooms_Csv()
	{
	   ArrayList<String[]> data = CommonService.getData("C:\\Users\\danid\\eclipse-workspace\\Hotel_Lipari_v3\\src\\data\\Rooms.csv");
	   //lista clienti
	   HashMap<String, Room> listRooms = new HashMap<>();
	   for (String[] record : data) {
		   Room newRoom = new Room(record[0] , Integer.parseInt(record[1]) , Integer.parseInt(record[2]), CommonService.convertStringToBoolean(record[3]));
		   listRooms.put(newRoom.getIdCamera(), newRoom);
	   }
		  
	   return listRooms;	   
   }
	
	
	 public static void printRooms(HashMap<String, Reservation> listReservation) {
		 System.out.println("Le tue prenotazioni:");
		 if(listReservation.size() == 0) {
			 System.out.println("Non hai nessuna prenotazione al momento");
		 }else {
			 
			 for(Reservation reservationValue : listReservation.values() ) {
				 
				 System.out.println("Hai prenotato la camera: " + reservationValue.getIdCamera() +
	                     " Dal " + CommonService.convertFormatDate(reservationValue.getStartDate()) + " al " +
	                     CommonService.convertFormatDate(reservationValue.getEndDate()) );
			 }
			 
		 }
	 }

	 
	 public static Reservation takeRoom(Scanner scanner, String emailUser, HashMap<String, Room> listRooms, HashMap<String, Reservation> listReservation)
	 {
		String idReservation = CommonService.generateRandomId();
		Date startDate = null;
		Date endDate = null;
		boolean validDates = false;
	
		// Ciclo per ottenere date valide
		while (!validDates) {
			System.out.println("Digita la data di INIZIO del pernottamento, nel formato dd/MM/yyyy:");
			String startDateReservation = scanner.nextLine();
			System.out.println("Digita la data di FINE del pernottamento, nel formato dd/MM/yyyy:");
			String endDateReservation = scanner.nextLine();
	
			startDate = CommonService.convertStringToDate(startDateReservation);
			endDate = CommonService.convertStringToDate(endDateReservation);
	
			if (startDate != null && endDate != null && startDate.before(endDate)) {
				System.out.println("Stanze disponibili dal: " + CommonService.convertFormatDate(startDate) + " al " + CommonService.convertFormatDate(endDate));
				validDates = true;
			} else {
				System.out.println("Formato data non valido: la data di inizio deve essere prima della data di fine o i caratteri utilizzati non sono validi");
			}
		}
	
		//Ottieni stanze libere
		HashMap<String, Room> freeRooms = getFreeRooms(listRooms, listReservation, startDate, endDate);
	
		//Mostra stanze disponibili
		freeRooms.values().forEach(room -> System.out.println("- CAMERA Numero: " + room.getIdCamera() + " Posti letto: " + room.getPostiLetto() + " Costo a notte: " + room.getCostoCamera() + " Euro"));
	
		//Chiedi all'utente di scegliere una stanza
		String codeRoom = checkIdRoom(scanner, listRooms);
		Reservation newReservationUser = new Reservation(idReservation, codeRoom, emailUser, startDate, endDate);
	
		//Salva la prenotazione su file CSV
		String[] newReservationString = {
			idReservation,
			codeRoom,
			emailUser,
			CommonService.convertFormatDate(startDate),
			CommonService.convertFormatDate(endDate)
		};
		CommonService.insertDataToFile("C:\\Users\\danid\\eclipse-workspace\\Hotel_Lipari_v3\\src\\data\\Reservations.csv", newReservationString);
	
		System.out.println("Prenotazione avvenuta con successo!");
		return newReservationUser;
	 }


	 
	 private static HashMap<String, Room> getFreeRooms(HashMap<String, Room> rooms, HashMap<String, Reservation> reservationList, Date startDate, Date endDate) {

		 

	        //Lista id delle stanze GIA' prenotate
	        ArrayList<String> roomsBookedIds = new ArrayList<>();
	        
	        //Ritorna id delle stanze prenotate
	        for (Reservation reservationValue : reservationList.values()) {
	            if (startDate.before(reservationValue.getEndDate()) && endDate.after(reservationValue.getStartDate())) {
	                roomsBookedIds.add(reservationValue.getIdCamera());
	            }
	        }

	        //Ottenere il Set delle chiavi delle stanze
	        Set<String> roomKeys = rooms.keySet();
	        
	        //Le stanze che coincidono con le mie date
	        HashMap<String, Room> freeRooms = new HashMap<>();

	        //Se l'id delle prenotate non è compreso, me lo inserisci nella lista delle libere
	        for (String key : roomKeys) {
	            if (!roomsBookedIds.contains(key)) {
	                freeRooms.put(key, rooms.get(key));
	            }
	        }

	        return freeRooms; 
	    }
	 
	 private static String checkIdRoom(Scanner scanner, HashMap<String, Room> rooms) {
		 String inputUser = "";
		 boolean finish = false;
		 
		 while(!finish) {
			 System.out.println("Digita il numero della camera che si desidera prenotare: ");
	     	inputUser = scanner.nextLine();
	     	if(rooms.get(inputUser) != null) {
	     		finish = true;
	     	}
	     	 if(!finish) {
	     		System.out.println("Camera non trovato");
	     	 }
	     	
		 }
		
    	 return inputUser;
	 }

	
}
