package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiClients extends Thread {

    Socket s;
    DataInputStream sInput;
    DataOutputStream sOutput;
    boolean quite = false;
    public ClientData c;
    public arayuz GUI;

    public MultiClients(Socket cokluSoket, arayuz gui) {
        s = cokluSoket;
        c = new ClientData();
        GUI = gui;
    }

    public void ClientOutServerIn(String Text) {
        try {
            if (Text.equals("change channel")) {
                sOutput.writeUTF(Text);
                sOutput.flush();
            } else if (Text.equals("new user")) {
                sOutput.writeUTF(Text + ":" + c.GetName() + "=" + c.GetChannel());
                sOutput.flush();
            } else {
                sOutput.writeUTF(c.GetChannel() + "=" + this.getName() + ": " + Text);
                sOutput.flush();
            }
        } catch (IOException e) {
        }
    }

    public void SetClient(String channel, String Name) {
        c.SetName(Name);
        c.SetChannel(channel);
    }

    public void run() {
        try {
            sInput = new DataInputStream(s.getInputStream());
            sOutput = new DataOutputStream(s.getOutputStream());
            while (!quite) {
                try {
                    while (sInput.available() == 0) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                        }
                    }
                    String reply = sInput.readUTF();
                    String chn = channelBul(reply);
                    String name = ismiBul(reply);

                    if (name.equals("new user")) {
                        setChannel(reply);
                    } else {
                        cevapYazdir(chn, reply);
                    }
                } catch (IOException e) {
                    try {
                        sInput.close();
                        sOutput.close();
                        s.close();
                    } catch (IOException e1) {
                    }
                }
            }
        } catch (IOException e) {
            try {
                sInput.close();
                sOutput.close();
                s.close();
            } catch (IOException x) {
            }
        }
    }
    public void cevapYazdir(String chn, String Rep) {
        if (c.GetChannel().equals(chn)) {
            String[] Y = Rep.split("=");
            GUI.setDisplay(Y[1]);
        }
    }
    public String ismiBul(String x) {
        String[] Y = x.split(":");
        return Y[0];
    }

    public String channelBul(String X) {
        String[] Y = X.split("=");
        return Y[0];
    }

    public void setChannel(String x) {
        String[] Y = x.split(":");
        String[] Z = Y[1].split("=");
        GUI.setUserInChannel(Z[0]);
    }

    public void setChangedChannel() {
        GUI.setUserInChannel(c.GetName() + ": " + c.GetChannel());
    }

    class ClientData {

        public String ClientName;
        public String channel;

        public void SetChannel(String chn) {
            channel = chn;
        }

        public void SetName(String name) {
            ClientName = name;
        }

        public String GetChannel() {
            return channel;
        }

        public String GetName() {
            return ClientName;
        }
    }

}
