package com.rajatv.surajv.roshank.sac.Results;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.dashboard.RegisterObject;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * Created by CREATOR on 12/11/2017.
 */

public class Publish_Result extends AppCompatActivity implements View.OnClickListener {

    View results_back;
    Spinner event_type, main_event_type, subevent_event_type;
    ArrayList<String> event_list = new ArrayList<>(), main_event_list = new ArrayList<>(), subevent_list = new ArrayList<>();
    ArrayAdapter<String> event_arrayadapter, main_eventadapter, subevent_arrayadapter;
    EditText position, position_holder_name;
    Button add_more, publish_result;
    TextView added_positions_show;
    String event_name, event_type_name, subevent_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.create_result);
        Log.e("Publish Result", "Everything Fine...(1)");

        init();
    }

    private void init() {
        results_back = findViewById(R.id.results_back);
        results_back.setOnClickListener(this);
        position = (EditText) findViewById(R.id.position);
        position_holder_name = (EditText) findViewById(R.id.position_holder_name);
        position.setVisibility(GONE);
        position_holder_name.setVisibility(GONE);
        add_more = (Button) findViewById(R.id.add_more);
        add_more.setOnClickListener(this);
        add_more.setVisibility(GONE);
        added_positions_show = (TextView) findViewById(R.id.added_positions_show);
        added_positions_show.setVisibility(GONE);
        publish_result = (Button) findViewById(R.id.publish_result);
        publish_result.setVisibility(GONE);
        publish_result.setOnClickListener(this);

        event_type = (Spinner) findViewById(R.id.event_type);
        main_event_type = (Spinner) findViewById(R.id.main_event_type);
        main_event_type.setVisibility(GONE);
        subevent_event_type = (Spinner) findViewById(R.id.subevent_event_type);
        subevent_event_type.setVisibility(GONE);
        set_event_type();
        event_arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, event_list);
        event_arrayadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        event_type.setAdapter(event_arrayadapter);

        event_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("Event Type---", String.valueOf(i));
                event_type_name = event_list.get(i);
                set_main_event_type(i);
                Log.e("Main Event---", "Set");

                main_eventadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, main_event_list);
                main_eventadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                main_event_type.setAdapter(main_eventadapter);

                main_event_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        event_name = main_event_list.get(i);
                        Log.e("Main Event---", main_event_list.get(i));
                        set_sub_event_type(main_event_list.get(i));
                        subevent_arrayadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subevent_list);
                        subevent_arrayadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        subevent_event_type.setAdapter(subevent_arrayadapter);

                        subevent_event_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                subevent_name = subevent_list.get(i);
                                position.setVisibility(View.VISIBLE);
                                position_holder_name.setVisibility(View.VISIBLE);
                                add_more.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void set_sub_event_type(String i) {
        switch (i) {
            case "CONCREATE":
                subevent_list.clear();
                subevent_list.add("Cryptic");
                subevent_list.add("Real Estate");
                subevent_list.add("Bridge Designing");
                subevent_list.add("Estratto");
                subevent_list.add("Novus");
                break;
            case "BYTE WORLD":
                subevent_list.clear();
                subevent_list.add("Algo-Z-Ripper");
                subevent_list.add("Web Weaver");
                subevent_list.add("Panorama");
                subevent_list.add("Hackerzilla");
                subevent_list.add("Appathon");
                subevent_list.add("Logo Design");
                break;
            case "ROBOTICS":
                subevent_list.clear();
                subevent_list.add("Path Seguidor");
                subevent_list.add("Death Race");
                subevent_list.add("Robo Soccer");
                subevent_list.add("Lift Off");
                break;
            case "OHM":
                subevent_list.clear();
                subevent_list.add("virtual circuitarix");
                subevent_list.add("Smash debug");
                subevent_list.add("Electromaze");
                subevent_list.add("Open hardware");
                break;
            case "AAYAM":
                subevent_list.clear();
                subevent_list.add("Jenga");
                subevent_list.add("Ayame (Main Design)");
                subevent_list.add("Hatsumi (Model Making)");
                subevent_list.add("Junihitoe (Apparel Making)");
                subevent_list.add("Tsukimi (Art Expo)");
                subevent_list.add("Jinsei (Photography)");
                break;
            case "YANTRIKI":
                subevent_list.clear();
                subevent_list.add("Machinist");
                subevent_list.add("Mech-a-rush");
                subevent_list.add("Trivia do moto");
                subevent_list.add("Innovative Examplar");
                break;
            case "ABHINAY":
                subevent_list.clear();
                subevent_list.add("Andaaz e adakari");
                subevent_list.add("Poezia");
                subevent_list.add("Skit/Play");
                subevent_list.add("Film-ignito");
                subevent_list.add("Cine Quiz");
                break;
            case "AVLOKAN":
                subevent_list.clear();
                subevent_list.add("Geek-o-mania");
                subevent_list.add("Virtual placement");
                subevent_list.add("Mystery solving");
                subevent_list.add("Word rush");
                subevent_list.add("Clash-o-mania");
                break;
            case "NRITYANGANA":
                subevent_list.clear();
                subevent_list.add("Solo Dance");
                subevent_list.add("Duet / Couple Dance");
                subevent_list.add("Group Dance");
                subevent_list.add("Impromptu");
                break;
            case "KALAKRITI":
                subevent_list.clear();
                subevent_list.add("Origami");
                subevent_list.add("Face Canvas");
                subevent_list.add("Express your mood");
                subevent_list.add("Build your shape");
                subevent_list.add("No brushes");
                subevent_list.add("Tooncon");
                subevent_list.add("Salty feeling");
                subevent_list.add("Make your own T");
                subevent_list.add("Rang barse");
                subevent_list.add("Get inked");
                break;
            case "SANHITA":
                subevent_list.clear();
                subevent_list.add("Parliamentary Debate");
                subevent_list.add("Debate");
                subevent_list.add("A Minute to Win it");
                subevent_list.add("Pic a Story");
                subevent_list.add("Six Word Story");
                subevent_list.add("Open Mic");
                break;
            case "RAAGA":
                subevent_list.clear();
                subevent_list.add("Solo Singing");
                subevent_list.add("Music Mob");
                subevent_list.add("Antakshari");
                break;
            case "PRATIBIMB":
                subevent_list.clear();
                subevent_list.add("Brain Stroke(compulsary round)");
                subevent_list.add("Exhiber(Talent Round)");
                subevent_list.add("Round-De NIT Patna(Treasure Hunt)");
                subevent_list.add("Personal Interview");
                subevent_list.add("Photoshoot");
                subevent_list.add("Grand Finale");
                break;
            default:
                subevent_list.clear();
        }
        subevent_event_type.setVisibility(View.VISIBLE);
    }

    private void set_main_event_type(int i) {
        switch (i) {
            case 0:
                main_event_list.clear();
                main_event_list.add("CONCREATE");
                main_event_list.add("BYTE WORLD");
                main_event_list.add("ROBOTICS");
                main_event_list.add("OHM");
                main_event_list.add("AAYAM");
                main_event_list.add("YANTRIKI");
                break;
            case 1:
                main_event_list.clear();
                main_event_list.add("PRATIBIMB");
                main_event_list.add("RAAGA");
                main_event_list.add("SANHITA");
                main_event_list.add("AVLOKAN");
                main_event_list.add("NRITYANGANA");
                main_event_list.add("KALAKRITI");
                main_event_list.add("ABHINAY");
                break;
            case 2:
                main_event_list.clear();
                main_event_list.add("INFERNO X");
                main_event_list.add("GULLY CRICKET");
                main_event_list.add("FUTSAL");
                main_event_list.add("PAPER DANCE");
                main_event_list.add("VIRTUAL JUNKIE");
                main_event_list.add("STREETS");
                break;
        }
        main_event_type.setVisibility(View.VISIBLE);
    }

    private void set_event_type() {
        event_list.add("Technical Event");
        event_list.add("Cultural Event");
        event_list.add("Fun Event");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.results_back:
                finish();
                break;
            case R.id.add_more:
                check_if_position();
                break;
            case R.id.publish_result:
                publish_results();
                break;
        }
    }

    private void publish_results() {
        String extra_data = position_holder_name.getText().toString();
        String extra_position = position.getText().toString();
        if(!extra_data.isEmpty()){
            if(!extra_position.isEmpty()){
                added_positions_show.append(extra_position + ". "+extra_data+" \n");
            }else {
                added_positions_show.append(extra_data + " \n");
            }
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Publishing Result...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.RESULTS).push();
        databaseReference.child(StringVariable.EVENT_TYPE).setValue(event_type_name);
        databaseReference.child(StringVariable.EVENT_NAME).setValue(event_name);
        databaseReference.child(StringVariable.SUBEVENT_NAME).setValue(subevent_name);
        databaseReference.child(StringVariable.EVENT_RESULT).setValue(added_positions_show.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Published", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void check_if_position() {
        String position_data = position.getText().toString();
        String position_holder_data = position_holder_name.getText().toString();
        if (position_data.equals(null)) {
            position_data = "";
        } else if (position_holder_data.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Add Position Holder name please...", Toast.LENGTH_SHORT).show();
        } else {
            added_positions_show.setVisibility(View.VISIBLE);
            added_positions_show.append(position_data + ". " + position_holder_data + "\n");
            position.setText("");
            position_holder_name.setText("");
            publish_result.setVisibility(View.VISIBLE);
        }
    }
}
