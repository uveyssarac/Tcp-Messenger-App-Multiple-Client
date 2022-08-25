package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFrame;

public class client{

	MultiClients ClientThread;
	public static void main(String[] args) {
		new client();
	}
	public client()
	{	
		arayuz crape = new arayuz();
		crape.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		crape.setSize(500,600);
		crape.setVisible(true);
		
	}
	
}
