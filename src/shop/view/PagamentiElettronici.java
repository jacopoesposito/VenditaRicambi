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
        String errorMessage = "";

        if(nomeTitolareTextField.getText().length() == 0 || nomeTitolareTextField.getText() == null){
            errorMessage += "Inserisci il nome del titolare\n";
        }
        if(cognomeTitolareTextField.getText().length() == 0 || cognomeTitolareTextField.getText() == null){
            errorMessage += "Inserisci il cognome del titolare\n";
        }
        if(dataScadenzaTextField.getText().length() == 0 || dataScadenzaTextField.getText() == null){
            errorMessage += "Inserisci la data in cui scade la carta\n";
        }
        else {
            Date parsed = null;
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                parsed = formatter.parse(dataScadenzaTextField.getText());
                if(parsed.before(Calendar.getInstance().getTime())){
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
