package com.example.hossam.keyboard;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethod;
import android.widget.Switch;

import java.util.List;
import java.util.Locale;

import static android.R.attr.start;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
/**
 * Created by Hossam on 7/15/2017.
 */

public class keyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private android.inputmethodservice.Keyboard keyboard;
    Context context;

    private boolean caps = false;




    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new android.inputmethodservice.Keyboard(this, R.xml.keyboard);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    public void playback(int KeyCode) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        switch (KeyCode) {
            case 32:
                audioManager.playSoundEffect(audioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case 10:
                audioManager.playSoundEffect(audioManager.FX_KEYPRESS_RETURN);
                break;
            case android.inputmethodservice.Keyboard.KEYCODE_DELETE:
                audioManager.playSoundEffect(audioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                audioManager.playSoundEffect(audioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        InputConnection cn = getCurrentInputConnection();
        playback(primaryCode);
        switch (primaryCode) {
            case -101:
                ((Speech)context).speechText();
                break;
            case android.inputmethodservice.Keyboard.KEYCODE_DELETE:
                cn.deleteSurroundingText(1, 0);
                break;
            case android.inputmethodservice.Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                keyboardView.invalidateAllKeys();
                break;
            case android.inputmethodservice.Keyboard.KEYCODE_DONE:
                cn.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char) primaryCode;
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                cn.commitText(String.valueOf(code), 1);
        }
    }


    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }


}
