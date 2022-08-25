package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServerConnection extends Thread {

    Socket s;
    DataInputStream sInput;
    DataOutputStream sOutput;
    server ss;
    boolean quite = false;

    public MultiServerConnection(Socket OurSocket, server OurServer) {
        super("MultiServerConnection");//server connection thread
        this.s = OurSocket;
        this.ss = OurServer;
    }

    public void ServerOutClientIn(String OutText) {
        try {
            long ThreadID = this.getId();
            sOutput.writeUTF(OutText);
            sOutput.flush();//buffer error olmaması için
        } catch (IOException e) {
        }
    }

    public void ServerOutAllClientIn(String OutText) {
        for (int i = 0; i < ss.OurDomainsConnections.size(); i++) {
            MultiServerConnection Connection = ss.OurDomainsConnections.get(i);
            Connection.ServerOutClientIn(OutText);
        }
    }

    public void run() {
        try {
            sInput = new DataInputStream(s.getInputStream());
            sOutput = new DataOutputStream(s.getOutputStream());

            while (!quite) {
                while (sInput.available() == 0) {
                    try {
                        Thread.sleep(1);//sleep if there is not data coming
                    } catch (InterruptedException e) {

                    }
                }
                String ComingText = sInput.readUTF();
                ServerOutAllClientIn(ComingText);
            }
            sInput.close();
            sOutput.close();
            s.close();
        } catch (IOException e) {
        }
    }
}
