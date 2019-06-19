package shop;

import javafx.collections.ObservableList;
import shop.model.RicambioModel;
import shop.model.UserModel;

public interface PagamentiStrategy {
    public void paga(float totale, ObservableList<RicambioModel> carelloList, UserModel user);
}
