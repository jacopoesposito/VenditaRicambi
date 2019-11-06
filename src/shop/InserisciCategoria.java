package shop;

import shop.model.CategoriaModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class InserisciCategoria {

    private String nomeCategoria;
    private String pkCategoria;

    private String categoria;

    public void insertCategoria(CategoriaModel categoria){
        categoria.setPkCategoria(UUID.randomUUID().toString()); //Genero la chiave primaria della categoria
        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al DB.

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("INSERT INTO CATEGORIA (NOME_CATEGORIA, ID_CATEGORIA) " + "VALUES (?, ?)"); //Preparo la query di inserimento della nuova categoria
            preparedStatement.setString(1, categoria.getNomeCategoria()); //Setto il nome della categoria
            preparedStatement.setString(2, categoria.getPkCategoria()); //Setto la chiave primaria della categoria
            preparedStatement.executeUpdate(); //Eseguo la query
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CategoriaModel selectCategoria(String nomeCategoria){
        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al DB.
        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT * FROM CATEGORIA WHERE NOME_CATEGORIA = ?"); //Preparo la query
            preparedStatement.setString(1, nomeCategoria); //Setto il nome della categoria che dobbiamo cercare
            ResultSet rs = preparedStatement.executeQuery(); //Eseguo la query
            while (rs.next()){
                //Recupero i valori restituiti dalla query
                nomeCategoria = rs.getString(1);
                pkCategoria = rs.getString(2);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CategoriaModel categoria = new CategoriaModel(nomeCategoria, pkCategoria); //Instanzio un oggetto categoria di tipo CategoriaModel passando al costruttore come parametri il nome e la PK.
        System.out.println(categoria.getNomeCategoria() + " " + categoria.getPkCategoria());
        return categoria; //Ritorno la categoria
    }
}
