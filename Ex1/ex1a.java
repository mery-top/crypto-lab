public class CaesarCipher {
   
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

           
            if (Character.isUpperCase(ch)) {
                char encryptedChar = (char) (((int) ch + shift - 65) % 26 + 65);
                result.append(encryptedChar);
            } 
            
            else if (Character.isLowerCase(ch)) {
                char encryptedChar = (char) (((int) ch + shift - 97) % 26 + 97);
                result.append(encryptedChar);
            } 
            else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    
    public static String decrypt(String text, int shift) {
     
        return encrypt(text, 26 - (shift % 26));
    }

    public static void main(String[] args) {
        String message = "MEET AT GATE";
        int shift = 3;

        String encrypted = encrypt(message, shift);
        String decrypted = decrypt(encrypted, shift);

        System.out.println("Original:  " + message);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
