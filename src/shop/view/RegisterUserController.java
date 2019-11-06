package shop.view;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import shop.RegisterUser;
import shop.model.UserModel;
import shop.RegisterUser;
import javafx.scene.control.TextField;


import java.awt.*;

public class RegisterUserController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passField;

    @FXML
    private TextField repeatPassField;

    @FXML
    private TextField viaField;

    @FXML
    private TextField numeroCivicoField;

    @FXML
    private TextField cittaField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField cognomeField;



    private Stage dialogStage;
    private UserModel user = new UserModel();
    private RegisterUser register = new RegisterUser();
    private boolean okClicked = false;

    @FXML
    private void initialize(){}

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setUser(UserModel user){// Metodo set per l'User
        this.user = user;

        nomeField.setText(user.getNome());
        cognomeField.setText(user.getCognome());
        emailField.setText(user.getMail());
        passField.setText(user.getPassword());
        viaField.setText(user.getNomeVia());
        numeroCivicoField.setText((Integer.toString(user.getNumeroCivico())));
        cittaField.setText(user.getCitta());


    }

    @FXML
    private void handleOK(){//Questo metodo viene invocato quando l'utente clicca Continua nel form di Registrazione.
        if(isInputValid()){
            user.setNome(nomeField.getText());
            user.setCognome(cognomeField.getText());
            user.setMail(emailField.getText());
            user.setPassword(passField.getText());
            user.setReinserisciPassword(repeatPassField.getText());
            user.setNomeVia(viaField.getText());
            user.setNumeroCivico(Integer.parseInt(numeroCivicoField.getText()));
            user.setCitta(cittaField.getText());


            okClicked = true;
            register.insertUser(user); //Invoco il metodo insertUser della classe register.
            dialogStage.close(); //Chiudo la DialogStage
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @SuppressWarnings("Duplicates")
    private Boolean isInputValid(){ //Controllo che l'input sia valido
        String errorMessage = "";

        if(nomeField.getText() == null || nomeField.getText().length() == 0){
            errorMessage += "Lunghezza nome non valida\n";
        }
        if(cognomeField.getText() == null || cognomeField.getText().length() == 0){
            errorMessage += "Lunghezza Cognome non valida\n";
        }
        if(emailField.getText() == null || emailField.getText().length() == 0){
            errorMessage += "Lunghezza Indirizzo Mail non valido\n";
        }
        if(passField.getText() == null || passField.getText().length() == 0){
            errorMessage += "Lunghezza pass non valida\n";
        }
        else if(!passField.getText().equals(repeatPassField.getText())){
            errorMessage += "Le password non coincidono\n";
        }
        if(viaField.getText() == null || viaField.getText().length() == 0){
            errorMessage += "Lunghezza Via non valida\n";
        }
        if (numeroCivicoField.getText() == null || numeroCivicoField.getText().length() == 0) {
            errorMessage += "Lunghezza non valida!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(numeroCivicoField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Il numero civico deve essere un numero (Integer)!\n";
            }
        }
        if(cittaField.getText() == null || cittaField.getText().length() == 0){
            errorMessage += "Lunghezza Via non valida\n";
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
}
