package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import shop.InserisciAdmin;
import shop.MysqlConnection;
import shop.model.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InserisciAdminController {

    private UserModel user = new UserModel();
    private Stage dialogStage = new Stage();
    private Boolean okClicked = false;
    private MainAppController mainAppController = new MainAppController();
    private ObservableList<UserModel> options = FXCollections.observableArrayList();

    @FXML
    Button inserisciButton;

    @FXML
    Button cancell;

    @FXML
    private ComboBox<UserModel> userComboBox = new ComboBox<>();

    @FXML
    private void handleInserisci(){
        //Questo metodo viene invocato quando si clicca il pulsante aggiungi
        user = userComboBox.getSelectionModel().getSelectedItem(); //Prendo l'User selezionato
        if(user != null){ //Effettuo un controllo per verificare che l'User abbia selezionato un utente
            InserisciAdmin admin = new InserisciAdmin(); //Instanzio un oggetto di tipo InserisciAdmin
            admin.inserisciAdmin(user.getCodiceUtente()); //Evoco il metodo insersciAdmin passando il codice utente dell'User selezionato
            dialogStage.close(); //Chiudo la dialog stage
        }
        else{
            mainAppController.alert("Seleziona un utente!", "Errore", "Errore");
        }

    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    @FXML
    public void initialize(){

        fillUserCB();

        userComboBox.getItems().addAll(options);
        userComboBox.setMaxHeight(30);

    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkayClicKed() {
        return okClicked;
    }

    private void fillUserCB(){
        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT CODICE_UTENTE, NOME, COGNOME FROM UTENTE WHERE ADMIN !=1");
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                UserModel users = new UserModel();

                users.setCodiceUtente(rs.getString("CODICE_UTENTE"));
                users.setNome(rs.getString("NOME"));
                users.setCognome(rs.getString("COGNOME"));

                options.add(users);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }


    }
}
