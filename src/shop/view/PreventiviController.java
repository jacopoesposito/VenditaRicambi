package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import shop.model.RicambioModel;
import shop.model.UserModel;



public class PreventiviController {

    private MainAppController mainAppController = new MainAppController();
    Stage dialogStage = new Stage();
    UserModel user = new UserModel();
    private ObservableList<RicambioModel> pezziSelezionati = FXCollections.observableArrayList();


    @FXML
    private Label motorePrimoText;

    @FXML
    private Label ruotaPrimoText;

    @FXML
    private Label telaioPrimoText;

    @FXML
    private Label fanaliPrimoText;

    @FXML
    private Label paraurtiPrimoText;

    @FXML
    private Label parabrezzaPrimoText;

    @FXML
    private Label freniPrimoText;

    @FXML
    private void handleContinua(){}

    @FXML
    private void handleAnnulla(){}

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        fillLabel(pezziSelezionati, 0);
    }

    public void setUser(UserModel user){
        this.user = user;
    }

    public void setPezziSelezionati(ObservableList<RicambioModel> pezziSelezionati){
        this.pezziSelezionati = pezziSelezionati;
    }

    private void fillLabel(ObservableList<RicambioModel> pezziSelezionati, int i){
        if(i == 0){
            for(RicambioModel ricambio : pezziSelezionati){
                motorePrimoText.setText(ricambio.getNomeProdotto() + " Fornitore:" + ricambio.getNomeFornitore());
                ruotaPrimoText.setText(ricambio.getNomeProdotto() + " Fornitore" + ricambio.getNomeFornitore());
                telaioPrimoText.setText(ricambio.getNomeProdotto() + " Fornitore" + ricambio.getNomeFornitore());
                fanaliPrimoText.setText(ricambio.getNomeProdotto() + " Fornitore" + ricambio.getNomeFornitore());
                paraurtiPrimoText.setText(ricambio.getNomeProdotto() + " Fornitore" + ricambio.getNomeFornitore());
                parabrezzaPrimoText.setText(ricambio.getNomeProdotto() + " Fornitore" + ricambio.getNomeFornitore());
                freniPrimoText.setText(ricambio.getNomeProdotto() + " Fornitore" + ricambio.getNomeFornitore());
            }
        }
    }
}
