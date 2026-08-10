package Ex2;

public class RailFenceCipher {

    // Method to encrypt the message using Rail Fence Cipher
    public static String encrypt(String message, int rails) {
        // 1. Remove spaces from the original message
        message = message.replace(" ", "").toUpperCase();
        int len = message.length();
        
        // 2. Create a 2D matrix to represent the rails
        char[][] fence = new char[rails][len];
        
        // Initialize the fence with a placeholder character
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < len; j++) {
                fence[i][j] = '\n';
            }
        }
        
        // 3. Place characters in a zig-zag pattern
        int row = 0;
        boolean movingDown = false;
        
        for (int i = 0; i < len; i++) {
            fence[row][i] = message.charAt(i);
            
            // Reverse direction if we hit the top or bottom rail
            if (row == 0 || row == rails - 1) {
                movingDown = !movingDown;
            }
            
            // Move to the next row
            row += movingDown ? 1 : -1;
        }
        
        // 4. Read the matrix row by row to construct the ciphertext
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < len; j++) {
                if (fence[i][j] != '\n') {
                    ciphertext.append(fence[i][j]);
                }
            }
        }
        
        return ciphertext.toString();
    }

    public static void main(String[] args) {
        // Given input from the problem statement
        String inputMessage = "DELIVER GOODS AT NOON";
        int rails = 3; // Standard number of rails used in the example
        
        System.out.println("Original Message: " + inputMessage);
        
        // Encrypt the message
        String encryptedMessage = encrypt(inputMessage, rails);
        System.out.println("Encrypted Message: " + encryptedMessage);
    }
}

