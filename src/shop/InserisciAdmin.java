package shop;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InserisciAdmin {

    public void inserisciAdmin(String codiceUtente){

        MysqlConnection db = MysqlConnection.getDbCon();

        try {
            PreparedStatement preparedStatement = db.conn.prepareStatement("UPDATE UTENTE SET ADMIN = ? WHERE CODICE_UTENTE = ?");
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, codiceUtente);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
