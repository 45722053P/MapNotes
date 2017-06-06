package com.project.mapnotes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.firebase.client.Firebase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class AddActivity extends AppCompatActivity {

    EditText addTitle, addNote;
    ImageButton cameraButton, galleryButton, micButton;
    ImageView checkGallery, checkCamera, checkMic;
    boolean tookPhoto = false;
    boolean tookVideo = false;
    boolean tookAudio = false;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    double latitude = 0;
    double longitude = 0;


    public AddActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //Nos subscribimos al Evento que captura la Localizacion
        EventBus.getDefault().register(this);

        addTitle = (EditText) findViewById(R.id.addTitle);
        addNote = (EditText) findViewById(R.id.addNote);
        checkGallery = (ImageView) findViewById(R.id.checkGallery);
        checkCamera = (ImageView) findViewById(R.id.checkCamera);
        checkGallery.setVisibility(View.INVISIBLE);
        checkCamera.setVisibility(View.INVISIBLE);

        //Imagebuttons. Pero tambien se podria hacer con el layout qque coge el imagebutton y el texto
        galleryButton = (ImageButton) findViewById(R.id.galleryButton);
        cameraButton = (ImageButton) findViewById(R.id.cameraButton);

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideo();
                checkGallery.setVisibility(View.VISIBLE);
                //Toast.makeText(getContext(), "Attached picture", Toast.LENGTH_SHORT).show();

            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
                checkCamera.setVisibility(View.VISIBLE);
                //Toast.makeText(getContext(), "Attached photo", Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton sendNote = (FloatingActionButton) findViewById(R.id.sendNote);
        sendNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyApplication myApp = (MyApplication) getApplication();
                Firebase note = myApp.getRef().push();

                Nota nota = new Nota();
                nota.setTitle(addTitle.getText().toString());
                nota.setNota(addNote.getText().toString());
                nota.setLatitud(latitude);
                nota.setLongitud(longitude);
                if (tookPhoto) {

                        nota.setImagePath(imageFile());


                    //Lo ponemos a false otra vez
                    tookPhoto = false;
                }
                if (tookVideo) {
                    nota.setVideoPath(videoFile());
                    //Lo ponemos a false otra vez
                    tookVideo = false;
                }
                //Instruccion para subir a Firebase
                note.setValue(nota);

                //Vaciamos el editTextNota
                addTitle.setText("");
                addNote.setText("");

                //Escondemos los iconos
                checkGallery.setVisibility(View.INVISIBLE);
                checkCamera.setVisibility(View.INVISIBLE);
                //Toast.makeText(getContext(), videoFile(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void openVideo(){
        tookVideo = true;
        Intent takeVideoIntent = new Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
        // Ensure that there's a camera activity to handle the intent
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void openCamera() {

        tookPhoto = true;
        Intent takePictureIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }
//        //Creamos el Intent para llamar a la Camara
//        Intent cameraIntent = new Intent(
//                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        //Creamos una carpeta en la memeria del terminal
//        File imagesFolder = new File(
//                Environment.getExternalStorageDirectory(), "Tutorialeshtml5");
//        imagesFolder.mkdirs();
//        //añadimos el nombre de la imagen
//        File image = new File(imagesFolder, "foto.jpg");
//        Uri uriSavedImage = Uri.fromFile(image);
//        //Le decimos al Intent que queremos grabar la imagen
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
//        //Lanzamos la aplicacion de la camara con retorno (forResult)
//        startActivityForResult(cameraIntent, 1);




    public String videoFile() {

        //Cogemos el PATH del ultimo video tomado
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToLast();
        String path =  cursor.getString(column_index_data);

        return path;
    }

    public String imageFile() {

        //Cogemos el PATH del ultimo video tomado
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();
        String path =  cursor.getString(column_index_data);

        return path;
    }


    //Recogemos el evento con la localizacion que nos da el LocationChangedEvent al que estamos suscritos
    @Subscribe
    public void onLocationChangedEvent(LocationChangedEvent event) {

        latitude = event.getLocation().getLatitude();
        longitude = event.getLocation().getLongitude();

        //Para verificar la posicion
        Log.e("LATITUDE_RECIBIEDO_ADD", latitude + " -------------------------");
        Log.e("LONGITUDE_RECIBIEDO_ADD", longitude + " -------------------------");
    }
}
