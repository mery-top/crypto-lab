import java.security.*;

public class DSADemo {
    public static void main(String[] args) {
        try {
            System.out.println("Generating DSA Key Pair (1024-bit)...");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(1024, random);

            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            System.out.println("Keys generated successfully.\n");

            String documentText = "Project Approval Document - Version 1.0";
            byte[] documentData = documentText.getBytes();
            System.out.println("Original Document Content: \"" + documentText + "\"");

            System.out.println("\n--- ALICE: Signing the Document ---");
            Signature dsaSign = Signature.getInstance("SHA1withDSA");
            dsaSign.initSign(privateKey);
            dsaSign.update(documentData);

            byte[] signature = dsaSign.sign();
            System.out.println("Signature generated successfully (in bytes).");
            System.out.println("Signature (Hex format): " + bytesToHex(signature));

            System.out.println("\n--- BOB: Verifying the Signature ---");
            Signature dsaVerify = Signature.getInstance("SHA1withDSA");
            dsaVerify.initVerify(publicKey);
            dsaVerify.update(documentData);

            boolean isVerified = dsaVerify.verify(signature);
            System.out.println("Is the signature valid and authentic? " + isVerified);

            System.out.println("\n--- SECURITY TEST: Simulating Document Alteration ---");
            String tamperedText = "Project Approval Document - Version 2.0 (Altered)";
            byte[] tamperedData = tamperedText.getBytes();

            dsaVerify.update(tamperedData);
            boolean isTamperedVerified = dsaVerify.verify(signature);
            System.out.println("Is the tampered signature valid? " + isTamperedVerified);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}