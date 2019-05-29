package shop;

import com.google.common.hash.Hashing;
import shop.model.UserModel;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RegisterUser implements userDAO {

    @Override
    public void insertUser(UserModel user) {

        String saltGenerato = generateSalt();
        System.out.println(saltGenerato);

        MysqlConnection db = MysqlConnection.getDbCon();
        try {
            PreparedStatement preparedStatement = db.conn.prepareStatement("INSERT INTO UTENTE (CODICE_UTENTE , NOME, COGNOME, MAIL, PASSWORD,  VIA, NUMERO_CIVICO, CITTA, SALT) " +
                    "VALUES (? , ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, user.getNome());
            preparedStatement.setString(3, user.getCognome());
            preparedStatement.setString(4, user.getMail());
            preparedStatement.setString(5, Hashing.sha256().hashString(user.getPassword() + saltGenerato, StandardCharsets.UTF_8).toString());
            preparedStatement.setString(6, user.getNomeVia());
            preparedStatement.setInt(7, user.getNumeroCivico());
            preparedStatement.setString(8, user.getCitta());
            preparedStatement.setString(9, saltGenerato);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public UserModel selectUser(String mail) {
        return null;
    }

    public String generateSalt(){
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        String saltString = salt.toString();
        return saltString;
    }



    @Override
    public List<UserModel> select() {
        return null;
    }
}
