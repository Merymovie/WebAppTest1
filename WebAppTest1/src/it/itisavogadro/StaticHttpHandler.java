package it.itisavogadro;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//import org.omg.CORBA.portable.OutputStream;

public class StaticHttpHandler implements HttpHandler {

    @Override

    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("HTTP request Static Context");

        String htmlResponse;

        try {
            System.out.println("HTTP request STUDENTI");
            System.out.println("\nURI: " + httpExchange.getRequestURI()
                    + "\nmethod:" + httpExchange.getRequestMethod()
                    + "\ncontext-path: " + httpExchange.getHttpContext().getPath()
                    + "\npath: " + httpExchange.getRequestURI().getPath()
                    // poter vedere sulla console la richiesta e i parametri
                    + "\nquery: " + httpExchange.getRequestURI().getQuery() + "");
            //la risposta che arriva deve essere una get altrimenti è errore
            if (!"GET".equalsIgnoreCase(httpExchange.getRequestMethod())) {
                //stampa errore per utente
                htmlResponse = "<html><body><h1>" + httpExchange.getRequestMethod()
                        + " - OPERAZIONE CON METHOD NON GESTITO</h1>"
                        + "</body>"
                        + "</html>";
                httpExchange.sendResponseHeaders(200, (long) htmlResponse.length());
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(htmlResponse.getBytes());
                outputStream.flush();
                outputStream.close();
                return;
            }
            // i file static devono essere richiesti tramite get
            String staticFilePath = httpExchange.getRequestURI().getPath();
            //getResource va a cercare un percorso che si chiama come il percorso statico fatto
            URL resourceURL = this.getClass().getResource(staticFilePath);
            //se manca viene generata una Exception altrimenti:
            if (resourceURL == null) {
                throw new RuntimeException("Pagina mancante.");
            } else {
                //diventa un percorso file
                Path path = Paths.get(resourceURL.toURI());
                //legge tutti i byte dentro la risorsa e li mette in una stringa
                htmlResponse = new String(Files.readAllBytes(path));
                //se è stato scritto correttamente viene mandato in output staticamente
                httpExchange.sendResponseHeaders(200, 0L);
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(htmlResponse.getBytes());
                outputStream.flush();
                outputStream.close();
            }
            // viene segnalato l'errore in modalità dinamica html
    } catch(Exception ex) {
            ex.printStackTrace();
            htmlResponse = "<html><body><h1>ERRORE:</h1><h1>" + ex.getMessage()
                    + "</h1>" + "</body>" + "</html>";
            httpExchange.sendResponseHeaders(200, (long) htmlResponse.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(htmlResponse.getBytes());
            outputStream.flush();
            outputStream.close();
    }


}

}
