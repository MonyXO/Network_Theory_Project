import java.util.Scanner;

public class Demo {

    public static char[] strToArray(String str) {
        char[] ch = str.toCharArray();
        return ch;
    }

    public static char[] swap(String str) {

        int bit_3 = 2;
        int bit_6 = 5;

        char ch[] = str.toCharArray();
        char temp = ch[bit_3];
        ch[bit_3] = ch[bit_6];
        ch[bit_6] = temp;
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

    public static String binaryInt(char value) {
        
        int dec=(int) value;
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
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter A character value: ");
        char input = sc.next().charAt(0);
        int ascii = (int) input;

        String result = binaryInt(input);
        char[] swapped1 = swap(result);
        char[] swapped2 = swap(result);
        //char[] charArr = new char[8];
        //char ch = 'a';
        System.out.println(ascii);
        System.out.println(Integer.toBinaryString(ascii));
        System.out.println("Original: " + result);
        System.out.println("Swapped: " + toString(swapped1));
        System.out.println("Flipped: " + toString(flipBits(swapped1)));
        //System.out.println("Rotated: " + toString(leftRotate(swapped1, 3)));
        //System.out.println("Rotated: " + toString(rightRotate(swapped2, 1)));
        System.out.println(Integer.parseInt(toString(swapped1), 2));
    }
}
