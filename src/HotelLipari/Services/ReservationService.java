package HotelLipari.Services;
import HotelLipari.Models.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;




public class ReservationService {


	public static HashMap<String, Reservation> populateListReservations_Csv()
	{
	   ArrayList<String[]> data = CommonService.getData("C:\\Users\\danid\\eclipse-workspace\\Hotel_Lipari_v3\\src\\data\\Reservations.csv");
	   //lista clienti
	   HashMap<String, Reservation> listReservations = new HashMap<>();
	   for (String[] record : data) {
		   Reservation newReservation = new Reservation(record[0] , record[1], record[2], CommonService.convertStringToDate(record[3]), CommonService.convertStringToDate(record[4]));
		   listReservations.put(newReservation.getIdReservation(), newReservation);
	   }
		  
	   return listReservations;	   
   }

	
	public static HashMap<String, Reservation> getReservation(String _emailUser, HashMap<String, Reservation> reservationList) {
		HashMap<String, Reservation> reservationsUser = new HashMap<>();
		for (Reservation valueReservation :  reservationList.values() ) {
			if (valueReservation.getIdUser().equals(_emailUser)) {
                reservationsUser.put(valueReservation.getIdReservation(), valueReservation );
            }
		}
		
        return reservationsUser;
    }
	
	public static HashMap<String, Reservation> deleteReservation(Scanner scanner, HashMap<String, Reservation> listReservation) {
		 boolean flag = false;
		 while(!flag) {
			 System.out.println("Digita CODICE PRENOTAZIONE della prenotazione che si desidera eliminare: ");
			 //printRooms(userReservation);
			 String codeReservation = scanner.nextLine();
			 //mi cancello dalla lista di Tutte le prenotazioni quella che voglio eliminare
			 
			 if(listReservation.get(codeReservation) != null) {
				 listReservation.remove(codeReservation);
				 flag = true;
			 }
			 
			 if(!flag)
				 System.out.println("Il CODICE PRENOTAZIONE non e' corretto!");
		 }
		 System.out.println("Prenotazione eliminata con successo!");
		 return listReservation;
	 }

	 //to do: cancellare un rigo dal file.csv delle prenotazioni
	 public static HashMap<String, Reservation> deleteReservation_Csv(Scanner scanner, HashMap<String, Reservation> listReservation) {
		String filePath = "C:\\Users\\danid\\eclipse-workspace\\Hotel_Lipari_v3\\src\\data\\Reservations.csv";

		// List di tutte le righe
		List<String> lines = new ArrayList<>();
	
		// Lettura del file CSV
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		boolean flag = false;
		while (!flag) {
			System.out.println("Digita CODICE PRENOTAZIONE della prenotazione che si desidera eliminare: ");
			String codeReservation = scanner.nextLine();
	
			// Cancello dalla lista di tutte le prenotazioni quella che voglio eliminare
			if (listReservation.get(codeReservation) != null) {
				listReservation.remove(codeReservation);
				flag = true;
	
				// Rimozione della riga desiderata dal CSV
				Iterator<String> iterator = lines.iterator();

				while (iterator.hasNext()) {
					String line = iterator.next();
					if (line.contains(codeReservation)) {
						iterator.remove();
					}
				}
	
				// Scrittura del nuovo contenuto nel file CSV esistente
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
					for (String line : lines) {
						bw.write(line);
						bw.newLine();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				System.out.println("Prenotazione eliminata con successo!");
			} else {
				System.out.println("Il CODICE PRENOTAZIONE non è corretto!");
			}
		}
	
		return listReservation;
	}
	
	public static void getAllReservation(HashMap<String, Reservation> listReservation){
		System.out.println();
     	System.out.println("Lista di tutte le prenotazioni: ");
     	System.out.println("Ci sono al momento:" + listReservation.size() + " prenotazioni");
     	for(Reservation reservation : listReservation.values() ) {
     		 System.out.println("Camera N° : " + reservation.getIdCamera() +
	                     " prenotata Dal " + CommonService.convertFormatDate(reservation.getStartDate()) + " al " +
	                     CommonService.convertFormatDate(reservation.getEndDate()) + " CODICE PRENOTAZIONE: " + reservation.getIdReservation() );
     	}
    
	 }

	 
}
