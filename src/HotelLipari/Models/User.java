package HotelLipari.Models;

public class User {
	boolean admin;
	String idUtente;
    String nome;
    String cognome;
    String cell;
    String email;
    String password;
    String indirizzo;
    String datiCartaDicredito;

    public User(boolean admin, String idUtente,String nome, String cognome, String cell, String email, String password, String indirizzo, String datiCartaDicredito ) {
    	this.admin = admin;
    	this.idUtente = idUtente;
    	this.nome = nome;
        this.cognome = cognome;
        this.cell = cell;
        this.email = email;
        this.password = password;
        this.indirizzo = indirizzo;
        this.datiCartaDicredito = datiCartaDicredito;
    }
    
    public String getNome() 
    {
    	return this.nome;
    }
    public String getCognome() 
    {
    	return this.cognome;
    }
    
    public String getEmail() 
    {
    	return this.email;
    }
    
    public String getPassword() 
    {
    	return this.password;
    }
    
    public boolean isAdmin() 
    {
    	return this.admin;
    }
    
    public String getIdUtente() 
    {
    	return this.idUtente;
    }
    
    public String getCell()
    {
    	return this.cell;
    }

    public String getIndirizzo()
    {
    	return this.indirizzo;
    }

    public String getDatiCartaDiCredito()
    {
    	return this.datiCartaDicredito;
    }
}
