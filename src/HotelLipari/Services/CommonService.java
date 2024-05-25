package HotelLipari.Services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.Locale;
import java.util.Scanner;


public class CommonService {
	public static int id = 882; //contatore usato per l'id utente
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int ID_LENGTH = 6; // Lunghezza dell'ID

	// Genera un ID casuale alfanumerico
    public static String generateRandomId() {
		

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(ID_LENGTH);

        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
	
	public static Date convertStringToDate(String dateString) 
	{ 
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false); // evita che ci possono essere date non valide
        try 
        {
            return dateFormat.parse(dateString);
        } 
        catch (ParseException e) 
        {
            // Gestisce l'eccezione se la stringa non può essere convertita in una data
            return null;
        }
	 }
	
	//funzione che converte la data scritta cosi: Dal Mon May 20 00:00:00 CEST 2024 a cosi 20/05/2024
		 public static String convertFormatDate(Date userDate) {
		        // Formato originale della data
		        SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

		        // Nuovo formato della data
		        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");

		        // Conversione delle date nel nuovo formato
				String formattedStartDate = newFormat.format(userDate);
				
				return formattedStartDate;	
		 }
	
	//Funzione che mi prende le informazioni dal files csv nella cartella data
	public static ArrayList<String[]> getData(String filePath) 
	{
		ArrayList<String[]> records = new ArrayList<>();
		File file = new File(filePath);

		if (file.exists() && !file.isDirectory()) { // Se il file esiste e non è una cartella
			try (Scanner fileReader = new Scanner(file)) { // Utilizza try-with-resources per chiudere automaticamente lo Scanner
				while (fileReader.hasNextLine()) { // continua a leggere finché ci sono righe
					String line = fileReader.nextLine();
					String[] listData = line.split(";", -1);
					records.add(listData);
				}
			} catch (FileNotFoundException e) {
				System.out.println("File non trovato");
				e.printStackTrace();
			}
		} else {
			System.out.println("Il file non esiste o è una directory");
		}

		return records;
	}

	public static boolean convertStringToBoolean(String value){
		if(value.toLowerCase().equals("true"))
			return true;
		else
			return false;
	}

	public static String convertBooleanToString(boolean value){
		if(value)
			return "true";
		else
			return "false";
	}

	public static void insertDataToFile(String filePath, String[] record)
	{
		String nuovaRiga = "";
		for (String item : record) 
		{
			nuovaRiga += item + ";";
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(nuovaRiga);
            writer.newLine(); // Aggiungi un nuovo line separator
        } catch (IOException e) {
            System.err.println("Errore durante l'aggiunta della riga: " + e.getMessage());
        }
	}
	
}

