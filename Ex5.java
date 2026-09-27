import java.util.HashSet;
import java.util.Scanner;

public class DiffieHellman {

    public static long power(long base, long exp, long mod) {
        long res = 1;
        base = base % mod;
        while (exp > 0) {
            if (exp % 2 == 1) {
                res = (res * base) % mod;
            }
            base = (base * base) % mod;
            exp /= 2;
        }
        return res;
    }

    public static boolean isPrimitiveRoot(long a, long q) {
        if (a <= 0 || a >= q) return false;

        HashSet<Long> values = new HashSet<>();
        for (long i = 1; i < q; i++) {
            long val = power(a, i, q);
            if (values.contains(val)) {
                return false;
            }
            values.add(val);
        }
        return values.size() == q - 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Step 1: Enter a prime number (q): ");
        long q = sc.nextLong();

        System.out.print("Step 2: Enter a primitive root (a): ");
        long a = sc.nextLong();
        if (!isPrimitiveRoot(a, q)) {
            System.out.println("Error: " + a + " is not a primitive root of " + q);
            return;
        }

        System.out.print("Step 3: Enter private key for User A (XA < " + q + "): ");
        long xA = sc.nextLong();
        if (xA >= q) {
            System.out.println("Error: XA must be less than q.");
            return;
        }

        long yA = power(a, xA, q);
        System.out.println("Step 4: Calculated Public Key for User A (YA) = " + yA);

        System.out.print("Step 5: Enter private key for User B (XB < " + q + "): ");
        long xB = sc.nextLong();
        if (xB >= q) {
            System.out.println("Error: XB must be less than q.");
            return;
        }

        long yB = power(a, xB, q);
        System.out.println("Step 6: Calculated Public Key for User B (YB) = " + yB);

        long kA = power(yB, xA, q);
        System.out.println("Step 7: Secret Key for User A (K) = " + kA);

        long kB = power(yA, xB, q);
        System.out.println("Step 8: Secret Key for User B (K) = " + kB);

        if (kA == kB) {
            System.out.println("\nSuccess: Both secret keys match! Symmetric Key = " + kA);
        } else {
            System.out.println("\nFailure: Secret keys do not match.");
        }

        sc.close();
    }
}