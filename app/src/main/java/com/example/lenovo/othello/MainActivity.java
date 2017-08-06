package com.example.lenovo.othello;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int player1=1;
    public static final int draw=3;
    public static final int player2=2;
    public static final int empty=0;
    public static Drawable player1symbol;
    public static Drawable player2symbol;
    public static int player1color=R.color.black;
    public static int player2color=R.color.golden;
    public static int NoPlayercolor=R.color.white;
    public static boolean player1Turn=true;
    public static int boardSize=6;
    public static OthelloButton buttons[][];
    public static LinearLayout mainLayout;
    public static LinearLayout rowLayout[];
    HashMap<Integer,String> map=new HashMap<>();
    public static boolean gameover=false;
    public static final int yArray[]={-1,-1,-1,0,1,1,1,0};
    public static final int xArray[]={-1,0,1,1,1,0,-1,-1};
   // int countNonSimultaneous =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout=(LinearLayout)findViewById(R.id.Main_LinearLayout);
        player1symbol=  ContextCompat.getDrawable(this,R.drawable.shape_blackring);
        player2symbol =ContextCompat.getDrawable(this,R.drawable.shape_goldenring);
        setBoard();
    }

    private void setBoard() {
        mainLayout.removeAllViews();
        rowLayout=new LinearLayout[boardSize];
        for(int i=0;i<boardSize;i++){
            rowLayout[i]=new LinearLayout(this);
            LinearLayout.LayoutParams paras=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1f);
            paras.setMargins(5,5,5,5);
            rowLayout[i].setLayoutParams(paras);
            rowLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rowLayout[i]);
        }
       int key=0;
        buttons=new OthelloButton[boardSize][boardSize];
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
              buttons[i][j]=new OthelloButton(this);
                LinearLayout.LayoutParams paras=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1f);
                paras.setMargins(3,3,3,3);
                buttons[i][j].setLayoutParams(paras);
                String str=""+i+'a'+j;
                map.put(key,str);
                buttons[i][j].setId(key);
                key++;
                buttons[i][j].setBackground(null);
                buttons[i][j].setBackgroundColor(ContextCompat.getColor(this,NoPlayercolor));
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].number=empty;
                rowLayout[i].addView(buttons[i][j]);
            }
        }

        //for setting four middle element
        buttons[(boardSize/2)-1][(boardSize/2)-1].number=player1;
        buttons[(boardSize/2)-1][(boardSize/2)-1].setBackground(player1symbol);
        buttons[(boardSize/2)-1][(boardSize/2)-1].buttonPressed=true;
        buttons[(boardSize/2)][(boardSize/2)].number=player1;
        buttons[(boardSize/2)][(boardSize/2)].setBackground(player1symbol);
        buttons[(boardSize/2)][(boardSize/2)].buttonPressed=true;

        buttons[(boardSize/2)-1][(boardSize/2)].number=player2;
        buttons[(boardSize/2)-1][(boardSize/2)].setBackground(player2symbol);
        buttons[(boardSize/2)-1][(boardSize/2)].buttonPressed=true;
        buttons[(boardSize/2)][(boardSize/2)-1].number=player2;
        buttons[(boardSize/2)][(boardSize/2)-1].setBackground(player2symbol);
        buttons[(boardSize/2)][(boardSize/2)-1].buttonPressed=true;

        gameover=false;
        player1Turn=true;
       // countNonSimultaneous =0;
    }

    private boolean EnableButton(int i, int j) {
        if(buttons[i][j].number==empty) {
            for (int k = 0; k < 8; k++) {
                if (isButtonEnabled(i, j, k)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isButtonEnabled(int i, int j,int direction) {
        if(direction==5){
            Log.i("is button enabled at 5",""+buttons[i][j].number);
        }
       int x=j+(2*xArray[direction]);
        int y=i+(2*yArray[direction]);
            if ((i + yArray[direction] >= 0) && (j + xArray[direction] >= 0) && (i + yArray[direction] < boardSize) && (j + xArray[direction] < boardSize) && (buttons[i + yArray[direction]][j + xArray[direction]].number != empty)) {
                while ((x >= 0) && (y >= 0) && (x < boardSize) && (y < boardSize)) {
                    if(direction==5){
                        Log.i("is button enabled ","direc="+direction);
                    }
                    if (player1Turn) {
                        if (buttons[i + yArray[direction]][j + xArray[direction]].number != player1) {
                            if (buttons[y][x].number == player1) {
                               // Log.i("button enabled in ","direc= "+direction);
                                return true;
                            }
                            if (buttons[y][x].number == 0) {
                                return false;
                            }
                        }
                    } else {
                        if (buttons[i + yArray[direction]][j + xArray[direction]].number != player2) {
                            if (buttons[y][x].number == player2) {
                               // Log.i("button enabled in ","direc= "+direction);
                                return true;
                            }
                            if (buttons[y][x].number == 0) {
                                return false;
                            }
                        }
                    }
                    y += yArray[direction];
                    x += xArray[direction];

                }
            }
            return false;
    }

    @Override
    public void onClick(View v) {
        if(gameover){
            Toast.makeText(this,"Game Over Try NewGame ",Toast.LENGTH_SHORT);
            return ;
        }
     OthelloButton button=(OthelloButton)v;
        String str=map.get(button.getId());
      int x=0;
      int y=0;
      int i=0;
        String temp="";
        while(i<str.length()&&str.charAt(i)!='a'){
            temp+=str.charAt(i++);
        }
        y=Integer.parseInt(temp);
       // Log.i("x value ",":"+x);  //log statement
        temp="";
        i++;
        while(i<str.length()){
            temp+=str.charAt(i++);
        }
        x=Integer.parseInt(temp);
     //   Log.i("y value ",":"+y);  //log statement
       if(EnableButton(y,x)) {
               placemove(y,x);
               player1Turn=!player1Turn;
               if(isBoardEmpty()){
                   int a=getcountplayer1();
                   int b=getcountplayer2();
                   gameover=true;
                   if(a>b){
                       int c=a-b;
                       Toast.makeText(this,"GameOver player1 wins over Player2 by "+c+" margin",Toast.LENGTH_SHORT).show();
                   }else if(a==b){
                       Toast.makeText(this,"Match Drawn ",Toast.LENGTH_SHORT).show();
                   }else{
                       int c=b-a;
                       Toast.makeText(this,"GameOver player2 wins over Player1 by "+c+" margin",Toast.LENGTH_SHORT).show();
                   }
               }

    }else{
          Toast.makeText(this,"Invalid Move ",Toast.LENGTH_SHORT).show();
       }
    }

    private boolean isBoardEmpty() {
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                if(buttons[i][j].number==0){
                    return false;
                }
            }
        }
        return true;
    }

    private void placemove(int y, int x) {
       for(int direction=0;direction<8;direction++) {
         //  Log.i("placemove","direc="+ direction +isButtonEnabled(y, x, direction));
           if (isButtonEnabled(y, x, direction)) {
              // Log.i("placemove","direc= "+direction);
               if (player1Turn) {
                   buttons[y][x].number = player1;
                   buttons[y][x].setBackground(player1symbol);
                   turnbuttons(y + yArray[direction], x + xArray[direction], direction);
               } else {
                   buttons[y][x].number = player2;
                   buttons[y][x].setBackground(player2symbol);
                   turnbuttons(y + yArray[direction], x + xArray[direction], direction);
               }
           }
       }
    }

    private void turnbuttons(int y,int x,int direction) {
        int i=y;
        int j=x;
     //   Log.i("turn button ","x= "+x+" y= "+y+" direc.="+direction);  //log statement
        if(player1Turn){
            while((i>=0)&&(j>=0)&&(i<boardSize)&&(j<boardSize)&&buttons[i][j].number!=player1){
          //      Log.i("turn button player1 ","i= "+i+" j= "+j);  //log statement
                buttons[i][j].number=player1;
                buttons[i][j].setBackground(player1symbol);
                i+=yArray[direction];
                j+=xArray[direction];
            }
        }else{
            while((i>=0)&&(j>=0)&&(i<boardSize)&&(j<boardSize)&&buttons[i][j].number!=player2){
              //  Log.i("turn button player1 ","i= "+i+" j= "+j);  //log statement
                buttons[i][j].number=player2;
                buttons[i][j].setBackground(player2symbol);
                i+=yArray[direction];
                j+=xArray[direction];
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.newgame){
            resetGame();
        }else if(id==R.id.size6){
         boardSize=6;
           setBoard();
        }else if(id==R.id.size8){
            boardSize=8;
            setBoard();
        }
        return true;
    }

    private void resetGame() {
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                buttons[i][j].number=0;
                buttons[i][j].setBackground(null);
                buttons[i][j].setBackgroundColor(ContextCompat.getColor(this,NoPlayercolor));
                buttons[i][j].buttonPressed=false;
            }
        }
        //for setting four middle element
        buttons[(boardSize/2)-1][(boardSize/2)-1].number=player1;
        buttons[(boardSize/2)-1][(boardSize/2)-1].setBackground(player1symbol);
        buttons[(boardSize/2)-1][(boardSize/2)-1].buttonPressed=true;
        buttons[(boardSize/2)][(boardSize/2)].number=player1;
        buttons[(boardSize/2)][(boardSize/2)].setBackground(player1symbol);
        buttons[(boardSize/2)][(boardSize/2)].buttonPressed=true;

        buttons[(boardSize/2)-1][(boardSize/2)].number=player2;
        buttons[(boardSize/2)-1][(boardSize/2)].setBackground(player2symbol);
        buttons[(boardSize/2)-1][(boardSize/2)].buttonPressed=true;
        buttons[(boardSize/2)][(boardSize/2)-1].number=player2;
        buttons[(boardSize/2)][(boardSize/2)-1].setBackground(player2symbol);
        buttons[(boardSize/2)][(boardSize/2)-1].buttonPressed=true;

        gameover=false;
        player1Turn=true;
        //countNonSimultaneous =0;
    }
    private int getcountplayer1(){
        int player1count=0;
        for(int i=0;i<boardSize;i++) {
            for (int j = 0; j < boardSize; j++) {
                if (buttons[i][j].number == player1) {
                    player1count++;

                }
            }
        }
        return player1count;
    }

    private int getcountplayer2(){
        int player2count=0;
        for(int i=0;i<boardSize;i++) {
            for (int j = 0; j < boardSize; j++) {
                if (buttons[i][j].number == player2) {
                    player2count++;

                }
            }
        }
        return player2count;
    }
}
