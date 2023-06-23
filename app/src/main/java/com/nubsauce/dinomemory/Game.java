package com.nubsauce.dinomemory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class Game extends AppCompatActivity {

    Animation scaleUp, scaleDown;

    private Button Dimension_select20, Dimension_select25, startButton;

    private MediaPlayer mediaPlayer;

    private MediaPlayer soundEffects;

    private TextView countdownText;

    private SoundPool soundPool;

    private int sound1, sound2, sound3, sound4, sound5, sound6, sound7, sound8, sound9, sound10,sound11, sound12;

    public static final String PREFS_NAME2 = "MyPrefsFile2";

    private long timeleftInMilliseconds = 18000;

    private boolean timerRunning;

    private CountDownTimer countDownTimer;

    TextView tv_p1, tv_p3;

    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34;

    Integer [] cardsArray = {101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206 };

    //actual images
    int image101, image102, image103, image104, image105, image106, image201, image202, image203, image204, image205, image206;

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;

    int turn = 1;
    int playerPoints = 0, cpuPoints =0;

    private static int bestie = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(12)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPool = new SoundPool(12, AudioManager.STREAM_MUSIC,0);
        }
        sound1 = soundPool.load(this,R.raw.kingboo,1);
        sound2 = soundPool.load(this,R.raw.bowser,1);
        sound3 = soundPool.load(this,R.raw.donkeykong,1);
        sound4 = soundPool.load(this,R.raw.luigi,1);
        sound5 = soundPool.load(this,R.raw.peach,1);
        sound6 = soundPool.load(this,R.raw.plant,1);
        sound7 = soundPool.load(this,R.raw.kingboo,1);
        sound8 = soundPool.load(this,R.raw.bowser,1);
        sound9 = soundPool.load(this,R.raw.donkeykong,1);
        sound10 = soundPool.load(this,R.raw.luigi,1);
        sound11 = soundPool.load(this,R.raw.peach,1);
        sound12 = soundPool.load(this,R.raw.plant,1);

        mediaPlayer = MediaPlayer.create(Game.this,R.raw.yoshiathletic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        bestie = LoadtheInt();

        SharedPreferences settings =getSharedPreferences(PREFS_NAME2,0);
        bestie = settings.getInt("Best: ", bestie);

        tv_p3 = findViewById(R.id.tv_p3);

        ((TextView) findViewById(R.id.tv_p3)).setText("Best: " + bestie);
        


        //Poke Mode
        Dimension_select20 = (Button) findViewById(R.id.dimension_select20);

        //Dino Mode
        Dimension_select25 = (Button) findViewById(R.id.dimension_select25);


        Dimension_select20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFourBySix();
            }
        });

        Dimension_select25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFourbyFour();
            }
        });

        countdownText = findViewById(R.id.countdown_text);

        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                startButton.setVisibility(View.INVISIBLE);

            }
        });


        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        Dimension_select20.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Dimension_select20.startAnimation(scaleUp);
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    Dimension_select20.startAnimation(scaleDown);
                }
                return false;
            }
        });
        Dimension_select25.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Dimension_select25.startAnimation(scaleUp);
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    Dimension_select25.startAnimation(scaleDown);
                }
                return false;
            }
        });
        startButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startButton.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    startButton.startAnimation(scaleDown);
                }
                return false;
            }
        });

        tv_p1 = (TextView) findViewById(R.id.tv_p1);
        tv_p3 = (TextView) findViewById(R.id.tv_p3);

        iv_11 = (ImageView) findViewById(R.id.iv_11);
        iv_12 = (ImageView) findViewById(R.id.iv_12);
        iv_13 = (ImageView) findViewById(R.id.iv_13);
        iv_14 = (ImageView) findViewById(R.id.iv_14);
        iv_21 = (ImageView) findViewById(R.id.iv_21);
        iv_22 = (ImageView) findViewById(R.id.iv_22);
        iv_23 = (ImageView) findViewById(R.id.iv_23);
        iv_24 = (ImageView) findViewById(R.id.iv_24);
        iv_31 = (ImageView) findViewById(R.id.iv_31);
        iv_32 = (ImageView) findViewById(R.id.iv_32);
        iv_33 = (ImageView) findViewById(R.id.iv_33);
        iv_34 = (ImageView) findViewById(R.id.iv_34);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");
        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");

        //load the card images
        frontOfCardsResources();

        Collections.shuffle(Arrays.asList(cardsArray));

        //changing the color of the second player(inactive)
        tv_p3.setTextColor(Color.BLACK);

        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_11, theCard);
                }
            }
        });
        iv_11.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_11.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_11.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_12, theCard);
                }

            }
        });
        iv_12.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_12.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_12.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_13, theCard);
                }

            }
        });
        iv_13.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_13.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_13.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_14, theCard);
                }

            }
        });
        iv_14.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_14.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_14.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_21, theCard);
                }
            }
        });
        iv_21.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_21.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_21.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_22, theCard);
                }
            }
        });
        iv_22.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_22.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_22.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_23, theCard);
                }

            }
        });
        iv_23.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_23.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_23.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_24, theCard);
                }

            }
        });
        iv_24.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_24.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_24.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_31, theCard);
                }

            }
        });
        iv_31.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_31.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_31.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_32, theCard);
                }
            }
        });
        iv_32.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_32.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_32.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_33, theCard);
                }

            }
        });
        iv_33.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_33.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_33.startAnimation(scaleDown);
                }
                return false;
            }
        });

        iv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_34, theCard);
                }

            }
        });
        iv_34.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_34.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_34.startAnimation(scaleDown);
                }
                return false;
            }
        });

    }
    private void doStuff(ImageView iv, int card){

        //set the correct image to the imageview

        if(cardsArray[card] == 101) {
            iv.setImageResource(image101);
            soundPool.play(sound1,1,1,1,0,1);
        }
        else if(cardsArray[card] == 102){
            iv.setImageResource(image102);
            soundPool.play(sound2,1,1,1,0,1);
        }
        else if(cardsArray[card] == 103){
            iv.setImageResource(image103);
            soundPool.play(sound3,1,1,1,0,1);
        }
        else if(cardsArray[card] == 104){
            iv.setImageResource(image104);
            soundPool.play(sound4,1,1,1,0,1);
        }
        else if(cardsArray[card] == 105){
            iv.setImageResource(image105);
            soundPool.play(sound5,1,1,1,0,1);
        }
        else if(cardsArray[card] == 106){
            iv.setImageResource(image106);
            soundPool.play(sound6,1,1,1,0,1);
        }
        else if(cardsArray[card] == 201){
            iv.setImageResource(image201);
            soundPool.play(sound7,1,1,1,0,1);
        }
        else if(cardsArray[card] == 202){
            iv.setImageResource(image202);
            soundPool.play(sound8,1,1,1,0,1);
        }
        else if(cardsArray[card] == 203){
            iv.setImageResource(image203);
            soundPool.play(sound9,1,1,1,0,1);
        }
        else if(cardsArray[card] == 204){
            iv.setImageResource(image204);
            soundPool.play(sound10,1,1,1,0,1);
        }
        else if(cardsArray[card] == 205){
            iv.setImageResource(image205);
            soundPool.play(sound11,1,1,1,0,1);
        }
        else if(cardsArray[card] == 206){
            iv.setImageResource(image206);
            soundPool.play(sound12,1,1,1,0,1);
        }
        //check which image is selected and save it to temporary variable
        if(cardNumber == 1){
            firstCard = cardsArray[card];
            if(firstCard > 200){
                firstCard = firstCard - 100;
            }
            cardNumber =2;
            clickedFirst = card;

            iv.setEnabled(false);

        } else if (cardNumber == 2){
            secondCard = cardsArray[card];
            if(secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNumber =1;
            clickedSecond = card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //check if the selected images are equal
                    calculate();
                }
            }, 1000);

        }

    }

    private void calculate(){

        //if images are equal remove them and add point
        if(firstCard == secondCard) {
            if (clickedFirst == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 4) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            }

            if (clickedSecond == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 4) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            }

            //add point to the correct player
            if(turn ==1){
                playerPoints++;
                tv_p1.setText("Score: " + playerPoints);
            }
        } else{


            iv_11.setImageResource(R.drawable.boxquestion);
            iv_12.setImageResource(R.drawable.boxquestion);
            iv_13.setImageResource(R.drawable.boxquestion);
            iv_14.setImageResource(R.drawable.boxquestion);
            iv_21.setImageResource(R.drawable.boxquestion);
            iv_22.setImageResource(R.drawable.boxquestion);
            iv_23.setImageResource(R.drawable.boxquestion);
            iv_24.setImageResource(R.drawable.boxquestion);
            iv_31.setImageResource(R.drawable.boxquestion);
            iv_32.setImageResource(R.drawable.boxquestion);
            iv_33.setImageResource(R.drawable.boxquestion);
            iv_34.setImageResource(R.drawable.boxquestion);



        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);

        //Check if the game is over
        checkEnd();
    }

    private void checkEnd(){
        if(iv_11.getVisibility() == View.INVISIBLE &&
                iv_12.getVisibility() == View.INVISIBLE &&
                iv_13.getVisibility() == View.INVISIBLE &&
                iv_14.getVisibility() == View.INVISIBLE &&
                iv_21.getVisibility() == View.INVISIBLE &&
                iv_22.getVisibility() == View.INVISIBLE &&
                iv_23.getVisibility() == View.INVISIBLE &&
                iv_24.getVisibility() == View.INVISIBLE &&
                iv_31.getVisibility() == View.INVISIBLE &&
                iv_32.getVisibility() == View.INVISIBLE &&
                iv_33.getVisibility() == View.INVISIBLE &&
                iv_34.getVisibility() == View.INVISIBLE) {
            if (playerPoints > bestie) {
                bestie = playerPoints;
                SharedPreferences settings = getSharedPreferences(PREFS_NAME2, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("bestScore", bestie);
                editor.commit();
                SavetheInt("thekey", bestie);

            }

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Game.this);
            alertDialogBuilder
                    .setMessage("GAME OVER! \nScore: " +playerPoints + "\nBest: " + bestie)
                    .setCancelable(false)
                    .setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            Intent intent = new Intent(getApplicationContext(), Game.class);
                            finish();
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            finish();
                            mediaPlayer.stop();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void frontOfCardsResources(){


        image101 = R.drawable.thekingboo;
        image102 = R.drawable.bowser1;
        image103 = R.drawable.dk1;
        image104 = R.drawable.luigione;
        image105 = R.drawable.peach1;
        image106 = R.drawable.piranhaplant;
        image201 = R.drawable.thekingbootwo;
        image202 = R.drawable.bowser2;
        image203 = R.drawable.dk2;
        image204 = R.drawable.luigitwo;
        image205 = R.drawable.peach2;
        image206 = R.drawable.piranhaplanttwo;

    }
    public void openFourbyFour(){
        Intent intent = new Intent(this, fourbyfour.class);
        startActivity(intent);
        finish();
    }
    public void openFourBySix(){
        Intent intent = new Intent(this, fourbysix.class);
        startActivity(intent);
        finish();
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeleftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeleftInMilliseconds = l;
                updateTimer();
                if(timeleftInMilliseconds < 20000 && timeleftInMilliseconds> 10000){
                    countdownText.setTextColor(Color.parseColor("#FFE9DD33"));
                }
                if( timeleftInMilliseconds < 10000){
                    countdownText.setTextColor(Color.parseColor("#ff0006"));
                }

            }

            @Override
            public void onFinish() {
                timeleftInMilliseconds = 0;
                updateTimer();
                timerRunning = false;

                if (timeleftInMilliseconds == 0) {
                    if (playerPoints > bestie) {
                        bestie = playerPoints;
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME2, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("bestScore", bestie);
                        editor.commit();
                        SavetheInt("thekey", bestie);

                    }
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Game.this);
                    alertDialogBuilder
                            .setMessage("GAME OVER! \nScore: " + playerPoints + "\nBest: " + bestie)
                            .setCancelable(false)
                            .setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {


                                    Intent intent = new Intent(getApplicationContext(), Game.class);
                                    startActivity(intent);
                                    finish();

                                }
                            })
                            .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    finish();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    try {
                        alertDialog.show();
                    }
                    catch (WindowManager.BadTokenException e ){
                        //previous activity window popping
                    }

                }
            }
        }.start();

        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void updateTimer() {
        int minutes = (int) timeleftInMilliseconds / 60000;
        int seconds = (int) timeleftInMilliseconds % 60000 / 1000;


        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;


        countdownText.setText(timeLeftText);


    }





    private void SavetheInt(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    private int LoadtheInt(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int savedValue = sharedPreferences.getInt("thekey", 0);
        return savedValue;
    }
    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}



