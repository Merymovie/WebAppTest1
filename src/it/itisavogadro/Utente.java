package it.itisavogadro;

public class Utente {
    int id;
    String username;
    String password;
    String email;
    String cognome;
    String nome;

    public String getEmail() { return email;}

    public void setEmail(String email) { this.email = email;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString(){
        return toString(" | ");
    }
    //visualizzare in una stringa tutti i suoi campi
    /*
    public java.lang.String toString(String sep){
        return "Utente{" +
                sep + "id= " + id +
                sep +  "username= '" + username + '\''+
                sep +  "password= '" + password + '\''+
                sep +  "cognome= '" + cognome + '\''+
                sep +  "nome= '" + nome + '\''+
                sep +  "email= '" + email + '\''+
                sep +  '}';
                */



    public java.lang.String toString(String separator) {
    return "<ul> ID = " + id  +
    separator + "Username = '" + username + '\'' +
    separator + "Password = '" + password + '\'' +
    separator + "Indirizzo email = '" + email + '\'' +
    separator + "Nome = '" + nome + '\'' +
    separator + "Cognome = '" + cognome + '\'' + "</ul>";

    }




}
