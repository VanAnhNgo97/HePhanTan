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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import testThread.Fragment;

/**
 *
 * @author user-pc
 */
public class ClientFileTransfer implements Runnable{
    private File workingFile;
    private Thread thread;
    private Socket socket;
    private ArrayList<ClientFragment> listFragments;
//    private boolean flag; //true if send, false if receive
    private int part;
    private String serverIP;
    private int port;
    
    public ClientFileTransfer(File iniFile, String iniServerIP, int iniPort) {
    //   socket = iniSocket;
       workingFile = iniFile;
       listFragments = new ArrayList<>();
       serverIP = iniServerIP;
       port = iniPort;
//       flag = iniFlag;
       part = 5;
    }
    
    public void start() {
        if(thread != null){
            thread = new Thread(this);
            thread.start();
        }
    }
    public void receiveFile() {
        
    }
    public void sendFile() {
        long lengthPart = workingFile.length() / part;
        for(int i=0; i < part; i++) {
            long iniOffset = i * lengthPart;
            long iniLength = 0;
            if(i !=  (part-1))
                iniLength = lengthPart - 1;
            else
                iniLength = workingFile.length() - (part - 1) * lengthPart;
            try {
                Socket iniSocket = new Socket(serverIP, port);
            } catch (IOException ex) {
                Logger.getLogger(ClientFileTransfer.class.getName()).log(Level.SEVERE, null, ex);
            }
            ClientFragment frag = new ClientFragment(workingFile, iniOffset, iniLength, true, iniOffset, i);
            listFragments.add(frag);
        }
    }
    @Override
    public void run() {
        for(ClientFragment fr : listFragments) {
            fr.start();
        }
    }
    
}
