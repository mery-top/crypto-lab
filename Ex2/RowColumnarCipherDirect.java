package Ex2;


public class RowColumnarCipherDirect {
    public static void main(String[] args) {
        // Direct Inputs
        String message = "DELIVER GOODS AT NOON";
        int columns = 5;

        // Clean the message: remove spaces and convert to uppercase
        String plaintext = message.replace(" ", "").toUpperCase();
        
        // Calculate the number of rows needed
        int rows = (int) Math.ceil((double) plaintext.length() / columns);
        
        // Create the grid (matrix)
        char[][] grid = new char[rows][columns];
        int charIndex = 0;
        
        // Fill the grid row by row
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (charIndex < plaintext.length()) {
                    grid[i][j] = plaintext.charAt(charIndex++);
                } else {
                    grid[i][j] = 'X'; // Padding character if needed
                }
            }
        }
        
        // Read the grid column by column to create the ciphertext
        StringBuilder ciphertext = new StringBuilder();
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                ciphertext.append(grid[i][j]);
            }
        }
        
        // Print results
        System.out.println("Original Message: " + message);
        System.out.println("Ciphertext: " + ciphertext.toString());
    }
}






