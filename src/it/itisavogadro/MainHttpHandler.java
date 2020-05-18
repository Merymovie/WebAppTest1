package it.itisavogadro;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

//import org.omg.CORBA.portable.OutputStream;

public class MainHttpHandler implements HttpHandler {

    @Override

    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("HTTP request Generic Context");

        //

        // Sul canale di risposta carica nell'elemento intestazione la location del messaggio di errore
        // risponde 302 che significa che il contenuto genera errore
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Location", "/static/error.html");
        //302 significa che il contenuto è mancante
        httpExchange.sendResponseHeaders(302, 0L);


    }

}
