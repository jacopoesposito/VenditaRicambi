package shop.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shop.MainApp;
import shop.model.UserModel;

import java.io.IOException;

public class InserisciRicambioController {

    private MainApp mainApp = new MainApp();
    private UserModel user = new UserModel();
    private Stage dialogStage;

    @FXML
    private Button cancel;

    @FXML
    private void initialize(){}

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
            dialogStage.setScene(scene);

            ShopOverviewController controller = loader.getController();
            controller.setUser(user);
            controller.setDialogStage(dialogStage);
            dialogStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
