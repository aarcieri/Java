
package Crypting;

import java.util.Random;

public class CBC{
    private Icipher cipher;
    private String initialVector;
    
    public CBC(Icipher cipher, String initialVector){
        this.cipher = cipher;
        this.initialVector = initialVector;
    }
    
    public CBC(Icipher cipher){
        this.cipher = cipher;
        this.initialVector = generateCharString(this.cipher.singleBlockDimension());
    }
     
    public String generateCharString(int dim){
        String iv = "";
        Random rand = new Random();
        int randomNum;
        for(int i = 0;i <dim; i++){
            randomNum = rand.nextInt((126 - 33) + 1) + 33;
            iv += (char)randomNum;
        }
        return iv;
    }
    
    // xor between two char strings
    public String xor(String s1, String s2){
        
        char[] c = new char[s1.length()];
        
        for(int i=0; i<s1.length(); i++){
            c[i] = (char)((int)s1.charAt(i) ^ (int)s2.charAt(i));
        }
        String xorSting = String.valueOf(c);
        return xorSting;
    }
    
   public String encrypt(String plainText, String key){
        
        String totalEncryptText = "";
        int dim = this.cipher.singleBlockDimension();
        
        // completiamo la stringa con spazzi per arrivare a multipli di singleBlockDimension caratteri (8 char sono 64 bit)
        int n = plainText.length()%dim;
        
        if(n>0) for(int i=0; i< (dim-n); i++ ) plainText+=' ';

        // calcoliamo numero di blocchi da codificare
        n = plainText.length()/dim;
        
        String encryptText = this.getInitialVector();
        
        for(int block=0; block<n; block++){
            
           String plainTextBlock = plainText.substring(block*dim, (block+1) * dim);
                 
           plainTextBlock = this.xor(plainTextBlock, encryptText);
            
            
            encryptText = this.cipher.encrypt(plainTextBlock, key);
            
            totalEncryptText += encryptText;
        }
        
        return totalEncryptText;
   }
   
   public String decrypt(String plainText, String key){
       String totalDencryptText = "";
       int dim = this.cipher.singleBlockDimension();
        // completiamo la stringa con spazzi per arrivare a multipli di dim caratteri (8 char sono 64 bit)
        int n;

        // calcoliamo numero di blocchi da 64 bit da codificare
        n = plainText.length()/dim;
        
        String previousPlainText = this.getInitialVector();
        
        for(int block=0; block<n; block++){
            
            String plainTextBlock = plainText.substring(block*dim, (block+1) * dim);
                 
            String decrypted = this.cipher.decrypt(plainTextBlock, key);
            
            totalDencryptText += this.xor(decrypted, previousPlainText);
            //totalDencryptText = decrypted;
            previousPlainText= plainTextBlock;
        }
        
        return totalDencryptText;
   }

    public String getInitialVector() {
        return initialVector;
    }

    public void setInitialVector(String initialVector) {
        this.initialVector = initialVector;
    }
}
