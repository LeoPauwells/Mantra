package com.example.mantraprototipo;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity{

    private Button SigninBtn;
    private TextView correo;
    private  TextView contraseña;
    private TextView telefono;
    private TextView signupTextView;
    private TextView username;
    private  String contraseñaS;
    private  String correoS;
    private String telefonoS;
    private String usernameS;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Creacion de constantes
    private  static final int REQUEST_CODE_NAME=0;
    private static final String TAG="etiqueta";
    public static Intent newIntent(Context context, String message){
        Intent i=new Intent(context,Signup.class);
       // i.putExtra(SEND_MESSAGE,message);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SigninBtn= findViewById(R.id.button_signin);
        correo = findViewById(R.id.et_email);
        contraseña = findViewById(R.id.et_password);
        telefono = findViewById(R.id.et_phone);
        signupTextView = findViewById(R.id.signupTextView2);
        username= findViewById(R.id.et_username);



        SigninBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View view){
                correoS=correo.getText().toString();
                contraseñaS=contraseña.getText().toString();
                usernameS=correo.getText().toString();
                telefonoS=contraseña.getText().toString();
                if(TextUtils.isEmpty(correoS)&&TextUtils.isEmpty(contraseñaS)) {
                    Warning();
                }
                else
                {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword("leo.pauwells17@tectijuana.edu.mx",
                            "17212346").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {

                                String correoAlta = correo.getText().toString();
                                String passwordAlta = contraseña.getText().toString();
                                String telefonoAlta = telefono.getText().toString();
                                String usernameAlta = username.getText().toString();

                                if (TextUtils.isEmpty(correoAlta) || TextUtils.isEmpty(passwordAlta))
                                {
                                    Error1();
                                }
                                else
                                {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("correo", correoAlta);
                                    data.put("contraseña", passwordAlta);
                                    data.put("telefono",telefonoAlta);
                                    data.put("username",usernameAlta);

                                    db.collection("usuarios-aprobar")
                                            .add(data)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    SeInsertoCorrectamente();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Warning();
                                                }
                                            });
                                }
                            }
                            else
                            {
                                Warning();
                            }
                        }
                    });
                }
            }
        });
    }

    private  void Warning()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Signup.this).create();
        alertDialog.setTitle("Alerta");
        alertDialog.setMessage("Hubo un error al registrarse. Verifique su contraseña y correo");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private  void SeInsertoCorrectamente()
    {
        String imgDefault = "iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAIAAACx0UUtAAAAA3NCSVQICAjb4U/gAAAZM0lEQVR4 nO2de0BU1brAv3luHoqCEKIY2BEyPZh6bgkZXS3T6KilWZ3SE3qO+EjNTLTCMjT15iMzhdT0HsUD kgFqpuIRTRMfqMdQUESxBEUIAbmOM8xeM7Nn7h+DKDNrb+YBuLz3+/2le+3Ze83ev1nPby1kXL+F gCAMI3/QGUCQZkBHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR 1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHW QUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZB RxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFH EdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR 1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHWQUcR1kFHEdZBRxHW QUcR1kFHEdZBRxHWQUcR1nnIHCVEeNBZQNqah8lRUmeIi+nhxSkfdEaQNuWhcZToTAlTn06Y+VJd he5B5wVpUx4aR6HOGPdW1MjJ/4SO6gedFaRNkXH9Fj7oPDQPIULskO6/VWpzz1dzHooHnR2kTWnr th0xmkFrcu4zJgt4KQICfFLWF0GQmuhd6jZ5KTiuGbmJzgQGs+1RhQy8lZxC5spNXYWdnLgJESyg MVISPBWOlzVtWo4Sozn6Cf8PJ0UTo3Oede3se+P3Opfvy6kU2YeLk3cUSWhK6gwJU5/u3q2TzXFv L9XHq4+U1ejaTA6xnHiq5fO+OdaWOXETIlj6dvOJnxCpb/p781TL886VJ2Wed1DTti1Hq0nm4dhH OrVz6cOh7tx5+OydoBRtfBMiRPV/ZPHcEfZJRSWVZRdquS6e7tzdcYjR3Ld3J2pOCosryi7v5gK4 tslJC3BJt2XbxIieXexTkreuAZWjfaG2c5QIloj+AVsyTujqaYX/fXz2wcvU42lZeUW/VquVTrdH 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 9hW0yLRh6fWaxISf4XFvXy+VaOzI0uON75VTyXMPlmu0vJuz6iOmpTeMTWpNRSWVYo0Hp4hPzAQA qOB3ZdNjR9zZd8RZnHCU6Ezxf+3rbN3nOBIbNGxMPx736T5orlopr3Mo3gwA0n843bBzhgpS0i9N HHvFtZXB9zNqaiqEeUG5PlskNOQvM7+DLk11DOTiEzNd2JmikSZr5wO4iR9luh8heTK/dMP689CF E9t35N7TaxOciB8lRLi6b7qXV2u1ka1F4M1arc3xqmpNn0FJjjwRUk1ys8Y3a5tGy3d4+ot7HXAA UqHfun4UdTbIQTamH4+buxe8FFL7jrxP2c2e1JLYV8NWL37ThdK09HpN96dWNtnLRGeKfvKRzLUu /9EBAOvCQK0BNMbbFz6xz5VGy3fosbDNBAVnY5xJhR6Mrk9gSnHLvPofMT17dB763Cbwa9r3VMkc fCJEsIDRXLB9kvRuInMW7VixOd+msU+qSUi47ycTo4Y8G+7sXiAaLd/hiUXWNjp1+yDq9j73bs0L oDMlTBsw8sUIp4JaXn5nXfaZ3203vDCaoZrEjev1zhuRfXoFO6v+gpV7E9ecAICtq0ZSf7TTP/k+ OeOC42sB3IehvyFmbfK72U+0bjvTNyrwyT/4ip2TsudX6l0advmrM4JKFjUwKNyxlrdapcgrqi68 rgG5LKJr+8heAYamnTC1SnG6uOb+nWfoOdeZ4LYRdOagJzsO/GOgdds6CXTEnHmoVHTxNC/AHRP8 j4kL8x7xdHCzV2skZd9vnLeSECEupoeB1ptM2XWljbflZ8jRFsRaoNLTHAs8bdjR08E6426zQfS+ Coe2/3Tu1q3xRe67ZkNYsEs3bVn+b/4NY04hA4Vbj9K1K7h/35a6iPtXa3sXxXh4/qYt8v8VdBRh HXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEd dBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10 FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQU YR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRh HXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEd dBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10FGEddBRhHXQUYR10 FGGd/wVC9uZxCRhJJwAAAABJRU5ErkJggg== ";

        Map<String, Object> data = new HashMap<>();
        data.put("nombre", "---");
        data.put("contraseña", contraseñaS);
        data.put("correo", correoS);
        data.put("username",usernameS);
        data.put("telefono", telefonoS);
        data.put("isAdmin", false);

        db.collection("usuarios").document(correoS).set(data);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(correoS, contraseñaS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AlertDialog alertDialog = new AlertDialog.Builder(Signup.this).create();
                            alertDialog.setTitle("Operacion Exitosa");
                            alertDialog.setMessage("Tu cuenta ha sido creada.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            Error1();
                        }
                    }
                });
    }

    private  void Error1()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Signup.this).create();
        alertDialog.setTitle("Error con la operacion");
        alertDialog.setMessage("Ocurrio un error con la operacion\nverifique que haya llenado todos los datos\n");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}

