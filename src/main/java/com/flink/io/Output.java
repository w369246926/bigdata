package com.flink.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Output {


    private void fileoutput(byte[] data, String Time_stamp) throws IOException {
        String path = "D:\\down\\'"+ Time_stamp +"'";
        File f=new File(path);
        FileOutputStream out=new FileOutputStream(f);
        ObjectOutputStream objwrite=new ObjectOutputStream(out);
        objwrite.writeObject(data);
        objwrite.flush();
        objwrite.close();
    }
}
