package com.example.lenovo.othello;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by lenovo on 19-06-2017.
 */

public class OthelloButton extends ImageButton {

    int number;
    boolean buttonPressed;

    public OthelloButton(Context context) {
        super(context);
        number=0;
        buttonPressed=false;
    }
}
