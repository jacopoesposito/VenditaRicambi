package shop.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

abstract class PagamentiElettronici {

    @SuppressWarnings("Duplicates")
    protected void annullaOperazione(UserModel user, Stage dialogStage, ObservableList<RicambioModel> carrelloList){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/Carrello.fxml"));
            VBox carrello = (VBox) loader.load();

            dialogStage.setTitle("Carrello");
            Scene scene = new Scene(carrello);
            dialogStage.setScene(scene);

            CarrelloController controllerCarrello = loader.getController();
            controllerCarrello.setUser(user);
            controllerCarrello.setDialogStage(dialogStage);
            controllerCarrello.setCarrelloList(carrelloList);


            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    protected void confermaOperazione(UserModel user, Stage dialogStage, ObservableList<RicambioModel> carrelloList){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/shop/view/PersonOverview.fxml"));
            AnchorPane shopOverview = (AnchorPane) loader.load();

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


    protected String checkNameData(TextField nomeTitolareTextField, TextField cognomeTitolareTextField, TextField dataScadenzaTextField){
        //Il presente metodo si occupa di effettuare il checking del Nome, del Cognome e della data di scadenza di una CC o BM, verificando
        //che i dati siano inseriti e siano validi
        String errorMessage = "";

        if(nomeTitolareTextField.getText().length() == 0 || nomeTitolareTextField.getText() == null){ //Verifico che sia stato inserito il nome
            errorMessage += "Inserisci il nome del titolare\n";
        }
        if(cognomeTitolareTextField.getText().length() == 0 || cognomeTitolareTextField.getText() == null){ //Verifico che sia stato inserito il cognome
            errorMessage += "Inserisci il cognome del titolare\n";
        }
        if(dataScadenzaTextField.getText().length() == 0 || dataScadenzaTextField.getText() == null){ //Verifico che sia stata inserita la data di scadenza
            errorMessage += "Inserisci la data in cui scade la carta\n";
        }
        else {
            Date parsed = null;
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //Specifico il formata in cui è formattata la data
                parsed = formatter.parse(dataScadenzaTextField.getText()); //Effettuo il parsing della data, in tal modo otterro un oggetto Date da poter successivamente confrontare
                if(parsed.before(Calendar.getInstance().getTime())){ //Condronto la data inserita dall'utente con quella odierna
                    errorMessage += "La seguente carta di credito è già scaduta";
                }
            } catch (ParseException e) {
                errorMessage += "inserisci la data nel formato GIORNO/MESE/ANNO Esempio: 18/06/2019\n";
            }
        }
        return errorMessage;
    }

    abstract protected Boolean isInputValid();
}
