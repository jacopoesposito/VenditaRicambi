package shop;

import shop.model.UserModel;
import shop.userDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoginUser implements userDAO {

    private String mail;
    private String nome;
    private String cognome;
    private String password;
    private int numeroCivico;
    private String nomeVia;
    private String citta;
    private String salt;

    @Override
    public UserModel selectUser(String mail) {
        MysqlConnection db = MysqlConnection.getDbCon();
        try {
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT * FROM UTENTE WHERE MAIL = ?");
            preparedStatement.setString(1, mail);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                mail = rs.getString("MAIL");
                nome = rs.getString("NOME");
                cognome = rs.getString("COGNOME");
                password = rs.getString("PASSWORD");
                numeroCivico = rs.getInt("NUMERO_CIVICO");
                nomeVia = rs.getString("VIA");
                citta = rs.getString("CITTA");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserModel user = new UserModel(mail, nome, cognome, password, password, numeroCivico, nomeVia, citta);
        System.out.println(user.getNome());
        return user;
    }

    public String getSalt(String mail){
        MysqlConnection db1 = MysqlConnection.getDbCon();

        try {
            PreparedStatement preparedStatement = db1.conn.prepareStatement("SELECT SALT FROM UTENTE WHERE MAIL = ?");
            preparedStatement.setString(1, mail);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            salt = rs.getString("SALT");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(salt);
        return salt;
    }

    @Override
    public void insertUser(UserModel user) {

    }


    @Override
    public List<UserModel> select() {
        return null;
    }
}
