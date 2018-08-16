package com.example.santos.camera_for_dl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    public Button camera_button;
    public ImageView picture_image;
    public int IMAGE_REQUEST_CODE = 1;//Código de requisição da câmera
    public Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picture_image = (ImageView) findViewById(R.id.imageView_camera);
        camera_button = (Button) findViewById(R.id.button_camera);

        //Checa se o usuário deu permissão para utilizar a câmera
        //Essa permissão se encontra no Manifest
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            //Se não possui permissão, a Activity faz a solicitação ao usuário
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        //Quando o botão for pressionado ele ativará a função de chamar a câmera
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCamera();
            }
        });
    }

    //Função que chama a câmera
    public void callCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            //Aqui nós recebemos a imagem com um bundle e depois passamos para o tipo Bitmap
            Bundle extra = data.getExtras();
            Bitmap image = (Bitmap) extra.get("data");

            //Nessa etapa nós convertemos a imagem em um array de bytes
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            //Aqui nós passamos a imagem para a Base64
            //encodedImage é a nossa imagem na Base64
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

            //Exibimos a imagem no ImageView antes da conversão para Base64
            picture_image.setImageBitmap(image);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


