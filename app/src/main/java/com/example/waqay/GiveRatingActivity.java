package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;

public class GiveRatingActivity extends AppCompatActivity implements DownloadFile.Listener {
String year_sem,subject,student_id,topic,home_work_name,checked,date,pdf_info,remarks;
    ProgressBar progressBar;
    String url = "http://www.cals.uidaho.edu/edComm/curricula/CustRel_curriculum/content/sample.pdf";

    RemotePDFViewPager remotePDFViewPager ;
    PagerAdapter adapter;
    TabLayout tabLayout;
RelativeLayout root;
TextView textView;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_rating);
        root=(RelativeLayout) findViewById(R.id.root);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
       year_sem= getIntent().getStringExtra("year_sem");
        subject=getIntent().getStringExtra("subject");
        student_id=getIntent().getStringExtra("student_id");
        topic=getIntent().getStringExtra("topic");
        home_work_name=getIntent().getStringExtra("home_work_name");
        checked=getIntent().getStringExtra("checked");
        date=getIntent().getStringExtra("date");
        pdf_info=getIntent().getStringExtra("pdf_info");
        remarks=getIntent().getStringExtra("remarks");
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(GiveRatingActivity.this);
                LayoutInflater inflater = GiveRatingActivity.this.getLayoutInflater();
                View  row_view = inflater.inflate(R.layout.remarks1,null);
                EditText t_handwriting=row_view.findViewById(R.id.t_handwriting);
                EditText  t_neatness=row_view.findViewById(R.id.t_neatness);
                EditText      t_sentence_framing=row_view.findViewById(R.id.t_sentence_framing);
                EditText t_creativity=row_view.findViewById(R.id.t_creativity);
                EditText       t_presentation=row_view.findViewById(R.id.t_presentation);
                EditText t_highlighters=row_view.findViewById(R.id.t_highlighters);
                builder.setView(row_view);
                builder.setTitle("Remarks");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    if(t_handwriting.getText().toString().length()!=0&&t_neatness.getText().toString().length()!=0&&t_sentence_framing.getText().toString().length()!=0&&t_creativity.getText().toString().length()!=0&&t_presentation.getText().toString().length()!=0&&t_highlighters.getText().toString().length()!=0)
                    {
                        ArrayList<String> al = new ArrayList<>();
                        al.add(t_handwriting.getText().toString());
                        al.add(t_neatness.getText().toString());
                        al.add(t_sentence_framing.getText().toString());
                        al.add(t_creativity.getText().toString());
                        al.add(t_presentation.getText().toString());
                        al.add(t_highlighters.getText().toString());
                        StudentHomeWork studentHomeWork= new StudentHomeWork();
                        studentHomeWork.checked="checked";
                        studentHomeWork.remarks="Availbale";
                        studentHomeWork.date=date;
                        studentHomeWork.pdf_info=pdf_info;
                        studentHomeWork.remarks_list=al;
                        FirebaseDatabase.getInstance().getReference("StudentHomeWork/"+year_sem+"/"+subject+"/"+student_id+"/"+topic+"/"+home_work_name).setValue(studentHomeWork).addOnCompleteListener
                                (task -> {
                                            Toast.makeText( GiveRatingActivity.this,"Validation Uploaded Successfully",Toast.LENGTH_LONG).show();

                                        }

                                );
                        finish();
                    }



                });
                builder.setNegativeButton("Cancel",(dialog, which) -> {});
                builder.show();
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        textView=findViewById(R.id.textView1);
        progressBar.setVisibility(View.VISIBLE);
        // url="https://firebasestorage.googleapis.com/v0/b/waqay-a20cc.appspot.com/o/uploads%2Fstack_applications1.pdf?alt=media&token=ded2c608-ed16-4e15-a564-710475c369f2";
       // remotePDFViewPager =(RemotePDFViewPager)findViewById(R.id.pdfViewPager);
        remotePDFViewPager =
                new RemotePDFViewPager(getApplicationContext(), pdf_info, this);
//        // remotePDFViewPager.setScreenReaderFocusable();
        adapter= remotePDFViewPager.getAdapter();


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setCustomView(R.layout.toolbar);
            View v=  getSupportActionBar().getCustomView();
            TextView t_title= v.findViewById(R.id.t_title);
            t_title.setText("Pasha");
            this.getSupportActionBar().setDisplayShowCustomEnabled(true);
            this.getSupportActionBar().setDisplayShowTitleEnabled(false);
            LayoutInflater inflator = LayoutInflater.from(this);
            View v1 = inflator.inflate(R.layout.toolbar, null);

//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
            ((TextView)v.findViewById(R.id.t_title)).setText("PDF VIEWER");

//assign the view to the actionbar
            this.getSupportActionBar().setCustomView(v);
        }
       // pdfView =(PDFView) findViewById(R.id.pdf);
        String url=pdf_info;
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        // progressBar.setVisibility(View.VISIBLE);
        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setType("application/pdf");
        //intent.setData(Uri.parse(url));
        //startActivity(intent);
        // pdfView.fromUri(Uri.parse(getIntent().getStringExtra("list"))).load();
       // new RetrievePDFStream().execute(url);
       // progressBar.setVisibility(View.GONE);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Toast.makeText(KYCNewActivity.this,item.getItemId()+"",Toast.LENGTH_LONG).show();
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return true;
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter= new PDFPagerAdapter(this, destinationPath);
        // new PDFPagerAdapter()
        remotePDFViewPager.setAdapter(adapter);
int count=adapter.getCount();
        textView.setText((1)+"/"+count);
       // tabLayout.setupWithViewPager(remotePDFViewPager, true);
        //int limit= remotePDFViewPager.getOffscreenPageLimit();
        int  limit= remotePDFViewPager.getChildCount();
        remotePDFViewPager.scrollTo(0,0);
        // remotePDFViewPager.dispatchDisplayHint(1);
//     remotePDFViewPager.requestFitSystemWindows();
//     remotePDFViewPager.autofill(AutofillValue.forText("ma"));
        // remotePDFViewPager.
        remotePDFViewPager.buildLayer();
        remotePDFViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Toast.makeText(PDFViewerActivity.this,position+","+positionOffset+","+positionOffsetPixels,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
               // Toast.makeText(GiveRatingActivity.this,limit+","+position,Toast.LENGTH_SHORT).show();
textView.setText((position+1)+"/"+count);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Toast.makeText(PDFViewerActivity.this,state+"s",Toast.LENGTH_SHORT).show();
            }
        });

       // setContentView(R.layout.activity_give_rating);
        Toast.makeText(GiveRatingActivity.this,"Success",Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        root.removeAllViews();
        // root.addView(etPdfUrl,

        root.addView(remotePDFViewPager,
                RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        root.addView(textView);
        root.addView(fab);

    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    class RetrievePDFStream extends AsyncTask<String,Void, InputStream>
//    {
//        @Override
//        protected void onPostExecute(InputStream inputStream) {
//            pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {
//                @Override
//                public void loadComplete(int nbPages) {
//
//                    progressBar.setVisibility(View.GONE);
//                }
//            }).load();
//            //pdfView.fromStream(inputStream).load();
//        }
//
//        @Override
//        protected InputStream doInBackground(String... strings) {
//            InputStream inputStream=null;
//            try {
//                URL url=new URL(strings[0]);
//                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
//                if(urlConnection.getResponseCode()==200)
//                {
//                    inputStream= new BufferedInputStream(urlConnection.getInputStream());
//                }
//            }
//            catch(IOException e) {
//                return null;
//            }
//            return  inputStream;
//        }
//
//
//    }
}