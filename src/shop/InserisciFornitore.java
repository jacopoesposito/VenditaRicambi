package shop;

import shop.model.FornitoreModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class InserisciFornitore {

    private String nomeFornitore;
    private String pkFornitore;

    public void insertFornitore(FornitoreModel fornitore){
        fornitore.setPkFornitore(UUID.randomUUID().toString()); //Genero la chiave primaria del fornitore
        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al DB.

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("INSERT INTO FORNITORE (NOME_FORNITORE, CODICE_FORNITORE) " + "VALUES(?, ?)");
            preparedStatement.setString(1, fornitore.getNomeFornitore()); //Setto il nome del fornitore
            preparedStatement.setString(2, fornitore.getPkFornitore()); //Setto la chiave primaria del fornitore
            preparedStatement.executeUpdate(); //Eseguo l'update
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FornitoreModel selectFornitore(String nomeFornitore){
        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al db

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT * FROM FORNITORE WHERE NOME_FORNITORE = ?"); //Preparo la query
            preparedStatement.setString(1, nomeFornitore); //Setto il nome del fornitore che dobbiamo cercaro
            ResultSet rs = preparedStatement.executeQuery(); //Eseguo la query
            while(rs.next()){
                //Recupero i valori restituiti dalla query
                nomeFornitore = rs.getString(1);
                pkFornitore = rs.getString(2);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        FornitoreModel fornitore = new FornitoreModel(nomeFornitore, pkFornitore);  //Instanzio un oggetto forntitore di tipo FornitoreModel passando al costruttore come parametri il nome e la PK.
        System.out.println(fornitore.getPkFornitore() + " " + fornitore.getNomeFornitore());
        return fornitore;
    }

}
