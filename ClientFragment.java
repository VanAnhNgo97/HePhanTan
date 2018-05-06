/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_test1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user-pc
 */
public class ClientFragment implements Runnable{
    
    private long offset;
    private long length;
    private File file;
    private RandomAccessFile accessFile;
    private Thread fragmentThread;
    private boolean flag; // true if send, false if receive
    private Socket socket;
    private DataInputStream din;
    private DataOutputStream outToServer;
    private int index;
    
    public ClientFragment(File iniFile, long iniOffset, long iniLength, 
            boolean  iniFlag, Socket iniSocket, int iniIndex) {
        file = iniFile;
        offset = iniOffset;
        length = iniLength;
        flag = iniFlag;
        index = iniIndex;
        try {
            iniAccessFile();
            din = new DataInputStream(socket.getInputStream());
            outToServer = new DataOutputStream(socket.getOutputStream());
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientFragment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    ClientFragment(File workingFile, long iniOffset, long iniLength, boolean b, long iniOffset0, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void iniAccessFile() throws FileNotFoundException {
        if(this.flag) {
            try {
                accessFile = new RandomAccessFile(file, "r");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ClientFragment.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            accessFile = new RandomAccessFile(file, "w");
        }
    }
    public void start() {
        if(fragmentThread == null) {
            fragmentThread = new Thread(this);
            fragmentThread.start();
        }
    }
    public void sendFragment() {
        try {
            this.accessFile.seek(offset);
            int bufferLength = 1024;
            byte[ ] buffer = new byte[bufferLength];
            int totalRead = 0;
            while(totalRead < length) {
                int read = 0;
                if((length - totalRead) > bufferLength)
                    read = accessFile.read(buffer);
                else 
                    read = accessFile.read(buffer, 0, (int) (length - totalRead));
                totalRead += read;
                outToServer.write(buffer, 0, read);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientFragment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void receiveFragment() {
        
    }
    public void setOffset(long off) {
        this.offset = off;
    }
    public long getOffset() {
        return this.offset;
    }
    public int getIndex() {
        return this.index;
    }
    @Override
    public void run() {
        if(flag) {
            sendFragment();
        } else {
            receiveFragment();
        }
    }
    
}
