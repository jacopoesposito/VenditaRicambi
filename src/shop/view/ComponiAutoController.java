package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shop.MysqlConnection;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponiAutoController {

    private MainAppController mainAppController = new MainAppController();
    private RicambioModel ricambio = new RicambioModel();
    private Stage dialogStage = new Stage();
    private UserModel user = new UserModel();

    @FXML
    private Button cancella;

    @FXML
    private Button avanti;

    @FXML
    private ComboBox<RicambioModel> motoreComboBox = new ComboBox<>();

    @FXML
    private ComboBox<RicambioModel> ruoteComboBox = new ComboBox<>();

    @FXML
    private ComboBox<RicambioModel> telaioComboBox = new ComboBox<>();

    @FXML
    private ComboBox<RicambioModel> fanaliComboBox = new ComboBox<>();

    @FXML
    private ComboBox<RicambioModel> paraurtiComboBox = new ComboBox<>();

    @FXML
    private ComboBox<RicambioModel> parabrezzaComboBox = new ComboBox<>();

    @FXML
    private ComboBox<RicambioModel> freniComboBox = new ComboBox<>();

    private ObservableList<RicambioModel> pezziSelezionati = FXCollections.observableArrayList();


    @FXML
    private void initialize(){
        motoreComboBox.getItems().setAll(fillRicambioCB("motore"));
        motoreComboBox.setMaxHeight(30);
        ruoteComboBox.getItems().setAll(fillRicambioCB("ruote"));
        ruoteComboBox.setMaxHeight(30);
        telaioComboBox.getItems().setAll(fillRicambioCB("telaio"));
        telaioComboBox.setMaxHeight(30);
        fanaliComboBox.getItems().setAll(fillRicambioCB("fanali"));
        fanaliComboBox.setMaxHeight(30);
        paraurtiComboBox.getItems().setAll(fillRicambioCB("paraurti"));
        paraurtiComboBox.setMaxHeight(30);
        parabrezzaComboBox.getItems().setAll(fillRicambioCB("parabrezza"));
        parabrezzaComboBox.setMaxHeight(30);
        freniComboBox.getItems().setAll(fillRicambioCB("freni"));
        freniComboBox.setMaxHeight(30);
    }

    @FXML
    private void handleContinua(){
        if(isInputValid()){
            System.out.println(motoreComboBox.getSelectionModel().getSelectedItem().getCostoScontato());
            //RicambioModel ricambio = motoreComboBox.getSelectionModel().getSelectedItem();
            //pezziSelezionati.add(ricambio);

            //pezziSelezionati.add(ruoteComboBox.getSelectionModel().getSelectedItem());
            //pezziSelezionati.add(telaioComboBox.getSelectionModel().getSelectedItem());
            //pezziSelezionati.add(paraurtiComboBox.getSelectionModel().getSelectedItem());
            //pezziSelezionati.add(parabrezzaComboBox.getSelectionModel().getSelectedItem());
            //pezziSelezionati.add(freniComboBox.getSelectionModel().getSelectedItem());

            System.out.println(freniComboBox.getSelectionModel().getSelectedItem());

            showPreventivi();
        }
    }

    @FXML
    private void handleAnnulla(){
        showShopOverview();
    }

    @SuppressWarnings("Duplicates")
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

    private void showPreventivi(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/Preventivi.fxml"));
            VBox preventivi = (VBox) loader.load();

            Stage dialogStage = this.dialogStage;
            dialogStage.setTitle("Preventivi");
            Scene scene = new Scene(preventivi);
            dialogStage.setScene(scene);

            PreventiviController controller = loader.getController();
            controller.setUser(user);
            controller.setPezziSelezionati(pezziSelezionati);
            controller.setDialogStage(dialogStage);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("Duplicates")
    private ObservableList<RicambioModel> fillRicambioCB(String tipologiaRicambio){

        ObservableList<RicambioModel> options = FXCollections.observableArrayList();


        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT NOME_PRODOTTO, CODICE_PRODOTTO, DESCRIZIONE_PRODOTTO, " +
                    "PERCENTUALE_SCONTO, COSTO, PREZZO_S, FK_CODICE_FORNITORE, " +
                    "FK_ID_CATEGORIA, NOME_CATEGORIA, NOME_FORNITORE, QUANTITA, VISIBILE FROM PRODOTTO " +
                    "JOIN FORNITORE ON prodotto.FK_CODICE_FORNITORE = fornitore.CODICE_FORNITORE " +
                    "JOIN CATEGORIA ON prodotto.FK_ID_CATEGORIA = categoria.ID_CATEGORIA WHERE NOME_CATEGORIA = ?");
            preparedStatement.setString(1, tipologiaRicambio);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){

                RicambioModel ricambio = new RicambioModel();

                ricambio.setNomeProdotto(rs.getString(1));
                ricambio.setPkProdotto(rs.getString(2));
                ricambio.setDescrizioneProdotto(rs.getString(3));
                ricambio.setPercentualeSconto(rs.getInt(4));
                ricambio.setCosto(rs.getFloat(5));
                ricambio.setCostoScontato(rs.getFloat(6));
                ricambio.setFkFornitore(rs.getString(7));
                ricambio.setFkCategoria(rs.getString(8));
                ricambio.setNomeCategoria(rs.getString(9));
                ricambio.setNomeFornitore(rs.getString(10));
                ricambio.setQuantita(rs.getInt(11));
                ricambio.setVisibilita(rs.getInt(12));

                if(ricambio.getVisibilita() == 0){
                    options.add(ricambio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return options;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(UserModel user){
        this.user = user;
    }

    private Boolean isInputValid(){

        String errorMessage = "";

        if(motoreComboBox.getSelectionModel().getSelectedItem() == null){
            errorMessage += "Seleziona il motore\n";
        }
        if(ruoteComboBox.getSelectionModel().getSelectedItem() == null){
            errorMessage += "Seleziona le ruote\n";
        }
        if(telaioComboBox.getSelectionModel().getSelectedItem() == null){
            errorMessage += "Seleziona il telaio\n";
        }
        if(fanaliComboBox.getSelectionModel().getSelectedItem() == null){
            errorMessage += "Seleziona i fanali\n";
        }
        if(paraurtiComboBox.getSelectionModel().getSelectedItem() == null){
            errorMessage += "Seleziona il paraurti\n";
        }
        if(parabrezzaComboBox.getSelectionModel().getSelectedItem() == null){
            errorMessage += "Seleziona il parabrezza\n";
        }
        if(freniComboBox.getSelectionModel().getSelectedItem() == null){
            errorMessage += "Seleziona i freni\n";
        }

        if(errorMessage.length() == 0){
            return true;
        }
        else{
            mainAppController.alert(errorMessage, "Errore", "Correggi i seguenti campi:");
            return false;
        }
    }
}
