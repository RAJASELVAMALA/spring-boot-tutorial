package com.example.demo;

public class JavaException {

    public static void main(String[] args) {

        try {


//
//            int a[] = null;
//            System.out.println("a : " + a[2]);

            try {
                int[] b = {1, 2, 3};
                System.out.println(b[3]);

//                            System.out.println("a : " + b[2]/0);

            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                System.out.println("ArrayIndexOutOfBoundsException : " + arrayIndexOutOfBoundsException);

            }
        }


        catch (ArithmeticException a) {
            System.out.println("ArithmeticException  :  " + a.getMessage());
            System.err.println("ArithmeticException  :  " + a.getMessage());

        }
        catch (NullPointerException n) {
            System.out.println("NullPointerException  :  " + n.getMessage());

        }

        catch (Exception e) {
            System.out.println("Exception  :  " + e.getMessage());

        }

        finally {
            System.out.println("This is the finally block always executed no matter whether there is an exception or not.");
        }
    }
}
