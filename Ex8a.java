import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import javax.crypto.Cipher;

public class GnuPGSimulation {

    public static void main(String[] args) {
        try {
            System.out.println("--- STARTING SECURITY DEMONSTRATION --- \n");

            System.out.println("[Step 1] Generating 2048-bit RSA Key Pair...");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println("Keys generated successfully.\n");

            String originalMessage = "Confidential Data for Secure Transmission";
            System.out.println("Original Message: " + originalMessage + "\n");

            System.out.println("[Step 2] Encrypting data using the Public Key...");
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(originalMessage.getBytes(StandardCharsets.UTF_8));
            String encryptedMessageBase64 = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("Encrypted Message (Base64): " + encryptedMessageBase64 + "\n");

            System.out.println("[Step 3] Decrypting data using the Private Key...");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessageBase64));
            String decryptedMessage = new String(decryptedBytes, StandardCharsets.UTF_8);
            System.out.println("Decrypted Message: " + decryptedMessage + "\n");

            System.out.println("[Step 4] Creating Digital Signature using the Private Key...");
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(originalMessage.getBytes(StandardCharsets.UTF_8));
            byte[] digitalSignatureBytes = signature.sign();
            String signatureBase64 = Base64.getEncoder().encodeToString(digitalSignatureBytes);
            System.out.println("Digital Signature (Base64): " + signatureBase64 + "\n");

            System.out.println("[Step 5] Verifying Digital Signature using the Public Key...");
            signature.initVerify(publicKey);
            signature.update(originalMessage.getBytes(StandardCharsets.UTF_8));
            boolean isVerified = signature.verify(digitalSignatureBytes);
            System.out.println("Signature Verification Result: " + (isVerified ? "SUCCESS (Valid)" : "FAILED (Invalid)"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}