package shop;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RimuoviRicambio {

    public void rimuoviRicambio(String pkProdotto){

        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al DB.

        try {
            PreparedStatement preparedStatement = db.conn.prepareStatement("UPDATE PRODOTTO SET VISIBILE = ? WHERE CODICE_PRODOTTO = ?");
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, pkProdotto);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
