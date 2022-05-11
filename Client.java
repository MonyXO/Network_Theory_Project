// A Java program for a Client
import java.net.*;
import java.io.*;

public class Client
{
    // initialize socket and input output streams
    private Socket socket = null;
    private FileInputStream inputFile = null;
    private DataOutputStream out = null;
    private BufferedReader br = null;

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

    public static char[] leftRotate(char[] arr, int d) {
        char[] temp = new char[d];
        int n = arr.length;
        for(int i = 0; i < d; i++) {
            temp[i] = arr[i];
        }

        for(int i = d; i < n; i++) {
            arr[i - d] = arr[i];
        }

        for (int i = 0; i < d; i++) {
            arr[i + n - d] = temp[i];
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

    public static String encryptText(String str) {
        StringBuilder sb = new StringBuilder();
        String binary = "";
        char[] arr;
        for(int i = 0; i < str.length(); i++) {
            // get ascii value of character
            int ascii = (int) str.charAt(i);

            // convert to binary string
            binary = binaryInt(ascii);
            
            // convert string to array
            arr = strToArray(binary);
            
            // rotate bits left
            char[] binaryArr = leftRotate(arr, 3);
            String result = toString(binaryArr);
            
            //flip bits
            char[] flippedBinary = flipBits(binaryArr);
            String flipped = toString(flippedBinary);
            
            // swap bits 3 and 6
            char[] swap = swap3_6(flipped);
            String swapped = toString(swap);
            
            // swap bits 4 and 5
            char[] swappedF = swap4_5(swapped);
            
            // binary string
            String finalString = toString(swappedF);
            
            // convert to encrypted ascii
            ascii = Integer.parseInt(finalString, 2);

            // add 25 to ascii
            int finalAscii = ascii + 25;
            if(finalAscii > 255) {
                finalAscii = finalAscii - 256;
            }
            char finalChar = (char) finalAscii;
            
            // add it to a string
            sb.append(finalChar);
            //System.out.println(binary + " = " + str.charAt(i) + " = " + result + " = " + Integer.parseInt(result, 2) + " = " + flipped + " = " + Integer.parseInt(flipped, 2) + " = " + swapped + " = " + asciiConvert + " = " + asciiSub + " = " + binaryF + " = " + finalString + " = " + Integer.parseInt(finalString, 2));
        }
        String encryption = sb.toString();
        return encryption;
    }
    
    // constructor to put ip address and port
    public Client(String address, int port) throws IOException
    {
        
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");
            
            // open input file 
            inputFile = new FileInputStream("message.txt");
            br = new BufferedReader(new InputStreamReader(inputFile));

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println("Error");
        }
        
        // string to read message from input
        // Create file to save encryption
        File file = new File("Encrypted_Text.txt");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        // keep reading until "Over" is input
        for (String line=br.readLine(); line!=null; line=br.readLine()) 
        {
            //System.out.println(line);
            String encrypted = encryptText(line);
            System.out.println(encrypted);
            bw.write(encrypted);
            bw.newLine();
            if (encrypted != null) {
                out.writeUTF(encrypted);
            }
        }

        bw.close();
        System.out.println("file closed");
        
        // close the connection
        try
        {
            inputFile.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
   public static void main(String args[]) throws IOException
    {
        Client client = new Client("127.0.0.1", 5000);
    }
}
