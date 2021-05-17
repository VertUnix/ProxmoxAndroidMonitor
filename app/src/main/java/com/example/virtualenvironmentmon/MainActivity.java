
package com.example.virtualenvironmentmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtualenvironmentmon.models.Locations;
import com.example.virtualenvironmentmon.models.LocationsDB;
import com.example.virtualenvironmentmon.models.LoginValidationResult;
import com.example.virtualenvironmentmon.models.daos.LocationsDao;

import static com.example.virtualenvironmentmon.Constants.*;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import it.corsinvest.proxmoxve.api.PveClient;
import it.corsinvest.proxmoxve.api.Result;

public class MainActivity extends AppCompatActivity {

    private EditText etIP;
    private EditText etPort;
    private EditText etUser;
    private EditText etPass;
    private Button login_btn;
    private TextView etAttempts;
    private ProgressBar progressBar;
    private TextView tvLogin;
    private TextView tvServerVersion;
    private String versiune;
    PveClient client;

    LoginValidationResult loginResult;
    private int counter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        login_btn = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.loginProgress);
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setVisibility(View.INVISIBLE);
        tvServerVersion = findViewById(R.id.tvServerVersion);

        PveClient cl = new PveClient("dummy_IP", 8006);
        loginResult = new LoginValidationResult(-1, cl);

        //Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        //startActivity(intent);

        SharedPreferences sp;
        sp = getSharedPreferences("sharedPref", MODE_PRIVATE);
        String mesaj = sp.getString("username", "");
        if(mesaj != "")
            etUser.setText(mesaj);
        String IP_server = sp.getString("IP", "");
        if(IP_server != "")
            etIP.setText(IP_server);

            login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputIP = etIP.getText().toString();
                int inputPort = Integer.parseInt(etPort.getText().toString());
                String inputName = etUser.getText().toString();
                String inputPassword = etPass.getText().toString();

                if (inputName.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter both username and password.", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    tvLogin.setVisibility(View.VISIBLE);

                    Thread thread_login = new Thread() {
                        public void run() {
                                System.out.println("Does it work?");
                                try {
                                    validate(inputName, inputPassword, inputIP, inputPort);
                                } catch (Exception ex) {
                                    Log.d("Validation thread: ","Validation unsuccessful.");
                                }
                                System.out.println("Nope, it doesnt...again.");
                            }

                    };

                    Log.d("Login code 1:", String.valueOf(loginResult.loginCode));
                    thread_login.start();
                    Log.d("Login code: 2", String.valueOf(loginResult.loginCode));

                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //loginCode = validate(inputName, inputPassword, inputIP, inputPort);
                    Log.d("Login code: 3", String.valueOf(loginResult.loginCode));


                    --counter;
                     if(loginResult.loginCode == WRONG_CREDENTIALS) {
                        Toast.makeText(MainActivity.this, "Wrong credentials.", Toast.LENGTH_LONG).show();
                        GradientDrawable gd = new GradientDrawable();
                        gd.setColor(Color.parseColor("#00ffffff"));
                        gd.setStroke(2,Color.RED);
                        etUser.setBackground(gd);
                        etPass.setBackground(gd);
                        progressBar.setVisibility(View.INVISIBLE);
                        tvLogin.setVisibility(View.INVISIBLE);


                    }
                    else if(loginResult.loginCode == WRONG_NODE) {
                        Toast.makeText(MainActivity.this, "Wrong IP or port.", Toast.LENGTH_LONG).show();
                        GradientDrawable gd = new GradientDrawable();
                        gd.setColor(Color.parseColor("#00ffffff"));
                        gd.setStroke(2,Color.RED);
                        etIP.setBackground(gd);
                        etPort.setBackground(gd);
                        progressBar.setVisibility(View.INVISIBLE);
                        tvLogin.setVisibility(View.INVISIBLE);

                    }
                    else if(loginResult.loginCode == LOGIN_OK){


                        Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                        etPass.setText("");
                        progressBar.setVisibility(View.INVISIBLE);
                        tvLogin.setVisibility(View.INVISIBLE);

                        SharedPreferences sp;
                        sp = getSharedPreferences("sharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", etUser.getText().toString());
                        editor.putString("IP", etIP.getText().toString());
                        editor.putString("port", etPort.getText().toString());
                        editor.commit();

                        tvServerVersion.setText(versiune);

                         try {
                             String serverReply = String.valueOf(loginResult.client.getVersion().version().getResponse().get("data"));
                             System.out.println("+++++++++++++" + serverReply);
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }

                        /**
                         * The following lines works only if this activity and HomeActivity run on the same process.
                         */
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);

                        final PveClient objSent = loginResult.client;
                        final Bundle bundle = new Bundle();
                        bundle.putBinder("object_value", new ObjectWrapperForBinder(objSent));
                        startActivity(intent.putExtras(bundle));
                        Log.d("objSent", "original object=");

                    }

//                    if(counter == 0)
//                        login_btn.setEnabled(false);

                }
            }
        });
    }

    /**
     * Validates the login and returns the client object, wrapped together with the login status.
     * @param name username
     * @param password user password
     * @param IP server IP
     * @param port defaulted to 8006
     * @return Returns a class that contains both an integer login status code (successful/ unsuccessful)
     * and a PveClient class which contains the access token needed to authorise further actions.
     */
    private LoginValidationResult validate(String name, String password, String IP, int port) {

        LoginValidationResult loginValidationResult= new LoginValidationResult ();
        try {
            client = new PveClient(IP, port);
            Result result = client.login(name, password, "pam");
            loginValidationResult.client = client;
            if (result.isSuccessStatusCode()) {
                Log.d("MainActivity", "Login Successful");
                String serverReply = String.valueOf(client.getVersion().version().getResponse().get("data"));
                System.out.println(serverReply);


                //JSONObject replyJsonObject = new JSONObject(serverReply);
                //String version = replyJsonObject.getString("version");
                //System.out.println("VERSIUNE: " + version);
                //this.versiune = version;
                loginValidationResult.loginCode = LOGIN_OK;
                loginResult = loginValidationResult;
                return  loginValidationResult;
            } else if (result.getStatusCode() == CONN_TIMEOUT) {
                loginValidationResult.loginCode = WRONG_NODE;
                loginResult = loginValidationResult;
                return  loginValidationResult;
            } else if (result.getStatusCode() == FORBIDDEN) {
                loginValidationResult.loginCode = WRONG_CREDENTIALS;
                loginResult = loginValidationResult;
                return  loginValidationResult;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            loginValidationResult.loginCode = UNKNOWN_ERROR;
            loginResult = loginValidationResult;
            return  loginValidationResult;
        }
        loginValidationResult.loginCode = UNKNOWN_ERROR;
        return  loginValidationResult;
    }
}

//        try {
//
//            PveClient client = new PveClient("192.168.0.11", 8006);
//            if (client.login("test", "1234567890", "pam")) {
//                //version
//                System.out.println("=======================\n=======================");
//                System.out.println(client.getVersion().version().getResponse().get("data"));
//                System.out.println("=======================\n=======================");
//                JSONArray nodes = client.getNodes().index().getResponse().getJSONArray("data");
//                for (int i = 0; i < nodes.length(); i++) {
//                    System.out.println(nodes.get(i));
//                }
//
//                //loops vms qemu
//                JSONArray vms = client.getNodes().get("pve").getQemu().vmlist().getResponse().getJSONArray("data");
//                for (int i = 0; i < vms.length(); i++) {
//                    System.out.println(vms.get(i));
//                }
//            }
//        }
//
//        catch(Exception e)
//        {
//            System.out.println("ERR: " +  e.getMessage());
//        }


