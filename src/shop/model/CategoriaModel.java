package shop.model;

public class CategoriaModel {

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
