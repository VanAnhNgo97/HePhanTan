/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_test1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author user-pc
 */
public class Client {

    private Socket clientSocket;
    private String serverIP;
    private int port;
    private DataInputStream client_din; 
    private DataOutputStream client_outToServer;
    public Client(String iniServerIp, int iniPort) {
        try {
            serverIP = iniServerIp;
            port = iniPort;
            clientSocket = new Socket(serverIP, port);
            client_din = new DataInputStream(clientSocket.getInputStream());
            client_outToServer = new DataOutputStream(clientSocket.getOutputStream());
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }
    public void send(String fileName) {
        try{
            File sendFile = new File(fileName);
            client_outToServer.writeUTF("SEND");
            ClientFileTransfer transfer = new ClientFileTransfer(sendFile, serverIP, port);
            client_outToServer.writeUTF(fileName);

            transfer.sendFile();
            transfer.start();
        } catch (IOException ex) {
            
        }
        
    }
    public void receive(String fileName) {
        
    }
    
}
