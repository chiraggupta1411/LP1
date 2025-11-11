import java.util.Scanner;

public class library {
    // Declare native methods
    public native int add(int a, int b);
    public native int subtract(int a, int b);
    public native int multiply(int a, int b);
    public native double divide(int a, int b);

 
    static {
        System.loadLibrary("library");
    }

    public static void main(String[] args) {
        library ml = new library();
        Scanner sc=new Scanner(System.in);
        System.out.println("enter 1 no:");
        int a=sc.nextInt();
        System.out.println("enter 2 no:");
        int b=sc.nextInt();
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("Addition: " + ml.add(a, b));
        System.out.println("Subtraction: " + ml.subtract(a, b));
        System.out.println("Multiplication: " + ml.multiply(a, b));
        System.out.println("Division: " + ml.divide(a, b));
    }
}
