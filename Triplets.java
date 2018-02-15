import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Triplets {

    static int[] solve(int a0, int a1, int a2, int b0, int b1, int b2){
        int A = 0;
        int B = 0;
        
        int[] Ar = new int []{a0,a1,a2}; 
        int[] Br = new int []{b0,b1,b2};
        int[] Ans = new int []{0,0};
        
        for (int i=0; i < Ar.length; i++){
            if (Ar[i]>Br[i]){
                A++;
            }
            else if (Ar[i]==Br[i]){
                continue;
            }
            else {
                B++;
            }
        }
        Ans[0] = A;
        Ans[1] = B;
        
        return Ans;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a0 = in.nextInt();
        int a1 = in.nextInt();
        int a2 = in.nextInt();
        int b0 = in.nextInt();
        int b1 = in.nextInt();
        int b2 = in.nextInt();
        int[] result = solve(a0, a1, a2, b0, b1, b2);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + (i != result.length - 1 ? " " : ""));
        }
        System.out.println("");
        

    }
}
