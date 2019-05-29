package shop.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shop.InserisciFornitore;
import shop.model.CategoriaModel;
import shop.model.FornitoreModel;
import shop.model.UserModel;


public class InserisciFornitoreController {

    private UserModel user = new UserModel();
    private Stage dialogStage = new Stage();
    private Boolean okClicked = false;
    private MainAppController mainAppController = new MainAppController();
    private InserisciFornitore inserisciFornitore = new InserisciFornitore();

    @FXML
    private TextField nomeFornitoreField;

    @FXML
    Button inserisciButton;

    @FXML
    Button cancell;

    @FXML
    private void handleCancell(){dialogStage.close();}

    @FXML
    private void handleInserisci(){
        if(isInputValid()){
            String nomeFornitore = nomeFornitoreField.getText().toLowerCase();
            FornitoreModel fornitore = inserisciFornitore.selectFornitore(nomeFornitore);
            okClicked = true;
            if(fornitore.getPkFornitore() == null){
                fornitore.setNomeFornitore(nomeFornitore);
                System.out.println(fornitore.getPkFornitore() + " " + fornitore.getNomeFornitore());
                inserisciFornitore.insertFornitore(fornitore);
                dialogStage.close();
            }
            else{
                mainAppController.alert("Fornitore già esistente", "Errore", "Errore");
                dialogStage.close();
            }
        }
    }

    public void setUser(UserModel user){
        this.user = user;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkayClicked(){
        return okClicked;
    }

    private Boolean isInputValid(){
        if(nomeFornitoreField.getText() == null || nomeFornitoreField.getText().length() == 0){
            mainAppController.alert("Inserisci una categoia", "Errore", "Errore");
            return false;
        }
        return true;
    }
}
