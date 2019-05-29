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
        categoria.setPkCategoria(UUID.randomUUID().toString());
        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("INSERT INTO CATEGORIA (NOME_CATEGORIA, ID_CATEGORIA) " + "VALUES (?, ?)");
            preparedStatement.setString(1, categoria.getNomeCategoria());
            preparedStatement.setString(2, categoria.getPkCategoria());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CategoriaModel selectCategoria(String nomeCategoria){
        MysqlConnection db = MysqlConnection.getDbCon();
        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT * FROM CATEGORIA WHERE NOME_CATEGORIA = ?");
            preparedStatement.setString(1, nomeCategoria);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                nomeCategoria = rs.getString(1);
                pkCategoria = rs.getString(2);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CategoriaModel categoria = new CategoriaModel(nomeCategoria, pkCategoria);
        System.out.println(categoria.getNomeCategoria() + " " + categoria.getPkCategoria());
        return categoria;
    }
}
