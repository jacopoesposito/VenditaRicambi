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
    private void handleAccedi(){
        if(isInputValid()){
            mail = mailTextField.getText();
            password = passTextField.getText();
            UserModel user = login.selectUser(mail);
            System.out.println(user.getNome());
            if(user.getNome() == null){
                alert("Nessun account associato a questa E-Mail", "Mail Sbagliata", "Errore: ");
            }
            else{
                salt = login.getSalt(mail);
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
    private Boolean isInputValid(){
        String errorMessage = "";
        if(mailTextField.getText() == null || mailTextField.getText().length() == 0){
            errorMessage += "Inserisci indirizzo Mail \n";
        }
        if(passTextField.getText() == null || passTextField.getText().length() == 0){
            errorMessage += "Inserisci Password \n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
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

}
