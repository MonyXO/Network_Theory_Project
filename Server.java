// A Java program for a Server
import java.net.*;
import java.io.*;
public class Server
{
    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    public static char[] strToArray(String str) {
        char[] ch = str.toCharArray();
        return ch;
    }

    public static char[] swap3_6(String str) {

        int bit_3 = 2;
        int bit_6 = 5;

        char ch[] = str.toCharArray();
        char temp = ch[bit_3];
        ch[bit_3] = ch[bit_6];
        ch[bit_6] = temp;
        return ch;
    }

    public static char[] swap4_5(String str) {

        int bit_4 = 3;
        int bit_5 = 4;

        char ch[] = str.toCharArray();
        char temp = ch[bit_4];
        ch[bit_4] = ch[bit_5];
        ch[bit_5] = temp;
        return ch;
    }

    public static String toString(char[] data) {
        String result = "";
        StringBuilder sb = new StringBuilder(result);
        for(int i = 0; i < data.length; i++){
            sb = new StringBuilder();
            sb.append(data);
        }
        result = sb.toString();
        return result;
    }

    public static char[] rightRotate(char[] arr, int d) {
        int n = arr.length;
        while (d > n) {
            d = d - n;
        }
        char[] temp = new char[n - d];
        for(int i = 0; i < n - d; i++) {
            temp[i] = arr[i];
        }

        for(int i = n - d; i < n; i++) {
            arr[i - n + d] = arr[i];
        }

        for (int i = 0; i < n - d; i++) {
            arr[i + d] = temp[i];
        }
        return arr;
    }

    public static char[] flipBits(char[] arr) {
        int n = arr.length;
        char[] b = new char[n];
        int j = n;
        for(int i = 0; i < n; i++) {
            b[j - 1] = arr[i];
            j = j - 1;
        }
        return b;
    }

    public static String binaryInt(int value) {
        
        int dec= value;
        String result= "00000000";
        int i=result.length()-1;
        while(dec!=0)
        {
            char a[]=result.toCharArray();
            a[i--]= String.valueOf(dec%2).charAt(0);
            result=new String(a);
            dec=dec/2;  
        }
        return result; 
    }

    public static String decryptText(String str) {
        StringBuilder sb = new StringBuilder();
        String binary = "";
        char[] arr;
        for(int i = 0; i < str.length(); i++) {
            // get ascii value of character
            int ascii = (int) str.charAt(i);
            
            // subtract 25 from ascii
            ascii = ascii - 25;
            if(ascii < 0) {
                ascii = ascii + 256;
            }

            // convert to binary string
            binary = binaryInt(ascii);
            
            // swap bits 4 and 5
            char[] swapped4_5 = swap4_5(binary);
            String swapped = toString(swapped4_5);

            // swap bits 3 and 6
            char[] swapped3_6 = swap3_6(swapped);

            // flip bits
            char[] flipped = flipBits(swapped3_6);

            // rotate right 3
            char[] rotated = rightRotate(flipped, 3);
            
            // convert back binary
            String binaryF = toString(rotated);
            
            // convert to encrypted ascii
            int finalAscii = Integer.parseInt(binaryF, 2);
            char finalChar = (char) finalAscii;
            
            // add it to a string
            sb.append(finalChar);
            //System.out.println(binary + " = " + str.charAt(i) + " = " + result + " = " + Integer.parseInt(result, 2) + " = " + flipped + " = " + Integer.parseInt(flipped, 2) + " = " + swapped + " = " + asciiConvert + " = " + asciiSub + " = " + binaryF + " = " + finalString + " = " + Integer.parseInt(finalString, 2));
        }
        String encryption = sb.toString();
        return encryption;
    }

    // constructor with port
    public Server(int port) throws EOFException
    {

        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");
            System.out.println("Decrypting file: \n");
            
            // takes input from the client socket
            InputStream ifs = socket.getInputStream();
            in = new DataInputStream(ifs);
            String line = "";
            String decryption = "";
            File file = new File("Decrypted_Text.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            // reads message from client until "Over" is sent
            while (!decryption.equals("Over"))
            {
                //System.out.println(line);
                line = in.readUTF();
                decryption = decryptText(line);
                bw.write(decryption);
                bw.newLine();
                System.out.println(decryption);
            }
            
            bw.close();
            System.out.println("File saved and closed.");
            System.out.println("\nClosing connection");
            // close connection;
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i + "error");
        }
    }
    
    public static void main(String args[]) throws EOFException
    {
        Server server = new Server(5000);
    }
}