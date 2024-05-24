package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class BookScrabbleHandler implements ClientHandler{

    private BufferedReader reader;
    private PrintWriter writer;

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        reader = new BufferedReader(new InputStreamReader(inFromclient));
        writer = new PrintWriter(outToClient,true);      
        
        try {
            String line = reader.readLine();
            if (line == null) {
                return;
            }

            String[] parts = line.split(",");
            String command = parts[0].trim(); 
            String[] books = new String[parts.length - 1];
            System.arraycopy(parts, 1, books, 0, parts.length-1);
            boolean result = false;
            if (command.equals("Q"))
            {
                result = DictionaryManager.get().query(books);
            }
            else if (command.equals("C"))
            {
                result = DictionaryManager.get().challenge(books);
            }

            writer.println(result ? "true" : "false");


        } catch (IOException e) {
            e.printStackTrace();
        }
        close();

    }
    
    @Override
    public void close() {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
