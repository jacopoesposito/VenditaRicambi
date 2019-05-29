package shop.model;


public class UserModel {

    private String mail;
    private String nome;
    private String cognome;
    private String password;
    private String reinserisciPassword;
    private int numeroCivico;
    private String nomeVia;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReinserisciPassword() {
        return reinserisciPassword;
    }

    public void setReinserisciPassword(String reinserisciPassword) {
        this.reinserisciPassword = reinserisciPassword;
    }

    public int getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(int numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public String getNomeVia() {
        return nomeVia;
    }

    public void setNomeVia(String nomeVia) {
        this.nomeVia = nomeVia;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    private String citta;

    public UserModel(){

    }

    public UserModel(String mail, String nome, String cognome, String password, String reinserisciPassword,
                     int numeroCivico, String nomeVia, String citta){
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.password = password;
        this.reinserisciPassword = reinserisciPassword;
        this.numeroCivico = numeroCivico;
        this.nomeVia = nomeVia;
        this.citta = citta;
    }


}
