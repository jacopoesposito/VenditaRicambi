package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.io.IOException;

public class CarrelloController {
    
    private UserModel user = new UserModel();
    private ShopOverviewController shopOverviewController = new ShopOverviewController();
    private Stage dialogStage = new Stage();
    private RicambioModel ricambio = new RicambioModel();
    private ObservableList<RicambioModel> carrelloList = FXCollections.observableArrayList();

    @FXML
    private Button annulla;

    @FXML
    private Button elimina;

    @FXML
    private Button acquista;

    @FXML
    private TableView<RicambioModel> tableView;

    @FXML
    private TableColumn<RicambioModel, String> colNomePorodotto;

    @FXML
    private TableColumn<RicambioModel, String> colFornitoreProdotto;

    @FXML
    private TableColumn<RicambioModel, String> colCategoriaProdotto;

    @FXML
    private TableColumn<RicambioModel, Integer> colQuantitaProdotto;

    @FXML
    private TableColumn<RicambioModel, Float> colPrezzoProdotto;

    @FXML
    private void initialize(){
        ObservableList<RicambioModel> carrelloList = shopOverviewController.getCarrelloList();
        for (RicambioModel ricambio : carrelloList) {
            System.out.println(ricambio.toString());
        }
        colNomePorodotto.setCellValueFactory(new PropertyValueFactory<>("nomeProdotto"));
        colFornitoreProdotto.setCellValueFactory(new PropertyValueFactory<>("nomeFornitore"));
        colCategoriaProdotto.setCellValueFactory(new PropertyValueFactory<>("nomeCategoria"));
        colQuantitaProdotto.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        colPrezzoProdotto.setCellValueFactory(new PropertyValueFactory<>("costoScontato"));

        tableView.setItems(carrelloList);
    }

    @FXML
    private void handleCancell(){
        showShopOverview();
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCarrelloList(ObservableList<RicambioModel> carrelloList) {
        this.carrelloList = carrelloList;
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
            controller.setCarelloList(carrelloList);
            controller.setDialogStage(dialogStage);
            dialogStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
