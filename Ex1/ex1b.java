public class PlayfairCipher {
    private char[][] matrix = {
        {'K', 'E', 'Y', 'W', 'O'},
        {'R', 'A', 'B', 'C', 'D'},
        {'F', 'G', 'H', 'I', 'L'},
        {'M', 'N', 'P', 'Q', 'S'},
        {'T', 'U', 'V', 'X', 'Z'}
    };

    private int[] getPosition(char ch) {
        if (ch == 'J') ch = 'I';
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (matrix[row][col] == ch) return new int[]{row, col};
            }
        }
        return null;
    }

    public String encrypt(String text) {
        text = text.toUpperCase().replace(" ", "");
        StringBuilder prepared = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            prepared.append(text.charAt(i));
            if (i + 1 < text.length() && text.charAt(i) == text.charAt(i + 1)) {
                prepared.append('X');
            }
        }
        if (prepared.length() % 2 != 0) prepared.append('X');

        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < prepared.length(); i += 2) {
            int[] pos1 = getPosition(prepared.charAt(i));
            int[] pos2 = getPosition(prepared.charAt(i + 1));

            int r1 = pos1[0], c1 = pos1[1];
            int r2 = pos2[0], c2 = pos2[1];

            if (r1 == r2) {
                result.append(matrix[r1][(c1 + 1) % 5]);
                result.append(matrix[r2][(c2 + 1) % 5]);
            } else if (c1 == c2) {
                result.append(matrix[(r1 + 1) % 5][c1]);
                result.append(matrix[(r2 + 1) % 5][c2]);
            } else {
                result.append(matrix[r1][c2]);
                result.append(matrix[r2][c1]);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        PlayfairCipher cipher = new PlayfairCipher();
        String message = "MEET AT GATE";
        String encrypted = cipher.encrypt(message);
        System.out.println("Playfair Encrypted: " + encrypted);
    }
}
