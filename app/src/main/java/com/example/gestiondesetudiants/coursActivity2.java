package com.example.gestiondesetudiants;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class coursActivity2 extends AppCompatActivity {
     private CardView addpdf;
     private EditText pdftitle;
     private Button uploadbtn;
     private DatabaseReference reference;
     private  StorageReference storageReference;
     String downloadUr1="";
     private ProgressDialog pd;
     private final int REQ=1;
     ListView list;
     private Uri pdfData;
     Button Downloadbtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours2);
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);

        addpdf=findViewById(R.id.addpdf);
        pdftitle=findViewById(R.id.pdftitle);
        uploadbtn=findViewById(R.id.uploadbtn);



        list = findViewById(R.id.list);



        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user.getEmail().toString().equals("admin@admin.ma")){
            pdftitle.setVisibility(View.VISIBLE);
            uploadbtn.setVisibility(View.VISIBLE);
            addpdf.setVisibility(View.VISIBLE);

        }




        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("PDFs");

        ArrayList<String> newsListLink = new ArrayList<>();

        final ArrayList<String> newsList = new ArrayList<>();
        final ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this ,R.layout.list_ithem, newsList);
        list.setAdapter(newsAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = newsListLink.get(position);
                downloadPDF(link);
                // Open the link in a web browser or other appropriate action
            }
        });



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                newsList.clear();
                newsListLink.clear();
                for (DataSnapshot data: snapshot.getChildren()){
                    for(DataSnapshot dataSnapshot : data.getChildren()){
                        if(dataSnapshot.getKey().equals("title")){
                            newsList.add(dataSnapshot.getValue().toString());
                        }
                        if(dataSnapshot.getKey().equals("pdfUrl")){
                            newsListLink.add(dataSnapshot.getValue().toString());
                        }
                    }
                }
                newsAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(coursActivity2.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });












        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfData!=null){
                    String id = UUID.randomUUID().toString();
                    storageReference.child(id+"."+getFileExtension(pdfData)).putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(coursActivity2.this, "File is added", Toast.LENGTH_SHORT).show();

                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();

                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PDFs");
                                    databaseReference.child(id).child("title").setValue(pdftitle.getText().toString());
                                    databaseReference.child(id).child("pdfUrl").setValue(downloadUrl);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(coursActivity2.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(coursActivity2.this, "Please select file", Toast.LENGTH_SHORT).show();
                }
            }
        });















        addpdf.setOnClickListener(view -> openGallery());

    }


    private void downloadPDF(String pdfUrl) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfUrl));

        // Customize download settings:
        request.setTitle("title");
        request.setDescription("Downloading PDF file");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "pdfUrl");

        downloadManager.enqueue(request);
    }




    private void openGallery(){
        Intent intent=new Intent();
        intent.setType("pdf/docs/ppt");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF"), REQ);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode == RESULT_OK){
            pdfData=data.getData();
        }
    }

    String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }






}



