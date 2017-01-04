package com.example.amolishah.bestprice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class EncryptTechniques extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt_techniques);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public class PlayfairCipher {
        private String KeyWord = new String();
        private String Key = new String();
        private char matrix_arr[][] = new char[5][5];

        public void setKey(String k) {
            String K_adjust = new String();
            boolean flag = false;
            K_adjust = K_adjust + k.charAt(0);
            for (int i = 1; i < k.length(); i++) {
                for (int j = 0; j < K_adjust.length(); j++) {
                    if (k.charAt(i) == K_adjust.charAt(j)) {
                        flag = true;
                    }
                }
                if (flag == false)
                    K_adjust = K_adjust + k.charAt(i);
                flag = false;
            }
            KeyWord = K_adjust;
        }

        public void KeyGen() {
            boolean flag = true;
            char current;
            Key = KeyWord;
            for (int i = 0; i < 26; i++) {
                current = (char) (i + 97);
                if (current == 'j')
                    continue;
                for (int j = 0; j < KeyWord.length(); j++) {
                    if (current == KeyWord.charAt(j)) {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    Key = Key + current;
                flag = true;
            }
            matrix();
        }

        private void matrix() {
            int counter = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    matrix_arr[i][j] = Key.charAt(counter);
                    counter++;
                }
            }
        }

        private String format(String old_text) {
            int i = 0;
            int len = 0;
            String text = new String();
            len = old_text.length();
            for (int tmp = 0; tmp < len; tmp++) {
                if (old_text.charAt(tmp) == 'j') {
                    text = text + 'i';
                } else
                    text = text + old_text.charAt(tmp);
            }
            len = text.length();
            for (i = 0; i < len; i = i + 2) {
                if (text.charAt(i + 1) == text.charAt(i)) {
                    text = text.substring(0, i + 1) + 'x' + text.substring(i + 1);
                }
            }
            return text;
        }

        private String[] Divid2Pairs(String new_string) {
            String Original = format(new_string);
            int size = Original.length();
            if (size % 2 != 0) {
                size++;
                Original = Original + 'x';
            }
            String x[] = new String[size / 2];
            int counter = 0;
            for (int i = 0; i < size / 2; i++) {
                x[i] = Original.substring(counter, counter + 2);
                counter = counter + 2;
            }
            return x;
        }

        public int[] GetDiminsions(char letter) {
            int[] key = new int[2];
            if (letter == 'j')
                letter = 'i';
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (matrix_arr[i][j] == letter) {
                        key[0] = i;
                        key[1] = j;
                        break;
                    }
                }
            }
            return key;
        }

        public String encryptMessage(String Source) {
            String src_arr[] = Divid2Pairs(Source);
            String Code = new String();
            char one;
            char two;
            int part1[] = new int[2];
            int part2[] = new int[2];
            for (int i = 0; i < src_arr.length; i++) {
                one = src_arr[i].charAt(0);
                two = src_arr[i].charAt(1);
                part1 = GetDiminsions(one);
                part2 = GetDiminsions(two);
                if (part1[0] == part2[0]) {
                    if (part1[1] < 4)
                        part1[1]++;
                    else
                        part1[1] = 0;
                    if (part2[1] < 4)
                        part2[1]++;
                    else
                        part2[1] = 0;
                } else if (part1[1] == part2[1]) {
                    if (part1[0] < 4)
                        part1[0]++;
                    else
                        part1[0] = 0;
                    if (part2[0] < 4)
                        part2[0]++;
                    else
                        part2[0] = 0;
                } else {
                    int temp = part1[1];
                    part1[1] = part2[1];
                    part2[1] = temp;
                }
                Code = Code + matrix_arr[part1[0]][part1[1]]
                        + matrix_arr[part2[0]][part2[1]];
            }
            return Code;
        }
    }
        public void PFC (View view)
    {
        String text="";
        PlayfairCipher x = new PlayfairCipher();
        Intent a = getIntent();
        String key_input="";
        String keyword="";
        if(a != null) {
            key_input = a.getStringExtra("text");
            keyword = getIntent().getStringExtra("key");
        }
        x.setKey(keyword);
        x.KeyGen();
        if (key_input.length() % 2 != 0)
            key_input += "x";
        text = x.encryptMessage(key_input);
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
    public class HillCipher {
        int keymatrix[][];
        int linematrix[];
        int resultmatrix[];

        public String  divide(String temp, int s) {
            String u ="";
            while (temp.length() > s) {
                String sub = temp.substring(0, s);
                temp = temp.substring(s, temp.length());
                u  = u +  perform(sub);
            }
            if (temp.length() == s)
                u = u + perform(temp);
            else if (temp.length() < s) {
                for (int i = temp.length(); i < s; i++)
                    temp = temp + 'x';
                u = u + perform(temp);
            }
            return u;
        }

        public String perform(String line) {
            linetomatrix(line);
            linemultiplykey(line.length());
            String t =result(line.length());
            return  t;
        }

        public void keytomatrix(String key, int len) {
            keymatrix = new int[len][len];
            int c = 0;
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    keymatrix[i][j] = ((int) key.charAt(c)) - 97;
                    c++;
                }
            }
        }

        public void linetomatrix(String line) {
            linematrix = new int[line.length()];
            for (int i = 0; i < line.length(); i++) {
                linematrix[i] = ((int) line.charAt(i)) - 97;
            }
        }

        public void linemultiplykey(int len) {
            resultmatrix = new int[len];
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    resultmatrix[i] += keymatrix[i][j] * linematrix[j];
                }
                resultmatrix[i] %= 26;
            }
        }

        public String result(int len) {
            String result = "";
            for (int i = 0; i < len; i++) {
                result += (char) (resultmatrix[i] + 97);
            }
             return (result);
        }

        public boolean check(String key, int len) {
            keytomatrix(key, len);
            int d = determinant(keymatrix, len);
            d = d % 26;
            if (d == 0) {
                Log.i("key","Invalid key!!! Key is not invertible because determinant=0...");
                return false;
            } else if (d % 2 == 0 || d % 13 == 0) {
                Log.i("key","Invalid key!!! Key is not invertible because determinant has common factor with 26...");
                return false;
            } else {
                return true;
            }
        }

        public int determinant(int A[][], int N) {
            int res;
            if (N == 1)
                res = A[0][0];
            else if (N == 2) {
                res = A[0][0] * A[1][1] - A[1][0] * A[0][1];
            } else {
                res = 0;
                for (int j1 = 0; j1 < N; j1++) {
                    int m[][] = new int[N - 1][N - 1];
                    for (int i = 1; i < N; i++) {
                        int j2 = 0;
                        for (int j = 0; j < N; j++) {
                            if (j == j1)
                                continue;
                            m[i - 1][j2] = A[i][j];
                            j2++;
                        }
                    }
                    res += Math.pow(-1.0, 1.0 + j1 + 1.0) * A[0][j1]
                            * determinant(m, N - 1);
                }
            }
            return res;
        }

    }
    public void HFC (View view)
    {
        HillCipher obj = new HillCipher();
        Intent a = getIntent();
        String key_input="";
        String keyword="";
        if(a != null) {
            key_input = a.getStringExtra("text");
            keyword = getIntent().getStringExtra("key");
        }
        double sq = Math.sqrt(keyword.length());
        if (sq != (long) sq)
            Log.i("Key length","Not Proper key length");
        else
        {
            int s = (int) sq;
            if (obj.check(keyword, s))
            {
                String y = obj.divide(key_input, s);
                Toast.makeText(this,y,Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class VigenereCipher
    {
        private String key;
        public VigenereCipher(String key) {
            setKey(key);
        }
        public void setKey(String key) {

            if(key == null) {
                this.key = "";
                return;
            }
            char[] digit = key.toUpperCase().toCharArray();
            StringBuilder sb = new StringBuilder(digit.length);
            for(char c : digit) {
                if(c >= 'A' && c <= 'Z')
                    sb.append(c);
            }
            this.key = sb.toString();
        }
        public String encode(String clear) {
            // ignore if null
            if(clear == null)
                return "";
            if(key.length() == 0)

                return clear.toUpperCase();

            char[] digit = clear.toLowerCase().toCharArray();
            String longKey = key;
            while(longKey.length() < clear.length())
                longKey += key;
            for(int i = 0; i < digit.length; i++) {
                if(digit[i] < 'a' || digit[i] > 'z')
                    continue;
                char offset = longKey.charAt(i);
                int nbShift = offset - 'A';
                digit[i] = Character.toUpperCase(digit[i]);
                digit[i] += nbShift;
                if(digit[i] > 'Z') {
                    digit[i] -= 'Z';
                    digit[i] += ('A' - 1);
                }
            }
            return new String(digit);
        }
    }
    public void VC (View view)
    {
        String text="";
        Intent a = getIntent();
        String key_input="";
        String keyword="";
        if(a != null) {
            key_input = a.getStringExtra("text");
            keyword = getIntent().getStringExtra("key");
        }
        VigenereCipher x = new VigenereCipher(keyword);
        text =  x.encode(key_input);
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
    public class CaesarCipher {
        public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

        public String encrypt(String plainText, int shiftKey)
        {
            plainText = plainText.toLowerCase();
            String cipherText = "";
            for (int i = 0; i < plainText.length(); i++)
            {
                int charPosition = ALPHABET.indexOf(plainText.charAt(i));
                int keyVal = (shiftKey + charPosition) % 26;
                char replaceVal = ALPHABET.charAt(keyVal);
                cipherText += replaceVal;
            }
            return cipherText;
        }
    }
    public void CC (View view)
    {
        String text="";
        CaesarCipher x = new CaesarCipher();
        Intent a = getIntent();
        String key_input="";
        String keyword="";
        if(a != null) {
            key_input = a.getStringExtra("text");
            keyword = getIntent().getStringExtra("key");
        }
        text = x.encrypt(key_input,Integer.parseInt(keyword));
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
