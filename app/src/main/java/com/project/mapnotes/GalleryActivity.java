package com.project.mapnotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;

public class GalleryActivity extends AppCompatActivity {

    GridView notasGrid;
    TextView reportTitle, reportDescription;
    ImageView imageView;
    VideoView videoView;
    int position1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        notasGrid = (GridView) findViewById(R.id.notesGrid);


        MyApplication myApp = (MyApplication) getApplication();

        final FirebaseListAdapter adapter = new FirebaseListAdapter<Nota>(this, Nota.class, R.layout.nota_row, myApp.getRef()) {
            @Override
            protected void populateView(View v, Nota model, int position) {
                super.populateView(v, model, position);

                position1 = position;
                reportTitle = (TextView) v.findViewById(R.id.textTitle);
                reportDescription = (TextView) v.findViewById(R.id.textReport);
                imageView = (ImageView) v.findViewById(R.id.photoReport);
                videoView = (VideoView) v.findViewById(R.id.videoReport);

                imageView.setVisibility(View.INVISIBLE);
                videoView.setVisibility(View.INVISIBLE);

                reportTitle.setText(model.getTitle());
                reportDescription.setText(model.getNota());

                if(model.getImagePath()!= null) {
                    imageView.setVisibility(View.VISIBLE);
                    File imagePath = new File(model.getImagePath());
                    Picasso.with(getBaseContext()).load(imagePath).fit().into(imageView);
                }
                if(model.getVideoPath()!= null) {
                    MediaController mediaController = new MediaController(getBaseContext());
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setVideoPath(model.getVideoPath());
                    videoView.setMediaController(mediaController);
                    mediaController.hide();
                    //videoView.requestFocus();
                    videoView.start();

                }
                if(model.getImagePath() == null && model.getVideoPath()== null){
                    imageView.setVisibility(View.VISIBLE);
                    Picasso.with(getBaseContext()).load(R.drawable.noimageavailable).fit().into(imageView);
                }
            }
        };

        notasGrid.setAdapter(adapter);
    }
}
