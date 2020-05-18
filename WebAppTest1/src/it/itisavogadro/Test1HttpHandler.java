package it.itisavogadro;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
//import org.omg.CORBA.portable.OutputStream;

import java.io.OutputStream;
import java.io.IOException;
import java.util.function.DoubleToIntFunction;

public class Test1HttpHandler implements HttpHandler {

    @Override

    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("HTTP request TEST1");

        System.out.println(
                "\nURI:"+httpExchange.getRequestURI()+ //indirizzo completo richiesta
                        "\nmethod:"+httpExchange.getRequestMethod()+ //metodo (get,post,put,delete)
                        "\ncontext-path:"+httpExchange.getHttpContext().getPath()+ //percorso attivazione contesto
                        "\npath:"+httpExchange.getRequestURI().getPath()+ // percorso completo
                        "\nquery:"+httpExchange.getRequestURI().getQuery()+ // elenco parametri specificati dopo il percorso e che vengono attivati con ?
                        // es--> percorso?parametro1=valore1&parametro2=val2
                        ""
        );

        //risposta web in codice html:
    String htmlResponde = ""+
            "<html>"+
            "<body>"+
            "<h1>"+httpExchange.getRequestMethod()+" - Accedi alla WebApp!</h1>"+
            "<p1>Per eseguire login clicca <a href=\"/login\">QUI</a></p1><br>"+
            "<h1>"+httpExchange.getRequestMethod()+" - Cambia la password</h1>"+
            "<p1>Per cambiare la password clicca <a href=\"/cambiopassword\">QUI</a></p1>"+
            "</body>"+
            "</html>"
            ;
                   //intestazione risposta:
        httpExchange.sendResponseHeaders(200, htmlResponde.length());
        //corpo risposta:
        OutputStream outputStream = httpExchange.getResponseBody();
        //scrive in body
        outputStream.write(htmlResponde.getBytes()/*trasforma caratteri di stringa in vettore byte*/);
        //svuotamento buffer
        outputStream.flush();
        //chiusura canale
        outputStream.close();
    }

}
