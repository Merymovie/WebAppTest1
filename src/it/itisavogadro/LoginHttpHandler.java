package it.itisavogadro;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
//import org.omg.CORBA.portable.OutputStream;

import java.io.OutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.function.DoubleToIntFunction;

public class LoginHttpHandler implements HttpHandler {

    @Override

    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("HTTP request LOGIN");

        System.out.println(
                "\nURI:" + httpExchange.getRequestURI() + //indirizzo completo richiesta
                        "\nmethod:" + httpExchange.getRequestMethod() + //metodo (get,post,put,delete)
                        "\ncontext-path:" + httpExchange.getHttpContext().getPath() + //percorso attivazione contesto
                        "\npath:" + httpExchange.getRequestURI().getPath() + // percorso completo
                        "\nquery:" + httpExchange.getRequestURI().getQuery() + // elenco parametri specificati dopo il percorso e che vengono attivati con ?
                        // es--> percorso?parametro1=valore1&parametro2=val2
                        ""
        );

        //risposta web in codice html:
        String htmlResponse = null;
        // se il metodo è un get la risposta è il form da compilare.
        if ("GET".equalsIgnoreCase(httpExchange.getRequestMethod())) {
            htmlResponse = "" +
                    "<html>" +
                    "<body>" +
                    "<h1>" + httpExchange.getRequestMethod() + "- LOGIN Test - 1 App</h1>" +
                    "<form method=\"POST\" action=\"/login\">" +
                    //testo richiesta
                    "<label>Utente:</label><br>" +
                    //inserimento in input
                    "<input type=text name=\"user\"><br>" +
                    "<label>Password:</label><br>" +
                    "<input type=password name=\"password\"><br>" +
                    //pulsante invio
                    "<input type= submit value=\"Invio\">" +
                    "</form>" +
                    "</body>" +
                    "</html>"
            ;
        } else {
            // potrebbero essere put del o post, noi usiamo solo post
            //richiesta del corpo ossia il canale di dati del browser
            Scanner scanner = new Scanner(httpExchange.getRequestBody(), StandardCharsets.UTF_8.name());
            // .next legge la riga dei parametri
            String paramLine = scanner.useDelimiter("\\A").next();
            //divide la stringa dell'input dal browser, il separatore è &
            //la situa sarebbe: Variabilenome=Valoreinput&variabilenome=Valoreinput
            //& è il punto di split
            String params[] = paramLine.split("&");
            //le salvo nelle stringhe
            String user = "missing";
            String pass = "missing";
            //ciclo per prendere stringa con parametro
            //ogni variabile è = Variabilenome=valoreinput
            for (String param : params) {
                //splitta in ==
                String paramName = param.split("=")[0];
                String paramValue = param.split("=")[1];
                //tutti i caratteri vengono decodificati in ascii
                paramValue = java.net.URLDecoder.decode(paramValue, StandardCharsets.UTF_8.name());
                //se il parametro è user lo salva nella variabile
                if ("user".equalsIgnoreCase(paramName)) {
                    user = paramValue;
                }
                //se il parametro è password lo salva nella variabile
                if ("password".equalsIgnoreCase(paramName)) {
                    pass = paramValue;
                }
            }

            boolean credenzialiValide = false;
            // accedere al DB per verificare l'accesso
            //la connessione al DB può sempre creare errore, meglio prevederli
            try {
                //bisogna predisporre la stringa di connessione, spiegata in seguito
                String dbURL = "jdbc:postgresql://localhost:5432/postgres";
                //permette di eseguire una select
                Connection conn = DriverManager.getConnection(dbURL, "postgres", "Polpette96");

                Statement stmt = conn.createStatement();
                //cerchiamo in username la tavola interessata
                //ResultSet è un insieme di risultati
                ResultSet rs = stmt.executeQuery(
                        "select * from webapp.utente" //where username = '"+user+"'"
                );
                //stringa utenti con i pallini
                String utenti = "<ul>";
                //next permette di avere getter specifici sul tipo
                //es. nome della colonna è un int == getInt ecc

                //crittografia della password
                //classe Java standard che serve per trasformare il testo secondo algoritmi matemarici
                MessageDigest md = MessageDigest.getInstance("sha-1");
                //la password viene trasformata in un array di bytes e la diget diventa un valore numerico.
                String sha1 = String.format("%040x", new BigInteger(1, md.digest(pass.getBytes())));
                // String shal = String.format("%040x", new BigInteger(1, ))

                while (rs.next()) {
                    //crea istanta utente
                    Utente utente = new Utente();
                    //settiamo campi record
                    utente.setId(rs.getInt("ID"));
                    utente.setPassword(rs.getString("Password"));
                    utente.setEmail(rs.getString("Email"));
                    utente.setUsername(rs.getString("Username"));
                    utente.setCognome(rs.getString("Cognome"));
                    utente.setNome(rs.getString("Nome"));

                    //se l'utente del DB è uguale al nome inserito:
                    if (utente.getUsername().equalsIgnoreCase(user)){
                        if (utente.getPassword().equals(sha1)){
                            credenzialiValide=true;
                        }
                    }


                    //visualizzo sulla console
                    System.out.println("Utente: " + utente);
                    //visualizzo sulla pagina web dell'interlocutore
                    //toString richiama il metodo dentro a "Utente"
                    utenti += "<li>" + utente.toString("<br>") + "</li>";

                }
                rs.close(); //va chiuso resultSet




                // visualizzazione
                htmlResponse = "" +
                        "<html>" +
                        "<body>" +
                        "<h1>" + httpExchange.getRequestMethod() + " - Eseguito Login Test-1 App con credenziali: <h1>" +
                        "user: " + user + "<br>" +
                        "pass: " + pass + "<br>" +
                        //viualizzo la password cifrata
                        "SHA-1: " + sha1 + "<br>" +
                        //condizione: se credenziali valide inerisci testo
                        (credenzialiValide ? "<h1> Accesso Consentito</h1>" : "<h1style= 'color:red'> Accesso Negato </h1>")+
                        "utenti: " + utenti + "<br>" +
                        //visualizziamo anche i dati dell'utente
                        "</body>" +
                        "</html>";

            } catch (Exception ex) {
                //mostra il messaggio di errore su html
                ex.printStackTrace();
                htmlResponse = "" +
                        "<html>" +
                        "<body>" +
                        "<h1> Error: " + ex.getMessage() + "</h1>" +
                        "</body>" +
                        "</html>"
                ;
            }
        }
        //intestazione risposta:
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        //corpo risposta:
        OutputStream outputStream = httpExchange.getResponseBody();
        //scrive in body
        outputStream.write(htmlResponse.getBytes()/*trasforma caratteri di stringa in vettore byte*/);
        //svuotamento buffer
        outputStream.flush();
        //chiusura canale
        outputStream.close();
    }

}