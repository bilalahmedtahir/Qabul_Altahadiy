package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class FruitActivity extends AppCompatActivity {
    private static final String JSON_URL = "https://opentdb.com/api.php?amount=50&category=17&type=multiple";

    ArrayList<String> questionApifromjson = new ArrayList<>();
    ArrayList<String> CorrectAnswersApi = new ArrayList<>();
    ArrayList<String> WrongAnswersApi1 = new ArrayList<>();
    ArrayList<String> WrongAnswersApi2 = new ArrayList<>();
    ArrayList<String> WrongAnswersApi3 = new ArrayList<>();

    Boolean[] Isvisted;
    Button[] btn;


    TextView textView;
    // Button next;
    ImageView hangman1,hangman2,hangman3,hangman1andhalf;
    CardView hangmanVCard;

    ImageView life1,life2,life3;

    int mylifes = 2;


    int a = 0;

    ImageView[] imageViewsforhangman;
    ImageView[] lifes;


    Button optionA,optionB,optionC,optionD,helpIQuit,helpGiveTwoChoices,helpChangeTheQuestion;

    int CorrectAnswerposition;

    Random random;
    int no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        getdatafromJson();



        random = new Random();
        // next = findViewById(R.id.NextBtn);
        hangman1andhalf = findViewById(R.id.hangman1andhalf);
        hangman2 = findViewById(R.id.hangman2);
        hangman3 = findViewById(R.id.hangman3);
        imageViewsforhangman = new ImageView[]{hangman1andhalf, hangman2, hangman3};
        hangmanVCard = findViewById(R.id.card_view_hangman);

        life1 = findViewById(R.id.life1);
        life2 = findViewById(R.id.life2);
        life3 = findViewById(R.id.life3);
        lifes = new ImageView[]{life1, life2, life3};
        init();
    }


    public void getdatafromJson() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jarray = response.getJSONArray("results");

                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject demoObject = jarray.getJSONObject(i);
//                                Demo demo = new Demo(demoObject.getString("question"));
                                if (demoObject.getString("type").equals("multiple")) {
                                    questionApifromjson.add(demoObject.getString("question"));
                                    CorrectAnswersApi.add(demoObject.getString("correct_answer"));
                                    WrongAnswersApi1.add(String.valueOf(demoObject.getJSONArray("incorrect_answers").get(0)));
                                    WrongAnswersApi2.add(String.valueOf(demoObject.getJSONArray("incorrect_answers").get(1)));
                                    WrongAnswersApi3.add(String.valueOf(demoObject.getJSONArray("incorrect_answers").get(2)));

                                }
                            }
                            Isvisted = new Boolean[questionApifromjson.size()];
                            for (int i = 0; i < questionApifromjson.size(); i++) {
                                Isvisted[i] = false;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(request);


    }

    public void Nextbtn(View v) {
        btn = new Button[]{optionA, optionB, optionC, optionD};



//
        for (int i = 0; i < btn.length; i++) {
            btn[i].setEnabled(true);
        }
        Random random = new Random();
        no = random.nextInt(questionApifromjson.size());
        CorrectAnswerposition = random.nextInt(btn.length);


//        //  howManyQuestion++;
//
        if (Isvisted[no] == false) {
            textView.setText(questionApifromjson.get(no));
            Isvisted[no] = true;
            for (int i = 0; i < btn.length; i++) {
                btn[i].setBackgroundColor(Color.YELLOW);

            }

            btn[CorrectAnswerposition].setText(CorrectAnswersApi.get(no));
            if (CorrectAnswerposition == 0) {
                btn[1].setText(WrongAnswersApi1.get(no));
                btn[2].setText(WrongAnswersApi2.get(no));
                btn[3].setText(WrongAnswersApi3.get(no));

            } else if (CorrectAnswerposition == 1) {
                btn[0].setText(WrongAnswersApi1.get(no));
                btn[2].setText(WrongAnswersApi2.get(no));
                btn[3].setText(WrongAnswersApi3.get(no));

            } else if (CorrectAnswerposition == 2) {
                btn[0].setText(WrongAnswersApi1.get(no));
                btn[1].setText(WrongAnswersApi2.get(no));
                btn[3].setText(WrongAnswersApi3.get(no));

            }

        } else {
            Nextbtn(v);
        }
    }


    public void init() {


        helpIQuit = findViewById(R.id.Iquit);
        helpChangeTheQuestion = findViewById(R.id.changeQuestion);
        helpGiveTwoChoices = findViewById(R.id.givetwoChoice);
//
        textView = findViewById(R.id.txtDescription);
//
        optionA = findViewById(R.id.a);
        optionB = findViewById(R.id.b);
        optionC = findViewById(R.id.c);
        optionD = findViewById(R.id.d);

    }

    public void GiveTwoChoices(View v) {

        final Button[] A = {optionB, optionC, optionD};
        final Button[] B = {optionA, optionD, optionC};
        final Button[] C = {optionB, optionA, optionD};
        final Button[] D = {optionA, optionB, optionC};

        lifes[mylifes].setVisibility(View.GONE);
        mylifes--;
        if (mylifes == 0) {
            helpChangeTheQuestion.setEnabled(false);
        }
        final int whichToDisable1 = random.nextInt(A.length);
        final int whichToDisable2 = random.nextInt(A.length);

        if (optionA.getText().equals(btn[CorrectAnswerposition].getText())) {

            if (whichToDisable1 != whichToDisable2) {
                A[whichToDisable1].setEnabled(false);
                A[whichToDisable2].setEnabled(false);
            } else {
                GiveTwoChoices(v);
            }
        } else if (optionB.getText().equals(btn[CorrectAnswerposition].getText())) {

            if (whichToDisable1 != whichToDisable2) {
                B[whichToDisable1].setEnabled(false);
                B[whichToDisable2].setEnabled(false);
            } else {
                GiveTwoChoices(v);
            }
        } else if (optionC.getText().equals(btn[CorrectAnswerposition].getText())) {

            if (whichToDisable1 != whichToDisable2) {
                C[whichToDisable1].setEnabled(false);
                C[whichToDisable2].setEnabled(false);
            } else {
                GiveTwoChoices(v);
            }
        } else if (optionD.getText().equals(btn[CorrectAnswerposition].getText())) {

            if (whichToDisable1 != whichToDisable2) {
                D[whichToDisable1].setEnabled(false);
                D[whichToDisable2].setEnabled(false);
            } else {
                GiveTwoChoices(v);
            }
        } else {

        }
    }

    public void ChangeQuestion(View v) {
        lifes[mylifes].setVisibility(View.GONE);
        mylifes--;
        if (mylifes == 0) {
            helpChangeTheQuestion.setEnabled(false);
        }
        no = random.nextInt(questionApifromjson.size());
        int hcq = random.nextInt(btn.length);

        if (Isvisted[no] == false) {
            textView.setText(questionApifromjson.get(no));
            Isvisted[no] = true;

            for (int i = 0; i < btn.length; i++) {
                btn[i].setBackgroundColor(Color.YELLOW);

            }

            btn[CorrectAnswerposition].setText(CorrectAnswersApi.get(no));
            if (CorrectAnswerposition == 0) {
                btn[1].setText(WrongAnswersApi1.get(no));
                btn[2].setText(WrongAnswersApi2.get(no));
                btn[3].setText(WrongAnswersApi3.get(no));

            } else if (CorrectAnswerposition == 1) {
                btn[0].setText(WrongAnswersApi1.get(no));
                btn[2].setText(WrongAnswersApi2.get(no));
                btn[3].setText(WrongAnswersApi3.get(no));

            } else if (CorrectAnswerposition == 2) {
                btn[0].setText(WrongAnswersApi1.get(no));
                btn[1].setText(WrongAnswersApi2.get(no));
                btn[3].setText(WrongAnswersApi3.get(no));

            }

        } else {
            ChangeQuestion(v);
        }
    }

    public void IQuitGame(View v) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);


    }

    public void OptionA(View view) {
        if (optionA.getText().equals(btn[CorrectAnswerposition].getText())) {
            optionA.setBackgroundColor(Color.GREEN);
            optionB.setBackgroundColor(Color.RED);
            optionC.setBackgroundColor(Color.RED);
            optionD.setBackgroundColor(Color.RED);
//                    btnprize[howManyQuestion].setBackgroundColor(Color.GREEN);

            //  next.setEnabled(true);
        } else {
            optionA.setBackgroundColor(Color.RED);
            hangmanVCard.setVisibility(View.VISIBLE);
            imageViewsforhangman[a].setVisibility(View.VISIBLE);
            //next.setEnabled(true);

            a++;
//                    btnprize[howManyQuestion].setBackgroundColor(Color.RED);


        }
    }

    public void OptionB(View view) {
        if (optionB.getText().equals(btn[CorrectAnswerposition].getText())) {
            optionB.setBackgroundColor(Color.GREEN);
            optionA.setBackgroundColor(Color.RED);
            optionC.setBackgroundColor(Color.RED);
            optionD.setBackgroundColor(Color.RED);
//                    btnprize[howManyQuestion].setBackgroundColor(Color.GREEN);

            //    next.setEnabled(true);
        } else {
            optionB.setBackgroundColor(Color.RED);
            hangmanVCard.setVisibility(View.VISIBLE);

//                    btnprize[howManyQuestion].setBackgroundColor(Color.RED);
            imageViewsforhangman[a].setVisibility(View.VISIBLE);
            //    next.setEnabled(true);

            a++;

        }
    }

    public void OptionC(View view) {
        if (optionC.getText().equals(btn[CorrectAnswerposition].getText())) {
            optionC.setBackgroundColor(Color.GREEN);
            optionA.setBackgroundColor(Color.RED);
            optionB.setBackgroundColor(Color.RED);
            optionD.setBackgroundColor(Color.RED);
//                    btnprize[howManyQuestion].setBackgroundColor(Color.GREEN);

            //    next.setEnabled(true);
        } else {
            optionC.setBackgroundColor(Color.RED);
            hangmanVCard.setVisibility(View.VISIBLE);

//                    btnprize[howManyQuestion].setBackgroundColor(Color.RED);
            imageViewsforhangman[a].setVisibility(View.VISIBLE);
            //       next.setEnabled(true);

            a++;

        }
    }

    public void OptionD(View v) {
        if (optionD.getText().equals(btn[CorrectAnswerposition].getText())) {
            optionD.setBackgroundColor(Color.GREEN);
            optionA.setBackgroundColor(Color.RED);
            optionB.setBackgroundColor(Color.RED);
            optionC.setBackgroundColor(Color.RED);
//                    btnprize[howManyQuestion].setBackgroundColor(Color.GREEN);

            //   next.setEnabled(true);
        } else {
            optionD.setBackgroundColor(Color.RED);
            hangmanVCard.setVisibility(View.VISIBLE);

//                    btnprize[howManyQuestion].setBackgroundColor(Color.RED);
            imageViewsforhangman[a].setVisibility(View.VISIBLE);
            //   next.setEnabled(true);
            a++;

        }
    }
}
