package shop.model;


public class UserModel {

    //La presente classe rappressenta un utente del nostro applicativo, vengono di seguito descritti i campi atti alla
    //descrizione di un singolo utente e tutti i metodi neccessari per operare su di essi

    private String mail;
    private String nome;
    private String cognome;
    private String password;
    private String reinserisciPassword;
    private int numeroCivico;
    private String nomeVia;
    private String codiceUtente;
    private int admin;

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

    public String getCodiceUtente() {
        return codiceUtente;
    }

    public void setCodiceUtente(String codiceUtente) {
        this.codiceUtente = codiceUtente;
    }

    public int getAdmin(){return admin;}

    public void setAdmin(int admin){
        this.admin = admin;
    }

    private String citta;

    public UserModel() {

    }

    public UserModel(String nome, String cognome, String codiceUtente, String admin){

    }

    public UserModel(String mail, String nome, String cognome, String password, String reinserisciPassword,
                     int numeroCivico, String nomeVia, String citta, String codiceUtente){
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.password = password;
        this.reinserisciPassword = reinserisciPassword;
        this.numeroCivico = numeroCivico;
        this.nomeVia = nomeVia;
        this.citta = citta;
        this.codiceUtente = codiceUtente;
    }

    //Override del metodo toString per far sì che restituisse il nome e il cognome dell'utente.
    public String toString(){
        return nome +  " " + cognome;
    }


}
