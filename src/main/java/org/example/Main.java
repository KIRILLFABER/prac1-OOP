package org.example;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        try{
            str = URLEncoder.encode(str, "UTF-8");
            str = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=" + str;
        }catch (Exception e){
            System.out.println("Error" + e.toString());
        }


        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try{
            URL url = new URL(str);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();

                File file = new File("test.json");
                outputStream = new FileOutputStream(file);
                int byteRead = -1;
                byte[] buffer = new byte[2048];
                while((byteRead = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, byteRead);
                }
            }
        }catch(IOException e){
            System.out.println("ERROR " + e.toString());
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e){}

        }


        Parser parser = new Parser();
        Root result = parser.parse();
        for(int i = 0 ; i < result.query.search.size(); i++){
            System.out.printf("%d - %s\n", i, result.query.search.get(i).title);
        }
        int command = scan.nextInt();
        if (command < result.query.search.size()){
            try{
                URI page = new URI("https://ru.wikipedia.org/w/index.php?curid=" + result.query.search.get(command).pageid);
                java.awt.Desktop.getDesktop().browse(page);
            }catch (Exception e){
                System.out.println("ERROR " + e.toString());
            }




        }
        else{
            System.out.println("ERROR");
        }




    }
}