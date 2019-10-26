package shop.model;

public class FornitoreModel {

    //La presente classe rappressenta un fornitore, vengono di seguito descritti i campi atti alla
    //descrizione di un singolo utente e tutti i metodi neccessari per operare su di essi

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
