package shop;

import javafx.collections.ObservableList;
import shop.model.RicambioModel;
import shop.model.UserModel;
import shop.view.MainAppController;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.UUID;

public class InserisciOrdine {

    private MainAppController mainAppController = new MainAppController();
    private InserisciRicambio inserisciRicambio = new InserisciRicambio();

    public void insertOrdine(float totale, ObservableList<RicambioModel> carelloList, UserModel user){
        String pkOrdine = UUID.randomUUID().toString(); //Genero la primary key dell'ordine
        MysqlConnection db = MysqlConnection.getDbCon(); //Recupero la connessione al database.

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("INSERT INTO ORDINI (ID_ORDINE, DATA_ORDINE, FK_CODICE_UTENTE, TOTALE_ORDINE) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, pkOrdine); //Setto la chiave primaria dell'ordine
            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now())); //Setto la data dell'ordine, utilizzando il metodo now() della classe LocalDate
            preparedStatement.setString(3, user.getCodiceUtente()); //Setto il codice utente dell'User che ha effettuato l'ordine
            preparedStatement.setFloat(4, totale); //Setto il totale
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            PreparedStatement prepStatement = db.conn.prepareStatement("INSERT INTO PRODOTTO_ORDINATO (QUANTITA, FK_CODICE_PRODOTTO, FK_ID_ORDINE, COSTO) VALUES (?, ?, ?, ?)");
            PreparedStatement prepStatememt2 = db.conn.prepareStatement("UPDATE PRODOTTO SET QUANTITA = ? WHERE CODICE_PRODOTTO = ?");
            Iterator<RicambioModel> i = carelloList.iterator();//Dichiaro un Iterator per scorrere la lista carelloList

            final int batchSize = 1000; //Dichiaro la lunghezza del Batch utilizzato per salvare le query di inserimento dei ProdottiOrdinati/ aggiornamento della quantità disponibile in modo da esqguire tutte le Query in blocco, ciò comporta un risparmio di risorse
            int count = 0;

            while (i.hasNext()){
                RicambioModel ricambio = i.next(); //Recupero l'iesimo oggetto.
                prepStatement.setInt(1, ricambio.getQuantitaAcquistata()); //Setto la quantita da acquistata
                prepStatement.setString(2, ricambio.getPkProdotto()); //Setto la chiave primaria del prodotto
                prepStatement.setString(3, pkOrdine); //Setto la FK dell'ordine a cui è associata la vendita di questo prodotto
                prepStatement.setFloat(4, ricambio.getCostoScontato()); //Setto il prezzo a cui il prodotto è venduto
                int quantitaDB = inserisciRicambio.selectQuantitaRicambio(ricambio); //Chiedo al db la quantità di scorte presenti in magazziono del prodotto
                if(quantitaDB >= ricambio.getQuantitaAcquistata()){ //Controllo che non sia inferiore alla quantità che si tenta di acquistare
                    prepStatememt2.setInt(1, quantitaDB - ricambio.getQuantitaAcquistata()); //Sottraggo la quantita acquistata alla quantita presente nel Database
                    prepStatememt2.setString(2, ricambio.getPkProdotto()); //Setto la PK del prodotto per il quale deve essere effettuata la modifica sopracitata
                }
                else{
                    mainAppController.alert("Prodotto non disponibile", "Errore", "Il prodotto da lei selezionato: " + ricambio.getNomeProdotto() +
                            "non è disponibile all'acquisto");
                    carelloList.remove(ricambio);
                }
                prepStatement.addBatch(); //Aggiungo lo statement al Batch
                prepStatememt2.addBatch(); //Aggiungo il secondo statement al Batch

                if(++count % batchSize==0) { //Controllo che il Batch non sia pieno
                    prepStatement.executeBatch(); //Inserisco i record
                    prepStatememt2.executeBatch();
                    carelloList.clear(); //Pulisco la lista carello
                }
            }
            prepStatement.executeBatch(); //Inserisco Record rimanenti
            prepStatememt2.executeBatch();
            prepStatement.close();
            prepStatememt2.close();
            carelloList.clear(); //Pulisco la lista carello
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
