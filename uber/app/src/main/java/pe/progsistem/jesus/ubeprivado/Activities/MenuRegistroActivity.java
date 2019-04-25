package pe.progsistem.jesus.ubeprivado.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pe.progsistem.jesus.ubeprivado.Menu;
import pe.progsistem.jesus.ubeprivado.R;

public class MenuRegistroActivity extends AppCompatActivity {
  private Button btnPasajero,btnConductor,btnEmpresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_registro);

        btnConductor =(Button) findViewById(R.id.btnConductor);
        btnPasajero =(Button) findViewById(R.id.btnPasajero);
        btnEmpresa =(Button) findViewById(R.id.btnEmpresa);




        btnConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg = new Intent(MenuRegistroActivity.this, RegistroChofer.class);
                MenuRegistroActivity.this.startActivity(intentReg);
            }
        });
        btnPasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg = new Intent(MenuRegistroActivity.this, Menu.class);
                MenuRegistroActivity.this.startActivity(intentReg);

            }
        });
        btnEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
