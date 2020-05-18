package it.itisavogadro;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

//import org.omg.CORBA.portable.OutputStream;

public class StudentiHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("HTTP request Studenti");
        //Context studenti
        //la URI sarà /studenti/azione da eseguire
        //quello che segue il prefisso del contex lo uso come selettore
        String azione = httpExchange.getRequestURI().getPath().replace("/studenti", "");

        String htmlResponse = "";
        if("GET".equalsIgnoreCase(httpExchange.getRequestMethod())){
            switch (azione){
                case "Elenco":
                    elencoStudenti(httpExchange);
                    break;
                case "Nuovo":
                    aggiungiStudente(httpExchange);
                    break;
                case "Modifica":
                    modificaStudente(httpExchange);
                    break;
                case "Cancella":
                    cancellaStudente(httpExchange);
                    break;
                default:
                    htmlResponse = "" +
                            "<html>" +
                            "<body>" +
                            "<h1>L'operazione "+azione+" non è gestita</h1>" +
                            "</body>" +
                            "</html>";
                    httpExchange.sendResponseHeaders(200, htmlResponse.length());
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.write(htmlResponse.getBytes());
                    outputStream.flush();
                    outputStream.close();
                    return;
            }
            return;
        }
    }

    private void elencoStudenti(HttpExchange httpExchange) throws IOException{
        String htmlResponse = "" +
                "<html>" +
                "<body>" +
                "<h1>" + httpExchange.getRequestMethod() +" - Elenco Studenti </h1>" +
                "</body>" +
                "</html>" ;
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
        return;
    }

    private void aggiungiStudente(HttpExchange httpExchange) throws IOException{
        String htmlResponse = "" +
                "<html>" +
                "<body>" +
                "<h1>" + httpExchange.getRequestMethod() +" - Aggiungi Studente </h1>" +
                "</body>" +
                "</html>" ;
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
        return;
    }

    private void modificaStudente(HttpExchange httpExchange) throws IOException{
        String htmlResponse = "" +
                "<html>" +
                "<body>" +
                "<h1>" + httpExchange.getRequestMethod() +" - Modifica Studente </h1>" +
                "</body>" +
                "</html>" ;
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
        return;
    }


    private void cancellaStudente(HttpExchange httpExchange) throws IOException{
        String htmlResponse = "" +
                "<html>" +
                "<body>" +
                "<h1>" + httpExchange.getRequestMethod() +" - Cancella Studente </h1>" +
                "</body>" +
                "</html>" ;
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
        return;
    }

}
