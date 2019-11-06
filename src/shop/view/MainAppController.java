package shop.view;

import com.google.common.hash.Hashing;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import shop.LoginUser;
import shop.MainApp;
import shop.MysqlConnection;
import shop.RegisterUser;
import shop.model.UserModel;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;


public class MainAppController {

    @FXML
    private TextField mailTextField;

    @FXML
    private TextField passTextField;

    @FXML
    private Button accediButton;

    @FXML
    private Button registratiButton;

    //@FXML
   // private ButtonBase barButton;

    private MainApp mainApp;
    private Stage dialogStage;
    private String mail;
    private String password;
    private String salt;
    private LoginUser login = new LoginUser();
    private boolean okClicked = false;

    @FXML
    private void handleRegistrati(){
        mainApp.showAddUser();
    }

    @FXML
    private void handleAccedi(){ //Questo metodo viene invocato nel momento in cui si clicca sul pulsante accedi.
        if(isInputValid()){ //Eseguo prima un controllo per verificare che l'input sia valido.
            mail = mailTextField.getText(); //Recupero la mail e la password che sono state inserite
            password = passTextField.getText();
            UserModel user = login.selectUser(mail); //Invoco il metodo selectUser della classe Login in modo da avere l'utente associato a quella mail.
            System.out.println(user.getNome());
            if(user.getNome() == null){ //Eseguo un controllo sull'esistenza dell'utente, in caso di esito negativo mostro una DialogStage che comunica che nessun utente è associato alla mail inserita.
                alert("Nessun account associato a questa E-Mail", "Mail Sbagliata", "Errore: ");
            }
            else{ //In caso di esito positivo procedo alla verifica della password.
                salt = login.getSalt(mail); //Recupero il Salt
                //Per verificare che la password inserita sia corretta faccio l'hashing della password inserita + il Salt e lo confronto alla password salavata nel DB.
                if(Hashing.sha256().hashString(password + salt, StandardCharsets.UTF_8).toString().equals(user.getPassword())){
                    System.out.println("ACCESSO ESEGUITO");
                    mainApp.showShopOverview(user);
                }
            else{
                    alert("Password Errata!", "Password Sbagliata!", "Correggi i seguenti campi: \n");
                }
            }
        }
    }



    public MainAppController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    @SuppressWarnings("Duplicates")
    private Boolean isInputValid(){ //Questo metodo si occcupa di verificare che l'input sia valido
        String errorMessage = "";
        if(mailTextField.getText() == null || mailTextField.getText().length() == 0){//Verifico che venga inserito un indirizzo mail.
            errorMessage += "Inserisci indirizzo Mail \n";
        }
        if(passTextField.getText() == null || passTextField.getText().length() == 0){//Verifico che sia inserita la password.
            errorMessage += "Inserisci Password \n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostro il messaggio di errore.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Campi Errati");
            alert.setHeaderText("Correggi i seguenti campi:");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }

    }

    public void  alert(String errorMessage, String title, String headerText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(errorMessage);

        alert.showAndWait();

    }

    public void confirm(String message, String title, String headerText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(dialogStage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);

        alert.showAndWait();
    }

}
