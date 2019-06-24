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
        String pkOrdine = UUID.randomUUID().toString();
        MysqlConnection db = MysqlConnection.getDbCon();

        try{
            PreparedStatement preparedStatement = db.conn.prepareStatement("INSERT INTO ORDINI (ID_ORDINE, DATA_ORDINE, FK_CODICE_UTENTE, TOTALE_ORDINE) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, pkOrdine);
            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setString(3, user.getCodiceUtente());
            preparedStatement.setFloat(4, totale);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            PreparedStatement prepStatement = db.conn.prepareStatement("INSERT INTO PRODOTTO_ORDINATO (QUANTITA, FK_CODICE_PRODOTTO, FK_ID_ORDINE, COSTO) VALUES (?, ?, ?, ?)");
            PreparedStatement prepStatememt2 = db.conn.prepareStatement("UPDATE PRODOTTO SET QUANTITA = ? WHERE CODICE_PRODOTTO = ?");
            Iterator<RicambioModel> i = carelloList.iterator();

            final int batchSize = 1000;
            int count = 0;

            while (i.hasNext()){
                RicambioModel ricambio = i.next();
                prepStatement.setInt(1, ricambio.getQuantitaAcquistata());
                prepStatement.setString(2, ricambio.getPkProdotto());
                prepStatement.setString(3, pkOrdine);
                prepStatement.setFloat(4, ricambio.getCostoScontato());
                int quantitaDB = inserisciRicambio.selectQuantitaRicambio(ricambio);
                if(quantitaDB >= ricambio.getQuantitaAcquistata()){
                    prepStatememt2.setInt(1, quantitaDB - ricambio.getQuantitaAcquistata());
                    prepStatememt2.setString(2, ricambio.getPkProdotto());
                }
                else{
                    mainAppController.alert("Prodotto non disponibile", "Errore", "Il prodotto da lei selezionato: " + ricambio.getNomeProdotto() +
                            "non è disponibile all'acquisto");
                    carelloList.remove(ricambio);
                }
                prepStatement.addBatch();
                prepStatememt2.addBatch();

                if(++count % batchSize==0) {
                    prepStatement.executeBatch();
                    prepStatememt2.executeBatch();
                    carelloList.clear();
                }
            }
            prepStatement.executeBatch(); //Inserisco Record rimanenti
            prepStatememt2.executeBatch();
            prepStatement.close();
            prepStatememt2.close();
            carelloList.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
