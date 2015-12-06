import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by makjdrn on 2015-12-05.
 */
public class ThreadClass {
    static int k;
    static int dd;
    static Random r = new SecureRandom();
    static BigInteger N = new BigInteger("1");
    static BigInteger phi = new BigInteger("1");
    static BigInteger p;
    static BigInteger q;
    static BigInteger pM1;
    static BigInteger qM1;
    static BigInteger d;
    static BigInteger e;
    static BigInteger dp, dq, c2;
    static BufferedWriter inpfile;
    static BufferedWriter keys;
    static BufferedWriter enc;
    BigInteger pi[];
    public static void main(String[] args) throws IOException {

        k = Integer.parseInt(args[0]);
        dd = Integer.parseInt(args[1]);
        if(args[2].equals("E")) {
            e = BigInteger.probablePrime(dd / 2, r);
            keys = new BufferedWriter(new FileWriter("keys.txt"));
            GenerateKeys();
            enc = new BufferedWriter(new FileWriter("enc.txt"));
            String path = args[3].trim();
            String text = new Scanner(new File(path)).useDelimiter("\\Z").next();
            String s = text.trim();
            BigInteger m = new BigInteger(text.getBytes());
            System.out.println("text: " + s + "\nm: " + m + "\ntext.getBytes(): " + text.getBytes() + "\nRSAEncrypt: " + RSADecrypt(m).toString());
            enc.write(RSAEncrypt(m).toString());
            enc.close();
        }
        else if(args[2].equals("D")) {
            Scanner sc = new Scanner(new File("keys.txt"));
            String NN = sc.useDelimiter("\n").next();
            N = new BigInteger(NN);
            String dd = sc.useDelimiter("\n").next();
            String dpp = sc.useDelimiter("\n").next();
            String dqq = sc.useDelimiter("\n").next();
            String cc2 = sc.useDelimiter("\n").next();
            String pp = sc.useDelimiter("\n").next();
            String qq = sc.useDelimiter("\n").next();
            sc.close();
            d = new BigInteger(dd);
            dp = new BigInteger(dpp);
            dq = new BigInteger(dqq);
            c2 = new BigInteger(cc2);
            p = new BigInteger(pp);
            q = new BigInteger(qq);
            String path = args[3].trim();
            inpfile = new BufferedWriter(new FileWriter(path));
            String enctext = new Scanner(new File("enc.txt")).useDelimiter("\\Z").next();
            BigInteger c = new BigInteger(enctext);
            System.out.println("enctext: " + enctext + "\nenctext.getBytes(): " + enctext.getBytes()+ "\nc: " + c + "\nRSADecrypt: " + RSADecrypt(c).toString());
            inpfile.write(new String(RSADecrypt(c).toByteArray()));
            inpfile.close();
        }
    }

    private static void GenerateKeys() throws IOException {
        r = new SecureRandom();
        p = BigInteger.probablePrime(dd, r);
        q = BigInteger.probablePrime(dd, r);

        pM1 = p.subtract(BigInteger.ONE);
        qM1 = q.subtract(BigInteger.ONE);

        N = p.multiply(q);
        phi = pM1.multiply(qM1);
        d = e.modInverse(phi);
        dp = d.remainder(pM1);
        dq = d.remainder(qM1);
        c2 = p.modInverse(q);
        keys.write(N.toString() + "\n" + d.toString()  +"\n"+ dp.toString() + "\n"+ dq.toString() + "\n" + c2.toString() +"\n" + p.toString() + "\n" + q.toString());
        keys.close();
    }
    public static BigInteger RSADecrypt(BigInteger c) {
        BigInteger cDp = c.modPow(dp, p);
        BigInteger cDq = c.modPow(dq, q);
        BigInteger u = ((cDq.subtract(cDp)).multiply(c2)).remainder(q);
        if (u.compareTo(BigInteger.ZERO) < 0) u = u.add(q);
        return cDp.add(u.multiply(p));
    }
    public static BigInteger RSAEncrypt(BigInteger m) {
        return m.modPow(e, N);
    }
}
