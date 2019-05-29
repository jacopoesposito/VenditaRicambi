package shop.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shop.MainApp;
import shop.MysqlConnection;
import shop.ShopOverview;
import shop.model.UserModel;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShopOverviewController {

    private ShopOverview shopOverview;
    private Stage dialogStage;
    private boolean okClicked = false;
    private UserModel user;
    private MainApp mainApp = new MainApp();


    @FXML
    private MenuButton account;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu admin = new Menu("Admin");
    private MenuItem menuAggiungi = new MenuItem("Aggiungi Ricambio");
    private MenuItem menuCreaAdmin = new MenuItem("Aggiungi Admin");
    private MenuItem menuAggiungiCategoria = new MenuItem("Aggiungi Categoria");

    @FXML
    private MenuItem esci;

    @FXML
    private MenuItem informazioni;

    @FXML
    private void initialize(){}

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        if(checkIsAdmin()){
            admin.getItems().add(menuAggiungi);
            admin.getItems().add(menuAggiungiCategoria);
            admin.getItems().add(menuCreaAdmin);
            menuBar.getMenus().add(admin);
        }
        menuAggiungi.setOnAction(event -> {showInserisciRicambio();});
        menuAggiungiCategoria.setOnAction(event -> {showAggiungiCategoria();});
    }



    public boolean isOkClicked() { return okClicked;
    }

    public void setUser(UserModel user){
        this.user = user;
    }

    @FXML
    private void handleAcquista(){
    }

    @FXML
    private void handleExit(){
        mainApp.start(dialogStage);
    }



    private boolean checkIsAdmin(){
        int admin;
        boolean controll = false;
        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT ADMIN FROM UTENTE WHERE MAIL = ?");
            preparedStatement.setString(1, user.getMail());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            admin = rs.getInt("ADMIN");
            preparedStatement.close();
            if(admin == 1){
                controll = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controll;
    }

    private void showInserisciRicambio(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/InserisciRicambio.fxml"));
            VBox inserisciRicambio = (VBox) loader.load();

            dialogStage.setTitle("Inserisci Ricambio");
            Scene scene = new Scene(inserisciRicambio);
            dialogStage.setScene(scene);

            InserisciRicambioController controllerRicambio = loader.getController();
            controllerRicambio.setUser(user);
            controllerRicambio.setDialogStage(dialogStage);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean showAggiungiCategoria() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/InserisciCategoria.fxml"));
            VBox inserisciCategoria = (VBox) loader.load();

            Stage newDialogStage = new Stage();
            newDialogStage.setTitle("Inserisci Cateegoria");
            newDialogStage.initOwner(dialogStage);
            newDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(inserisciCategoria);
            newDialogStage.setScene(scene);

            InserisciCategoriaController controller = loader.getController();
            controller.setUser(user);
            controller.setDialogStage(newDialogStage);

            newDialogStage.showAndWait();

            return controller.isOkayClicKed();
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
