package com.example.numberguessgame;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FrameLayout player1fragment,player2fragment;
    private TextView player1number,player2number,gameResult;
    public static final int MAIN_VARIABLE = 0 ;
    public static final int PLAYER1_RESPONSE=11;
    public static final int PLAYER2_RESPONSE=22;

    public static final int PLAYER1_GUESS=1111;
    public static final int PLAYER2_GUESS=2222;

    Thread player1,player2;

    public static int countp1=0;
    public static int countp2=0;

    long player1num=0;
    long player2num=0;


    int[] player2g = new int[4];
    int[] player2oldGuess = new int[4];

    ArrayList<Integer> okNumbersP2 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    ArrayList<Integer> okNumbersP1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    FragmentManager mFragmentManager;
    Button start;
    long pn1;
    long pn2;
    private static final String TAG = "MainActivity";
    ListAdapter adapterp1,adapterp2;

    ListView player1list,player2list;
    ArrayList<ListItem> player1listitems,player2listitems;

    public static Handler player2Handler;
    public static Handler player1Handler;

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            int what=msg.what;
            Message p2msg;
            Message p1msg;
            boolean state=checkGameStatus(msg);
            if (state){
            switch (what) {
                case PLAYER1_RESPONSE:
                    p2msg= player2Handler.obtainMessage(Player2Thread.PLAYER2CODE) ;
                    p2msg=obtainMessage(PLAYER1_RESPONSE);
                    p2msg.obj=msg.obj;
                    player2Handler.sendMessage(p2msg) ;
                    break;
                case PLAYER2_RESPONSE:
                    p1msg = player1Handler.obtainMessage(Player1Thread.PLAYER1CODE) ;
                    p1msg=obtainMessage(PLAYER2_RESPONSE);
                    p1msg.obj=msg.obj;
                    player1Handler.sendMessage(p1msg) ;
                    break;
                case PLAYER1_GUESS:
                    p2msg = player2Handler.obtainMessage(Player2Thread.PLAYER2CODE) ;
                    p2msg=obtainMessage(PLAYER1_GUESS);
                    p2msg.arg1=msg.arg1;
                    player2Handler.sendMessage(p2msg) ;
                    break;
                case PLAYER2_GUESS:
                    p1msg = player1Handler.obtainMessage(Player1Thread.PLAYER1CODE) ;
                    p1msg=obtainMessage(PLAYER2_GUESS);
                    p1msg.arg1=msg.arg1;
                    player1Handler.sendMessage(p1msg) ;
                    break;
            }
        }}
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");

        setContentView(R.layout.activity_main);
        player1fragment = (FrameLayout) findViewById(R.id.player1);
        player2fragment = (FrameLayout) findViewById(R.id.player2);

        player1list=(ListView) findViewById(R.id.player1list);
        player2list=(ListView) findViewById(R.id.player2list);

        player1number=(TextView) findViewById(R.id.player1number);
        player2number=(TextView) findViewById(R.id.player2number);

        gameResult=(TextView) findViewById(R.id.gameResult);

        start=(Button) findViewById(R.id.restartG);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });

    }



    public void restartGame()
    {
        if(player1!=null && player2!=null) {
            System.out.println("====I cleared everything====");
            mHandler.removeCallbacksAndMessages(null);
            player1Handler.removeCallbacksAndMessages(null);
            player1Handler.removeCallbacksAndMessages(null);

            player1Handler.getLooper().quitSafely();
            player2Handler.getLooper().quitSafely();

            adapterp1.clear();
            adapterp2.clear();

            okNumbersP1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
            
        }
        player1listitems = new ArrayList<ListItem>();
        adapterp1 = new ListAdapter(this, player1listitems);
        player1list.setAdapter(adapterp1);
        player2listitems = new ArrayList<ListItem>();
        adapterp2 = new ListAdapter(this, player2listitems);
        player2list.setAdapter(adapterp2);
        player1num=0;
        player2num=0;
        countp1 = 0;
        countp2=0;
        gameResult.setText("");
        player1 = new Thread(new Player1Thread());
        player2 = new Thread(new Player2Thread());
        player1.start();
        player2.start();
    }

    public void endGame()
    {
        player1Handler.getLooper().quitSafely();
        player2Handler.getLooper().quitSafely();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }




    public class Player1Thread implements Runnable{
        private static final int PLAYER1CODE=111;

        long player2guess=0;
        ArrayList<Integer> player2res;
        ArrayList<Integer> player1res;
        public void run()
        {

            mHandler.post(new Runnable() {
                public void run() {
                    player1num=getUnique();
                    System.out.println("=====Player 1 set the player1 number");
                    player1number.setText(String.valueOf(player1num));
                }
            } ) ;

            System.out.println("===player1num="+player1num+ " player2num="+player2num);
            System.out.println(countp1+ " "+countp2);
            while(countp1!=countp2) {
                SystemClock.sleep(300);
            }
            System.out.println("=====Player 1 started");


            Message msgMain = mHandler.obtainMessage(MainActivity.MAIN_VARIABLE) ;
            msgMain=mHandler.obtainMessage(PLAYER1_GUESS);
            msgMain.arg1=guessPlayer1(countp1,new ArrayList<>());
            System.out.println("=====Player 1 sent a guess number "+countp1);

            mHandler.sendMessage(msgMain) ;

            Looper.prepare();
            player1Handler=new Handler(Looper.myLooper())
            {
                public void handleMessage(Message msg)
                {
                    int what=msg.what;
                    switch (what) {
                        case PLAYER2_RESPONSE:
                            System.out.println("=====Player 1 got a feedback from player 2 and saved the response");
                            player2res=(ArrayList<Integer>) msg.obj;
                            break;
                        case PLAYER2_GUESS:
                            player2guess=msg.arg1;
                            while(player1num==0)
                            {
                                SystemClock.sleep(300);
                            }
                            player1res=genResponse(player1num,player2guess);
                            Message msgforplayer2 = mHandler.obtainMessage(MainActivity.MAIN_VARIABLE) ;
                            msgforplayer2=mHandler.obtainMessage(PLAYER1_RESPONSE);
                            msgforplayer2.obj=player1res;
                            mHandler.sendMessage(msgforplayer2) ;

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("=====Player 1 updated the list with player 2 guess and player1s response");
                                    ListItem listItem=new ListItem(String.valueOf(player2guess),"CN="+player1res.get(1)+" ICN="+player1res.get(2)+" Not In Actual="+player1res.get(3));
                                    adapterp1.add(listItem);
                                }
                            });

                            //waiting for human to read
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            if(countp1<=20) {
                                //sending a guess
                                System.out.println("=====Player 1 sent a guess number "+countp1);
                                Message msgMainP1Guess = mHandler.obtainMessage(MainActivity.PLAYER1_GUESS);
                                msgMainP1Guess.arg1 = guessPlayer1(countp1+1,player2res);
                                mHandler.sendMessage(msgMainP1Guess);
                                countp1++;
                            }
                            break;
                    }
                }
            };


            Looper.loop();

        }
    }

    public class Player2Thread implements Runnable{
        private static final int PLAYER2CODE=222;
        long player1guess=0;
        ArrayList<Integer> player2res;
        ArrayList<Integer> player1res;

        public void run()
        {
            System.out.println("====Player 2 started");
            mHandler.post(new Runnable() {
                public void run() {
                    System.out.println("=====Player 2 set the player 2 number");
                    player2num=getUnique();
                    player2number.setText(String.valueOf(player2num));
                }
            } ) ;

            Looper.prepare();
            player2Handler=new Handler(Looper.myLooper())
            {
                public void handleMessage(Message msg)
                {

                    int what=msg.what;
                    //waiting for other thread to process the guess
                    while (countp1<countp2 && player1Handler.hasMessages(PLAYER2_GUESS))
                    {
                        SystemClock.sleep(2000);
                    }
                    switch (what) {
                        case PLAYER1_RESPONSE:
                            player1res=(ArrayList<Integer>) msg.obj;
                            System.out.println("=====Player 2 got a feedback from player 1 and saved the response");
                            break;
                        case PLAYER1_GUESS:
                            player1guess=msg.arg1;
                            player2res=genResponse(player2num,player1guess);
                            Message msgforplayer1 = mHandler.obtainMessage(MainActivity.MAIN_VARIABLE) ;
                            msgforplayer1=mHandler.obtainMessage(PLAYER2_RESPONSE);
                            msgforplayer1.obj=player2res;
                            mHandler.sendMessage(msgforplayer1) ;

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("=====Player 2 updated the list with player 1 guess and player2s response");
                                    ListItem listItem=new ListItem(String.valueOf(player1guess),"CN="+player2res.get(1)+" ICN="+player2res.get(2)+" Not In Actual="+player2res.get(3));
                                    adapterp2.add(listItem);
                                }
                            });

                            //waiting for human to read
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if(countp2<=20) {
                                //sending a guess
                                System.out.println("=====Player 2 sent a guess number "+countp2);
                                Message msgMainP2Guess = mHandler.obtainMessage(MainActivity.PLAYER2_GUESS);
                                msgMainP2Guess.arg1 = guessPlayer2();
                                mHandler.sendMessage(msgMainP2Guess);
                                countp2++;
                            }
                            break;
                    }
                }
            };

            while (player1num==0)
            {
                SystemClock.sleep(1000);
            }
            Looper.loop();
        }
    }

    public static ArrayList genResponse(long actual, long guess){
        ArrayList<Integer> resArr=new ArrayList();
        ArrayList<String> rn = new ArrayList();
        String notIn = new String();
        System.out.println("For actual=" + actual + " for guessed=" + guess);

        if(actual==guess)
        {
            resArr.add(1);
            resArr.add(4);
            resArr.add(0);
            resArr.add(10);

            return resArr;
        }
        else {
            String a = String.valueOf(actual);
            String g = String.valueOf(guess);

            Boolean flag = false;
            Boolean flag2 = false;
            int correctCount = 0;
            int incorrectPosCount = 0;
            String digitA0 = String.valueOf(a.charAt(0));
            String digitA1 = String.valueOf(a.charAt(1));
            String digitA2 = String.valueOf(a.charAt(2));
            String digitA3 = String.valueOf(a.charAt(3));

            String digitG0 = String.valueOf(g.charAt(0));
            String digitG1 = String.valueOf(g.charAt(1));
            String digitG2 = String.valueOf(g.charAt(2));
            String digitG3 = String.valueOf(g.charAt(3));

            ArrayList<String> ga = new ArrayList();

            ga.add(digitA0);
            ga.add(digitA1);
            ga.add(digitA2);
            ga.add(digitA3);



            if (!ga.contains(digitG0)) {
                rn.add(digitG0);
                flag = true;
            }

            if (!ga.contains(digitG1)) {
                rn.add(digitG1);
                flag = true;
            }

            if (!ga.contains(digitG2)) {
                rn.add(digitG2);
                flag = true;
            }

            if (!ga.contains(digitG3)) {
                rn.add(digitG3);
                flag = true;
            }



            if (flag) {
                int ntg = rn.size();

                int myrn = getRandomNumberUsingNextInt(0, ntg);

                notIn = rn.get(myrn);

            }


            if (digitA0.equals(digitG0)) {
                correctCount++;

            }

            if (digitA1.equals(digitG1)) {
                correctCount++;

            }

            if (digitA2.equals(digitG2)) {
                correctCount++;

            }

            if (digitA3.equals(digitG3)) {
                correctCount++;

            }

            if (digitA0.equals(digitG1)) {
                incorrectPosCount++;
            }

            if (digitA0.equals(digitG2)) {
                incorrectPosCount++;
            }

            if (digitA0.equals(digitG3)) {
                incorrectPosCount++;
            }

            if (digitA1.equals(digitG0)) {
                incorrectPosCount++;
            }

            if (digitA1.equals(digitG2)) {
                incorrectPosCount++;
            }

            if (digitA1.equals(digitG3)) {
                incorrectPosCount++;
            }

            if (digitA2.equals(digitG0)) {
                incorrectPosCount++;
            }

            if (digitA2.equals(digitG3)) {
                incorrectPosCount++;
            }

            if (digitA2.equals(digitG1)) {
                incorrectPosCount++;
            }

            if (digitA3.equals(digitG0)) {
                incorrectPosCount++;
            }

            if (digitA3.equals(digitG1)) {
                incorrectPosCount++;
            }

            if (digitA3.equals(digitG2)) {
                incorrectPosCount++;
            }

            resArr.add(0);
            resArr.add(correctCount);
            resArr.add(incorrectPosCount);

            if(notIn.equals(""))
            {
                resArr.add(null);
            }
            else {
                resArr.add(Integer.parseInt(notIn));
            }
            System.out.println(" Correct count =" + correctCount);
            System.out.println(" InCorrect count =" + incorrectPosCount);

            return resArr;
        }
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static long getUnique() {
        int ndigits=4;
        String digits = "0123456789";
        long num = 0;
        for (int i = 0; i < ndigits; i++) {
            int d = (int) (Math.random() * digits.length());
            if (d == 0 && i == 0) {
                i--;
                continue;
            }
            // convert character to an int and "append" to the num.
            num = num * 10 + digits.charAt(d) - '0';

            // effectively delete the just used digit from the string.
            digits = digits.substring(0, d) + digits.substring(d + 1);
        }

        return num;
    }


    String arrayListToString(ArrayList<Integer> arrayList){
        String output="";
        if(arrayList.size() != 0) {
            output = String.valueOf(arrayList.get(0));
            for (int i = 1; i < arrayList.size(); i++) {
                output += "" + String.valueOf(arrayList.get(i));
            }
        }
        return output;
    }

    String intArrayToString(int[] a){
        String output = Integer.toString(a[0]);
        for(int i=1; i<a.length;i++) {
            output = output + Integer.toString(a[i]);
        }
        return output;
    }


    //Just generated different number from last time
    int guessPlayer2(){
        int guess;
        Collections.shuffle(okNumbersP2);
        for(int i=0; i<4; i++){
            player2g[i] = okNumbersP2.get(i);
            Log.i("p2guess " + i,""+ player2g[i]);
            player2oldGuess[i] = okNumbersP2.get(i);
        }
        if(player2g[0] == 0){
            guess = guessPlayer2();
        }
        guess = Integer.parseInt(intArrayToString(player2g));

        return guess;
    }

    public boolean checkGameStatus(Message msg)
    {
        if(msg.what==PLAYER1_GUESS)
        {
            if(player2num==msg.arg1)
            {

                gameResult.setText("Game Over!! Player1 Guessed "+msg.arg1+" and Won");
                endGame();
                return false;
            }
        }
        if(msg.what==PLAYER2_GUESS)
        {
            if(player1num==msg.arg1)
            {

                gameResult.setText("Game Over!! Player2 Guessed "+msg.arg1+" and Won");
                endGame();
                return false;
            }
        }
        if(countp1>=20 & countp2>=20)
        {

                    gameResult.setText("Game Over!! No winner");
                    return false;

        }
        else{

                    gameResult.setText("Game needs to continue");
            return true;

        }
    }


    public static int p1guess=7584;

    //filters based on response from other thread
    public int guessPlayer1(int count,ArrayList<Integer> feedback)
    {

        int guess=p1guess;
        if(count==0){
            init();
            return 1230;
        }
        else if(count==1){
            Integer notIn=feedback.get(3);

            if(notIn==null)
            {
                guess=(int)getUnique();
            }
            else{
                filter(notIn);
                System.out.println("Size of okNumbersp1==="+okNumbersP1.size());
            }
            return 4567;
        }
        else if(count==2)
        {
            Integer notIn=feedback.get(3);

            if(notIn==null)
            {
                guess=(int)getUnique();
            }
            else{
                filter(notIn);
                System.out.println("Size of okNumbersp1==="+okNumbersP1.size());
            }
            return 8970;
        }
        else {
            int cgn=feedback.get(1);
            int ign=feedback.get(2);
            Integer notIn=feedback.get(3);

            if(notIn==null)
            {
                guess=(int)getUnique();
            }
            else{
                filter(notIn);
            }

        }

        int cgn=feedback.get(1);
        int ign=feedback.get(2);
        Integer notIn=feedback.get(3);
        if(notIn==null)
        {
            guess=(int)getUnique();
        }
        else{
            System.out.println("not in===="+notIn);
            filter(notIn);
        }
        System.out.println("valid size=="+valid.size());


            guess = Integer.parseInt(valid.get(getRandomNumberUsingNextInt(0, valid.size() - 1)));


        System.out.println("I return guess==="+guess);
        return guess;
    }



    public static ArrayList<String> valid=new ArrayList<>();
    public void init()
    {
        for (int i=1023; i<=9876; i++)
        {
            int arr[]=intTointArray(i);
            String s=intArrayToString(arr);
            if(s.charAt(0) ==s.charAt(1))
            {
                continue;
            }
            if(s.charAt(0)==s.charAt(2))
            {
                continue;
            }
            if(s.charAt(0)==s.charAt(3))
            {
                continue;
            }
            if(s.charAt(1)==s.charAt(2))
            {
                continue;

            }
            if(s.charAt(1)==s.charAt(3))
            {
                continue;
            }
            if(s.charAt(2)==s.charAt(3))
            {
                continue;
            }
            valid.add(s);
        }
    }

    public void filter(int a)
    {
        ArrayList<String> validCopy=new ArrayList<>(valid);
        for(String s:validCopy)
        {
            if(s.contains(String.valueOf(a)))
            {
                valid.remove(s);

            }
        }
    }

    int[] intTointArray(int a){
        String temp = Integer.toString(a);
        int[] arr1 = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++)
        {
            arr1[i] = temp.charAt(i) - '0';
        }
        return arr1;
    }


}