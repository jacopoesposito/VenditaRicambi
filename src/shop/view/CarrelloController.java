package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shop.ContantiStrategy;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.io.IOException;

public class CarrelloController {

    private MainAppController mainAppController = new MainAppController();
    private UserModel user = new UserModel();
    private Stage dialogStage = new Stage();
    private RicambioModel ricambio = new RicambioModel();
    private ObservableList<RicambioModel> carrelloList = FXCollections.observableArrayList();

    @FXML
    private Button annulla;

    @FXML
    private Button elimina;

    @FXML
    private Button acquista;

    @FXML
    private RadioButton cartaCredito;

    @FXML
    private RadioButton contanti;

    @FXML
    private RadioButton bancomat;

    @FXML
    private Label totale;

    @FXML
    private TableView<RicambioModel> tableView;

    @FXML
    private TableColumn<RicambioModel, String> colNomePorodotto;

    @FXML
    private TableColumn<RicambioModel, String> colFornitoreProdotto;

    @FXML
    private TableColumn<RicambioModel, String> colCategoriaProdotto;

    @FXML
    private TableColumn<RicambioModel, Integer> colQuantitaProdotto;

    @FXML
    private TableColumn<RicambioModel, Float> colPrezzoProdotto;

    @FXML
    private void initialize(){
    }

    @FXML
    private void handleCancell(){
        showShopOverview();
    }

    @FXML
    private void handleElimina() {
        //Questo metodo si occupa di rimuovere un oggetto dalla lista Carello
        RicambioModel ricambio = tableView.getSelectionModel().getSelectedItem(); //Recupero l'oggetto selezionato
        if (ricambio != null) { //Verifico che l'oggetto non sia nullo
            if (ricambio.getQuantitaAcquistata() >= 1) { //Verifico che la quantitaAcquistata sia maggiore uguale a uno
                ricambio.setQuantitaAcquistata(ricambio.getQuantitaAcquistata() - 1); //Diminuisco di uno la quantità acquistata
                if (ricambio.getQuantitaAcquistata() == 0) { //Se la quantità acquistata dell'oggetto è uguale a 0
                    carrelloList.remove(ricambio); //Allora rimuovo l'oggetto dalla lista altrimenti semplicemente si riduce di uno la quantità
                }
                tableView.refresh(); //Faccio il refresh della taballa in tal modo verranno visuallizati i nuovi dati
                totale.setText(Float.toString(calcoloTotale(carrelloList)));
            }
        }
    }

    @FXML
    private void handleAcquista(){
        //Questo metodo viene invocato quando l'Utente clicca sul pulsante acquista
        if(!carrelloList.isEmpty()){ //Controllo prima che il carello non sia vuoto, in caso di esito negativo mostro messaggio di errore
            if(cartaCredito.isSelected()) {
                showPagamentoCC(); //Mostra lo stage per il pagamento con Carta di Credito
            }
            if(bancomat.isSelected()) {
                showPagamentoBM(); //Mostra lo stage per il pagamento con Bancomat
            }
            if(contanti.isSelected()){
                ContantiStrategy contanti = new ContantiStrategy(); //Viene instanziato un oggetto di tipo ContantiStrategy
                contanti.paga(calcoloTotale(carrelloList), carrelloList, user);  //Viene invocato il metodo per processare il pagamento
                mainAppController.confirm("Pagamento in contanti effettuato", "Pagamento in Contanti", "Grazie e arrivederci");
            }
        }
        else{
            mainAppController.alert("Non hai prodotti nel carello", "Errore", "Nessun prodotto inserito");
        }
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCarrelloList(ObservableList<RicambioModel> carrelloList) {
        this.carrelloList = carrelloList;
        setTableView();
        totale.setText(Float.toString(calcoloTotale(carrelloList)));
    }

    @SuppressWarnings("Duplicates")
    public void showShopOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/PersonOverview.fxml"));
            AnchorPane shopOverview = (AnchorPane) loader.load();

            Stage dialogStage = this.dialogStage;
            dialogStage.setTitle("Vendita Ricambi");
            Scene scene = new Scene(shopOverview);
            dialogStage.setScene(scene);

            ShopOverviewController controller = loader.getController();
            controller.setUser(user);
            controller.setCarelloList(carrelloList);
            controller.checkQuantita();
            controller.setDialogStage(dialogStage);

            dialogStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showPagamentoCC(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/PagamentoCartaCredito.fxml"));
            VBox pagamentoCartaCredito = (VBox) loader.load();

            dialogStage.setTitle("Pagamento con Cartda di credito");
            Scene scene = new Scene(pagamentoCartaCredito);
            dialogStage.setScene(scene);

            PagamentoCCController controller = loader.getController();
            controller.setUser(user);
            controller.setCarrelloList(carrelloList);
            controller.setTotale(calcoloTotale(carrelloList));
            controller.setDialogStage(dialogStage);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPagamentoBM(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/PagamentoBancomat.fxml"));
            VBox pagamentoBancomat = (VBox) loader.load();

            dialogStage.setTitle("Pagamento con Bancomat");
            Scene scene = new Scene(pagamentoBancomat);
            dialogStage.setScene(scene);

            PagamentoBMController controller = loader.getController();
            controller.setUser(user);
            controller.setCarrelloList(carrelloList);
            controller.setTotale(calcoloTotale(carrelloList));
            controller.setDialogStage(dialogStage);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    private void setTableView(){
        for (RicambioModel ricambio : carrelloList) {
            System.out.println(ricambio.toString());
        }
        colNomePorodotto.setCellValueFactory(new PropertyValueFactory<>("nomeProdotto"));
        colFornitoreProdotto.setCellValueFactory(new PropertyValueFactory<>("nomeFornitore"));
        colCategoriaProdotto.setCellValueFactory(new PropertyValueFactory<>("nomeCategoria"));
        colQuantitaProdotto.setCellValueFactory(new PropertyValueFactory<>("quantitaAcquistata"));
        colPrezzoProdotto.setCellValueFactory(new PropertyValueFactory<>("costoScontato"));

        tableView.setItems(carrelloList);
    }

    private float calcoloTotale(ObservableList<RicambioModel> carelloList){
        float totale = 0.0F;
        for(RicambioModel ricambio : carelloList){
            totale += ricambio.getCostoScontato() * ricambio.getQuantitaAcquistata();
            System.out.println(totale);
        }
        return totale;
    }
}
