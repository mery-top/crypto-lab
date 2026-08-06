public class VigenereCipher {
    public static String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isUpperCase(ch)) {
                int shift = key.charAt(keyIndex) - 'A';
                char encryptedChar = (char) ((ch - 'A' + shift) % 26 + 'A');
                result.append(encryptedChar);
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String message = "MEET AT GATE";
        String key = "KEY";
        String encrypted = encrypt(message, key);
        System.out.println("Vigenere Encrypted: " + encrypted);
    }
}
