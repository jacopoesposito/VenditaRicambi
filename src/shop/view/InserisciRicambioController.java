package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shop.*;
import shop.model.CategoriaModel;
import shop.model.FornitoreModel;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InserisciRicambioController {

    private MainAppController mainAppController = new MainAppController();
    private UserModel user = new UserModel();
    private RicambioModel ricambio = new RicambioModel();
    private InserisciRicambio inserisciRicambio = new InserisciRicambio();
    private Stage dialogStage;
    final ObservableList<String> options = FXCollections.observableArrayList();
    final ObservableList<String> optionsFornitore = FXCollections.observableArrayList();

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
    private TextField quantitaTextField;

    @FXML
    private ComboBox<String> categoriaComboBox = new ComboBox<>();

    @FXML
    private ComboBox<String> fornitoreComboBox = new ComboBox<>();

    @FXML
    private void initialize(){
        System.out.println("Here Initialize");
        fillCategoriaCB();
        fillFornitoreCB();
        categoriaComboBox.getItems().add(0, "Seleziona Categoria");
        categoriaComboBox.getItems().addAll(options);
        categoriaComboBox.getSelectionModel().select(0);
        categoriaComboBox.setMaxHeight(30);
        fornitoreComboBox.getItems().add(0, "Seleziona Fornitore");
        fornitoreComboBox.getItems().addAll(optionsFornitore);
        fornitoreComboBox.getSelectionModel().select(0);
        fornitoreComboBox.setMaxHeight(30);
    }

    @FXML
    public void handleCancel(){
        showShopOverview();
    }

    @FXML
    public void handleInserisci(){
        if(isInputValid()){//Controllo che l'input sia valido
            ricambio.setNomeProdotto(nomeProdottoTextField.getText());
            ricambio.setDescrizioneProdotto(descrizioneProdottoTextField.getText());
            ricambio.setPercentualeSconto(Integer.parseInt(percentualeScontoTextField.getText()));
            ricambio.setCosto(Float.parseFloat(costoTextField.getText()));
            ricambio.setFkFornitore(inserisciRicambio.selectPkFornitore(fornitoreComboBox.getSelectionModel().getSelectedItem()));
            ricambio.setFkCategoria(inserisciRicambio.selectPkCategoria(categoriaComboBox.getSelectionModel().getSelectedItem()));
            ricambio.setQuantita(Integer.parseInt(quantitaTextField.getText()));

            inserisciRicambio.insertRicambio(ricambio);//Invoco il metodo per l'inserimento del ricambio all'interno del DB
            showShopOverview(); //Mostro la view del negozio
        }
    }


    public void setUser(UserModel user){
        this.user = user;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
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

    private void fillFornitoreCB(){
        //Recupero la connessione al DB
        MysqlConnection db = MysqlConnection.getDbCon();

        try {
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT NOME_FORNITORE FROM FORNITORE"); //Preparo la query per ottenere i fornitori
            ResultSet rs = preparedStatement.executeQuery(); //Eseguo la query
            while(rs.next()){
                optionsFornitore.add(rs.getString("NOME_FORNITORE")); //aggiungo i fornitori alla lista optionsFornitore
                System.out.println(optionsFornitore);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Boolean isInputValid(){ //Metodo per il controllo dell'Input
        String errorMessage = "";

        if(nomeProdottoTextField.getText().length() == 0 || nomeProdottoTextField.getText() == null){ //Verifico che siano inserito il nome
            errorMessage += "Inserisci nome prodotto\n";
        }
        if(descrizioneProdottoTextField.getText().length() == 0 || descrizioneProdottoTextField.getText() == null){ //Verifico che sia inserita una descrizione
            errorMessage += "Inserisci descrizione del prodotto\n";
        }
        if(percentualeScontoTextField.getText().length() == 0 || percentualeScontoTextField.getText() == null){ //Verifico che sia inserita la percentuale dello sconto
            errorMessage += "Inserire la percentuale di sconto del prodotto\n";
        }
        else{
            try{
                Integer.parseInt(percentualeScontoTextField.getText()); //Controllo che sia un numero intero
            } catch (NumberFormatException e){
                errorMessage += "Inerire un numero intero come percentuale di sconto\n";
            }
        }
        if(costoTextField.getText().length() == 0 || costoTextField.getText() == null){ //Verifico che sia inserito il prezzo dell'articolo
            errorMessage += "Inserire il prezzo del prodotto\n";
        }
        else{
            try{
                Float.parseFloat(costoTextField.getText()); //Controllo che sia un numero float
            } catch (NumberFormatException e){
                errorMessage += "Inserire un numero per indicare il prezzo del prodotto\n";
            }
        }
        if(quantitaTextField.getText().length() == 0 || quantitaTextField.getText() == null){ //Verifico che sia stata inserita la quantità del prodotto
            errorMessage+= "Inserire la quantità del ricambio\n";
        }
        else{
            try{
                Integer.parseInt(quantitaTextField.getText()); //Controllo che sia un numero intero
            } catch (NumberFormatException e){
                errorMessage += "Inserire un numero intero per indicare la quantita delle scorte\n";
            }
        }
        if(categoriaComboBox.getSelectionModel().getSelectedIndex() == 0){ //Verifico che sia stata selezionata una categoria
            errorMessage += "Seleziona una cateogoria\n";
        }
        if(fornitoreComboBox.getSelectionModel().getSelectedIndex() == 0){ //Verifico che sia stata selezionata una categoria
            errorMessage += "Seleziona un fornitore\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //Stampo a schermo gli errori
            mainAppController.alert("Campi Errati", "Correggi i seguenti campi", errorMessage);
            return false;
        }
    }
}
