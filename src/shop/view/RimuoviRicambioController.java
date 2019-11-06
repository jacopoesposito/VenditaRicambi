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
        //Questo metodo viene invocato non appena viene inizializzata la scena
        fillRicambioCB(); //Invoco il metodo che si occuperà di recuperare i dati per la ComboBox

        ricambioComboBox.getItems().setAll(options); //Setto la ComboBox con i dati ottenuti grazie all'utilizzo del metodo precedente
        ricambioComboBox.setMaxHeight(30);
    }

    @FXML
    private void handleRimuovi(){
        RicambioModel ricambio = ricambioComboBox.getSelectionModel().getSelectedItem(); //Recupero l'oggetto selezionato
        if(ricambio != null){  //Verifico che sia stato effettivamente selezionato l'oggetto
            RimuoviRicambio rimuovi = new RimuoviRicambio(); //Instanzio un oggetto di tipo RimuoviRicambio che si occuperà della rimoozione del ricambio.
            rimuovi.rimuoviRicambio(ricambio.getPkProdotto()); //Evoco il metodo rimuovi che si occuperà di rimuovere il prodotto corrispondente alla chiave primaria passata in input.
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
        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al DB.

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT CODICE_PRODOTTO, NOME_PRODOTTO FROM PRODOTTO WHERE VISIBILE != 1"); //Recupero la lista di tutti i prodotti presenti nel DB e ancora disponibili alla vendita
            ResultSet rs = preparedStatement.executeQuery(); //Eseguo la query

            while(rs.next()){
                RicambioModel ricambio = new RicambioModel();

                ricambio.setPkProdotto(rs.getString("CODICE_PRODOTTO"));
                ricambio.setNomeProdotto(rs.getString("NOME_PRODOTTO"));

                options.add(ricambio); //Popolo la lista ricambio con gli oggetti restituiti dalla query
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
