package shop;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InserisciAdmin {

    public void inserisciAdmin(String codiceUtente){

        MysqlConnection db = MysqlConnection.getDbCon();

        try {
            PreparedStatement preparedStatement = db.conn.prepareStatement("UPDATE UTENTE SET ADMIN = ? WHERE CODICE_UTENTE = ?"); //Preparo l'Updtate dell'Utente ad admin
            preparedStatement.setInt(1, 1); //Setto la variabile admin ad 1, in tal modo l'utente una volta loggato avrà i privilegi da amministratore
            preparedStatement.setString(2, codiceUtente); //Setto il codice dell'utente al quale devono essere concessi i privilegi da Admin
            preparedStatement.executeUpdate(); //Eseguo l'update
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
