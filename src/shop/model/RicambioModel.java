package shop.model;

public class RicambioModel {

    private String nomeProdotto;
    private String descrizioneProdotto;
    private int percentualeSconto;
    private float costo;
    private float costoScontato;
    private String nomeCategoria;
    private String nomeFornitore;

    public RicambioModel(){

    }

    public RicambioModel(String nomeProdotto, String descrizioneProdotto, int percentualeSconto, float costo, float costoScontato, String nomeCategoria, String nomeFornitore) {
        this.nomeProdotto = nomeProdotto;
        this.descrizioneProdotto = descrizioneProdotto;
        this.percentualeSconto = percentualeSconto;
        this.costo = costo;
        this.costoScontato = costoScontato;
        this.nomeCategoria = nomeCategoria;
        this.nomeFornitore = nomeFornitore;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public String getDescrizioneProdotto() {
        return descrizioneProdotto;
    }

    public void setDescrizioneProdotto(String descrizioneProdotto) {
        this.descrizioneProdotto = descrizioneProdotto;
    }

    public int getPercentualeSconto() {
        return percentualeSconto;
    }

    public void setPercentualeSconto(int percentualeSconto) {
        this.percentualeSconto = percentualeSconto;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getCostoScontato() {
        return costoScontato;
    }

    public void setCostoScontato(float costoScontato) {
        this.costoScontato = costoScontato;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getNomeFornitore() {
        return nomeFornitore;
    }

    public void setNomeFornitore(String nomeFornitore) {
        this.nomeFornitore = nomeFornitore;
    }
}
