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
        fornitore.setPkFornitore(UUID.randomUUID().toString());
        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("INSERT INTO FORNITORE (NOME_FORNITORE, CODICE_FORNITORE) " + "VALUES(?, ?)");
            preparedStatement.setString(1, fornitore.getNomeFornitore());
            preparedStatement.setString(2, fornitore.getPkFornitore());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FornitoreModel selectFornitore(String nomeFornitore){
        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT * FROM FORNITORE WHERE NOME_FORNITORE = ?");
            preparedStatement.setString(1, nomeFornitore);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                nomeFornitore = rs.getString(1);
                pkFornitore = rs.getString(2);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        FornitoreModel fornitore = new FornitoreModel(nomeFornitore, pkFornitore);
        System.out.println(fornitore.getPkFornitore() + " " + fornitore.getNomeFornitore());
        return fornitore;
    }

}
