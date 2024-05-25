package HotelLipari.Services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import HotelLipari.Models.*;

public class UserService {

	public static HashMap<String, User> populateListUsers(){
		 // clienti 
	        User cliente1 = new User(false, "123", "Tonino", "Barba", "12345678", "tonino@barba.com",
	                "tonino", "via Roma 12, Milano", "000000");
	        User cliente2 = new User(false, "567", "Giuliano", "Grigio", "12345897", "giuliano@grigio.com",
	                "giuliano", "piazza della Liberta 8, Messina", "989876");

	        // ADMIN
	        User amministratore1 = new User(true, "adm1", "Mario", "Rossi", "332456712", "mario@rossi.com",
	                "rossi", "", "");
	        
	        // lista Utenti (Clienti + Admin)
	        HashMap<String, User> listUsers = new HashMap<>();
	        listUsers.put(cliente1.getEmail(), cliente1);
	        listUsers.put(cliente2.getEmail(), cliente2);
	        listUsers.put(amministratore1.getEmail(), amministratore1);
	        
	        return listUsers;
	        
	 }

	 public static HashMap<String, User> populateListUsers_Csv()
	 {
		ArrayList<String[]> data = CommonService.getData("C:\\Users\\danid\\eclipse-workspace\\Hotel_Lipari_v3\\src\\data\\Users.csv");
		//lista clienti
		HashMap<String, User> listUsers = new HashMap<>();
		for (String[] record : data) {
			User newUser = new User(CommonService.convertStringToBoolean(record[0]) , record[1], record[2], record[3], record[4], record[5], record[6], record[7], record[8]);
			listUsers.put(newUser.getEmail(), newUser);
			CommonService.id++;
		}
		   
		return listUsers;	   
	}
	
	public static HashMap<String, User> createNewUser(Scanner scanner, HashMap<String, User> listUser) {
		System.out.println("Inserisci Nome:");
		String inputName = scanner.nextLine();
		System.out.println("Inserisci Cognome:");
		String inputSurname = scanner.nextLine();
		System.out.println("Inserisci Cellulare:");
		String inputCell = scanner.nextLine();
		System.out.println("Inserisci Email:");
		String inputEmail = scanner.nextLine();
		System.out.println("Inserisci Password:");
		String inputPassword = scanner.nextLine();
		System.out.println("Inserisci Indirizzo:");
		String inputAddress = scanner.nextLine();
		System.out.println("Inserisci numero Carta di credito:");
		String inputCreditCard = scanner.nextLine();

		User newUser = new User(false, String.valueOf(CommonService.id++), inputName, inputSurname, inputCell, inputEmail, inputPassword, inputAddress, inputCreditCard  );
		listUser.put(newUser.getEmail(), newUser);
		System.out.println("Registrazione Avvenuta con successo!!!");
		System.out.println();
		return listUser;
	}

	public static HashMap<String, User> createNewUser_Csv(Scanner scanner, HashMap<String, User> listUser) {
		System.out.println("Inserisci Nome:");
		String inputName = scanner.nextLine();
		System.out.println("Inserisci Cognome:");
		String inputSurname = scanner.nextLine();
		System.out.println("Inserisci Cellulare:");
		String inputCell = scanner.nextLine();
		System.out.println("Inserisci Email:");
		String inputEmail = scanner.nextLine();
		System.out.println("Inserisci Password:");
		String inputPassword = scanner.nextLine();
		System.out.println("Inserisci Indirizzo:");
		String inputAddress = scanner.nextLine();
		System.out.println("Inserisci numero Carta di credito:");
		String inputCreditCard = scanner.nextLine();

		User newUser = new User(false, String.valueOf(CommonService.id++), inputName, inputSurname, inputCell, inputEmail, inputPassword, inputAddress, inputCreditCard  );
		//uso sempre listUser, anche se salvo nel csv, perché altrimenti dovrei ricaricarmi i dati dal csv
		listUser.put(newUser.getEmail(), newUser);

		//Inserisco il nuovo utente nel csv
		String[] newUserString = new String[]{
			CommonService.convertBooleanToString(newUser.isAdmin()),
			newUser.getIdUtente(),
			newUser.getNome(),
			newUser.getCognome(),
			newUser.getCell(),
			newUser.getEmail(),
			newUser.getPassword(),
			newUser.getIndirizzo(),
			newUser.getDatiCartaDiCredito()
		};
		CommonService.insertDataToFile("C:\\Users\\danid\\eclipse-workspace\\Hotel_Lipari_v3\\src\\data\\Users.csv", newUserString);

		System.out.println("Registrazione Avvenuta con successo!!!");
		System.out.println();
		return listUser;
	}
	
	public static User getUser(String email, String password, HashMap<String, User> usersList) {
		
		User user = usersList.get(email);
		if(user != null && user.getPassword().equals(password)){
			return usersList.get(email);
		}else {
			  return null;
		}
      
    }
	
	public static HashMap<String, User> getAllRegisteredCustomers(HashMap<String, User> listUser, String emailAdmin) {
		 //operazione per rimuovere Admin Dalla lista degli user
		listUser.remove(emailAdmin);	 
		 return listUser;
		 
	 }
	 
	 public static void printUsers(HashMap<String, User> listUser) {
		 System.out.println("Dati utenti registati: ");
		 
		 for(User userValue : listUser.values()) {
			 System.out.println
  			 (
  					 " Nome e cognome: " + userValue.getNome() + 
  					 " " + userValue.getCognome() + 
  					 " Email: " + userValue.getEmail() +
  					 " cell: " + userValue.getCell() 
					 
  			 );
		 }
	 }
	 public static String checkIdUser(Scanner scanner, HashMap<String, User> listUser) {
		 String inputUser = "";
		 boolean finish = false;
		 while(!finish) {
			 System.out.println("Digitare l'email del cliente che si vuole prenotare: ");
	     	inputUser = scanner.nextLine();
	     	if(listUser.get(inputUser) != null) {
	     		finish = true;
	     	}else {
	     		System.out.println("Email cliente non trovata o utente non registrato");
	     	}
			
	     	
		 }
		
    	 return inputUser;
	 }
}
