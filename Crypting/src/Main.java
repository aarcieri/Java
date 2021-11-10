
import Crypting.*;

import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Main {
    
    public static void TestDES() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
        String key = "squirrel";

        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        System.out.println(" key = " + desKey.getEncoded().toString());

        DES cipher = new DES();
                
        
        String plainText = "Ciao car";
     
        byte[] message = plainText.getBytes();


        Cipher desCipher = Cipher.getInstance("DES");
        desCipher.init(Cipher.ENCRYPT_MODE,desKey);

        byte[] encryptedMessage = desCipher.doFinal(message);  
        String javaxCoded=encryptedMessage.toString();
        
        
        
        String str = cipher.encrypt(plainText, key);
        System.out.println("key = " + key);
        System.out.println("plainText " + plainText);
        System.out.println("codifica con des = " + str);
        System.out.println("decodifica con des = " + cipher.decrypt(str, key));
    }
    
    private static void TestCBC_Des(){
        DES cipher = new DES();
        CBC cbc = new CBC(cipher);
        
        String key = cbc.generateCharString(8);
        
        String plainText = "Caro amico come va, spero tutto ok";
        //String plainText = "Ciao car";
        
        String encrypt = cbc.encrypt(plainText, key);
        String decrypt = cbc.decrypt(encrypt, key);
        
        System.out.println("plainText = " + plainText);
        System.out.println("      key = " + key);
        System.out.println("  encrypt = " + Base64.getEncoder().encodeToString(encrypt.getBytes()));
        System.out.println("  decrypt = " + decrypt);
        
    }
    
    private static void TestCBCxor(){
        String a = "bello";
        String b = "12345";
        
        DES des = new DES();
        CBC cbc = new CBC(des);
        
        String abinary = Utility.toBinaryString(a);
        String bbinary = Utility.toBinaryString(b);
        
        String c = cbc.xor(a, b);
        String cbinary = Utility.xor(abinary, bbinary);
        
        System.out.println("           c = " + c);
        System.out.println("c to binary = " + Utility.toBinaryString(c));
        System.out.println("    cbinary = " + cbinary);
    }
    
    private static void TestDesOutput() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        String key = "squirrel";
        String plainText = "bauibaui";
        
        DES cipher = new DES();
        String desCrypetd = cipher.encrypt(plainText, key);
        String desDecrypted = cipher.decrypt(desCrypetd, key);
        
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        
        Cipher desCipher = Cipher.getInstance("DES");
        desCipher.init(Cipher.ENCRYPT_MODE,desKey);
        byte[] encrypted =desCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        desCipher.init(Cipher.DECRYPT_MODE,desKey);
        String decrypted = new String(desCipher.doFinal(encrypted));
        
        
        System.out.println("      plainText = " + plainText);
        System.out.println("            key = " + key);
        System.out.println("  javax encrypt = " + encrypted);
        System.out.println("  javax decrypt = " + decrypted);
        
        System.out.println("    des encrypt = " + desCrypetd.getBytes());
        System.out.println("    des decrypt = " + desDecrypted);
        AlgorithmParameters params = desCipher.getParameters();
    }
    
    private static void TestCBCDesOutput() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException{
        String plainText = "Ciao caro amico, ci vediamo presto, salutami Cammela...123";
        
        DES cipher = new DES();
        CBC cbc = new CBC(cipher);
        byte[] iv = cbc.getInitialVector().getBytes();
        String key =  cbc.generateCharString(8);
        
       
        
        SecretKeySpec desKey = new SecretKeySpec(key.getBytes(), "DES");
        
        Cipher desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        desCipher.init(Cipher.ENCRYPT_MODE, desKey ,new IvParameterSpec(iv));
        byte[] encrypted =desCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        desCipher.init(Cipher.DECRYPT_MODE, desKey, new IvParameterSpec(iv));
        String decrypted = new String(desCipher.doFinal(encrypted));
        
        String desCrypetd   = cbc.encrypt(plainText, key);
        String desDecrypted = cbc.decrypt(desCrypetd, key);
        
        System.out.println("      plainText = " + plainText);
        System.out.println("            key = " + key);
        System.out.println("  javax encrypt = " + encrypted);
        System.out.println("  javax binay encrypt = " + Utility.toBinaryString(encrypted.toString()));
        System.out.println("  javax str encrypt = " + Utility.toCharString(Utility.toBinaryString(encrypted.toString())));
        System.out.println("  javax decrypt = " + decrypted);
        
        System.out.println("    des encrypt = " + desCrypetd.getBytes());
        System.out.println("    des binay encrypt = " + Utility.toBinaryString(desCrypetd.toString()));
        System.out.println("    des decrypt = " + desDecrypted);
    }
    
    private static void TestAES(){
        String key = Utility.toHexString("123456789abcdefg");
        
        //String plainText = "ciao car amico b";
        String plainText = "Caro amico come_";
        key = "123456789abcdefg";
        
        AES aes = new AES();
        // crypt
        String encrypted = aes.encrypt(key, plainText);
        
        String decrypted = aes.decrypt(key, encrypted);
        
        System.out.println("plainText: " + plainText);
        System.out.println("  crypted: " + encrypted);
        System.out.println("decrypted: " + decrypted);

    }
    
    private static void TestBinAndHex(){
        DES des = new DES();
        String num = "1110111011111110111110111011111110111110111011111110111111101111111011111110111111";
        String hex = Utility.binToHex(num);
        String binary = Utility.hextoBin(hex);

        System.out.println("   num: "+num );
        System.out.println("   hex: " + hex);
        System.out.println("binary: "+binary);
    }
    
    private static void TestCBC_Aes(){
        Icipher cipher = new AES();
        CBC cbc = new CBC(cipher);
        
        String key = cbc.generateCharString(16); // 16 byte = 128 bit
        
        String plainText =  "Caro amico come va, spero tutto_"; // 2 blocchi da 128 bit (32 caratteri)
        
        
        String encrypt = cbc.encrypt(plainText, key);
        String decrypt = cbc.decrypt(encrypt, key);
        
        System.out.println("plainText = " + plainText);
        System.out.println("      key = " + key);
        System.out.println("  encrypt = " + encrypt);
        System.out.println("  decrypt = " + decrypt);
    }
    
    private static void TestModPow(){
        long m=12;
        long e=720924899L;
        long n = 519732709183516001L;
        
        long pow = Utility.ModPow(m, e, n);
        System.out.println("ModPow("+m+", "+e+", "+n+") = " + pow);
    }
    
    private static void TestEuclideEsteso(){
        long a=15212323;
        long b=222222222;
        long[] v = Utility.EuclideEsteso(a, b);
        
        System.out.println("MCD = " + v[0]);
        System.out.println("x = " + v[1]);
        System.out.println("y = " + v[2]);
        
        long check = a*v[1]+b*v[2];
        System.out.println("bezous = " + check);
        
        //System.out.println("num chiamate = " + Utility.num);
        
    }
    
    private static void TestMillerRabin(){
        //boolean check = Utility.MillerRabin(3098994721244174544L, 10);
        boolean check = Utility.MillerRabin(997, 10);
    }
    
    public static void TesttoLongArray(){
        Utility.CodificaToLongArray("ciao salvo", 3);
    }
    
    public static void TestRSAGenerateParameters(){
        RSA rsa = new RSA();
        rsa.GenerateParameters();
    }
    
     public static void TestCodificaDecodificaArrayDiLong(){
        String msg = "ciao bello";
        long[] c_msg = Utility.CodificaToLongArray(msg, 1);
        
        String d_msg = Utility.DecodificaFromLongArray(c_msg, 1);
        
        System.out.println("messaggio: " + msg);
        System.out.println("cifrato: " + Arrays.toString(c_msg));
        System.out.println("decrifato: " + d_msg);
    }
    
    public static void TestRSAConArray(){
        RSA rsa = new RSA();
        
        rsa.GenerateParameters();
        long[] publicKey  = rsa.GetPublicKey();
        long[] privateKey = rsa.GetPrivateKey();
        
        String plainText = "ciao caro amico ti scrivo, bla bla ___ che ! ?? ... ; BRAVO";
        //long[] plainText = new long[]{12, 56};
        
        long[] cmsg = rsa.encrypt(plainText, publicKey[0], publicKey[1]);

        String dmsg = rsa.decrypt(cmsg,privateKey[0], privateKey[1]);
        
        System.out.println("plainText = " + plainText);
        System.out.println("cifrato = " + Arrays.toString(cmsg));
        System.out.println("decifrato = " + dmsg);
    }
    
   public static void TestRSAConStringhe(){
        RSA rsa = new RSA();
        
        rsa.GenerateParameters();
        long[] publicKey  = rsa.GetPublicKey();
        long[] privateKey = rsa.GetPrivateKey();
        
        String plainText = "ciao caro amico ti scrivo, bla bla ___ che ! ?? ... ; BRAVO";
        //long[] plainText = new long[]{12, 56};
        
        String cmsg = rsa.encryptToString(plainText, publicKey[0], publicKey[1]);
        //long[] cmsg = rsa.crypt(plainText, publicKey[0], publicKey[1]);
        String dmsg = rsa.decryptFromString(cmsg,privateKey[0], privateKey[1]);
        
        System.out.println("plainText = " + plainText);
        System.out.println("cifrato = " + cmsg);
        System.out.println("decifrato = " + dmsg);
    }
    
    public static void main(String args[]){
        try {
            //TestDES();
            //TestCBC();
            //TestCBCxor();
            //TestDesOutput();
            //TestCBCDesOutput(); // TODO:  verificare
            //TestBinAndHex();
            //TestAES();
            //TestCBC_Aes();
            //TestModPow();
            //TestEuclideEsteso();
            //TesttoLongArray();
            //TestMillerRabin();
            //TestRSAGenerateParameters();
            //TestCodificaDecodificaArrayDiLong();
            TestRSAConArray();
            TestRSAConStringhe();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
