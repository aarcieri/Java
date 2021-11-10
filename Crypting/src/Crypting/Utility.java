
package Crypting;

import java.math.BigInteger;
import java.util.Random;

public class Utility {
    // to convert a char string to a binary string
    public static String toBinaryString(String str){
        String binaryString = "";
        char[] stringArray = str.toCharArray();
        for(int i=0;i<stringArray.length;i++){
            String binary = Integer.toBinaryString((int) stringArray[i]);
            int length = binary.length();
            if(length<8)
                for(int j=0; j<(8-length); j++) binary = '0'+binary;
            binaryString += binary;
        }
        return binaryString;
    }

    // converte una stringa di caratteri in esadecimale
    public static String toHexString(String str){
        String hexString = "";
        char[] stringArray = str.toCharArray();
        for(int i=0; i<str.length(); i++){
            String binary = Integer.toHexString((int)stringArray[i]);
            if(binary.length()==1) binary = '0'+binary;
            hexString += binary;
        }
        
        return hexString;
    }
    
    // to convert a binary string to a char string
    public static String toCharString(String binary){
        String charString = "";
        for(int i=0; i<binary.length()/8; i++){
            String a = binary.substring(i*8, (i+1)*8);
            int n =Integer.parseInt(a, 2);
            charString += (char)n;
        }
        return charString;
    }
    
    
    // binary to hexadecimal conversion
    public static String binToHex(String input)
    {
            int n = (int)input.length() % 4;
            
            if(n!=0){
                for(int i=0; i < (4-n); i++) input= "0" + input;
            }
            
            String text = "";
            
            n = (int)input.length() / 4;
            
            for(int i=0; i<n; i++){
                    text += Long.toHexString(
                    Long.parseUnsignedLong(input.substring(i*4, (i+1)*4), 2));
            }

            return text;
    }
    
    // hexadecimal to binary conversion
    public static String hextoBin(String input)
    {
            int n = input.length();
            String text = "";
            
            for(int i=0; i<n; i++){
                String bin = Long.toBinaryString(
                        Long.parseUnsignedLong(input.substring(i,i+1), 16));

                while (bin.length() < 4)
                        bin = "0" + bin;
                text +=bin;
            }
            
            return text;
    }

    
    // xor between two binary strings
    public static String xor(String a, String b){
        String xor="";
        
        for(int i=0;i<a.length();i++){
            int r = ((int)a.charAt(i)-48) ^ ((int)b.charAt(i)-48);
            xor+=(char)(r+48);
        }
        return xor;
    } 
    
    // aggiunge un carattere a sinistra (left pad) tante volte sino ad avere la lunghezza length
    public static String LeftPad(String input, char padCharacter, int length){
        String padded = input;
        int len = input.length();
        if(len<length){
            for(int i = 0; i<(length-len); i++) padded = padCharacter + padded;
        }
        
        return padded;
    }
    
    // converte una stringa in un array di long, raggruppa i numeri a blocchi di numCifrePerBlocco cifre
    public static long[] CodificaToLongArray(String input, int numCifrePerBlocco){
        
        String cifre = "";
        
        for(int i=0; i<input.length(); i++){
            cifre += String.valueOf((int)input.charAt(i) + 99);
        }
        long r = cifre.length() % numCifrePerBlocco;
        if(r!=0){
            long l = cifre.length() + numCifrePerBlocco - r;
            cifre = String.format("%1$"+l+"s", cifre).replace(' ', '0');
        }
        
       int dim = cifre.length() / numCifrePerBlocco;
       
       long[] v = new long[dim];
       
       for(int i=0; i<dim; i++){
           v[i] = Long.valueOf(cifre.substring(i * numCifrePerBlocco, (i+1)*numCifrePerBlocco));
       }
        
        return v;
    }
    
    public static String DecodificaFromLongArray(long[] input, int numCifrePerBlocco){
        String decoded="";
        String v_string;
        
        v_string = String.valueOf(input[0]);
        
        for(int i=1; i<input.length; i++){
            String blocco = String.valueOf(input[i]);
            if(blocco.length()<numCifrePerBlocco) {
                blocco = String.format("%1$"+numCifrePerBlocco+"s", blocco).replace(' ', '0');
            }
            v_string+=blocco;
        }
        
        int l=v_string.length() / 3; // 3 è il numero di cifre per ogni carattere
        
        for(int i=0; i<l; i++){
            long numASCII = Long.valueOf(v_string.substring(i*3, (i+1)*3))-99;
            decoded += String.valueOf((char)numASCII);
        }
        
        return decoded;
    }
    
    /* calcoliamo (a * b) % c */
    public static long mulMod(long a, long b, long mod) 
    {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).longValue();
    }
    
    // ritorna true se il numero è probabilisticamente primo
    public static boolean MillerRabin(long n, int iteration)
    {
        //valori non ammessi
        if (n == 0 || n == 1 || n == 2)
            return false;
       
        if (n % 2 == 0)
            return false;
        
        long k = 0;
        long s = n - 1;
        while (s % 2 == 0){
            s /= 2;
            k++;
        }
 
        Random rand = new Random();
        for (int i = 0; i < iteration; i++)
        {
            long r = Math.abs(rand.nextLong());            
            long a = r % (n - 1) + 1, temp = s;
            long mod = ModPow(a, temp, n); 
            if(mod ==1) continue;// prima condizione verificata
            
            // seconda condizione
            for(int j = 1; j<=k-1; j++){
                
                mod = mulMod(mod, mod, n);
                if(mod == n-1) break;
            }
            if(mod != n-1) return false;
        }
        return true;        
    }
    
    // calcola a^b modulo n
    public static long ModPow(long a, long b, long n){
        //long numCicli=0;
        
        long pow = 1;
        long d,r,p;
        
        p=a;
        
//        if(b==0) return 1;
//        if(b==1) return a%n;

        d=b; 
        while(d!=0){
//            r=d%n;
//            d=d/n;
            r=d%2;
            d=d/2;
            //if(r!=0) pow = ( pow%n * p%n ) % n;
            if(r!=0) pow = mulMod(pow, p, n);
            //p=(p*p)%n;
            p=mulMod(p, p, n);
            //numCicli++;
        }
        //System.out.println("NumCicli="+numCicli);
        return pow;
    }
 
    // ritorna MCD e x e y tali che: a * x + b * y = MCD(a,b)
    // [0] -> MCD
    // [1] -> x
    // [2] -> y
    //public static long num =0;
    public static long[] EuclideEsteso(long a, long b){
        //Utility.num++;
        if (a == 0)
        {
            return new long[] { b, 0, 1 };
        }
 
        long[] v = EuclideEsteso(b%a, a);
 
        long x = v[2] - (b/a) * v[1];
        long y = v[1];
 
        return new long[] { v[0], x, y };
    }
    
    public static long MCD(long a, long b){
        long[] v = EuclideEsteso(a, b);
        
        return v[0];
    }
            
}
