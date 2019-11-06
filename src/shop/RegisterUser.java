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

        String saltGenerato = generateSalt(); //Genero il codice di Salt.
        System.out.println(saltGenerato);

        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al DB.
        try {
            PreparedStatement preparedStatement = db.conn.prepareStatement("INSERT INTO UTENTE (CODICE_UTENTE , NOME, COGNOME, MAIL, PASSWORD,  VIA, NUMERO_CIVICO, CITTA, SALT) " +
                    "VALUES (? , ?, ?, ?, ?, ?, ?, ?, ?)"); //Preparo la query per l'inserimento dell'utente
            preparedStatement.setString(1, UUID.randomUUID().toString()); //Genero un UUID randomico che servirà da Primary Key.
            preparedStatement.setString(2, user.getNome()); //Recupero il nome dell'User.
            preparedStatement.setString(3, user.getCognome()); //Recupero il cognome dell'User.
            preparedStatement.setString(4, user.getMail()); //Recupero la mail dell'User
            preparedStatement.setString(5, Hashing.sha256().hashString(user.getPassword() + saltGenerato, StandardCharsets.UTF_8).toString());//Eseguo l'hashing della password aggiungendoci il Salt per una maggiore sicurezza.
            preparedStatement.setString(6, user.getNomeVia()); //Recupero l'indirizzo.
            preparedStatement.setInt(7, user.getNumeroCivico()); //Recupero il civico dell'User.
            preparedStatement.setString(8, user.getCitta()); //Recupero la città.
            preparedStatement.setString(9, saltGenerato);   //Salvo il Salt generato-
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

    public String generateSalt(){ //Questa funzione si occupa di generare il Salt.
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
