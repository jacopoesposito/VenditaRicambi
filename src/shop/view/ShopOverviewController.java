package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shop.MainApp;
import shop.MysqlConnection;
import shop.ShopOverview;
import shop.model.RicambioModel;
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
    private ObservableList<RicambioModel> ricambioList = FXCollections.observableArrayList();


    @FXML
    private MenuButton account;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu admin = new Menu("Admin");
    private MenuItem menuAggiungi = new MenuItem("Aggiungi Ricambio");
    private MenuItem menuCreaAdmin = new MenuItem("Aggiungi Admin");
    private MenuItem menuAggiungiCategoria = new MenuItem("Aggiungi Categoria");
    private MenuItem menuAggiungiFornitore = new MenuItem("Aggiungi Fornitore");

    @FXML
    private MenuItem esci;

    @FXML
    private MenuItem informazioni;

    @FXML
    private TableView<RicambioModel> tableView = new TableView<RicambioModel>();

    @FXML
    private TableColumn<RicambioModel, String> colNomePorodotto;

    @FXML
    private TableColumn<RicambioModel, String> colFornitoreProdotto;

    @FXML
    private TableColumn<RicambioModel, String> colCategoriaProdotto;

    @FXML
    private void initialize(){
        fillTableView();
        for (RicambioModel ricambio : ricambioList) {
            System.out.println(ricambio.toString());
        }


        colNomePorodotto.setCellValueFactory(new PropertyValueFactory<>("nomeProdotto"));
        colFornitoreProdotto.setCellValueFactory(new PropertyValueFactory<>("nomeFornitore"));
        colCategoriaProdotto.setCellValueFactory(new PropertyValueFactory<>("nomeCategoria"));

        tableView.setItems(ricambioList);

    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        if(checkIsAdmin()){
            admin.getItems().add(menuAggiungi);
            admin.getItems().add(menuAggiungiCategoria);
            admin.getItems().add(menuAggiungiFornitore);
            admin.getItems().add(menuCreaAdmin);
            menuBar.getMenus().add(admin);
        }
        menuAggiungi.setOnAction(event -> {showInserisciRicambio();});
        menuAggiungiCategoria.setOnAction(event -> {showAggiungiCategoria();});
        menuAggiungiFornitore.setOnAction(event -> {showInserisciFornitore();});

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

    private boolean showInserisciFornitore(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/InserisciFornitore.fxml"));
            VBox inserisciFornitore = (VBox) loader.load();

            Stage newDialogStage = new Stage();
            newDialogStage.setTitle("Inserisci Fornitore");
            newDialogStage.initOwner(dialogStage);
            newDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(inserisciFornitore);
            newDialogStage.setScene(scene);

            InserisciFornitoreController controller = loader.getController();
            controller.setUser(user);
            controller.setDialogStage(newDialogStage);

            newDialogStage.showAndWait();

            return controller.isOkayClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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

    private void fillTableView(){
        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT CODICE_PRODOTTO, `NOME_PRODOTTO`, `DESCRIZIONE_PRODOTTO`, `PERCENTUALE_SCONTO`, `COSTO`, `PREZZO_S`, `FK_CODICE_FORNITORE`, `FK_ID_CATEGORIA`, " +
                    "NOME_CATEGORIA, NOME_FORNITORE FROM `prodotto` " + "join fornitore ON prodotto.FK_CODICE_FORNITORE = fornitore.CODICE_FORNITORE " +
                    "join categoria ON prodotto.FK_ID_CATEGORIA = categoria.ID_CATEGORIA");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                RicambioModel ricambio = new RicambioModel();

                ricambio.setPkProdotto(rs.getString(1));
                ricambio.setNomeProdotto(rs.getString(2));
                ricambio.setDescrizioneProdotto(rs.getString(3));
                ricambio.setPercentualeSconto(rs.getInt(4));
                ricambio.setCosto(rs.getFloat(5));
                ricambio.setCostoScontato(rs.getFloat(6));
                ricambio.setFkFornitore(rs.getString(7));
                ricambio.setFkCategoria(rs.getString(8));
                ricambio.setNomeCategoria(rs.getString(9));
                ricambio.setNomeFornitore(rs.getString(10));
                ricambioList.add(ricambio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
