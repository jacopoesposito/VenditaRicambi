package shop;

import javafx.collections.ObservableList;
import shop.model.RicambioModel;
import shop.model.UserModel;

import java.util.Date;

public class BancomatStrategy implements PagamentiStrategy{

    private InserisciOrdine inserisciOrdine = new InserisciOrdine();

    private String nome;
    private String cognome;
    private String numeroBM;
    private int pin;
    private Date scadenza;

    public BancomatStrategy(String nome, String cognome, String numeroBM, int pin, Date scadenza) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeroBM = numeroBM;
        this.pin = pin;
        this.scadenza = scadenza;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    @Override
    public void paga(float totale, ObservableList<RicambioModel> carelloList, UserModel user) {
        inserisciOrdine.insertOrdine(totale, carelloList, user);
    }
}
