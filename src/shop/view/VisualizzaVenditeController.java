package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import shop.MysqlConnection;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class VisualizzaVenditeController {

    private Stage dialogStage = new Stage();
    private ObservableList<RicambioModel> venditeList = FXCollections.observableArrayList();
    private UserModel user = new UserModel();

    @FXML
    private TableView<RicambioModel> tableView = new TableView<RicambioModel>();

    @FXML
    private TableColumn<RicambioModel, String> colNomePorodotto;

    @FXML
    private TableColumn<RicambioModel, String> colQuantitaProdotto;

    @FXML
    private TableColumn<RicambioModel, String> colPrezzoProdotto;

    @FXML
    private Button cancell;

    public void initialize(){
        //Questo metodo viene chiamato quando viene inizializzata la scena
        fillTableV(); //Chiamo il metodo fillTableV che si occuperà di recupare i dati per TableView.

        colNomePorodotto.setCellValueFactory(new PropertyValueFactory<>("nomeProdotto"));
        colQuantitaProdotto.setCellValueFactory(new PropertyValueFactory<>("quantitaAcquistata"));
        colPrezzoProdotto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        tableView.setItems(venditeList); //Setto la tableview con la lista degli oggetti venduti, recuperata attraverso il metodo precedente
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Boolean isOkayClicked(){return true;}


    @FXML
    private void handleCancell(){
        dialogStage.close();
    }

    private void fillTableV(){

        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al DB.

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT CODICE_PRODOTTO, NOME_PRODOTTO, SUM(PRODOTTO_ORDINATO.QUANTITA) AS QUANTITA," +
                    "PRODOTTO_ORDINATO.COSTO FROM PRODOTTO JOIN PRODOTTO_ORDINATO ON CODICE_PRODOTTO = FK_CODICE_PRODOTTO GROUP BY CODICE_PRODOTTO"); //Prepraro la query che ritorna i prodotti venduti
            ResultSet rs = preparedStatement.executeQuery();//Eseguo la query

            while (rs.next()){
                RicambioModel ricambio = new RicambioModel();

                ricambio.setPkProdotto(rs.getString("CODICE_PRODOTTO"));
                ricambio.setNomeProdotto(rs.getString("NOME_PRODOTTO"));
                ricambio.setQuantitaAcquistata(rs.getInt(3));
                ricambio.setCosto(rs.getFloat(4));


                venditeList.add(ricambio);//Salvo gli oggetti recuperati attraverso la query all'interno della Lista venditeList.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
