package shop;

import shop.model.RicambioModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class InserisciRicambio {

    public void insertRicambio(RicambioModel ricambio){
        Float sconto = calcoloSconto(ricambio.getCosto(), ricambio.getPercentualeSconto()); //Calcolo lo sconto che deve avere il ricambio
        ricambio.setCostoScontato(ricambio.getCosto()-sconto); //Setto il prezzo scontato
        ricambio.setPkProdotto(UUID.randomUUID().toString()); //Genero la chiave primaria del ricambio
        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al DB

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("INSERT INTO PRODOTTO (CODICE_PRODOTTO, NOME_PRODOTTO, DESCRIZIONE_PRODOTTO, " +
                    "PERCENTUALE_SCONTO, COSTO, PREZZO_S, FK_CODICE_FORNITORE, FK_ID_CATEGORIA, QUANTITA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"); //Preparo la query di inserimento del ricambio
            preparedStatement.setString(1, ricambio.getPkProdotto()); //Setto la chiava primaria del ricambio
            preparedStatement.setString(2, ricambio.getNomeProdotto()); //Setto il nome del ricambio
            preparedStatement.setString(3, ricambio.getDescrizioneProdotto()); //Setto la descrizione del ricambio
            preparedStatement.setInt(4, ricambio.getPercentualeSconto()); //Setto la percentuale di scambio
            preparedStatement.setFloat(5, ricambio.getCosto()); //Setto il costo del prodotto
            preparedStatement.setFloat(6, ricambio.getCostoScontato()); //Setto il costo scontato del prodotto
            preparedStatement.setString(7, ricambio.getFkFornitore()); //Setto la FK del fornitore
            preparedStatement.setString(8, ricambio.getFkCategoria()); //Setto la FK della categoria
            preparedStatement.setInt(9, ricambio.getQuantita()); //Setto la quantità disponibile
            preparedStatement.executeUpdate(); //Eseguo la query di inserimento
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Float calcoloSconto(Float costo, int percentualeSconto){
        Float prezzoScontato = 0.0F;
        prezzoScontato = (costo * percentualeSconto) / 100;
        return prezzoScontato;
    }

    /*public RicambioModel selectRicambi(){

        return ricambio;
    }*/


    public String selectPkFornitore(String nomeFornitore){
        String pkFornitore = null;

        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connesione al Database

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT CODICE_FORNITORE FROM FORNITORE WHERE NOME_FORNITORE = ?"); //Preparo la query che ritorna il codice del fornitore dato il nome del fornitore
            preparedStatement.setString(1, nomeFornitore); //Setto il nome del fornitore
            ResultSet rs = preparedStatement.executeQuery(); //Eseguo la query
            while (rs.next()){
                pkFornitore=rs.getString(1); //Recupero il codice fornitore che mi ritorna la query precedente
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pkFornitore; //Riotrno il codice del fornitore
    }

    public String selectPkCategoria(String nomeCategoria){
        String pkCategoria = null;

        MysqlConnection db = MysqlConnection.getDbCon();//Recupero la connessione al DB

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT ID_CATEGORIA FROM CATEGORIA WHERE NOME_CATEGORIA = ?");//Preparo la query che data una categoria restituisce la sua Primary Key
            preparedStatement.setString(1, nomeCategoria);//Setto il nome della categoria
            ResultSet rs = preparedStatement.executeQuery();// Eseguo la query
            while (rs.next()){
                pkCategoria=rs.getString(1);//Recupero la chiave primaria della categoria
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pkCategoria; //Ritorno la chiave primaria della categoria
    }

    public int selectQuantitaRicambio(RicambioModel ricambio){
        int quantitaRicambioDB = 0;
        MysqlConnection db = MysqlConnection.getDbCon();//Recupero la connessione al DB
        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT QUANTITA FROM PRODOTTO WHERE CODICE_PRODOTTO = ?");//Preparo la query che dato un codice prodotto ritorna la quantità delle scorte in magazzino dello stesso
            preparedStatement.setString(1, ricambio.getPkProdotto()); //Setto il codice prodotto
            ResultSet rs = preparedStatement.executeQuery(); //Eseguo la query

            while (rs.next()){
                quantitaRicambioDB = rs.getInt("QUANTITA"); //Recupero la quantita delle scorte presenti
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantitaRicambioDB; //Ritorno la quantità
    }

}
