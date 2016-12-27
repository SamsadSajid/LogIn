package com.example.dolphin.login;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
    TextView tname,temail,tpass,trepass;
    EditText ename,eemail,epass,erepass;
    Button bsignup;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tname=(TextView)findViewById(R.id.tName);
        temail=(TextView)findViewById(R.id.tEmail);
        tpass=(TextView)findViewById(R.id.tPassword);
        trepass=(TextView)findViewById(R.id.tRePassword);

        ename=(EditText)findViewById(R.id.eName);
        eemail=(EditText)findViewById(R.id.eEmail);
        epass=(EditText)findViewById(R.id.ePassword);
        erepass=(EditText)findViewById(R.id.eRePassword);

        bsignup=(Button)findViewById(R.id.bSignUp);

        bsignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(ename.getText().toString().equals("")||eemail.getText().toString().equals("")||epass.getText().toString().equals("")){
                    builder=new AlertDialog.Builder(SignUp.this);
                    builder.setTitle("Something went wrong");
                    builder.setMessage("Please provide all the informations.");
                    builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface,int which){
                             dialogInterface.dismiss();
                        }
                    });

                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }

                else if(!(epass.getText().toString().equals(erepass.getText().toString()))){
                    builder=new AlertDialog.Builder(SignUp.this);
                    builder.setTitle("Password mismatch.");
                    builder.setMessage("Passwords do not match.");
                    builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface,int which){
                            dialogInterface.dismiss();
                            epass.setText("");
                            erepass.setText("");
                        }
                    });

                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }
            }
        });
    }
}
