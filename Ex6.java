import java.util.Scanner;

public class SHA1Algorithm {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the input string to hash: ");
        String input = scanner.nextLine();

        String hashResult = computeSHA1(input);

        System.out.println("\n--- SHA-1 HASH OUTPUT ---");
        System.out.println("Original Message : " + input);
        System.out.println("Hex Digest (160-bit): " + hashResult);

        scanner.close();
    }

    public static String computeSHA1(String input) {
        byte[] data = input.getBytes();
        long origLenBits = (long) data.length * 8;

        int paddingLen = 0;
        while ((data.length + 1 + paddingLen + 8) % 64 != 0) {
            paddingLen++;
        }

        byte[] paddedData = new byte[data.length + 1 + paddingLen + 8];
        System.arraycopy(data, 0, paddedData, 0, data.length);

        paddedData[data.length] = (byte) 0x80;

        long lenBits = origLenBits;
        for (int i = 0; i < 8; i++) {
            paddedData[paddedData.length - 1 - i] = (byte) (lenBits & 0xFF);
            lenBits >>>= 8;
        }

        int h0 = 0x67452301;
        int h1 = 0xEFCDAB89;
        int h2 = 0x98BADCFE;
        int h3 = 0x10325476;
        int h4 = 0xC3D2E1F0;

        int numChunks = paddedData.length / 64;
        for (int chunk = 0; chunk < numChunks; chunk++) {
            int[] w = new int[80];

            for (int i = 0; i < 16; i++) {
                int idx = chunk * 64 + i * 4;
                w[i] = ((paddedData[idx] & 0xFF) << 24) |
                       ((paddedData[idx + 1] & 0xFF) << 16) |
                       ((paddedData[idx + 2] & 0xFF) << 8) |
                       (paddedData[idx + 3] & 0xFF);
            }

            for (int i = 16; i < 80; i++) {
                w[i] = leftRotate(w[i - 3] ^ w[i - 8] ^ w[i - 14] ^ w[i - 16], 1);
            }

            int a = h0;
            int b = h1;
            int c = h2;
            int d = h3;
            int e = h4;

            for (int t = 0; t < 80; t++) {
                int f, k;
                if (t >= 0 && t <= 19) {
                    f = (b & c) | ((~b) & d);
                    k = 0x5A827999;
                } else if (t >= 20 && t <= 39) {
                    f = b ^ c ^ d;
                    k = 0x6ED9EBA1;
                } else if (t >= 40 && t <= 59) {
                    f = (b & c) | (b & d) | (c & d);
                    k = 0x8F1BBCDC;
                } else {
                    f = b ^ c ^ d;
                    k = 0xCA62C1D6;
                }

                int temp = leftRotate(a, 5) + f + e + k + w[t];
                e = d;
                d = c;
                c = leftRotate(b, 30);
                b = a;
                a = temp;
            }

            h0 += a;
            h1 += b;
            h2 += c;
            h3 += d;
            h4 += e;
        }

        return String.format("%08x%08x%08x%08x%08x", h0, h1, h2, h3, h4);
    }

    private static int leftRotate(int value, int bits) {
        return (value << bits) | (value >>> (32 - bits));
    }
}