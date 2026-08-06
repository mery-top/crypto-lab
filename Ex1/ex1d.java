public class HillCipher {
    private static final int[][] KEY = {
        {3, 3},
        {2, 5}
    };

    public static String encrypt(String text) {
        text = text.toUpperCase().replace(" ", "");
        if (text.length() % 2 != 0) {
            text += "X";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            int letter1 = text.charAt(i) - 'A';
            int letter2 = text.charAt(i + 1) - 'A';

            int encryptNum1 = (KEY[0][0] * letter1 + KEY[0][1] * letter2) % 26;
            int encryptNum2 = (KEY[1][0] * letter1 + KEY[1][1] * letter2) % 26;

            result.append((char) (encryptNum1 + 'A'));
            result.append((char) (encryptNum2 + 'A'));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String message = "GATE";
        String encrypted = encrypt(message);
        System.out.println("Original:  " + message);
        System.out.println("Hill Encrypted: " + encrypted);
    }
}
