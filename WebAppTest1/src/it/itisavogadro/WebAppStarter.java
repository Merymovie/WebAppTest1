package it.itisavogadro;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class WebAppStarter {

    public static void main(String[] args) {
        int port = 9999;
    //web server generico
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress/*ascolto su indirizzo e porta -->*/("Localhost"/*stesso pc, altrimenti IP*/, port), 0);

            server.createContext("/", new MainHttpHandler()); // prende tutto ciò che non è esplicito per evitare messaggi di errore
            server.createContext/* contesto*/( "/test-1", new Test1HttpHandler());//classe che gestisce la richiesta del client
            server.createContext/* contesto*/( "/login", new LoginHttpHandler());//classe che gestisce la richiesta del client
            server.createContext/* contesto*/( "/cambiopassword", new CambioPasswordHttpHandler());//classe gestisce il cambio password
            server.createContext/* contesto*/( "/static", new StaticHttpHandler());//classe gestisce il cambio password
            //parallelizzazione delle richieste:
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10/*numero richieste parallelo*/);

            //pool = insieme elementi stesso tipo

            //aggancio pool a server:
            server.setExecutor(threadPoolExecutor);
            //start servizio
            server.start();

            System.out.println("Server started on port "+port);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
