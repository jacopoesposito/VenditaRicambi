package shop;



import java.io.IOException;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import shop.model.UserModel;
import shop.view.MainAppController;
import shop.view.ShopOverviewController;
import shop.view.RegisterUserController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    public static void main(String[] args) {
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Vendita Ricambi");

        initRootLayout();


        showPersonOverview();

    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/shop/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPersonOverview() { //Inizializzo l'interfaccia utente del form di login
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/MainApp.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            MainAppController controller = loader.getController();
            controller.setMainApp(this);
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showShopOverview(UserModel user){ //Inizializzo l'interfaccia utente del negozio

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/PersonOverview.fxml"));
            AnchorPane shopOverview = (AnchorPane) loader.load();

            Stage dialogStage = primaryStage;
            dialogStage.setTitle("Vendita Ricambi");
            Scene scene = new Scene(shopOverview);
            dialogStage.setScene(scene);

            //Setto il controller e mostro lo stage.
            ShopOverviewController controller = loader.getController();
            controller.setUser(user);
            controller.setDialogStage(dialogStage);
            dialogStage.show();

            return controller.isOkClicked();
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }



    }

    public boolean showAddUser(){ //Inizializzo l'interfaccia utente del form di registrazione
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/Prova.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo la DialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Registrazione");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Setto il controller.
            RegisterUserController controller = loader.getController();
            controller.setDialogStage(dialogStage);


            //Mostra lo Stage e aspetta finchè non viene chiusa.
            dialogStage.showAndWait();

            return controller.isOkClicked();

        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }



    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
