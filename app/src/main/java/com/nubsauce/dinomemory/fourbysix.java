package com.nubsauce.dinomemory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class fourbysix extends AppCompatActivity {

    private Button Dimension_select3, Dimension_select2, startButton;

    private MediaPlayer mediaPlayer;

    private TextView countdownText;

    private long timeleftInMilliseconds = 44000;

    private boolean timerRunning;

    private static int bestScore = 0;

    public static TextView tv_p1, tv_p2;

    private CountDownTimer countDownTimer;

    private SoundPool soundPool;

    private int sound1, sound2, sound3, sound4, sound5, sound6, sound7, sound8, sound9, sound10,sound11, sound12, sound13, sound14, sound15, sound16, sound17, sound18, sound19, sound20, sound21, sound22, sound233, sound24;

    public static final String PREFS_NAME3 = "MyPrefsFile3";

    Animation scaleUp, scaleDown;

    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34, iv_41, iv_42, iv_43, iv_44,
            iv_51, iv_52, iv_53, iv_54, iv_61, iv_62, iv_63, iv_64;

    Integer [] cardsArray = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212 };

    //actual images
    int image101, image102, image103, image104, image105, image106, image107, image108, image109, image110, image111, image112, image201, image202, image203, image204, image205, image206, image207, image208, image209, image210, image211, image212;

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;

    int turn = 1;
    int playerPoints = 0, cpuPoints =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(24)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPool = new SoundPool(24, AudioManager.STREAM_MUSIC,0);
        }
        sound1 = soundPool.load(this,R.raw.mtrex,1);
        sound2 = soundPool.load(this,R.raw.headshield,1);
        sound3 = soundPool.load(this,R.raw.stegosaurus,1);
        sound4 = soundPool.load(this,R.raw.terodactylus,1);
        sound5 = soundPool.load(this,R.raw.dilophosaurus,1);
        sound6 = soundPool.load(this,R.raw.longneck,1);
        sound7 = soundPool.load(this,R.raw.tarchia,1);
        sound8 = soundPool.load(this,R.raw.dromaeosaurus,1);
        sound9 = soundPool.load(this,R.raw.mtrex,1);
        sound10 = soundPool.load(this,R.raw.headshield,1);
        sound11 = soundPool.load(this,R.raw.stegosaurus,1);
        sound12 = soundPool.load(this,R.raw.terodactylus,1);


        tv_p2 = findViewById(R.id.tv_p2);

        setContentView(R.layout.fourbysix);

        bestScore = LoadInt();

        SharedPreferences settings =getSharedPreferences(PREFS_NAME3,0);

        bestScore = settings.getInt("Best: ", bestScore);

        ((TextView) findViewById(R.id.tv_p2)).setText("Best: " + bestScore);

        countdownText = findViewById(R.id.countdown_text);


        mediaPlayer = MediaPlayer.create(fourbysix.this,R.raw.poketheme);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                startButton.setVisibility(View.INVISIBLE);

            }
        });

        Dimension_select3 = (Button) findViewById(R.id.dimension_select3);
        Dimension_select2= (Button) findViewById(R.id.dimension_select2);
        Dimension_select3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
        Dimension_select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame2();
            }
        });

        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        Dimension_select3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Dimension_select3.startAnimation(scaleUp);
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    Dimension_select3.startAnimation(scaleDown);
                }
                return false;
            }
        });

        Dimension_select2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Dimension_select2.startAnimation(scaleUp);
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    Dimension_select2.startAnimation(scaleDown);
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
        tv_p2 = (TextView) findViewById(R.id.tv_p2);

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
        iv_41 = (ImageView) findViewById(R.id.iv_41);
        iv_42 = (ImageView) findViewById(R.id.iv_42);
        iv_43 = (ImageView) findViewById(R.id.iv_43);
        iv_44 = (ImageView) findViewById(R.id.iv_44);
        iv_51 = (ImageView) findViewById(R.id.iv_51);
        iv_52 = (ImageView) findViewById(R.id.iv_52);
        iv_53 = (ImageView) findViewById(R.id.iv_53);
        iv_54 = (ImageView) findViewById(R.id.iv_54);
        iv_61 = (ImageView) findViewById(R.id.iv_61);
        iv_62 = (ImageView) findViewById(R.id.iv_62);
        iv_63 = (ImageView) findViewById(R.id.iv_63);
        iv_64 = (ImageView) findViewById(R.id.iv_64);

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
        iv_41.setTag("12");
        iv_42.setTag("13");
        iv_43.setTag("14");
        iv_44.setTag("15");
        iv_51.setTag("16");
        iv_52.setTag("17");
        iv_53.setTag("18");
        iv_54.setTag("19");
        iv_61.setTag("20");
        iv_62.setTag("21");
        iv_63.setTag("22");
        iv_64.setTag("23");

        //load the card images
        frontOfCardsResources();

        Collections.shuffle(Arrays.asList(cardsArray));

        //changing the color of the second player(inactive)
        tv_p2.setTextColor(Color.BLACK);

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
        iv_41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_41, theCard);
                }
            }
        });
        iv_41.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_41.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_41.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_42, theCard);
                }

            }
        });
        iv_42.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_42.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_42.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_43, theCard);
                }

            }
        });
        iv_43.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_43.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_43.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_44, theCard);
                }

            }
        });
        iv_44.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_44.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_44.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_51, theCard);
                }

            }
        });
        iv_51.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_51.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_51.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_52, theCard);
                }

            }
        });
        iv_52.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_52.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_52.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_53, theCard);
                }

            }
        });
        iv_53.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_53.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_53.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_54, theCard);
                }

            }
        });
        iv_54.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_54.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_54.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_61, theCard);
                }

            }
        });
        iv_61.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_61.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_61.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_62, theCard);
                }

            }
        });
        iv_62.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_62.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_62.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_63, theCard);
                }

            }
        });
        iv_63.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_63.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_63.startAnimation(scaleDown);
                }
                return false;
            }
        });
        iv_64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getVisibility() == View.INVISIBLE) {
                    int theCard = Integer.parseInt((String) v.getTag());

                    doStuff(iv_64, theCard);
                }

            }
        });
        iv_64.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_64.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    iv_64.startAnimation(scaleDown);
                }
                return false;
            }
        });

    }

    private void doStuff(ImageView iv, int card){

        //set the correct image to the imageview


        if(cardsArray[card] == 101) {
            iv.setImageResource(image101);
        }
        else if(cardsArray[card] == 102){
            iv.setImageResource(image102);
        }
        else if(cardsArray[card] == 103){
            iv.setImageResource(image103);
        }
        else if(cardsArray[card] == 104){
            iv.setImageResource(image104);
        }
        else if(cardsArray[card] == 105){
            iv.setImageResource(image105);
        }
        else if(cardsArray[card] == 106){
            iv.setImageResource(image106);
        }
        else if(cardsArray[card] == 107){
            iv.setImageResource(image107);
        }
        else if(cardsArray[card] == 108){
            iv.setImageResource(image108);
        }
        else if(cardsArray[card] == 109){
            iv.setImageResource(image109);
        }
        else if(cardsArray[card] == 110){
            iv.setImageResource(image110);
        }
        else if(cardsArray[card] == 111){
            iv.setImageResource(image111);
        }
        else if(cardsArray[card] == 112){
            iv.setImageResource(image112);
        }
        else if(cardsArray[card] == 201){
            iv.setImageResource(image201);
        }
        else if(cardsArray[card] == 202){
            iv.setImageResource(image202);
        }
        else if(cardsArray[card] == 203){
            iv.setImageResource(image203);
        }
        else if(cardsArray[card] == 204){
            iv.setImageResource(image204);
        }
        else if(cardsArray[card] == 205){
            iv.setImageResource(image205);
        }
        else if(cardsArray[card] == 206){
            iv.setImageResource(image206);
        }
        else if(cardsArray[card] == 207){
            iv.setImageResource(image207);
        }
        else if(cardsArray[card] == 208){
            iv.setImageResource(image208);
        }
        else if(cardsArray[card] == 209){
            iv.setImageResource(image209);
        }
        else if(cardsArray[card] == 210){
            iv.setImageResource(image210);
        }
        else if(cardsArray[card] == 211){
            iv.setImageResource(image211);
        }
        else if(cardsArray[card] == 212){
            iv.setImageResource(image212);
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
            iv_41.setEnabled(false);
            iv_42.setEnabled(false);
            iv_43.setEnabled(false);
            iv_44.setEnabled(false);
            iv_51.setEnabled(false);
            iv_52.setEnabled(false);
            iv_53.setEnabled(false);
            iv_54.setEnabled(false);
            iv_61.setEnabled(false);
            iv_62.setEnabled(false);
            iv_63.setEnabled(false);
            iv_64.setEnabled(false);

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
            } else if (clickedFirst == 12) {
                iv_41.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 13) {
                iv_42.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 14) {
                iv_43.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 15) {
                iv_44.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 16) {
                iv_51.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 17) {
                iv_52.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 18) {
                iv_53.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 19) {
                iv_54.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 20) {
                iv_61.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 21) {
                iv_62.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 22) {
                iv_63.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 23) {
                iv_64.setVisibility(View.INVISIBLE);
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
            } else if (clickedSecond == 12) {
                iv_41.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 13) {
                iv_42.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 14) {
                iv_43.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 15) {
                iv_44.setVisibility(View.INVISIBLE);
            }  else if (clickedSecond == 16) {
                iv_51.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 17) {
                iv_52.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 18) {
                iv_53.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 19) {
                iv_54.setVisibility(View.INVISIBLE);
            }  else if (clickedSecond == 20) {
                iv_61.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 21) {
                iv_62.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 22) {
                iv_63.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 23) {
                iv_64.setVisibility(View.INVISIBLE);
            }

            //add point to the correct player
            if(turn ==1){
                playerPoints++;
                tv_p1.setText("Score: " + playerPoints);
            }
        } else{


            iv_11.setImageResource(R.drawable.pokeball);
            iv_12.setImageResource(R.drawable.pokeball);
            iv_13.setImageResource(R.drawable.pokeball);
            iv_14.setImageResource(R.drawable.pokeball);
            iv_21.setImageResource(R.drawable.pokeball);
            iv_22.setImageResource(R.drawable.pokeball);
            iv_23.setImageResource(R.drawable.pokeball);
            iv_24.setImageResource(R.drawable.pokeball);
            iv_31.setImageResource(R.drawable.pokeball);
            iv_32.setImageResource(R.drawable.pokeball);
            iv_33.setImageResource(R.drawable.pokeball);
            iv_34.setImageResource(R.drawable.pokeball);
            iv_41.setImageResource(R.drawable.pokeball);
            iv_42.setImageResource(R.drawable.pokeball);
            iv_43.setImageResource(R.drawable.pokeball);
            iv_44.setImageResource(R.drawable.pokeball);
            iv_51.setImageResource(R.drawable.pokeball);
            iv_52.setImageResource(R.drawable.pokeball);
            iv_53.setImageResource(R.drawable.pokeball);
            iv_54.setImageResource(R.drawable.pokeball);
            iv_61.setImageResource(R.drawable.pokeball);
            iv_62.setImageResource(R.drawable.pokeball);
            iv_63.setImageResource(R.drawable.pokeball);
            iv_64.setImageResource(R.drawable.pokeball);



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
        iv_41.setEnabled(true);
        iv_42.setEnabled(true);
        iv_43.setEnabled(true);
        iv_44.setEnabled(true);
        iv_51.setEnabled(true);
        iv_52.setEnabled(true);
        iv_53.setEnabled(true);
        iv_54.setEnabled(true);
        iv_61.setEnabled(true);
        iv_62.setEnabled(true);
        iv_63.setEnabled(true);
        iv_64.setEnabled(true);

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
                iv_34.getVisibility() == View.INVISIBLE &&
                iv_41.getVisibility() == View.INVISIBLE &&
                iv_42.getVisibility() == View.INVISIBLE &&
                iv_43.getVisibility() == View.INVISIBLE &&
                iv_44.getVisibility() == View.INVISIBLE &&
                iv_51.getVisibility() == View.INVISIBLE &&
                iv_52.getVisibility() == View.INVISIBLE &&
                iv_53.getVisibility() == View.INVISIBLE &&
                iv_54.getVisibility() == View.INVISIBLE &&
                iv_61.getVisibility() == View.INVISIBLE &&
                iv_62.getVisibility() == View.INVISIBLE &&
                iv_63.getVisibility() == View.INVISIBLE &&
                iv_64.getVisibility() == View.INVISIBLE ) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(fourbysix.this);
            alertDialogBuilder
                    .setMessage("GAME OVER! \nScore: " +playerPoints + "\nBest: " + bestScore)
                    .setCancelable(false)
                    .setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            Intent intent = new Intent(getApplicationContext(), fourbysix.class);
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
            try {
                alertDialog.show();
            }
            catch (WindowManager.BadTokenException e ){
                //previous activity window popping
            }
        }
    }

    private void frontOfCardsResources(){


        image101 = R.drawable.poke1;
        image102 = R.drawable.poke2;
        image103 = R.drawable.poke3;
        image104 = R.drawable.poke4;
        image105 = R.drawable.poke5;
        image106 = R.drawable.poke6;
        image107 = R.drawable.poke7;
        image108 = R.drawable.poke8;
        image109 = R.drawable.poke9;
        image110 = R.drawable.poke10;
        image111 = R.drawable.poke11;
        image112 = R.drawable.poke12;
        image201 = R.drawable.poke111;
        image202 = R.drawable.poke222;
        image203 = R.drawable.poke333;
        image204 = R.drawable.poke444;
        image205 = R.drawable.poke555;
        image206 = R.drawable.poke666;
        image207 = R.drawable.poke777;
        image208 = R.drawable.poke888;
        image209 = R.drawable.poke999;
        image210 = R.drawable.poke101010;
        image211 = R.drawable.poke111111;
        image212 = R.drawable.poke121212;

    }

    public void openGame(){
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
        finish();
    }
    public void openGame2(){
        Intent intent = new Intent(this, fourbyfour.class);
        startActivity(intent);
        finish();
    }
    public void startTimer() {
        countDownTimer = new CountDownTimer(timeleftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeleftInMilliseconds = l;
                updateTimer();
                if(timeleftInMilliseconds < 25000 && timeleftInMilliseconds> 10000){
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
                    if (playerPoints > bestScore) {
                        bestScore = playerPoints;
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME3, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("bestScore", bestScore);
                        editor.commit();
                        SaveInt("keykey", bestScore);

                    }
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(fourbysix.this);
                    alertDialogBuilder
                            .setMessage("GAME OVER! \nScore: " + playerPoints + "\nBest: " + bestScore)
                            .setCancelable(false)
                            .setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {


                                    Intent intent = new Intent(getApplicationContext(), fourbysix.class);
                                    finish();
                                    startActivity(intent);

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
    private void SaveInt(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    private int LoadInt(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int savedValue = sharedPreferences.getInt("keykey", 0);
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