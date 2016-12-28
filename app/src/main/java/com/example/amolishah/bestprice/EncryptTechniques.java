package com.example.amolishah.bestprice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    public class encryption{
        String key;
        String key2;
        String text;
        char key_array[][]=new char[5][5];
        public void keySetter(String k)
        {
            String str=new String();
            boolean test=false;
            str=str+k.charAt(0);
            for(int i=1;i<k.length();i++)
            {
                for(int j=0;j<str.length();j++)
                    if(k.charAt(i)==str.charAt(j) || k.charAt(i)=='j')
                        test=true;
                if(!test)
                    str=str+k.charAt(i);
                test=false;
            }
            key=str;
            matrixBuilder(key);
        }
        public void matrixBuilder(String k)
        {
            key2=key2+key;
            boolean test=false;
            char current;
            for(int i=0;i<26;i++)
            {
                current=(char)(i+97);
                for(int j=0;j<key.length();j++)
                    if(current=='j' || current==key.charAt(j))
                        test=true;
                if(!test)
                    key2=key2+current;
                test=false;
            }
          //  System.out.println(key2);
            for(int i=0;i<5;i++)
                for(int j=0;j<5;j++)
                    key_array[i][j]=key2.charAt(i*5+j);
           /* for(int i=0;i<5;i++)
            {
                for(int j=0;j<5;j++)
                   System.out.print(key_array[i][j]+" ");
                System.out.println();
            }*/
        }
        public void stringConversion(String input)
        {
         //  String altered=input;//.replace('j','i');
            for(int i=0;i<input.length();i++)
                if(i>0 && input.charAt(i)==input.charAt(i-1))
                    input=input.substring(0,i)+'x'+input.substring(i);
            if((input.length()%2)!=0)
               input=input+'x';
            text=input;
         //   System.out.println(text);

        }
        public int[] getDimensions(char letter)
        {
            int key[]=new int[2];
            for (int i=0 ; i<5 ;i++)
                for (int j=0 ; j<5 ; j++)
                    if(key_array[i][j] == letter)
                    {
                        key[0]=i;
                        key[1]=j;
                        break;
                    }
            return key;
        }
        public void Encrypt()
        {
            char a,b;
            String Code="";
            int c[];
            int d[];
            for(int i=0;i<text.length();i=i+2)
            {
                a=text.charAt(i);
                b=text.charAt(i+1);
                c=getDimensions(a);
                d=getDimensions(b);
                if(c[0]==d[0])
                {
                    if (c[1]<4)
                        c[1]++;
                    else
                        c[1]=0;
                    if(d[1]<4)
                        d[1]++;
                    else
                        d[1]=0;
                }
                else if(c[1]==d[1])
                {
                    if (c[0]<4)
                        c[0]++;
                    else
                        c[0]=0;
                    if(d[0]<4)
                        d[0]++;
                    else
                        d[0]=0;
                }
                else
                {
                    int temp=c[1];
                    c[1]=d[1];
                    d[1]=temp;
                }
                Code=Code+key_array[c[0]][c[1]]+key_array[d[0]][d[1]];
            }
            System.out.println("Encrypted text:"+Code);
        }

    }
    public void PFC (View view)
    {
        Toast.makeText(this,"hello1",Toast.LENGTH_SHORT).show();
        Intent a = this.getIntent();
        String s2="";
        if(a != null)
            s2 = a.getStringExtra("text");
        Toast.makeText(this,"hello2",Toast.LENGTH_SHORT).show();
        // String s4 = getIntent().getStringExtra("key");
        encryption p=new encryption();
      //  String s2 = "key";
        String s4 = "text";
        p.keySetter(s4);
        p.stringConversion(s2);
        p.Encrypt();
        Toast.makeText(this,"hello3",Toast.LENGTH_SHORT).show();
    }
}
