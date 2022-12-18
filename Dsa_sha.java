package Assignment_6;

import java.math.BigInteger;

public class Dsa_sha {

    // hexadecimal to binary conversion
    public static String hextoBin(String input) {
        int n = input.length() * 4;
        input = Long.toBinaryString(Long.parseUnsignedLong(input, 16));
        while (input.length() < n)
            input = "0" + input;
        return input;
    }

    // binary to hexadecimal conversion
    public static String binToHex(String input) {
        int n = (int) input.length() / 4;
        input = Long.toHexString(
                Long.parseUnsignedLong(input, 2));
        while (input.length() < n)
            input = "0" + input;
        return input;
    }

    public static int[] generate_chunk(String x) {
        // int []asscii= new int[x.length()];
        String chunks = "";
        String t = "";
        for (int i = 0; i < x.length(); i++) {
            char character = x.charAt(i);
            int a = (int) character;
            t += a;
            // System.out.println("t'"+t);
            String z = Long.toBinaryString(Long.parseUnsignedLong(t, 10));
            // System.out.println("z"+z);
            // System.out.println("z"+z);
            for (int j = z.length(); j < 8; j++) {
                // System.out.println("kk");
                chunks += "0";
            }
            chunks += z;
            // asscii[i]=(int) character;
            t = "";
        }
        // System.out.println("ch:"+chunks);
        chunks += "1";
        int k_Zeros = 512 - chunks.length() - 64;
        // System.out.println("k: "+k_Zeros);

        for (int i = 0; i < k_Zeros; i++) {
            chunks += "0";
        }
        String l = "";
        l += x.length() * 8;
        String len = Long.toBinaryString(Long.parseUnsignedLong(l, 10));

        while (len.length() < 64)
            len = "0" + len;
        chunks += len;
        int[] z = new int[16];
        int iterator = 0;
        System.out.println("chunks length: " + chunks.length());
        System.out.println("chunks 512 bit input to sha1: " + chunks);

        String temp = "";
        for (int i = 0; i < 512; i += 32) {
            for (int j = 0; j < 32; j++) {
                temp += chunks.charAt(i + j);
            }
            // System.out.println("temp:"+temp);
            int decimal = Integer.parseInt(temp, 2);
            // System.out.println("dec:"+decimal);
            z[iterator] = decimal;
            iterator++;
            temp = "";
        }
        return z;
    }

    private static int leftrotate(int x, int shift) {
        // leftrotate function

        return ((x << shift) | (x >>> (32 - shift)));
        // >>> is an UNSIGNED shift compared >> which is not

    }

    // instance variables
    private static int h1 = 0x67452301;
    private static int h2 = 0xEFCDAB89;
    private static int h3 = 0x98BADCFE;
    private static int h4 = 0x10325476;
    private static int h5 = 0xC3D2E1F0;
    private static int k1 = 0x5A827999;
    private static int k2 = 0x6ED9EBA1;
    private static int k3 = 0x8F1BBCDC;
    private static int k4 = 0xCA62C1D6;

    private static int hash(int[] x) {

        // Extend the sixteen 32-bit words into eighty 32-bit words
        int integer_count = x.length;
        int[] W = new int[80];
        int j = 0;

        for (int i = 0; i < integer_count; i += 16) {
            for (j = 0; j <= 15; j++)
                W[j] = x[j + i];
            for (j = 16; j <= 79; j++) {
                // w[i] = (w[i-3] xor w[i-8] xor w[i-14] xor w[i-16]) leftrotate 1
                W[j] = leftrotate(W[j - 3] ^ W[j - 8] ^ W[j - 14] ^ W[j - 16], 1);
                // System.out.printf("J: %d ARRAY: %x\n", j, W[j]);
            }
            // calculate A,B,C,D,E:
            int A = h1;
            int B = h2;
            int C = h3;
            int D = h4;
            int E = h5;
            int t = 0; // temp

            for (int aa = 0; aa <= 19; aa++) {
                // temp = leftrotate(a leftrotate 5) + f(t) + e + w[i] + k
                t = leftrotate(A, 5) + ((B & C) | ((~B) & D)) + E + W[aa] + k1;
                E = D;
                D = C;
                C = leftrotate(B, 30);
                B = A;
                A = t;
            }
            for (int b = 20; b <= 39; b++) {
                t = leftrotate(A, 5) + (B ^ C ^ D) + E + W[b] + k2;
                E = D;
                D = C;
                C = leftrotate(B, 30);
                B = A;
                A = t;
            }
            for (int c = 40; c <= 59; c++) {
                t = leftrotate(A, 5) + ((B & C) | (B & D) | (C & D)) + E + W[c] + k3;
                E = D;
                D = C;
                C = leftrotate(B, 30);
                B = A;
                A = t;
            }
            for (int d = 60; d <= 79; d++) {
                t = leftrotate(A, 5) + (B ^ C ^ D) + E + W[d] + k4;
                E = D;
                D = C;
                C = leftrotate(B, 30);
                B = A;
                A = t;
            }

            h1 += A;
            h2 += B;
            h3 += C;
            h4 += D;
            h5 += E;

        }

        String h1Length = Integer.toHexString(h1);
        String h2Length = Integer.toHexString(h2);
        String h3Length = Integer.toHexString(h3);
        String h4Length = Integer.toHexString(h4);
        String h5Length = Integer.toHexString(h5);
        // System.out.println(h1Length.length());

        // Integer.toHexString does not include extra leading 0's
        if (h1Length.length() < 8) {
            StringBuilder h1L = new StringBuilder(h1Length);
            h1L.insert(0, 0);
            h1Length = h1L.toString();
        } else if (h2Length.length() < 8) {
            StringBuilder h2L = new StringBuilder(h2Length);
            h2L.insert(0, 0);
            h2Length = h2L.toString();
        } else if (h3Length.length() < 8) {
            StringBuilder h3L = new StringBuilder(h3Length);
            h3L.insert(0, 0);
            h3Length = h3L.toString();
        } else if (h4Length.length() < 8) {
            StringBuilder h4L = new StringBuilder(h4Length);
            h4L.insert(0, 0);
            h4Length = h4L.toString();
        } else if (h5Length.length() < 8) {
            StringBuilder h5L = new StringBuilder(h5Length);
            h5L.insert(0, 0);
            h5Length = h5L.toString();
        }

        // result
        String hh = h1Length + h2Length + h3Length + h4Length + h5Length;
        System.out.println("Result of hash 'sha1'  160 bit in hexa: " + hh);

        BigInteger b1 = new BigInteger(hh, 16);
        // System.out.println("bi:"+b1);
        BigInteger b2 = new BigInteger("29");
        BigInteger shaofX = b1.mod(b2);
        System.out.println("hash integer num mod q: " + shaofX);
        System.out.println("");
        int sha_ofX = shaofX.intValue();
        return sha_ofX;
    }

    public static int GCD(int Ke, int p) {
        if (Ke == 0) {
            return p;
        } else
            return GCD(p % Ke, Ke);
    }

    public static int modinv(int a, int p) {
        int x = 0;
        for (int i = 1; i < p; i++) {
            if ((a * i) % p == 1) {
                x = i;
                break;
            }
        }
        return x;
    }

    public static int Select_KE(int p) {

        int ke = 0;
        for (int i = 0; i < p - 2; i++) {
            int z = getRandomNumber(2, p - 2);
            if (GCD(p - 1, z) == 1) {
                ke = z;
                break;
            }
        }
        return ke;
    }

    public static int phi(int n) {
        int result = 1;

        for (int i = 2; i < n; i++)
            if (GCD(i, n) == 1)
                result++;

        return result;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static int Square_and_Multiply(int base, int pow, int m) {
        int res = 1;
        while (pow > 0) {
            if ((pow % 2) == 1) {
                res = (res * base) % m;
                pow--;
            } else {
                base = (base * base) % m;
                pow = pow / 2;
            }
        }
        return res;
    }

    public static int generate_alpha(int q) {
        for (int x = 2; x < q; x++) {
            if (Square_and_Multiply(x, q - 1, q) == 1)
                return x;
        }
        return 1;
    }

    public static boolean isPrime(int p, int s) {

        if (p == 0 || p == 1)
            return false;

        if (p == 2)
            return true;

        if (p % 2 == 0)
            return false;

        // Random rand = new Random();
        for (int i = 0; i < s; i++) {
            int a = getRandomNumber(2, p - 1);
            if (Square_and_Multiply(a, p - 1, p) != 1)
                return false;
        }

        return true;
    }

    public static void dsa(int x) {

        int p = 59;
        int q = 29;

        // generate_alpha(int q)
        int alpha = 3;

        if (isPrime(p, 4) && isPrime(q, 4)) {
            int d = 7;

            // int d=getRandomNumber(2,p-2);
            int B = Square_and_Multiply(alpha, d, p);

            // System.out.println("B:" +B);
            // comput signature for message

            // random from from 0 to q
            int KE = 10;
            // int KE=getRandomNumber(0,q);

            int r = Square_and_Multiply(alpha, KE, p);
            r = r % q;
            // System.out.println("r:" +r);
            int sum = (x + (d * r));
            // System.out.println("(x+(d*r))" +sum);
            int invKE = modinv(KE, q);
            // System.out.println("invKE" +invKE);
            int s = (sum * invKE);
            while (s < 0)
                s += (q);
            s = s % (q);
            // System.out.println("s:" +s);
            int w = modinv(s, q);
            // System.out.println("W:" +w);
            int u1 = (w * x) % q;
            // System.out.println("U1:"+u1);
            int u2 = w * r % q;
            // System.out.println("U2:"+u2);
            // verify
            int sum2 = Square_and_Multiply(alpha, u1, p);
            int sum3 = Square_and_Multiply(B, u2, p);
            int t = (sum2 * sum3) % p;
            t = t % q;
            System.out.println("T:" + t);
            int check = Square_and_Multiply(alpha, x, p);
            System.out.println("r:" + r);

            if (t == r)
                System.out.println("valid signature");
            else
                System.out.println("invalid signature");

        } else {
            System.out.println("enter large prime please");
        }
    }

    public static void main(String[] args) {

        String k = "bebo";
        String x = "ahm yas";
        int a[] = generate_chunk(x);
        int key[] = generate_chunk(k);
        String ip = "00110110";
        String op = "01011100";
        int ipad[] = null;
        int opad[] = null;
        for (int i = 0; i < 64; i++) {
            ipad[i] += ip.charAt(i);
        }
        for (int i = 0; i < 64; i++) {
            opad[i] += op.charAt(i);
        }
        int xorIpad;
        int xorOpad;
        int[] hashh = null;
        int[] hash2;
        for (int i = 0; i < ipad.length; i++) {
            xorIpad = key[i] ^ ipad[i];
            hashh[i] = a[i];
        }
        int haship[] = null;
        int hashop[] = null;
        for (int i = 0; i < ipad.length; i++) {
            haship[i] = ipad[i] + hashh[i];
        }
        int hash1 = hash(haship);
        for (int i = 0; i < opad.length; i++) {
            xorOpad = key[i] ^ opad[i];
        }
        for (int i = 0; i < opad.length; i++) {
            hashop[i] = opad[i] + hashh[i];
        }
        int hash11 = hash(hashop);
        dsa(hash11);
    }
}
