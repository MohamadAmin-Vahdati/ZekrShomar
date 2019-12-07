package com.MAVP.ZekrShomarTasbih;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.example.zekrshomar.R;

public class SelectBacgroundActivity extends AppCompatActivity {

    private AppCompatButton btn_ConfirmWallpaper ;
    private RadioGroup radioGroup ;
    private AppCompatRadioButton rb_Wall1 , rb_Wall2 , rb_Wall3 , rb_Wall4 , rb_Wall5 ;
    private RelativeLayout layout_SelectWallpaperPage ;
    private SharedPreferences sharedPreferences ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bacground);
        sharedPreferences = getSharedPreferences("Wallpaper",MODE_PRIVATE);

        FindViews();
        SetTypeFace();
        if (getResources().getBoolean(R.bool.isTablet)){
            findDisplayDensity();
        }

        initRBandWallpaper();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.RB_Wall1 :
                        layout_SelectWallpaperPage.setBackgroundResource(R.drawable.bg_1);
                        break;
                    case R.id.RB_Wall2 :
                        layout_SelectWallpaperPage.setBackgroundResource(R.drawable.bg_2);
                        break;
                    case R.id.RB_Wall3 :
                        layout_SelectWallpaperPage.setBackgroundResource(R.drawable.bg_3);
                        break;
                    case R.id.RB_Wall4 :
                        layout_SelectWallpaperPage.setBackgroundResource(R.drawable.bg_4);
                        break;
                    case R.id.RB_Wall5 :
                        layout_SelectWallpaperPage.setBackgroundResource(R.drawable.bg_5);
                        break;
                }
            }
        });

        btn_ConfirmWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.RB_Wall1 :
                        SetSharedPreferences(R.drawable.bg_1);
                        break;
                    case R.id.RB_Wall2 :
                        SetSharedPreferences(R.drawable.bg_2);
                        break;
                    case R.id.RB_Wall3 :
                        SetSharedPreferences(R.drawable.bg_3);
                        break;
                    case R.id.RB_Wall4 :
                        SetSharedPreferences(R.drawable.bg_4);
                        break;
                    case R.id.RB_Wall5 :
                        SetSharedPreferences(R.drawable.bg_5);
                        break;
                }

                Intent intent = new Intent(SelectBacgroundActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void FindViews(){
        radioGroup = findViewById(R.id.radio_group);
        rb_Wall1 = findViewById(R.id.RB_Wall1);
        rb_Wall2 = findViewById(R.id.RB_Wall2);
        rb_Wall3 = findViewById(R.id.RB_Wall3);
        rb_Wall4 = findViewById(R.id.RB_Wall4);
        rb_Wall5 = findViewById(R.id.RB_Wall5);
        btn_ConfirmWallpaper = findViewById(R.id.btn_ConfirmWallpaper);
        layout_SelectWallpaperPage = findViewById(R.id.layout_SelectWallpaperPage);
    }

    private void SetTypeFace(){
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/B Mitra Bold_YasDL.com_0.ttf");
        btn_ConfirmWallpaper.setTypeface(typeface);
        rb_Wall1.setTypeface(typeface);
        rb_Wall2.setTypeface(typeface);
        rb_Wall3.setTypeface(typeface);
        rb_Wall4.setTypeface(typeface);
        rb_Wall5.setTypeface(typeface);
    }

    private void initRBandWallpaper(){
        switch (sharedPreferences.getInt("WallpaperID",R.drawable.bg_1)){
            case R.drawable.bg_1 :
                rb_Wall1.setChecked(true);
                layout_SelectWallpaperPage.setBackgroundResource(R.drawable.bg_1);
                break;
            case R.drawable.bg_2 :
                rb_Wall2.setChecked(true);
                layout_SelectWallpaperPage.setBackgroundResource(R.drawable.bg_2);
                break;
            case R.drawable.bg_3 :
                rb_Wall3.setChecked(true);
                layout_SelectWallpaperPage.setBackgroundResource(R.drawable.bg_3);
                break;
            case R.drawable.bg_4 :
                rb_Wall4.setChecked(true);
                layout_SelectWallpaperPage.setBackgroundResource(R.drawable.bg_4);
                break;
            case R.drawable.bg_5 :
                rb_Wall5.setChecked(true);
                layout_SelectWallpaperPage.setBackgroundResource(R.drawable.bg_5);
                break;
        }
    }

    private void SetSharedPreferences(int ID){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("WallpaperID",ID) ;
        editor.apply();
    }

    private void ChangeForTablets(int width , int fontSize1 , int fontSize2 ){
        rb_Wall1.setTextSize(fontSize1);
        rb_Wall2.setTextSize(fontSize1);
        rb_Wall3.setTextSize(fontSize1);
        rb_Wall4.setTextSize(fontSize1);
        rb_Wall5.setTextSize(fontSize1);
        btn_ConfirmWallpaper.setTextSize(fontSize1);

        RadioGroup.LayoutParams params1 = (RadioGroup.LayoutParams) rb_Wall1.getLayoutParams();
        params1.width = width ;
        rb_Wall1.setLayoutParams(params1);

        RadioGroup.LayoutParams params2 = (RadioGroup.LayoutParams) rb_Wall2.getLayoutParams();
        params2.width = width ;
        rb_Wall2.setLayoutParams(params2);

        RadioGroup.LayoutParams params3 = (RadioGroup.LayoutParams) rb_Wall3.getLayoutParams();
        params3.width = width ;
        rb_Wall3.setLayoutParams(params3);

        RadioGroup.LayoutParams params4 = (RadioGroup.LayoutParams) rb_Wall4.getLayoutParams();
        params4.width = width ;
        rb_Wall4.setLayoutParams(params4);

        RadioGroup.LayoutParams params5 = (RadioGroup.LayoutParams) rb_Wall5.getLayoutParams();
        params5.width = width ;
        rb_Wall5.setLayoutParams(params5);

        RelativeLayout.LayoutParams params6 = (RelativeLayout.LayoutParams) btn_ConfirmWallpaper.getLayoutParams();
        params6.width = width - 200 ;
        params6.height = fontSize2 ;
        params6.setMargins(0,70,0,0);
        btn_ConfirmWallpaper.setLayoutParams(params6);


    }

    private void findDisplayDensity(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displaydensity = displayMetrics.densityDpi ;
        int width , fontSize1 , fontSize2 ;

        if (displaydensity<=160){
            width = 300 ;
            fontSize1 = 20 ;
            fontSize2 = 43 ;
        }else if (displaydensity<=213){
            width = 340 ;
            fontSize1 = 21 ;
            fontSize2 = 63 ;
        } else {
            width = 580 ;
            fontSize1 = 21 ;
            fontSize2 = 83 ;
        }
        ChangeForTablets(width,fontSize1 , fontSize2);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SelectBacgroundActivity.this,MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
