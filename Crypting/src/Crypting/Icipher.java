
package Crypting;

public interface Icipher {
    public String encrypt(String plainText, String key);
    public String decrypt(String plainText, String key);
    public int singleBlockDimension(); // ritorna il numero il byte per ogni blocco
}
