package com.ragingdevs.morsechat;

import java.util.ArrayList;

/**
 * Created by Vegard on 10/11/2016.
 */

public class MorseTranslator {
    int dashDuration, dotDuration, wordPause, charPause;

    public MorseTranslator(){
        dotDuration = 100;
        dashDuration = 3 * dotDuration;
        wordPause = 7 * dotDuration;
        charPause = 3 * dotDuration;
    }


    public ArrayList<Integer> translate(String morseString){
        char[] morseChars = morseString.toCharArray();
        ArrayList<Integer> result = new ArrayList<>();

        for(int i = 0; i < morseChars.length; i++){
            switch(morseChars[i]){
                case '.':
                    result.add(dotDuration);
                    break;
                case '-':
                    result.add(dashDuration);
                    break;
                case ' ':
                    result.add(charPause);
                    break;



            }
        }
        return null;
    }



    public void setDotDuration(int dotDuration) {
        this.dotDuration = dotDuration;
    }

    public int getDotDuration(){
        return dotDuration;
    }
}
