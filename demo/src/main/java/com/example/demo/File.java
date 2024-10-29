package com.example.demo;
import java.io.*;


public class File {


    public File(String file) {

    }

    public static void findFile() throws IOException {

            // code that may generate IOException
            File file = new File("test.txt");
            FileInputStream fileInputStream = new FileInputStream(file.toString());

            throw new IOException();
        }

        public static void main(String[] args) {
            try {
                findFile();
            }
            catch (IOException i) {
                System.out.println(i);
            }
        }

}
