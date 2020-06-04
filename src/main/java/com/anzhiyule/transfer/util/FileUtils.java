package com.anzhiyule.transfer.util;

public class FileUtils {

    public static String extension(String filename) {
        int index = filename.lastIndexOf('.');
        return filename.substring(index + 1);
    }

    //@ Example
    public static void main(String[] args) {
        System.out.println(extension("aaa.zip"));
    }
}
