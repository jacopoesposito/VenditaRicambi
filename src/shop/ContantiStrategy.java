package shop;

import javafx.collections.ObservableList;
import shop.model.RicambioModel;
import shop.model.UserModel;

public class ContantiStrategy implements PagamentiStrategy {

    private InserisciOrdine inserisciOrdine = new InserisciOrdine();

    @Override
    public void paga(float totale, ObservableList<RicambioModel> carelloList, UserModel user) {
        inserisciOrdine.insertOrdine(totale, carelloList, user); //Viene invocato il metodo inserisci ordine che si occupa dell'inserimento dell'ordine nel DB.
    }
}
