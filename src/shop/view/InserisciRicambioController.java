package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shop.MainApp;
import shop.MysqlConnection;
import shop.model.UserModel;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InserisciRicambioController {

    private MainApp mainApp = new MainApp();
    private UserModel user = new UserModel();
    private Stage dialogStage;
    final ObservableList<String> options = FXCollections.observableArrayList();

    @FXML
    private Button cancel;

    @FXML
    private TextField nomeProdottoTextField;

    @FXML
    private TextField descrizioneProdottoTextField;

    @FXML
    private TextField percentualeScontoTextField;

    @FXML
    private TextField costoTextField;

    @FXML
    private ComboBox<String> categoriaComboBox = new ComboBox<>();

    @FXML
    private ComboBox fornitoreComboBox;

    @FXML
    private void initialize(){
        System.out.println("Here Initialize");
        fillCategoriaCB();
        categoriaComboBox.getItems().addAll(options);
        categoriaComboBox.setMaxHeight(30);
    }

    @FXML
    public void handleCancel(){
        showShopOverview();
    }


    public void setUser(UserModel user){
        this.user = user;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    private void showShopOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/PersonOverview.fxml"));
            AnchorPane shopOverview = (AnchorPane) loader.load();

            Stage dialogStage = this.dialogStage;
            dialogStage.setTitle("Vendita Ricambi");
            Scene scene = new Scene(shopOverview);
            dialogStage.setScene(scene); //Provi che funzioni

            ShopOverviewController controller = loader.getController();
            controller.setUser(user);
            controller.setDialogStage(dialogStage);
            dialogStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void fillCategoriaCB(){
        MysqlConnection db = MysqlConnection.getDbCon();

        try {
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT NOME_CATEGORIA FROM CATEGORIA");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                options.add(rs.getString("NOME_CATEGORIA"));
                System.out.println(options);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
