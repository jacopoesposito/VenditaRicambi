package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import shop.MysqlConnection;
import shop.RimuoviRicambio;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RimuoviRicambioController {

    private UserModel user = new UserModel();
    private Stage dialogStage = new Stage();
    private Boolean okClicked = false;
    private MainAppController mainAppController = new MainAppController();
    private ObservableList<RicambioModel> options = FXCollections.observableArrayList();

    @FXML
    Button rimuoviButton;

    @FXML
    Button cancell;

    @FXML
    private ComboBox<RicambioModel> ricambioComboBox = new ComboBox<>();

    @FXML
    public void initialize(){
        fillRicambioCB();

        ricambioComboBox.getItems().setAll(options);
        ricambioComboBox.setMaxHeight(30);
    }

    @FXML
    private void handleRimuovi(){
        RicambioModel ricambio = ricambioComboBox.getSelectionModel().getSelectedItem();
        if(ricambio != null){
            RimuoviRicambio rimuovi = new RimuoviRicambio();
            rimuovi.rimuoviRicambio(ricambio.getPkProdotto());
            dialogStage.close();
        }
        else{
            mainAppController.alert("Selezionare un ricambio", "Errore", "Errore");
        }


    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
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

    private void fillRicambioCB(){
        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT CODICE_PRODOTTO, NOME_PRODOTTO FROM PRODOTTO WHERE VISIBILE != 1");
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                RicambioModel ricambio = new RicambioModel();

                ricambio.setPkProdotto(rs.getString("CODICE_PRODOTTO"));
                ricambio.setNomeProdotto(rs.getString("NOME_PRODOTTO"));

                options.add(ricambio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
