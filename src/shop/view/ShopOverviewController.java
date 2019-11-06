package shop.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shop.MainApp;
import shop.MysqlConnection;
import shop.ShopOverview;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class ShopOverviewController {

    private ShopOverview shopOverview;
    private Stage dialogStage;
    private boolean okClicked = false;
    private UserModel user;
    private MainApp mainApp = new MainApp();
    private MainAppController mainAppController = new MainAppController();
    private ObservableList<RicambioModel> ricambioList = FXCollections.observableArrayList();
    private ObservableList<RicambioModel> carelloList = FXCollections.observableArrayList();




    @FXML
    private MenuButton account;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu admin = new Menu("Admin");
    private MenuItem menuAggiungi = new MenuItem("Aggiungi Ricambio");
    private MenuItem menuCreaAdmin = new MenuItem("Aggiungi Admin");
    private MenuItem menuAggiungiCategoria = new MenuItem("Aggiungi Categoria");
    private MenuItem menuAggiungiFornitore = new MenuItem("Aggiungi Fornitore");
    private MenuItem menuRimuoviProdotto = new MenuItem("Rimuovi Prodotto");
    private MenuItem menuVisualizzaVendite = new MenuItem("Visualizza Stato Vendite");

    @FXML
    private Button aggiungi;

    @FXML
    private Label nomeProdottoText;

    @FXML
    private Label descrizioneProdottoText;

    @FXML
    private Label pkProdotto;

    @FXML
    private Label prezzoScontato;

    @FXML
    private Label costo;

    @FXML
    private Label sconto;

    @FXML
    private Label quantita;

    @FXML
    private MenuItem esci;

    @FXML
    private MenuItem assemblaAuto;

    @FXML
    private MenuItem informazioni;

    @FXML
    private TableView<RicambioModel> tableView = new TableView<RicambioModel>();

    @FXML
    private TableColumn<RicambioModel, String> colNomePorodotto;

    @FXML
    private TableColumn<RicambioModel, String> colFornitoreProdotto;

    @FXML
    private TableColumn<RicambioModel, String> colCategoriaProdotto;

    @FXML
    private void initialize(){//Metodo initialize viene chiamato ogni qual volta viene invocato lo Stage
        fillTableView(); //Viene chiamato il metodo che si occupa di recuperare tutti i dati da inserire nella TableView

        colNomePorodotto.setCellValueFactory(new PropertyValueFactory<>("nomeProdotto")); //Setto i nomi dei prodotti
        colFornitoreProdotto.setCellValueFactory(new PropertyValueFactory<>("nomeFornitore"));  //Setto i nomi dei fornitori
        colCategoriaProdotto.setCellValueFactory(new PropertyValueFactory<>("nomeCategoria"));  //Setto le categorie

        tableView.setItems(ricambioList); //Setto la TableView inserendo i ricambi

        tableView.setOnMousePressed( (MouseEvent event) -> { //Rilevo l'evento di pressione del mouse
            if(event.getClickCount() == 1){ //Se l'utente effettua un click sul mouese
                RicambioModel ricambio = tableView.getSelectionModel().getSelectedItem(); //Viene preso l'oggetto selezionato
                if(ricambio != null) { //Controllo che la selezione non sia nulla
                    //System.out.println(ricambio.getCosto());
                    //Setto le varie TextView della descrizione del prodotto
                    nomeProdottoText.setText(ricambio.getNomeProdotto());
                    descrizioneProdottoText.setText(ricambio.getDescrizioneProdotto());
                    pkProdotto.setText(ricambio.getPkProdotto());
                    prezzoScontato.setText(Float.toString(ricambio.getCostoScontato()));
                    costo.setText(Float.toString(ricambio.getCosto()));
                    sconto.setText(String.valueOf(ricambio.getPercentualeSconto()) + "%");
                    quantita.setText(String.valueOf(ricambio.getQuantita()));
                }
            }
        });
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        if(checkIsAdmin()){ //Controllo che l'user sia un Admin
            //Aggiungo dinamicamente le voci al menu Admin
            admin.getItems().add(menuAggiungi);
            admin.getItems().add(menuAggiungiCategoria);
            admin.getItems().add(menuAggiungiFornitore);
            admin.getItems().add(menuCreaAdmin);
            admin.getItems().add(menuRimuoviProdotto);
            admin.getItems().add(menuVisualizzaVendite);
            menuBar.getMenus().add(admin); //Aggiungo il menu admin
        }
        menuAggiungi.setOnAction(event -> {showInserisciRicambio();});
        menuAggiungiCategoria.setOnAction(event -> {showAggiungiCategoria();});
        menuAggiungiFornitore.setOnAction(event -> {showInserisciFornitore();});
        menuCreaAdmin.setOnAction(event -> {showAggiungiAdmin();});
        menuRimuoviProdotto.setOnAction(event -> {showRimuoviProdotto();});
        menuVisualizzaVendite.setOnAction(event -> {showVisualizzaVendite();});
    }

    public void setCarelloList(ObservableList<RicambioModel> carelloList) {
        //Setto la lista di oggetti inseriti nel carello
        this.carelloList = carelloList;
    }

    public boolean isOkClicked() { return okClicked;
    }

    public void setUser(UserModel user){
        this.user = user;
    }

    @SuppressWarnings("Duplicates")
    @FXML
    private void handleAcquista(){
        //Questo metodo viene invocato quando qualcuno clicca su acquista
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/Carrello.fxml"));
            VBox carrello = (VBox) loader.load();

            dialogStage.setTitle("Carrello");
            Scene scene = new Scene(carrello);
            dialogStage.setScene(scene);

            CarrelloController controllerCarrello = loader.getController();
            //Setto l'User, lo stage e la carelloList all'interno del Controller
            controllerCarrello.setUser(user);
            controllerCarrello.setDialogStage(dialogStage);
            controllerCarrello.setCarrelloList(carelloList);


            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void handleAggiungi(){
        //Questo metodo viene chiamato quando si clicca su aggiung
        RicambioModel ricambio = tableView.getSelectionModel().getSelectedItem(); //Viene preso l'oggetto selezionato
        if(ricambio != null) { //Si effettua un controllo per verificare che sia stato effettivamente selezionato un ricambio
            if (ricambio.getQuantita() != 0) { //Se la quantità del ricambio è diversa da zero allora posso procedere
                ricambio.setQuantitaAcquistata(ricambio.getQuantitaAcquistata() + 1); //Aument di uno la quantità acquistata
                ricambio.setQuantita(ricambio.getQuantita() - 1); //Diminusico di uno la quantità disponibile del ricambio
                if (!carelloList.contains(ricambio)) {//Se nel carrello non è già presente il ricambio allora lo inserisco, in tal modo non avrò oggetti duplicati nel carrello
                    carelloList.add(ricambio);
                }
                quantita.setText(String.valueOf(ricambio.getQuantita()));
            } else {
                mainAppController.alert("Prodotto non disponibile!", "Errore", "Il prodotto da lei selezionato " +
                        "non è al momento disponibile.");
            }
        }
    }

    @FXML
    private void handleAssembla(){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/ComponiAuto.fxml"));
            VBox componiAuto = (VBox) loader.load();

            dialogStage.setTitle("Componi Auto");
            Scene scene = new Scene(componiAuto);
            dialogStage.setScene(scene);

            ComponiAutoController controllerComponi = loader.getController();
            controllerComponi.setUser(user);
            controllerComponi.setDialogStage(dialogStage);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleExit(){
        mainApp.start(dialogStage);
    }

    private boolean checkIsAdmin(){
        int admin;
        boolean controll = false;
        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT ADMIN FROM UTENTE WHERE MAIL = ?");
            preparedStatement.setString(1, user.getMail());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            admin = rs.getInt("ADMIN");
            preparedStatement.close();
            if(admin == 1){
                controll = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controll;
    }

    private void showInserisciRicambio(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/InserisciRicambio.fxml"));
            VBox inserisciRicambio = (VBox) loader.load();

            dialogStage.setTitle("Inserisci Ricambio");
            Scene scene = new Scene(inserisciRicambio);
            dialogStage.setScene(scene);

            InserisciRicambioController controllerRicambio = loader.getController();
            controllerRicambio.setUser(user);
            controllerRicambio.setDialogStage(dialogStage);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    private boolean showInserisciFornitore(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/InserisciFornitore.fxml"));
            VBox inserisciFornitore = (VBox) loader.load();

            Stage newDialogStage = new Stage();
            newDialogStage.setTitle("Inserisci Fornitore");
            newDialogStage.initOwner(dialogStage);
            newDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(inserisciFornitore);
            newDialogStage.setScene(scene);

            InserisciFornitoreController controller = loader.getController();
            controller.setUser(user);
            controller.setDialogStage(newDialogStage);

            newDialogStage.showAndWait();

            return controller.isOkayClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("Duplicates")
    private boolean showAggiungiCategoria() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/InserisciCategoria.fxml"));
            VBox inserisciCategoria = (VBox) loader.load();

            Stage newDialogStage = new Stage();
            newDialogStage.setTitle("Inserisci Categoria");
            newDialogStage.initOwner(dialogStage);
            newDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(inserisciCategoria);
            newDialogStage.setScene(scene);

            InserisciCategoriaController controller = loader.getController();
            controller.setUser(user);
            controller.setDialogStage(newDialogStage);

            newDialogStage.showAndWait();

            return controller.isOkayClicKed();
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("Duplicates")
    private boolean showAggiungiAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/InserisciAdmin.fxml"));
            VBox inserisciAdmin = (VBox) loader.load();

            Stage newDialogStage = new Stage();
            newDialogStage.setTitle("Aggiungi Admin");
            newDialogStage.initOwner(dialogStage);
            newDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(inserisciAdmin);
            newDialogStage.setScene(scene);

            InserisciAdminController controller = loader.getController();
            controller.setUser(user);
            controller.setDialogStage(newDialogStage);

            newDialogStage.showAndWait();

            return controller.isOkayClicKed();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("Duplicates")
    public boolean showRimuoviProdotto(){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/shop/view/RimuoviRicambio.fxml"));
                VBox rimuoviRicambio = (VBox) loader.load();

                Stage newDialogStage = new Stage();
                newDialogStage.setTitle("Aggiungi Admin");
                newDialogStage.initOwner(dialogStage);
                newDialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(rimuoviRicambio);
                newDialogStage.setScene(scene);

                RimuoviRicambioController controller = loader.getController();
                controller.setUser(user);
                controller.setDialogStage(newDialogStage);

                newDialogStage.showAndWait();

                return controller.isOkayClicKed();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
    }

    @SuppressWarnings("Duplicates")
    public boolean showVisualizzaVendite(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/VisualizzaVendite.fxml"));
            VBox visualizzaVendite = (VBox) loader.load();

            Stage newDialogStage = new Stage();
            newDialogStage.setTitle("Visualizza Stato Vendite");
            newDialogStage.initOwner(dialogStage);
            newDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(visualizzaVendite);
            newDialogStage.setScene(scene);

            VisualizzaVenditeController controller = loader.getController();
            controller.setUser(user);
            controller.setDialogStage(newDialogStage);

            newDialogStage.showAndWait();

            return controller.isOkayClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    @SuppressWarnings("Duplicates")
    private void fillTableView(){
        //Questo metodo serve per riempire la Tabelle con tutti i ricambi
        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al DB.

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("SELECT CODICE_PRODOTTO, `NOME_PRODOTTO`, `DESCRIZIONE_PRODOTTO`, `PERCENTUALE_SCONTO`, `COSTO`, `PREZZO_S`, `FK_CODICE_FORNITORE`, `FK_ID_CATEGORIA`, " +
                    "NOME_CATEGORIA, NOME_FORNITORE, QUANTITA, VISIBILE FROM `prodotto` " + "join fornitore ON prodotto.FK_CODICE_FORNITORE = fornitore.CODICE_FORNITORE " +
                    "join categoria ON prodotto.FK_ID_CATEGORIA = categoria.ID_CATEGORIA");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                RicambioModel ricambio = new RicambioModel();

                ricambio.setPkProdotto(rs.getString(1));
                ricambio.setNomeProdotto(rs.getString(2));
                ricambio.setDescrizioneProdotto(rs.getString(3));
                ricambio.setPercentualeSconto(rs.getInt(4));
                ricambio.setCosto(rs.getFloat(5));
                ricambio.setCostoScontato(rs.getFloat(6));
                ricambio.setFkFornitore(rs.getString(7));
                ricambio.setFkCategoria(rs.getString(8));
                ricambio.setNomeCategoria(rs.getString(9));
                ricambio.setNomeFornitore(rs.getString(10));
                ricambio.setQuantita(rs.getInt(11));
                ricambio.setVisibilita(rs.getInt(12));
                //Se la visibilità del ricambio è impostata su 0 allora vuol dire che il ricambio è ancora disponibile alla vendita/visualizzaione quindi lo inserico nella lista, qu
                // questa scelta è stata operata per far sì che l'admin possa rimuovere dei ricambi dallo shop pur mantendo uno storico effettivo all'interno del DB.
                if(ricambio.getVisibilita() == 0) {
                    ricambioList.add(ricambio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkQuantita(){
        //Metodo per il controllo della quantita dei prodotti
            for(RicambioModel ricambioAggiunto : carelloList) {
                if (ricambioList.contains(ricambioAggiunto)) {
                    //Sottrago la quantita del prodotto aggiunto alla quantità del prodotto nella lista ricambi
                    int quantita = ricambioList.get(ricambioList.indexOf(ricambioAggiunto)).getQuantita() - ricambioAggiunto.getQuantitaAcquistata();
                    if(quantita > 0){ //Controllo che la quantita sia maggiore di 0
                        //Se maggiore di 0 setto la quantità
                        ricambioList.get(ricambioList.indexOf(ricambioAggiunto)).setQuantita(quantita);
                    }
                    else{
                        //Se minore la setto direttamente a 0, perchè vuol dire che sono stati venduti tutti i prodotti
                        ricambioList.get(ricambioList.indexOf(ricambioAggiunto)).setQuantita(0);
                    }
                }
            }
    }
}

