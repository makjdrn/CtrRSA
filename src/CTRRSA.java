import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by makjdrn on 2015-12-05.
 */
public class CTRRSA {
    int k;
    int dd;
    Random r = new SecureRandom();
    BigInteger N = new BigInteger("1");
    BigInteger phi = new BigInteger("1");
    BigInteger piM[];
    BigInteger dpiM[];
    BigInteger d;
    BigInteger e;
    BufferedWriter privkey;
    BufferedWriter pubkey;
    BufferedWriter Nkey;
    BigInteger pi[];

    public CTRRSA(int x, int y) throws IOException {
        k = x;
        dd = y;
        e = BigInteger.probablePrime(dd/2, r);
        privkey = new BufferedWriter(new FileWriter("privkey.txt"));
        pubkey = new BufferedWriter(new FileWriter("pubkey.txt"));
        piM = new BigInteger[k];
    }
    public void run() throws IOException {
        int i = 0;
        while(i < k) {
            N = N.multiply(GenerateKeys(i, dd));
            i++;
        }
        d = e.modInverse(phi);
        for(int j = 0; j < k; j++) {
            dpiM[j] = d.remainder(piM[j]);
            privkey.write(dpiM[j].toString());
        }
        privkey.close();
        pubkey.write(e.toString());
        pubkey.close();
        Nkey.write(N.toString());
        Nkey.close();
    }

    private BigInteger GenerateKeys(int i, int dd) {
        pi[i] = BigInteger.probablePrime(dd, r);
        return pi[i];
    }

}
