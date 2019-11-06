package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shop.CartaCreditoStrategy;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


public class PagamentoCCController extends PagamentiElettronici{

    private UserModel user = new UserModel();
    private Stage dialogStage = new Stage();
    private CarrelloController carello = new CarrelloController();
    private ObservableList<RicambioModel> carrelloList = FXCollections.observableArrayList();
    private MainAppController mainAppController = new MainAppController();
    private Float totale;

    @FXML
    private TextField nomeTitolareTextField;

    @FXML
    private TextField cognomeTitolareTextField;

    @FXML
    private TextField numeroCCTextField;

    @FXML
    private TextField cvvTextField;

    @FXML
    private TextField dataScadenzaTextField;

    @FXML
    private Label totaleField;

    @FXML
    private Button concludi;

    @FXML
    private Button annulla;

    @FXML
    private void handelConcludi(){
        if(isInputValid()){ //Effettuo i dovuti controlli sui dati inseriti dall'User
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                CartaCreditoStrategy cartaCreditoStrategy = new CartaCreditoStrategy(nomeTitolareTextField.getText(), cognomeTitolareTextField.getText(),
                        numeroCCTextField.getText(), Integer.valueOf(cvvTextField.getText()),formatter.parse(dataScadenzaTextField.getText()));
                cartaCreditoStrategy.paga(totale, carrelloList, user); //Viene instanziato un oggetto di tipo cartaCreditoStrategy che si occuperà di gestire il pagamento tramite CC.
                mainAppController.confirm("Ordine pagato con CC intestata a: " + cartaCreditoStrategy.getNome() +
                        " " + cartaCreditoStrategy.getCognome(), "Ordine effettuato", "Ordine effettuato"); //Mostro un pop-up di conferma
                showShopOverview();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCancell(){
        annullaOperazione(user, dialogStage, carrelloList);
    }

    public void initialize(){
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setDialogStage(Stage dialogStage) {
        totaleField.setText(Float.toString(totale));
        this.dialogStage = dialogStage;
    }

    private void showShopOverview(){
        confermaOperazione(user, dialogStage, carrelloList);
    }

    public void setCarrelloList(ObservableList<RicambioModel> carrelloList){
        this.carrelloList = carrelloList;
    }

    public void setTotale(Float totale) {
        this.totale = totale;
    }

    protected Boolean isInputValid(){


        String errorMessage = checkNameData(nomeTitolareTextField, cognomeTitolareTextField, dataScadenzaTextField); //Invoco il metodo chekNameData che si occuperà di verificare Nome, Cognome e Data di scadenza
        if(numeroCCTextField.getText().length() == 0 || numeroCCTextField.getText() == null){ //Verifico che sia stato inserito il numero della CC.
            errorMessage += "Inserisci il numero della carta di credito\n";
        }
        if(numeroCCTextField.getText().length() != 16){ //Verifico che si composto da 16 cifre
            errorMessage += "Inserire un codice di 16 cifre\n";
        }
        if(cvvTextField.getText().length() == 0 || cvvTextField.getText() == null){ //Verifico che sia stato inserito il CVV della carta di credito.
            errorMessage += "Inserisci il CVV\n";
        }
        else if(cvvTextField.getText().length() == 3){ //Verifico che sia composto da 3 cifre
            try{
                Integer.parseInt(cvvTextField.getText()); //Verifico che sia un numero Intero.
            }
            catch (NumberFormatException e){
                errorMessage += "Inserire un numero intero\n";
            }
        }
        else{
            errorMessage += "Inserire un codice di 3 cifre\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            mainAppController.alert("Campi Errati", "Correggi i seguenti campi", errorMessage);
            return false;
        }
    }
}
