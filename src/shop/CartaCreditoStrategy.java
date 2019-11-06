package shop;

import javafx.collections.ObservableList;
import shop.model.RicambioModel;
import shop.model.UserModel;


import java.util.Date;

public class CartaCreditoStrategy implements PagamentiStrategy {

    private InserisciOrdine inserisciOrdine = new InserisciOrdine();

    private String nome;
    private String cognome;
    private String numeroCC;
    private int cvv;
    private Date scadenza;

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public CartaCreditoStrategy(String nome, String cognome, String numeroCC, int cvv, Date scadenza) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeroCC = numeroCC;
        this.cvv = cvv;
        this.scadenza = scadenza;
    }

    @Override
    public void paga(float totale, ObservableList<RicambioModel> carelloList, UserModel user) {
        inserisciOrdine.insertOrdine(totale, carelloList, user); //Viene invocato il metodo inserisci ordine che si occupa dell'inserimento dell'ordine nel DB.
    }
}
