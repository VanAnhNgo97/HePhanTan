/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_test1;

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
public class Server implements Runnable{
    private Socket clientSocket;
    private String rootDirectory; 
    private DataInputStream inFromClient;
    private DataOutputStream outToClient;
    
    public Server(Socket s, String iniRoot) {
        try {
            clientSocket = s;
            rootDirectory = iniRoot;
            inFromClient = new DataInputStream(clientSocket.getInputStream());
            outToClient = new DataOutputStream(clientSocket.getOutputStream());
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        
    }
    public void send() {
        
    }
    
    public void receive() {
        String fileName;
        long fileSize = 0;
        try {
            fileName = inFromClient.readUTF();
            System.out.println("fileName" + fileName);
            File receiveFile = new File(rootDirectory + "/" + fileName);
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @Override
    public void run() {
        String command;
        
        while(true) {
            try {
               command = inFromClient.readUTF();
                if(command.equals("UPLOAD")) {
                    receive();
                    
                } else if(command.equals("DOWNLOAD")) {
                    send();
                }
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            
        }
    }
    
}
