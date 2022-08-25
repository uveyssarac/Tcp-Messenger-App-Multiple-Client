package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class arayuz extends JFrame {

    private JTextField msg;
    private JTextField username;
    private JTextField chnNumber;
    private JTextArea display;
    private JTextArea UserNames;
    private Container c;
    MultiClients ClientThread;
    private JLabel label;
    private JLabel label1;
    private JLabel label2;

    public arayuz() {

        setLayout(new FlowLayout());

        display = new JTextArea(30, 30);
        JScrollPane scrollPane = new JScrollPane(display);
        display.setEditable(false);

        add(scrollPane);

        UserNames = new JTextArea(30, 10);
        JScrollPane scrollPane3 = new JScrollPane(UserNames);
        UserNames.setEditable(false);

        add(scrollPane3);

        msg = new JTextField(20);
        msg.setEditable(false);
        add(msg);

        username = new JTextField(20);
        username.setEditable(true);
        add(username);
        
        chnNumber = new JTextField(20);
        chnNumber.setEditable(false);
        add(chnNumber);
        
        label2 = new JLabel("mesaj");
        label = new JLabel("channel number");
        label1 = new JLabel("İsim");

        
        c = this.getContentPane();
        c.setLayout(new FlowLayout());
        c.add(label1);
        c.add(username);

        

        c = this.getContentPane();
        c.setLayout(new FlowLayout());
        c.add(label);
        c.add(chnNumber);

        thehandler handler = new thehandler();
        msg.addActionListener(handler);
        username.addActionListener(handler);
        chnNumber.addActionListener(handler);
        try {
            Socket s = new Socket("localhost", 6000);
            ClientThread = new MultiClients(s, this);
            ClientThread.start();
        } catch (UnknownHostException e) {

        } catch (IOException e) {

        }
    }

    private class thehandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            String string = "";

            if (event.getSource() == msg) {
                string = String.format("%s", event.getActionCommand());
                String text = msg.getText();
                ClientThread.ClientOutServerIn(text);
                msg.setText("");
            } else if (event.getSource() == username) {
                string = String.format("%s", event.getActionCommand());
                if (string.matches("[0-9]*")) {
                    JOptionPane.showMessageDialog(null, "izin verilmeyen format");
                    username.setText("");
                } else {
                    ClientThread.setName(string);
                    ClientThread.SetClient("channel0", string);
                    username.setText("");
                    username.setEditable(false);
                    msg.setEditable(true);
                    chnNumber.setEditable(true);
                    ClientThread.ClientOutServerIn("new user");
                    label1.setVisible(false);
                }
            } else if (event.getSource() == chnNumber) {
                string = String.format("%s", event.getActionCommand());
                if (string.matches("[a-z A-Z]")) {
                    JOptionPane.showMessageDialog(null, "Lütfen sayı yada rakam giriniz");
                    chnNumber.setText("");
                } else {
                    ClientThread.c.SetChannel("channel" + string);
                    JOptionPane.showMessageDialog(null, "Channel degistirildi: channel " + string);
                    chnNumber.setText("");
                    ClientThread.ClientOutServerIn("change channel");
                }
            }
        }
    }

    public void setDisplay(String x) {
        display.append(x + "\n");
    }

    public void setUserInChannel(String x) {
        UserNames.append(x + "\n");
    }
    public void ClearDisplay() {
        UserNames.setText("");
    }
}
