package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shop.BancomatStrategy;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PagamentoBMController extends PagamentiElettronici {

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
    private TextField numeroBMTextField;

    @FXML
    private TextField pinTextField;

    @FXML
    private TextField dataScadenzaTextField;

    @FXML
    private Label totaleField;

    @FXML
    private Button concludi;

    @FXML
    private Button annulla;

    @FXML
    private void handleConcludi(){
        if(isInputValid()){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try{
                BancomatStrategy bancomatStrategy = new BancomatStrategy(nomeTitolareTextField.getText(), cognomeTitolareTextField.getText(),
                        numeroBMTextField.getText(), Integer.valueOf(pinTextField.getText()),formatter.parse(dataScadenzaTextField.getText()));
                bancomatStrategy.paga(totale, carrelloList, user); //Viene instanziato un oggetto di tipo BancomatStrategy che si occuppa di gestire il pagamento con Bancomat
                mainAppController.confirm("Ordine pagato con BM intestata a: " + bancomatStrategy.getNome() +
                        " " + bancomatStrategy.getCognome(), "Ordine effettuato", "Ordine effettuato");
                confermaOperazione(user, dialogStage, carrelloList); //Invoco il metodo ConfermaOperazione della classe Padre in tal modo da ritornare alla home del negozio

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void handleCancell(){
        annullaOperazione(user, dialogStage, carrelloList);
    }



    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setDialogStage(Stage dialogStage) {
        totaleField.setText(Float.toString(totale));
        this.dialogStage = dialogStage;
    }

    public void setCarrelloList(ObservableList<RicambioModel> carrelloList){
        this.carrelloList = carrelloList;
    }

    public void setTotale(Float totale) {
        this.totale = totale;
    }

    protected Boolean isInputValid(){

        String errorMessage = checkNameData(nomeTitolareTextField, cognomeTitolareTextField, dataScadenzaTextField); //Chiamo il metodo CheckNameData per verificare i dati del BM
        if(numeroBMTextField.getText().length() == 0 || numeroBMTextField.getText() == null){ //Verifico che sia inserito il numero del bancomat
            errorMessage += "Insersci il numero del Bancomat\n";
        }
        if(numeroBMTextField.getText().length() != 15 && numeroBMTextField.getText().length() != 16){ //Verifico che sia effettivamente il numero di un Bancomat attraverso la lunghezza del codice
            errorMessage += "Inserire un codice di 15 o 16 cifre\n";
        }
        if(pinTextField.getText().length() == 0 || pinTextField.getText() == null){ //Verifico che sia inserito il PIN
            errorMessage += "Inserisci il PIN\n";
        }
        else if(pinTextField.getText().length() == 4){ //Verifico che il PIN sia effettivamente composto da 4 cifre
            try{
                Integer.parseInt(pinTextField.getText()); //Verifico che il PIN sia un'intero
            }
            catch (NumberFormatException e){
                errorMessage += "Inserire un numero intero\n";
            }
        }
        else{
            errorMessage += "Inserire un codice di 4 cifre\n";
        }
        if(errorMessage.length() == 0){
            return true;
        }
        else{
            mainAppController.alert(errorMessage, "Errore", "Correggi i seguenti campi");
            return false;
        }
    }
}
