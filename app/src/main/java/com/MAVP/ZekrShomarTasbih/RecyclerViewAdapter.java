package com.MAVP.ZekrShomarTasbih;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zekrshomar.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Azkar> azkarList ;
    private Activity activity ;
    private MyViewHolder viewHolder ;
    public RecyclerViewAdapter(List<Azkar> azkarList , Activity activity){
        this.azkarList = azkarList ;
        this.activity = activity ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.samplezekradapter,parent,false);
        viewHolder = new MyViewHolder(view) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        viewHolder.bind(azkarList.get(position));
    }

    @Override
    public int getItemCount() {
        return azkarList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView ZekrforAdapter , tedad_gozarande , tedad_dore , btn_Delete ,tedad_gozarande2 , tedad_dore2;
        RelativeLayout select_zekr ;
        AzkarDbHelper dbHelper ;

        private int displaydensity ;

        public MyViewHolder(@NonNull View view) {
            super(view);

            ZekrforAdapter = view.findViewById(R.id.ZekrforAdapter);
            tedad_dore = view.findViewById(R.id.tedad_dore);
            tedad_gozarande = view.findViewById(R.id.tedad_gozarande);
            tedad_dore2 = view.findViewById(R.id.tedad_dore2);
            tedad_gozarande2 = view.findViewById(R.id.tedad_gozarande2);
            select_zekr = view.findViewById(R.id.select_zekr);
            btn_Delete = view.findViewById(R.id.btn_Delete);
            dbHelper = new AzkarDbHelper(activity) ;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            displaydensity = displayMetrics.densityDpi ;
        }

        public void bind (final Azkar azkar ){
            boolean IsTablet = activity.getResources().getBoolean(R.bool.isTablet);

            ZekrforAdapter.setText(azkar.getZekr());
            tedad_gozarande.setText(String.valueOf(azkar.getLastCount()));
            Typeface typeface = Typeface.createFromAsset(activity.getAssets(),"fonts/B Mitra Bold_YasDL.com_0.ttf");
            ZekrforAdapter.setTypeface(typeface);

            if (IsTablet){
                changeForTablets();
            }



            if (azkar.getFullCount() == -1 ){
                tedad_dore.setText("-");
            }else {
                tedad_dore.setText(String.valueOf(azkar.getFullCount()));
            }

            if (azkar.getID()<17){
                btn_Delete.setVisibility(View.INVISIBLE);
            }



            if (displaydensity <= 480 && !IsTablet){
                tedad_dore.setVisibility(View.INVISIBLE);
                tedad_dore2.setVisibility(View.INVISIBLE);
            }

            select_zekr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MyDialogs selectANDCounterDialog = new MyDialogs();
                    selectANDCounterDialog.ShowSelectDialog(activity , azkar.getID(),displaydensity);

                }
            });
            btn_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MyDialogs ConfirmDialog = new MyDialogs() ;
                    ConfirmDialog.ShowConfirmDialog(activity , displaydensity);
                    ConfirmDialog.btn_ConfirmDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dbHelper.DeleteAzkar(azkar.getID());

                            azkarList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            ConfirmDialog.dialogConfirm.dismiss();
                        }
                    });
                    ConfirmDialog.btn_CancelDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ConfirmDialog.dialogConfirm.dismiss();
                        }
                    });


                }
            });

        }
        private void changeForTablets(){
            ZekrforAdapter.setTextSize(29);
            tedad_dore.setTextSize(17);
            tedad_dore2.setTextSize(17);
            tedad_gozarande.setTextSize(17);
            tedad_gozarande2.setTextSize(17);


            if (displaydensity<=213 && displaydensity>160){
                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) select_zekr.getLayoutParams();
                params1.setMargins(45,5,45,5);
                select_zekr.setLayoutParams(params1);
            } else if(displaydensity>213 ){
                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) select_zekr.getLayoutParams();
                params1.setMargins(80,9,80,9);
                select_zekr.setLayoutParams(params1);
            }
        }
    }
}
