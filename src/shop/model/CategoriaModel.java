package shop.model;

public class CategoriaModel {

    //La presente classe rappressenta una categoria del nostro applicativo, vengono di seguito descritti i campi atti alla
    //descrizione di una singola categoria e tutti i metodi neccessari per operare su di essi

    private String nomeCategoria;
    private String pkCategoria;

    public CategoriaModel(){}

    public CategoriaModel(String nomeCategoria, String pkCategoria) {
        this.nomeCategoria = nomeCategoria;
        this.pkCategoria = pkCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getPkCategoria() {
        return pkCategoria;
    }

    public void setPkCategoria(String pkCategoria) {
        this.pkCategoria = pkCategoria;
    }


}
