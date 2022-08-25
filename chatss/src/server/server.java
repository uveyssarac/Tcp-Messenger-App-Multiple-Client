package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class server {

    ServerSocket ss;
    boolean quite = false;
    ArrayList<MultiServerConnection> OurDomainsConnections = new ArrayList<MultiServerConnection>();

    public static void main(String[] args) {
        new server();
    }

    public server() {
        try {
            ss = new ServerSocket(6000);//port numarasi
            while (!quite) {
                Socket s = ss.accept();//domain ile bir bağlantı bulunduğunda bunu kabul ediyoruz
                MultiServerConnection OurConnection = new MultiServerConnection(s, this);
                OurConnection.start();//Start Thread
                OurDomainsConnections.add(OurConnection);//Domain Bağlantımıza bağlantı ekleyin
            }
        } catch (IOException e) {

        }
    }
}
