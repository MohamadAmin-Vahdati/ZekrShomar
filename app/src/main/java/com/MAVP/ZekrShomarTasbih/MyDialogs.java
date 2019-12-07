package com.MAVP.ZekrShomarTasbih;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.zekrshomar.R;

public class MyDialogs implements View.OnClickListener {

    private Activity activity ;
    private Dialog dialogSelect , dialogSetCounter ;
    public Dialog dialogConfirm ;

    private AppCompatEditText ET_LastCount , ET_FullCount ;
    private AppCompatCheckBox CB_Ultimate ;
    private AzkarDbHelper azkarDbHelper ;

    public AppCompatButton btn_CancelDel , btn_ConfirmDel ;

    private int ID  , displaydensity ;

    private Azkar azkar ;



    public void ShowSelectDialog(Activity activity , int ID , int displaydensity){
        this.activity = activity ;
        this.ID = ID ;
        this.displaydensity = displaydensity ;
        AppCompatButton  Sreset , Soptions ;
        dialogSelect = new Dialog(activity);
        azkarDbHelper = new AzkarDbHelper(activity.getApplicationContext());
        azkar = azkarDbHelper.GetAzkar(ID);
        dialogSelect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (azkar.getLastCount()==azkar.getFullCount()){
            dialogSelect.setContentView(R.layout.selectwithoutcountinuslayout);
        }else {
            dialogSelect.setContentView(R.layout.selectcountinuslayout);
            AppCompatButton Scontinue ;
            Scontinue = dialogSelect.findViewById(R.id.btn_Scontinue);
            Scontinue.setOnClickListener(this);
            if (activity.getResources().getBoolean(R.bool.isTablet)){
                int height , fontSize ;
                if (displaydensity<=160){
                    height =  48 ;
                    fontSize = 17 ;
                }else if (displaydensity<=213){
                    height =  60 ;
                    fontSize = 18 ;
                } else {
                    height =  ViewGroup.LayoutParams.WRAP_CONTENT ;
                    fontSize = 18 ;
                }
                Scontinue.setTextSize(fontSize);
                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) Scontinue.getLayoutParams();
                params1.height = height ;
                Scontinue.setLayoutParams(params1);
            }
        }
        changeDialogSize(dialogSelect , 0.6);
        Sreset = dialogSelect.findViewById(R.id.btn_Sreset);
        Soptions = dialogSelect.findViewById(R.id.btn_Soptions);

        if (activity.getResources().getBoolean(R.bool.isTablet)){
            int height , fontSize ;
            if (displaydensity<=160){
                height =  48 ;
                fontSize = 17 ;
            }else if (displaydensity<=213){
                height =  60 ;
                fontSize = 18 ;
            } else {
                height =  ViewGroup.LayoutParams.WRAP_CONTENT ;
                fontSize = 18 ;
            }

            Sreset.setTextSize(fontSize);
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) Sreset.getLayoutParams();
            params2.height = height ;
            Sreset.setLayoutParams(params2);

            Soptions.setTextSize(fontSize);
            LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) Soptions.getLayoutParams();
            params3.height = height ;
            Soptions.setLayoutParams(params3);
        }

        Sreset.setOnClickListener(this);
        Soptions.setOnClickListener(this);
        dialogSelect.show();
    }

    private void ShowSetCounterDialog(){
        final AppCompatTextView TV_FullCount , TV_inputSuggested , TV_Suggested , TV_LastCount;
        AppCompatButton btn_Confirm ;

        dialogSetCounter = new Dialog(activity);
        dialogSetCounter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSetCounter.setContentView(R.layout.setcounter_page);
        changeDialogSize(dialogSetCounter , .85);
        CB_Ultimate = dialogSetCounter.findViewById(R.id.CB_Ultimate);
        ET_LastCount = dialogSetCounter.findViewById(R.id.ET_LastCount);
        ET_FullCount = dialogSetCounter.findViewById(R.id.ET_FullCount) ;
        btn_Confirm = dialogSetCounter.findViewById(R.id.btn_Confirm) ;
        TV_FullCount = dialogSetCounter.findViewById(R.id.TV_FullCount);
        TV_inputSuggested = dialogSetCounter.findViewById(R.id.TV_inputSuggested);
        TV_Suggested = dialogSetCounter.findViewById(R.id.TV_Suggested);
        TV_LastCount = dialogSetCounter.findViewById(R.id.TV_LastCount);

        CB_Ultimate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked){
                    ET_FullCount.setEnabled(false);
                    ET_FullCount.setTextColor(Color.GRAY);
                    TV_FullCount.setTextColor(Color.GRAY);
                }else {
                    ET_FullCount.setEnabled(true);
                    ET_FullCount.setTextColor(Color.BLACK);
                    TV_FullCount.setTextColor(Color.BLACK);
                }
            }
        });
        ET_LastCount.setText(String.valueOf(azkar.getLastCount()));
        if (!(azkar.getFullCount() == -1)){
            ET_FullCount.setText(String.valueOf(azkar.getFullCount() ));
        }
        if (azkar.getSuggested()==-1){
            TV_inputSuggested.setVisibility(View.INVISIBLE);
            TV_Suggested.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btn_Confirm.getLayoutParams();
            params.setMargins(0,0,0,0);
            btn_Confirm.setLayoutParams(params);
        }else {
            TV_inputSuggested.setText(String.valueOf(azkar.getSuggested()));
        }
        btn_Confirm.setOnClickListener(this);

        if (activity.getResources().getBoolean(R.bool.isTablet)){
            int width , height , fontSize ;
            if (displaydensity<=160){
                width =  130 ;
                height = 43 ;
                fontSize = 18 ;
            }else if (displaydensity<=213){
                width =  150 ;
                height = 60 ;
                fontSize = 19 ;
            } else {
                width =  250 ;
                height = 90 ;
                fontSize = 19 ;
            }

            ET_LastCount.setTextSize(fontSize+1);
            LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) ET_LastCount.getLayoutParams();
            params4.width = width ;
            ET_LastCount.setLayoutParams(params4);

            TV_LastCount.setTextSize(fontSize);

            ET_FullCount.setTextSize(fontSize+1);
            LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams) ET_FullCount.getLayoutParams();
            params5.width = width ;
            ET_FullCount.setLayoutParams(params5);

            TV_FullCount.setTextSize(fontSize);

            CB_Ultimate.setTextSize(fontSize-2);
            LinearLayout.LayoutParams params6 = (LinearLayout.LayoutParams) CB_Ultimate.getLayoutParams();
            params6.setMargins(0,15,0,0);
            CB_Ultimate.setLayoutParams(params6);

            TV_Suggested.setTextSize(fontSize-1);
            TV_inputSuggested.setTextSize(fontSize-2);

            btn_Confirm.setTextSize(fontSize-1);
            LinearLayout.LayoutParams params7 = (LinearLayout.LayoutParams) btn_Confirm.getLayoutParams();
            params7.height = height ;
            btn_Confirm.setLayoutParams(params7);


        }

        dialogSetCounter.show();
    }

    public void ShowConfirmDialog(Activity activity , int displaydensity ){
        AppCompatTextView TV_AreYouSure ;
        this.activity = activity ;
        dialogConfirm = new Dialog(activity);
        dialogConfirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogConfirm.setContentView(R.layout.confirmdelete);

        btn_ConfirmDel = dialogConfirm.findViewById(R.id.btn_ConfirmDel);
        btn_CancelDel = dialogConfirm.findViewById(R.id.btn_CancelDel);
        TV_AreYouSure = dialogConfirm.findViewById(R.id.tv_AreYouSure);

        if (activity.getResources().getBoolean(R.bool.isTablet)){
            int height ,width, fontSize ;
            if (displaydensity<=160){
                height =  45 ;
                width = 85 ;
                fontSize = 31 ;
            }else if (displaydensity<=213){
                height =  62 ;
                width = 102 ;
                fontSize = 32 ;
            } else {
                height =  92 ;
                width = 132 ;
                fontSize = 32 ;
            }

            TV_AreYouSure.setTextSize(fontSize);
            btn_ConfirmDel.setTextSize(fontSize-13);
            btn_CancelDel.setTextSize(fontSize-13);

            LinearLayout.LayoutParams params8 = (LinearLayout.LayoutParams) btn_ConfirmDel.getLayoutParams();
            params8.height = height ;
            params8.width = width ;
            btn_ConfirmDel.setLayoutParams(params8);

            LinearLayout.LayoutParams params9 = (LinearLayout.LayoutParams) btn_ConfirmDel.getLayoutParams();
            params9.height = ViewGroup.LayoutParams.WRAP_CONTENT ;
            params9.width = ViewGroup.LayoutParams.WRAP_CONTENT ;
            btn_CancelDel.setLayoutParams(params9);

        }

        Typeface typeface = Typeface.createFromAsset(activity.getAssets(),"fonts/B Mitra Bold_YasDL.com_0.ttf");
        TV_AreYouSure.setTypeface(typeface);

        changeDialogSize(dialogConfirm , 0.73);
        dialogConfirm.show();
    }

    private void SetDataBase (){
        int lastcount , fullcount ;
        String et_lastcount , et_fullcount ;
        ContentValues cv = new ContentValues();
        et_lastcount = ET_LastCount.getText().toString().trim();
        et_fullcount = ET_FullCount.getText().toString().trim();
        if (et_lastcount.isEmpty() ){
            Toast.makeText(activity, "مقداری برای شروع ذکر وارد کنید", Toast.LENGTH_SHORT).show();
        }else if (et_fullcount.isEmpty() && !CB_Ultimate.isChecked()){
            Toast.makeText(activity, "مقداری برای تعداد دوره وارد کنید", Toast.LENGTH_SHORT).show();
        } else {
            lastcount = Integer.valueOf(et_lastcount);
            if (!CB_Ultimate.isChecked()){
                fullcount = Integer.valueOf(et_fullcount);
                if (fullcount<=lastcount){
                    Toast.makeText(activity, "مقدار شروع ذکر باید کمتر از مقدار دوره باشد", Toast.LENGTH_SHORT).show();
                } else {
                    cv.put(Azkar.KEY_LASTCOUNT,lastcount);
                    cv.put(Azkar.KEY_FULLCOUNT,fullcount);
                    azkarDbHelper.Update(ID , cv);
                    dialogSetCounter.dismiss();
                    GotoCounter();
                }
            }else {
                cv.put(Azkar.KEY_LASTCOUNT,lastcount);
                cv.put(Azkar.KEY_FULLCOUNT,-1);
                azkarDbHelper.Update(ID , cv);
                dialogSetCounter.dismiss();
                GotoCounter();
            }
        }

    }

    private Point getScreenSize(Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size ;
    }

    private void changeDialogSize(Dialog dialog , double per){
        Point scrSize = getScreenSize(activity);
        if (dialog.getWindow()!=null){
            dialog.getWindow().setLayout((int) (per * scrSize.x), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void GotoCounter (){
        Intent intent = new Intent(activity, CounterPageActivity.class);
        intent.putExtra("ID",ID);
        activity.startActivity(intent);
        activity.finish();
    }

    private void ResetLastCount (){
        ContentValues cv = new ContentValues();
        cv.put(Azkar.KEY_LASTCOUNT,0);
        azkarDbHelper.Update(ID,cv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Scontinue:
                dialogSelect.dismiss();
                GotoCounter();
                break;
            case R.id.btn_Sreset:
                dialogSelect.dismiss();
                ShowConfirmDialog(activity,displaydensity);
                btn_CancelDel.setOnClickListener(this);
                btn_ConfirmDel.setOnClickListener(this);
                break;
            case R.id.btn_Soptions:
                dialogSelect.dismiss();
                ShowSetCounterDialog();
                break;
            case R.id.btn_Confirm:
                SetDataBase();
                break;
            case R.id.btn_ConfirmDel :
                ResetLastCount();
                dialogSelect.dismiss();
                GotoCounter();
                dialogConfirm.dismiss();
                break;
            case R.id.btn_CancelDel :
                dialogConfirm.dismiss();
                break;
        }
    }
}
