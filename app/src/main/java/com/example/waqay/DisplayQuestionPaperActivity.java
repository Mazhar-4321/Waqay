package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;

public class DisplayQuestionPaperActivity extends AppCompatActivity implements DownloadFile.Listener {

    ProgressBar progressBar;
    TextView textView;
    String url = "http://www.cals.uidaho.edu/edComm/curricula/CustRel_curriculum/content/sample.pdf";
    RelativeLayout root;
    RemotePDFViewPager remotePDFViewPager ;
    PagerAdapter adapter;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String s = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference reference_mazhar = database1.getReference("Users/" + s);
        reference_mazhar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUserData userData = snapshot.getValue(FirebaseUserData.class);
                if(userData.isActive.equals("0")) {
                    finish();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        setContentView(R.layout.activity_p_d_f_viewer);
        root=(RelativeLayout) findViewById(R.id.root);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        textView=(TextView)findViewById(R.id.textView1);
        // textView.setText("1/1");
        String url=getIntent().getStringExtra("list");
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        // url="https://firebasestorage.googleapis.com/v0/b/waqay-a20cc.appspot.com/o/uploads%2Fstack_applications1.pdf?alt=media&token=ded2c608-ed16-4e15-a564-710475c369f2";
        // remotePDFViewPager =(RemotePDFViewPager)findViewById(R.id.pdfViewPager);
        remotePDFViewPager =
                new RemotePDFViewPager(getApplicationContext(), url, this);
        // remotePDFViewPager.setScreenReaderFocusable();
        adapter= remotePDFViewPager.getAdapter();
        //int count=adapter.getCount();

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
            getSupportActionBar().hide();
        }
        //  pdfView =(PDFView) findViewById(R.id.pdf);
        //pdfView.setBackgroundColor(Color.parseColor("#2eaFe8"));


        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setType("application/pdf");
        //intent.setData(Uri.parse(url));
        //startActivity(intent);
        // pdfView.fromUri(Uri.parse(getIntent().getStringExtra("list"))).load();
//new RetrievePDFStream().execute(url);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Toast.makeText(KYCNewActivity.this,item.getItemId()+"",Toast.LENGTH_LONG).show();
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSuccess(String url, String destinationPath) {

        adapter= new PDFPagerAdapter(this, destinationPath);
        int count= adapter.getCount();
        textView.setText((1)+"/"+count);
        // new PDFPagerAdapter()
        remotePDFViewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(remotePDFViewPager, true);
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
                //Toast.makeText(PDFViewerActivity.this,position+","+positionOffset+","+positionOffsetPixels,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
                //  Toast.makeText(PDFViewerActivity.this,count+","+position,Toast.LENGTH_SHORT).show();
                textView.setText((position+1)+"/"+count);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Toast.makeText(PDFViewerActivity.this,state+"s",Toast.LENGTH_SHORT).show();
            }
        });
        // setContentView(remotePDFViewPager);
        // Toast.makeText(PDFViewerActivity.this,"Success",Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        root.removeAllViews();
        // root.addView(etPdfUrl,

        root.addView(remotePDFViewPager,
                RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        root.addView(textView);

    }

    @Override
    public void onFailure(Exception e) {
        Toast.makeText(DisplayQuestionPaperActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show(); progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        // Toast.makeText(PDFViewerActivity.this,progress+"",Toast.LENGTH_SHORT).show();

    }


}