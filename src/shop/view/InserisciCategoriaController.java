package shop.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shop.InserisciCategoria;
import shop.model.CategoriaModel;
import shop.model.UserModel;

public class InserisciCategoriaController {

    private UserModel user = new UserModel();
    private Stage dialogStage = new Stage();
    private Boolean okClicked = false;
    private MainAppController mainAppController = new MainAppController();
    private InserisciCategoria inserisciCategoria = new InserisciCategoria();

    private String nomeCategoria;

    @FXML
    Button cancel;

    @FXML
    Button inserisciButton;

    @FXML
    private TextField nomeCategoriaField;

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    @FXML
    private void handleInsersci(){
        if(isInputValid()){
            nomeCategoria = nomeCategoriaField.getText().toLowerCase();
            CategoriaModel categoria = inserisciCategoria.selectCategoria(nomeCategoria); //Select che ritorna la categoria desiderata

            System.out.println("CIAAAA");
            okClicked = true;

            if(categoria.getPkCategoria() == null){  //Eseguo un controllo sulla chiave primaria della categoria, cosi se risulta nulla vorrà dire che la categoria non è presente nel db
                categoria.setNomeCategoria(nomeCategoria);
                inserisciCategoria.insertCategoria(categoria);
                dialogStage.close();
            }
            else{
                mainAppController.alert("Categoria già esistente", "Errore", "Errore");
                dialogStage.close();
            }

        }

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

    private Boolean isInputValid(){
       if(nomeCategoriaField.getText() == null || nomeCategoriaField.getText().length() == 0){
           mainAppController.alert("Inserisci una categoia", "Errore", "Errore");
           return false;
       }
       return true;
    }
}
