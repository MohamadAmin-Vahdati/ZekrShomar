package com.MAVP.ZekrShomarTasbih;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.zekrshomar.R;

public class SplashScreenActivity extends AppCompatActivity {

    private AppCompatTextView tv_tasbih ;
    private AppCompatImageView iv_tasbih ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);
            tv_tasbih = findViewById(R.id.tv_tasbih);
            iv_tasbih = findViewById(R.id.iv_tasbih);

            Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Cronus Italic.otf");
            tv_tasbih.setTypeface(typeface);

            Animation animFadein = AnimationUtils.loadAnimation(this,R.anim.fadein);
            iv_tasbih.startAnimation(animFadein);
            Animation animMove = AnimationUtils.loadAnimation(this,R.anim.move);
            tv_tasbih.startAnimation(animMove);

        if (getResources().getBoolean(R.bool.isTablet)){
            findDisplayDensity();
        }



            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);


     }

    private void ChangeForTablets(int widthAndheight , int topMargin){
        tv_tasbih.setTextSize(31);

        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) iv_tasbih.getLayoutParams();
        params1.width = widthAndheight ;
        params1.height = widthAndheight ;
        iv_tasbih.setLayoutParams(params1);

        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) tv_tasbih.getLayoutParams();
        params2.setMargins(0,topMargin,0,0);
        tv_tasbih.setLayoutParams(params2);
    }

    private void findDisplayDensity(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displaydensity = displayMetrics.densityDpi ;
        int widthAndheight  , topMargin ;

        if (displaydensity<=160){
            topMargin = 80 ;
            widthAndheight = 160 ;
        }else if (displaydensity<=213){
            topMargin = 100 ;
            widthAndheight = 195 ;
        } else {
            topMargin = 145 ;
            widthAndheight = 295 ;
        }
        ChangeForTablets(widthAndheight,topMargin);
    }
}
