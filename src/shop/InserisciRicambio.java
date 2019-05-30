package shop;

import shop.model.RicambioModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class InserisciRicambio {

    public void insertRicambio(RicambioModel ricambio){
        Float sconto = calcoloSconto(ricambio.getCosto(), ricambio.getPercentualeSconto());
        ricambio.setCostoScontato(ricambio.getCosto()-sconto);
        ricambio.setPkProdotto(UUID.randomUUID().toString());
        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("INSERT INTO PRODOTTO (CODICE_PRODOTTO, NOME_PRODOTTO, DESCRIZIONE_PRODOTTO, " +
                    "PERCENTUALE_SCONTO, COSTO, PREZZO_S, FK_CODICE_FORNITORE, FK_ID_CATEGORIA) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, ricambio.getPkProdotto());
            preparedStatement.setString(2, ricambio.getNomeProdotto());
            preparedStatement.setString(3, ricambio.getDescrizioneProdotto());
            preparedStatement.setInt(4, ricambio.getPercentualeSconto());
            preparedStatement.setFloat(5, ricambio.getCosto());
            preparedStatement.setFloat(6, ricambio.getCostoScontato());
            preparedStatement.setString(7, ricambio.getFkFornitore());
            preparedStatement.setString(8, ricambio.getFkCategoria());
            preparedStatement.executeUpdate();
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

        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT CODICE_FORNITORE FROM FORNITORE WHERE NOME_FORNITORE = ?");
            preparedStatement.setString(1, nomeFornitore);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                pkFornitore=rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pkFornitore;
    }

    public String selectPkCategoria(String nomeCategoria){
        String pkCategoria = null;

        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT ID_CATEGORIA FROM CATEGORIA WHERE NOME_CATEGORIA = ?");
            preparedStatement.setString(1, nomeCategoria);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                pkCategoria=rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pkCategoria;
    }

}
