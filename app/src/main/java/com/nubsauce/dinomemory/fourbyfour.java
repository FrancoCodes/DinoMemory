package com.nubsauce.dinomemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.content.Context;
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
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;


public class fourbyfour extends AppCompatActivity {


    private Button Dimension_select10, Dimension_select15, startButton;

    private MediaPlayer mediaPlayer;

    private TextView countdownText;

    private CountDownTimer countDownTimer;

    private long timeleftInMilliseconds = 30000;

    private boolean timerRunning;

    private SoundPool soundPool;

    private int sound1, sound2, sound3, sound4, sound5, sound6, sound7, sound8, sound9, sound10,sound11, sound12, sound13, sound14, sound15, sound16;

    Animation scaleUp, scaleDown;

    public static TextView tv_p1, tv_p2;

    public static final String PREFS_NAME = "MyPrefsFile";


    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34, iv_41, iv_42, iv_43, iv_44;

    Integer[] cardsArray = {101, 102, 103, 104, 105, 106, 107, 108, 201, 202, 203, 204, 205, 206, 207, 208};

    //actual images
    int image101, image102, image103, image104, image105, image106, image107, image108, image201, image202, image203, image204, image205, image206, image207, image208;

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;

    int turn = 1;
    int playerPoints = 0;
    private static int bestScore = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(16)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC,0);
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
        sound13 = soundPool.load(this,R.raw.dilophosaurus,1);
        sound14 = soundPool.load(this,R.raw.longneck,1);
        sound15 = soundPool.load(this,R.raw.tarchia,1);
        sound16 = soundPool.load(this,R.raw.dromaeosaurus,1);

        mediaPlayer = MediaPlayer.create(fourbyfour.this,R.raw.dinosaur);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        bestScore = LoadInt();

        tv_p2 = findViewById(R.id.tv_p2);

        setContentView(R.layout.fourbyfour);

        SharedPreferences settings =getSharedPreferences(PREFS_NAME,0);

        bestScore = settings.getInt("Best: ", bestScore);

        ((TextView) findViewById(R.id.tv_p2)).setText("Best: " + bestScore);

        countdownText = findViewById(R.id.countdown_text);

        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                startButton.setVisibility(View.INVISIBLE);

            }
        });


        //Farm Mode
        Dimension_select10 = (Button) findViewById(R.id.dimension_select10);

        //Poke Mode
        Dimension_select15 = (Button) findViewById(R.id.dimension_select15);

        Dimension_select10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });

        Dimension_select15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame2();
            }
        });

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        Dimension_select10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Dimension_select10.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Dimension_select10.startAnimation(scaleDown);
                }
                return false;
            }
        });

        Dimension_select15.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Dimension_select15.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Dimension_select15.startAnimation(scaleDown);
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

    }

    private void doStuff(ImageView iv, int card) {

        //set the correct image to the imageview


        if (cardsArray[card] == 101) {
            iv.setImageResource(image101);
            soundPool.play(sound1,1,1,1,0,1);
        } else if (cardsArray[card] == 102) {
            iv.setImageResource(image102);
            soundPool.play(sound2,1,1,1,0,1);
        } else if (cardsArray[card] == 103) {
            iv.setImageResource(image103);
            soundPool.play(sound3,1,1,1,0,1);
        } else if (cardsArray[card] == 104) {
            iv.setImageResource(image104);
            soundPool.play(sound4,1,1,1,0,1);
        } else if (cardsArray[card] == 105) {
            soundPool.play(sound5,1,1,1,0,1);
            iv.setImageResource(image105);
        } else if (cardsArray[card] == 106) {
            soundPool.play(sound6,1,1,1,0,1);
            iv.setImageResource(image106);
        } else if (cardsArray[card] == 107) {
            soundPool.play(sound7,1,1,1,0,1);
            iv.setImageResource(image107);
        } else if (cardsArray[card] == 108) {
            soundPool.play(sound8,1,1,1,0,1);
            iv.setImageResource(image108);
        } else if (cardsArray[card] == 201) {
            soundPool.play(sound9,1,1,1,0,1);
            iv.setImageResource(image201);
        } else if (cardsArray[card] == 202) {
            soundPool.play(sound10,1,1,1,0,1);
            iv.setImageResource(image202);
        } else if (cardsArray[card] == 203) {
            soundPool.play(sound11,1,1,1,0,1);
            iv.setImageResource(image203);
        } else if (cardsArray[card] == 204) {
            soundPool.play(sound12,1,1,1,0,1);
            iv.setImageResource(image204);
        } else if (cardsArray[card] == 205) {
            soundPool.play(sound13,1,1,1,0,1);
            iv.setImageResource(image205);
        } else if (cardsArray[card] == 206) {
            soundPool.play(sound14,1,1,1,0,1);
            iv.setImageResource(image206);
        } else if (cardsArray[card] == 207) {
            soundPool.play(sound15,1,1,1,0,1);
            iv.setImageResource(image207);
        } else if (cardsArray[card] == 208) {
            soundPool.play(sound16,1,1,1,0,1);
            iv.setImageResource(image208);
        }
        //check which image is selected and save it to temporary variable
        if (cardNumber == 1) {
            firstCard = cardsArray[card];
            if (firstCard > 200) {
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            iv.setEnabled(false);

        } else if (cardNumber == 2) {
            secondCard = cardsArray[card];
            if (secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
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

    private void calculate() {

        //if images are equal remove them and add point
        if (firstCard == secondCard) {
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
            }

            //add point to the correct player
            if (turn == 1) {
                playerPoints++;
                tv_p1.setText("Score: " + playerPoints);
            }


        } else {


            iv_11.setImageResource(R.drawable.goldcube);
            iv_12.setImageResource(R.drawable.goldcube);
            iv_13.setImageResource(R.drawable.goldcube);
            iv_14.setImageResource(R.drawable.goldcube);
            iv_21.setImageResource(R.drawable.goldcube);
            iv_22.setImageResource(R.drawable.goldcube);
            iv_23.setImageResource(R.drawable.goldcube);
            iv_24.setImageResource(R.drawable.goldcube);
            iv_31.setImageResource(R.drawable.goldcube);
            iv_32.setImageResource(R.drawable.goldcube);
            iv_33.setImageResource(R.drawable.goldcube);
            iv_34.setImageResource(R.drawable.goldcube);
            iv_41.setImageResource(R.drawable.goldcube);
            iv_42.setImageResource(R.drawable.goldcube);
            iv_43.setImageResource(R.drawable.goldcube);
            iv_44.setImageResource(R.drawable.goldcube);


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

        //Check if the game is over
        checkEnd();
    }

    private void checkEnd() {
        if (iv_11.getVisibility() == View.INVISIBLE &&
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
                iv_44.getVisibility() == View.INVISIBLE) {
            if (playerPoints > bestScore) {
                bestScore = playerPoints;
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("bestScore", bestScore);
                editor.commit();
                SaveInt("key", bestScore);



            }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(fourbyfour.this);
            alertDialogBuilder
                    .setMessage("GAME OVER! \nScore: " + playerPoints + "\nBest: " + bestScore)
                    .setCancelable(false)
                    .setPositiveButton("NEW", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            Intent intent = new Intent(getApplicationContext(), fourbyfour.class);
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

    private void frontOfCardsResources() {


        image101 = R.drawable.dino1;
        image102 = R.drawable.dino2;
        image103 = R.drawable.dino3;
        image104 = R.drawable.dino4;
        image105 = R.drawable.dino5;
        image106 = R.drawable.dino6;
        image107 = R.drawable.dino7;
        image108 = R.drawable.dino8;
        image201 = R.drawable.dino11;
        image202 = R.drawable.dino22;
        image203 = R.drawable.dino33;
        image204 = R.drawable.dino44;
        image205 = R.drawable.dino55;
        image206 = R.drawable.dino66;
        image207 = R.drawable.dino77;
        image208 = R.drawable.dino88;

    }

    public void openGame() {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
        finish();
    }

    public void openGame2() {
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
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("bestScore", bestScore);
                        editor.commit();
                        SaveInt("key", bestScore);

                    }
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(fourbyfour.this);
                    alertDialogBuilder
                            .setMessage("GAME OVER! \nScore: " + playerPoints + "\nBest: " + bestScore)
                            .setCancelable(false)
                            .setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {


                                    Intent intent = new Intent(getApplicationContext(), fourbyfour.class);
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





    private void SaveInt(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    private int LoadInt(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int savedValue = sharedPreferences.getInt("key", 0);
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




