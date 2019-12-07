package com.MAVP.ZekrShomarTasbih;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.zekrshomar.R;

import java.util.Timer;
import java.util.TimerTask;

public class CounterPageActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton btn_counter , btn_again , btn_return , btn_Sefr ;
    private AppCompatImageView btn_play , btn_repeat ;
    private AppCompatSeekBar seekBar_audio ;
    private LinearLayout  layout_counter , layout_circleprogress , layout_etmamZekr   , layout_max ;
    private AppCompatTextView tv_max  , tv_zekr , tv_translate ,tv_ZekrFinish;
    private RelativeLayout layout_zekr , layout_player;


    private int counter , max , ID ;


    private Timer timer_btnmanual , timer_etmamZekr;
    private Handler timerHandler_btnmanual , timerHandler_etmamZekr;
    private TimerTask timerTask_btnmanual , timerTask_etmamZekr;

    private CircleProgress circleProgress ;





    private MediaPlayer audioplayer ;
    private AudioManager audioManager ;

    private AzkarDbHelper azkarDbHelper ;
    private ContentValues cv ;
    private Azkar azkar ;

    private MyDialogs ConfirmDialog ;

    private RelativeLayout layout_CounterPage ;

    private int displaydensity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_page);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displaydensity = displayMetrics.densityDpi ;


        initWallpaper();

        FindViews();

        SetTypeface();

        SetListeners();

        initFirst();

        initSecond();

        initAudioPlayer();

        initSeekBarVolume();


    }

    private void FindViews(){
        btn_counter = findViewById(R.id.btn_counter) ;
        circleProgress = findViewById(R.id.circleProgress);
        btn_play = findViewById(R.id.btn_play);
        seekBar_audio = findViewById(R.id.audioSeekBar);
        btn_repeat = findViewById(R.id.btn_repeat);
        btn_again = findViewById(R.id.btn_again);
        btn_return = findViewById(R.id.btn_return);
        tv_max = findViewById(R.id.tv_max);
        tv_translate = findViewById(R.id.TV_Translate);
        tv_zekr = findViewById(R.id.TV_Zekr);
        btn_Sefr = findViewById(R.id.btn_Sefr);
        tv_ZekrFinish = findViewById(R.id.TV_ZekrFinish) ;

        layout_circleprogress = findViewById(R.id.layout_circleprogress);
        layout_counter = findViewById(R.id.layout_counter);
        layout_etmamZekr = findViewById(R.id.layout_etmamZekr);
        layout_player = findViewById(R.id.layout_player);
        layout_zekr = findViewById(R.id.layout_zekr) ;
        layout_max = findViewById(R.id.layout_max) ;

    }
    private void initWallpaper(){
        SharedPreferences sharedPreferences = getSharedPreferences("Wallpaper",MODE_PRIVATE);
        layout_CounterPage = findViewById(R.id.layout_CounterPage);
        layout_CounterPage.setBackgroundResource(sharedPreferences.getInt("WallpaperID",R.drawable.bg_1));

    }

    private void SetTypeface(){
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/B Mitra Bold_YasDL.com_0.ttf");
        tv_zekr.setTypeface(typeface);
        tv_translate.setTypeface(typeface);
        btn_Sefr.setTypeface(typeface);
        btn_again.setTypeface(typeface);
        btn_return.setTypeface(typeface);
        tv_ZekrFinish.setTypeface(typeface);
        btn_counter.setTypeface(typeface);
        tv_max.setTypeface(typeface);
    }

    private void SetListeners(){
        btn_counter.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        btn_repeat.setOnClickListener(this);
        btn_again.setOnClickListener(this);
        btn_return.setOnClickListener(this);
        btn_Sefr.setOnClickListener(this);
    }

    private void initFirst(){
        timer_btnmanual= new Timer();
        timer_etmamZekr = new Timer();
        timerHandler_btnmanual = new Handler();
        timerHandler_etmamZekr = new Handler();

        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            if (extras.containsKey("ID")) {
                ID = extras.getInt("ID");
            }
        }
        if (!getResources().getBoolean(R.bool.isTablet)) {

            if (displaydensity <= 240) {

                int padding_layout_zekr;

                if (displaydensity <= 120) {
                    padding_layout_zekr = 2;
                } else if (displaydensity <= 160) {
                    padding_layout_zekr = 11;
                } else {
                    padding_layout_zekr = 24;
                }

                btn_Sefr.setVisibility(View.INVISIBLE);

                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) layout_zekr.getLayoutParams();
                params1.setMargins(padding_layout_zekr, padding_layout_zekr, padding_layout_zekr, 0);
                layout_zekr.setLayoutParams(params1);

                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) layout_player.getLayoutParams();
                params2.setMargins(0, 0, 0, 9);
                layout_player.setLayoutParams(params2);

            } else if (displaydensity <= 320) {

                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) layout_player.getLayoutParams();
                params2.setMargins(0, 0, 0, 22);
                layout_player.setLayoutParams(params2);

            } else if (displaydensity <= 480) {

                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) layout_player.getLayoutParams();
                params2.setMargins(0, 0, 0, 33);
                layout_player.setLayoutParams(params2);

            }
        } else {
            int widthAndheight_counter , fontSize , topMargin_btnSefr , topMargin_tvMax , widthAndheight_repeat , LeftMargin ;
            if (displaydensity<=160){
                widthAndheight_counter = 190 ;
                fontSize = 24 ;
                topMargin_btnSefr = 130 ;
                topMargin_tvMax = 165 ;
                widthAndheight_repeat = 30 ;
                LeftMargin = 50 ;
            }else if (displaydensity<=213){
                widthAndheight_counter = 230 ;
                fontSize = 24 ;
                topMargin_btnSefr = 160 ;
                topMargin_tvMax = 200 ;
                widthAndheight_repeat = 38 ;
                LeftMargin =  55 ;
            } else {
                widthAndheight_counter = 340 ;
                fontSize = 26 ;
                topMargin_btnSefr = 220 ;
                topMargin_tvMax = 310 ;
                widthAndheight_repeat = 50 ;
                LeftMargin = 90 ;
            }

            tv_zekr.setTextSize(fontSize+15);
            tv_translate.setTextSize(fontSize-1);
            tv_max.setTextSize(fontSize-2);
            btn_counter.setTextSize(fontSize+21);
            tv_ZekrFinish.setTextSize(fontSize+10);
            btn_return.setTextSize(fontSize-2);
            btn_again.setTextSize(fontSize-5);
            btn_Sefr.setTextSize(fontSize-4);


            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) btn_counter.getLayoutParams();
            params2.width = widthAndheight_counter ;
            params2.height = widthAndheight_counter ;
            btn_counter.setLayoutParams(params2);

            LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) circleProgress.getLayoutParams();
            params3.width = widthAndheight_counter +1 ;
            params3.height = widthAndheight_counter +1 ;
            circleProgress.setLayoutParams(params3);


            RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams) btn_Sefr.getLayoutParams();
            params4.setMargins(0,topMargin_btnSefr,0,0);
            btn_Sefr.setLayoutParams(params4);

            LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams) tv_max.getLayoutParams();
            params5.setMargins(0,topMargin_tvMax,0,0);
            tv_max.setLayoutParams(params5);

            RelativeLayout.LayoutParams params6 = (RelativeLayout.LayoutParams) btn_repeat.getLayoutParams();
            params6.width = widthAndheight_repeat ;
            params6.height = widthAndheight_repeat  ;
            btn_repeat.setLayoutParams(params6);

            RelativeLayout.LayoutParams params7 = (RelativeLayout.LayoutParams) btn_play.getLayoutParams();
            params7.width = widthAndheight_repeat  +9;
            params7.height = widthAndheight_repeat  +9 ;
            params7.setMargins(LeftMargin,0,0,0);
            btn_play.setLayoutParams(params7);

            RelativeLayout.LayoutParams params8 = (RelativeLayout.LayoutParams) seekBar_audio.getLayoutParams();
            params8.width = widthAndheight_counter  ;
            seekBar_audio.setLayoutParams(params8);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (azkar.getSound()!=0){
            if (audioplayer.isPlaying()) {
                audioplayer.pause();
            }
            audioplayer.seekTo(0);
        }

    }

    private void setBtnManualColor (){
        timerTask_btnmanual = new TimerTask() {
            @Override
            public void run() {
                timerHandler_btnmanual.post(new Runnable() {
                    @Override
                    public void run() {
                        btn_counter.setBackgroundResource(R.drawable.bg_for_manual);
                    }
                });
            }
        };
        timer_btnmanual.schedule(timerTask_btnmanual,100);
    }

    private void initSecond(){
        azkarDbHelper = new AzkarDbHelper(this);
        azkar = azkarDbHelper.GetAzkar(ID) ;
        counter = azkar.getLastCount() ;
        max = azkar.getFullCount() ;
        btn_counter.setText(String.valueOf(counter));
        circleProgress.setMax(max);
        if (max==-1){
            circleProgress.setProgress(0);
        }else {
            circleProgress.setProgress(counter);
            tv_max.setText(String.valueOf(max));
        }
        tv_zekr.setText(azkar.getZekr());
        if (!azkar.getTranslate().isEmpty()){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv_translate.getLayoutParams();
            layoutParams.addRule(RelativeLayout.BELOW,R.id.TV_Zekr);
            tv_translate.setLayoutParams(layoutParams);
        }
        tv_translate.setText(azkar.getTranslate());

        azkarDbHelper = new AzkarDbHelper(this);
        cv = new ContentValues();

        ConfirmDialog = new MyDialogs();
    }

    private void initAudioPlayer(){
        if (azkar.getSound()!=0){
            audioplayer = MediaPlayer.create(this,azkar.getSound()) ;
            audioplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (!audioplayer.isLooping()){
                        btn_play.setBackgroundResource(R.drawable.play_icon);
                    }
                }
            });
            audioplayer.setLooping(false);

        }else {
            layout_player.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) btn_Sefr.getLayoutParams();
            params1.setMargins(0,335,0,0);
            btn_Sefr.setLayoutParams(params1);

            if (!getResources().getBoolean(R.bool.isTablet)) {

                if (displaydensity <= 480) {
                    int bottom_counter, bottom_max;
                    if (displaydensity <= 160) {
                        bottom_counter = 10;
                        bottom_max = bottom_counter + 14;
                    } else if (displaydensity <= 240) {
                        bottom_counter = 42;
                        bottom_max = bottom_counter + 25;
                    } else if (displaydensity <= 320) {
                        bottom_counter = 44;
                        bottom_max = bottom_counter + 33;
                        btn_Sefr.setVisibility(View.INVISIBLE);
                    } else {
                        bottom_counter = 53;
                        bottom_max = bottom_counter + 48;
                        btn_Sefr.setVisibility(View.INVISIBLE);
                        tv_max.setTextSize(16);
                        btn_counter.setTextSize(33);
                    }

                    RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) layout_counter.getLayoutParams();
                    params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params2.setMargins(0, 0, 0, bottom_counter);
                    layout_counter.setLayoutParams(params2);

                    RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) layout_circleprogress.getLayoutParams();
                    params3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params3.setMargins(0, 0, 0, bottom_counter);
                    layout_circleprogress.setLayoutParams(params3);

                    RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams) layout_max.getLayoutParams();
                    params4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params4.setMargins(0, 0, 0, bottom_max);
                    layout_max.setLayoutParams(params4);
                } else {

                    tv_max.setTextSize(16);
                    btn_counter.setTextSize(34);

                    LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) btn_counter.getLayoutParams();
                    params2.setMargins(0, 960, 0, 0);
                    btn_counter.setLayoutParams(params2);

                    LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) circleProgress.getLayoutParams();
                    params3.setMargins(0, 960, 0, 0);
                    circleProgress.setLayoutParams(params3);

                    LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) tv_max.getLayoutParams();
                    params4.setMargins(0, 1210, 0, 0);
                    tv_max.setLayoutParams(params4);

                    RelativeLayout.LayoutParams params5 = (RelativeLayout.LayoutParams) btn_Sefr.getLayoutParams();
                    params5.setMargins(0, 250, 0, 0);
                    btn_Sefr.setLayoutParams(params5);
                }
            }else {
                int topMarginCounter , topMarginTvMAx , topMarginBtnSefr;

                if (displaydensity<=160){
                    topMarginCounter = 350 ;
                    topMarginTvMAx = 450 ;
                    topMarginBtnSefr = 80 ;
                }else if (displaydensity<=213){
                    topMarginCounter = 420 ;
                    topMarginTvMAx = 545 ;
                    topMarginBtnSefr = 100 ;
                } else {
                    topMarginCounter = 920 ;
                    topMarginTvMAx = 1110 ;
                    topMarginBtnSefr = 95 ;
                }

                LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) btn_counter.getLayoutParams();
                params2.setMargins(0, topMarginCounter, 0, 0);
                btn_counter.setLayoutParams(params2);

                LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) circleProgress.getLayoutParams();
                params3.setMargins(0, topMarginCounter, 0, 0);
                circleProgress.setLayoutParams(params3);

                LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) tv_max.getLayoutParams();
                params4.setMargins(0,topMarginTvMAx , 0, 0);
                tv_max.setLayoutParams(params4);

                RelativeLayout.LayoutParams params5 = (RelativeLayout.LayoutParams) btn_Sefr.getLayoutParams();
                params5.setMargins(0, topMarginBtnSefr, 0,0 );
                btn_Sefr.setLayoutParams(params5);
            }

        }
    }

    private void initSeekBarVolume (){
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        seekBar_audio.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBar_audio.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        seekBar_audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void EtmamZekr(){
        if (ID == 9 || ID == 8 ){
            timerTask_etmamZekr = new TimerTask() {
                @Override
                public void run() {
                    timerHandler_etmamZekr.post(new Runnable() {
                        @Override
                        public void run() {
                            circleProgress.setColor(Color.WHITE);
                            btn_counter.setTextColor(Color.WHITE);
                            ID = ID + 1 ;
                            initSecond();
                            initAudioPlayer();
                        }
                    });
                }
            };
            timer_etmamZekr.schedule(timerTask_etmamZekr,1500);


        }else {
            timerTask_etmamZekr = new TimerTask() {
                @Override
                public void run() {
                    timerHandler_etmamZekr.post(new Runnable() {
                        @Override
                        public void run() {
                            circleProgress.setColor(Color.WHITE);
                            btn_counter.setTextColor(Color.WHITE);
                            layout_zekr.setVisibility(View.INVISIBLE);
                            layout_counter.setVisibility(View.INVISIBLE);
                            layout_circleprogress.setVisibility(View.INVISIBLE);
                            if (azkar.getSound()!=0){
                                layout_player.setVisibility(View.INVISIBLE);
                            }
                            layout_max.setVisibility(View.INVISIBLE);
                            layout_etmamZekr.setVisibility(View.VISIBLE);
                            btn_Sefr.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            };
            timer_etmamZekr.schedule(timerTask_etmamZekr,1500);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_counter :
                if (max==-1){
                    btn_counter.setBackgroundResource(R.drawable.bg_for_manual_pressed);
                    counter = counter + 1;
                    btn_counter.setText(String.valueOf(counter));
                    setBtnManualColor();
                    if (!(ID==8 || ID==9 || ID==10)){
                        cv.put(Azkar.KEY_LASTCOUNT,counter);
                        azkarDbHelper.Update(ID,cv);
                    }
                }else {
                    if (counter < max) {
                        btn_counter.setBackgroundResource(R.drawable.bg_for_manual_pressed);
                        counter = counter + 1;
                        btn_counter.setText(String.valueOf(counter));
                        circleProgress.setProgress(counter);
                        setBtnManualColor();
                        if (!(ID==8 || ID==9 || ID==10)){
                            cv.put(Azkar.KEY_LASTCOUNT,counter);
                            azkarDbHelper.Update(ID,cv);
                        }
                        if (counter == max) {
                            btn_counter.setTextColor(getResources().getColor(R.color.ZekrFinished));
                            circleProgress.setColor(getResources().getColor(R.color.ZekrFinished));
                            EtmamZekr();
                            if (azkar.getSound()!=0){
                                if (audioplayer.isPlaying()) {
                                    audioplayer.pause();
                                    btn_play.setBackgroundResource(R.drawable.play_icon);
                                }
                                audioplayer.seekTo(0);
                            }
                        }
                    }
                }
                break;

            case R.id.btn_play :
                if (audioplayer.isPlaying()){
                    btn_play.setBackgroundResource(R.drawable.play_icon);
                    audioplayer.pause();
                }else {
                    btn_play.setBackgroundResource(R.drawable.pause_icon);
                    audioplayer.start();
                }
                break;

            case R.id.btn_repeat :
                if (audioplayer.isLooping()){
                    btn_repeat.setBackgroundResource(R.drawable.bg_for_repeat_off);
                    audioplayer.setLooping(false);
                }else {
                    btn_repeat.setBackgroundResource(R.drawable.bg_for_repeat_on);
                    audioplayer.setLooping(true);
                }
                break;

            case R.id.btn_again :
                layout_etmamZekr.setVisibility(View.INVISIBLE);
                layout_zekr.setVisibility(View.VISIBLE);
                layout_counter.setVisibility(View.VISIBLE);
                layout_circleprogress.setVisibility(View.VISIBLE);
                if (azkar.getSound()!=0){
                    layout_player.setVisibility(View.VISIBLE);
                }
                layout_max.setVisibility(View.VISIBLE);
                if ((displaydensity) <= 480 && azkar.getSound()==0 && !getResources().getBoolean(R.bool.isTablet)){
                    btn_Sefr.setVisibility(View.INVISIBLE);
                }else {
                    btn_Sefr.setVisibility(View.VISIBLE);
                }
                if (ID == 10 ){
                    ID = 8 ;
                    initSecond();
                    initAudioPlayer();
                } else {
                    counter = 0;
                    circleProgress.setProgress(counter);
                    btn_counter.setText(String.valueOf(counter));
                    cv.put(Azkar.KEY_LASTCOUNT,counter);
                    azkarDbHelper.Update(ID,cv);
                }
                break;


            case R.id.btn_return :
                Intent intent ;
               if (ID == 7 || ID == 10){
                   intent = new Intent(CounterPageActivity.this,MainActivity.class);
               } else {
                   intent = new Intent(CounterPageActivity.this,ZekrPagesActivity.class);
               }
               startActivity(intent);
               finish();
                break;

            case R.id.btn_Sefr :
                ConfirmDialog.ShowConfirmDialog(this , displaydensity);
                ConfirmDialog.btn_ConfirmDel.setOnClickListener(this);
                ConfirmDialog.btn_CancelDel.setOnClickListener(this);

                break;
            case R.id.btn_ConfirmDel :
                if (ID == 10 || ID == 9 || ID == 8 ){
                    ID = 8 ;
                    initSecond();
                    initAudioPlayer();
                } else {
                    counter = 0;
                    circleProgress.setProgress(counter);
                    btn_counter.setText(String.valueOf(counter));
                    cv.put(Azkar.KEY_LASTCOUNT,counter);
                    azkarDbHelper.Update(ID,cv);
                }
                ConfirmDialog.dialogConfirm.dismiss();
                break;
            case R.id.btn_CancelDel :
                ConfirmDialog.dialogConfirm.dismiss();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        seekBar_audio.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        seekBar_audio.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Intent intent ;
        if (ID < 11){
            intent = new Intent(CounterPageActivity.this,MainActivity.class);
        } else {
            intent = new Intent(CounterPageActivity.this,ZekrPagesActivity.class);
        }
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}

