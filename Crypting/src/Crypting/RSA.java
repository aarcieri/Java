package Crypting;

import java.util.Arrays;
import java.util.Random;



public class RSA {
    private long p;
    private long q;
    
    private long n;
    private long z;
    private long e;
    private long d;
    
    private boolean parametersGenerated;
    
    public RSA(){
        this.parametersGenerated = false;
    }
    
    // genera i due numeri primi p e q
    private long GeneratePrime(){
        Random rand = new Random();
        
        long x;
        
        do{
            x =Math.abs(rand.nextInt()); // con i long il tempo di esecuzione diviene enorme!!!
        }while(!Utility.MillerRabin(x, 10));
        
        return x;
    }
    
    private void GenerateKey(){
        // generiamo e
        for(this.e=(int)Math.sqrt(this.z); this.e<this.z;this.e++){
            if (Utility.MCD(this.e, this.z) == 1) {
                break;
            }
        }
        
        // d deve essere tale che:
        // d*e = 1 (mod z)
        // ma possiamo ottenerlo come inversomodulo di e cioé:
        // e * x + z * y = 1  (equazione diofantea o identità di Bezout)
        long[] v = Utility.EuclideEsteso(this.e, this.z);
        
        this.d=v[1];
        
        //long verifica_bezout = Utility.mulMod(this.e,this.d,this.z);
    }
    
    public void GenerateParameters(){
        // i parametri non possono essere negativi
        do{
            this.p=GeneratePrime();
            this.q=GeneratePrime();

            // questi prodotto può supperare il max long e quindi diviene un numero negativo
            // i numeri interi a causa della rappresentazione in complemeto a 2, 
            // dopo il più grande tornano al più piccolo e prima del più piccolo vannoal più grande
            this.n=this.p*this.q; 
            this.z=(this.p-1)*(this.q-1);

            this.GenerateKey();
        }while(this.p<0 || this.q<0 || this.n<0 || this.z<0 || this.e<0 || this.d<0);
        
        this.parametersGenerated = true;
    }
    
    public long[] GetPublicKey(){
        if(!parametersGenerated) this.GenerateParameters();
        return new long[]{this.n, this.e};
    }
    
    public long[] GetPrivateKey(){
        if(!parametersGenerated) this.GenerateParameters();
        return new long[]{this.n, this.d};
    }
    
    public long[] encrypt(String plainText, long key_n, long key_e){
       long[] v_msg = Utility.CodificaToLongArray(plainText, 3); 
       
       long[] c = crypt(v_msg, key_n, key_e);
       
       return c;
    }
    
    public String decrypt(long[] crypted, long key_n, long key_d){
        
        long[] c =crypt(crypted, key_n, key_d);
        
        return Utility.DecodificaFromLongArray(c, 3);
    }
    
    public String encryptToString(String plainText, long key_n, long key_e){
       return Arrays.toString(encrypt(plainText, key_n, key_e));
    }
    
    public String decryptFromString(String crypted, long key_n, long key_d){
        
        crypted = crypted.replace("[", "");
        crypted = crypted.replace("]", "");
        
        String[] num_str = crypted.split("," );
        long[] v_msg = new long[num_str.length];
        
        for(int i=0; i<num_str.length; i++) v_msg[i]=Long.valueOf(num_str[i].trim());
                
        long[] c =crypt(v_msg, key_n, key_d);

        return Utility.DecodificaFromLongArray(c, 3);
    }
    
    //private String crypt(String text, long n, long esponente){
    public long[] crypt(long[] v_msg, long n, long esponente){
        
        long[] out = new long[v_msg.length];
        
        for(int i=0; i< v_msg.length; i++){
            out[i]=Utility.ModPow(v_msg[i], esponente, n);
        }
        
        return out;
    }
}
