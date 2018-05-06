/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_test1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user-pc
 */
public class Test {
    public static void main(String[ ] agrs) throws IOException {
    //    Client client = new Client("localhost", 5000);
        File file = new File("E:/vat li/Hello.txt");
    //    File file1 = new File
        System.out.println("parent" + file.getParent() + file.getName());
        try {
            RandomAccessFile acc = new RandomAccessFile(file, "r");
            acc.seek(2);
            byte[ ] buffer = new byte[20];
            long lengthFile =  file.length();
            System.out.println("length file " + lengthFile);
            long totlaRead = 0;
            long length = 50;
            while(totlaRead < length) {
                int read = 0;
                System.out.println("pointer" + acc.getFilePointer());
                if((length - totlaRead) < buffer.length)
                    read = acc.read(buffer, 0, (int) (length -totlaRead));
                else
                   read = acc.read(buffer);
                System.out.println("read " + read);
                totlaRead += read;
            //    acc.close();
            }
            System.out.println("totalRead" + totlaRead);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
