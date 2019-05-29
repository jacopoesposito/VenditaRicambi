package shop.model;

public class FornitoreModel {

    private String nomeFornitore;
    private String pkFornitore;

    public FornitoreModel(){}

    public FornitoreModel(String nomeFornitore, String pkFornitore){
        this.nomeFornitore = nomeFornitore;
        this.pkFornitore = pkFornitore;
    }

    public String getNomeFornitore() {
        return nomeFornitore;
    }

    public void setNomeFornitore(String nomeFornitore) {
        this.nomeFornitore = nomeFornitore;
    }

    public String getPkFornitore() {
        return pkFornitore;
    }

    public void setPkFornitore(String pkFornitore) {
        this.pkFornitore = pkFornitore;
    }
}
