package Hotel_Lipari;

import java.util.HashMap;
import java.util.Scanner;
import HotelLipari.Services.*;
import HotelLipari.Models.*;

public class Main {
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		//Dati di prova :
		HashMap<String, User> listUsers = UserService.populateListUsers_Csv();

		HashMap<String, Room> listRooms = RoomService.populateListRooms_Csv();
		
		HashMap<String, Reservation> listReservation = ReservationService.populateListReservations_Csv();
		
		 boolean isClosed = false;
	        while(!isClosed) {
	        	 // Schermata iniziale
	            System.out.println("Benventi all'Hotel Lipari");
	            //login che ci mostra le operazioni da fare se siamo utenti gia registrati, o ci vogliamo registrare
	            login(listUsers);
	            System.out.println("Inserisci le tue credenziali, Email:");
	            String emailUser = scanner.nextLine();
	            System.out.println("Inserisci le tue credenziali, Password");
	            String passwordUser = scanner.nextLine();

	            // variabile dove vado a conservare i dati dell'Utente loggato (cliente o admin)
	            // che fa il login:
	            User userLoggedIn = UserService.getUser(emailUser, passwordUser, listUsers);

	            if (userLoggedIn != null) {
	                if (!userLoggedIn.isAdmin()) {
	                    showHome(userLoggedIn, listReservation, listRooms);
	                    isClosed = true;
	                } else {
	                    showBackOffice(userLoggedIn, listReservation, listRooms, listUsers);
	                    isClosed = true;
	                }
	            } else {
	                System.out.println("Email o Password errate");
	                isClosed = false;
	            }
	        }
		
		scanner.close();
	}
	
	
	
	private static void login(HashMap<String, User> listUsers) {
		boolean f = false;
		while(!f) {
			System.out.println("Digita il numero corrispondente all'operazione che vuoi svolgere:");
			System.out.println("1: Login , 2: Registrati");
			int inputLogin = scanner.nextInt();
			scanner.nextLine();
			if(inputLogin == 1) {
				f = true;
			}else if(inputLogin == 2){
				System.out.println("Registrati:");
				listUsers = UserService.createNewUser_Csv(scanner, listUsers);
				f = true; 
			}else {
				System.out.println("Valore non valido!");
			}
		}
	} 
	
	


	private static void showHome(User userLoggedIn, HashMap<String, Reservation> listReservation, HashMap<String, Room> listRooms ) {
        boolean stopLoop = false;
        System.out.println("----------------------------------");
        System.out.println("Benvenuto al Home! " + userLoggedIn.getNome() + " " + userLoggedIn.getCognome());
        while (!stopLoop) {
            System.out.println("Digita il numero corrispondente all'operazione che vuoi svolgere:");
            System.out.println("1: Visualizza le tue prenotazioni, 2: Crea nuova prenotazione, 3: Elimina prenotazione 0: ESCI");
            
            
            int codeUser = -1;
          
                if (scanner.hasNextInt()) {
                    codeUser = scanner.nextInt();
                } else {
                    String input = scanner.next(); 
                    System.out.println("Input non valido: " + input);
                    continue;
                }
         
            scanner.nextLine();

            switch (codeUser) {
                case 1:
                    // funzione che mi fa visualizzare le mie prenotazioni
                	System.out.println("----------------------------------");
                    HashMap<String, Reservation> userReservation = ReservationService.getReservation(userLoggedIn.getEmail(), listReservation);
                    RoomService.printRooms(userReservation);
                    break;
                case 2:
                	//Operazione che mi permette di creare una nuova prenotazione
                	System.out.println("----------------------------------");
					Reservation newReservation = RoomService.takeRoom(scanner, userLoggedIn.getEmail(), listRooms, listReservation);
                	listReservation.put(newReservation.getIdReservation(),newReservation);
                	//variabile che mi ritorna le prenotazioni dell'utente loggato
                	userReservation = ReservationService.getReservation(userLoggedIn.getEmail(), listReservation);
                	RoomService.printRooms(userReservation);
            
                    break;
                case 3:
                    // Operazione che mi fa vedere le mie prenotazioni e mi fa scegliere quale eliminare
                	System.out.println("----------------------------------");
                	userReservation = ReservationService.getReservation(userLoggedIn.getEmail(), listReservation);
                	RoomService.printRooms(userReservation);
                	//variabile che mi ritorna tutte la lista delle prenotazioni tranne quella che ho eliminato
                	listReservation = ReservationService.deleteReservation_Csv(scanner, listReservation);
                	//varibile dove inserisco e mi filtro solo le prenotazioni dell'utente loggato
                	userReservation = ReservationService.getReservation(userLoggedIn.getEmail(), listReservation);
                	RoomService.printRooms(userReservation);
               
                    break;
                case 0:
                    stopLoop = true;
                    System.out.println("Grazie, alla prossima!");
                    break;
                default:
                    // mi ritorna l'errore che ho sbagliato codice e mi ricarica questa schermata di selezione
                    System.out.println("Valore non consentito!");
                    break;
            }
        }
 
    }


    private static void showBackOffice(User userLoggedIn, HashMap<String, Reservation> listReservation, HashMap<String, Room> listRooms, HashMap<String, User> listUsers) {
    	boolean isStopped = false;
    	System.out.println("----------------------------------");
        System.out.println("Benvenuto Capo " + userLoggedIn.getNome() + " " + userLoggedIn.getCognome() + " !");
        System.out.println();
        while (!isStopped) {
            System.out.println("Digita il numero corrispondente all'operazione che vuoi svolgere:");
            System.out.println("1: Visualizza TUTTE le prenotazioni, 2: Crea nuova prenotazione, 3: Elimina prenotazione 0: ESCI");
            
            
            int codeUser = -1;
          
                if (scanner.hasNextInt()) {
                    codeUser = scanner.nextInt();
                } else {
                    String input = scanner.next(); 
                    System.out.println("Input non valido: " + input);
                    continue;
                }
         
            scanner.nextLine();

            switch (codeUser) {
                case 1:
                    // funzione che mi fa visualizzare TUTTE le  prenotazioni dell hotel
                	System.out.println("----------------------------------");
                	ReservationService.getAllReservation(listReservation);
                    break;
                case 2:
                	//Operazione che mi permette di creare una nuova prenotazione
                	//variabile che contiene tutta la lista dei clienti senza l'admin
                	System.out.println("----------------------------------");
                	listUsers = UserService.getAllRegisteredCustomers(listUsers, userLoggedIn.getEmail() );
                	UserService.printUsers(listUsers);
                    String inputUser = UserService.checkIdUser(scanner, listUsers);
                	//funzione che fa il controllo se id del cliente esiste
					Reservation newReservation = RoomService.takeRoom(scanner, inputUser, listRooms, listReservation);
                	listReservation.put(newReservation.getIdReservation(), newReservation);
                	//variabile che mi ritorna le prenotazioni dell'utente loggato
                	ReservationService.getAllReservation(listReservation);
                    break;
                case 3:
                
                	//Operazione che mi permette di eliminare una nuova prenotazione
                	System.out.println("----------------------------------");
                	ReservationService.getAllReservation(listReservation);
                	ReservationService.deleteReservation_Csv(scanner, listReservation);
                	ReservationService.getAllReservation(listReservation);
               
                    break;
                case 0:
                	isStopped = true;
                    System.out.println("Grazie, alla prossima!");
                    break;
                default:
                    // mi ritorna l'errore che ho sbagliato codice e mi ricarica questa schermata di selezione
                    System.out.println("Valore non consentito!");
                    break;
            }
        }
    }


}
