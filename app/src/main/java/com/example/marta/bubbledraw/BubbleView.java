package com.example.marta.bubbledraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Handler;

public class BubbleView extends ImageView implements View.OnTouchListener {

    private Random rand = new Random();
    private ArrayList<Bubble> bubbleList;
    private int size=50;
    private int delay = 33;
    Paint myPaint = new Paint();
    private android.os.Handler h = new android.os.Handler();
    public BubbleView(Context context, AttributeSet atrributeSet) // klasy Context i AttributeSet - android używa ich do przechowywania o aktualnej aplikacji, są potrzebne
            //aby wywołać metodę super
    {super(context, atrributeSet); // metoda super wywołuje konstruktor klasy nadrzędnej czyli ImmageView
    bubbleList = new ArrayList<Bubble>(); //inicjalizacja obiektu bubbleList
    // testBubbles();
        setOnTouchListener(this); // this - obiekt klasy Buuble View będzie obsługiwał zdarzenie dotyku
         }

protected void onDraw(Canvas canvas){ //metoda onDraw-informuje o tym co ma być narysowane przy odświezaniu ekranu
    for (Bubble b : bubbleList)
        b.draw(canvas);
    h.postDelayed(r, delay); // handler mówi runable kiedy ma działać
    }
public void  testBubbles(){
        for (int n = 0; n<100; n++)
        {
            int x =rand.nextInt(600);
            int y = rand.nextInt(1000);
            int s = rand.nextInt(size)+size;
            bubbleList.add(new Bubble(x,y,s));
        }
        invalidate();//odświeżanie ekranu
}
private Runnable r =new Runnable()
{
    @Override
    public void run(){ //między przerysowaniami procesor nie jest blokowany
        for (Bubble b : bubbleList)
            b.update();
        invalidate();
    }
};

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //getPointer zlicza ile zdarzeń dotyku miało miejsce-zwraca wzkaźnik
        for(int n=0; n<motionEvent.getPointerCount(); n++) {
            int x = (int) motionEvent.getX(); //getX da liczbe zmiennoprzecinkowa więc rzutujemy na inta
        int y = (int) motionEvent.getY();
        int s = rand.nextInt(size)+size;
        if (motionEvent.getPointerCount() >1) {
            s =  (s/motionEvent.getPointerCount());

        bubbleList.add(new Bubble(x,y,s));}

        else
            bubbleList.add(new Bubble(x,y,s,8,8));}

        return true;} //true jeśli metoda w pełni obsłużyła zdarzenie dotyku, false gdy np chcemy scrollować


    private class Bubble



{
    private int x;
    private int y;
    private int size;
    private int color;
    private int xspeed, yspeed;
        private final int MAX_SPEED=15;
    public Bubble (int newX, int newY, int newSize)
    {x=newX;
        y=newY;
        size = newSize;
        color = Color.argb(rand.nextInt(256),
                rand.nextInt(256),
                rand.nextInt(256),
                rand.nextInt(256));
        xspeed=rand.nextInt(MAX_SPEED*2+1)-MAX_SPEED;
        yspeed=rand.nextInt(MAX_SPEED*2+1)-MAX_SPEED;
        if (xspeed==0)
            xspeed=1;
        if (yspeed==0)
            yspeed=1;

    }

    public Bubble (int newX, int newY, int newSize, int XSpeed, int YSpeed)
    {x=newX;
        y=newY;
        size = newSize;

        color = Color.argb(rand.nextInt(256),
                rand.nextInt(256),
                rand.nextInt(256),
                rand.nextInt(256));

        xspeed=XSpeed;
        yspeed=YSpeed;



    }









    public void draw(Canvas canvas) {
       myPaint.setColor(color); //obiekt canvas ustawienie koloru
        canvas.drawOval(x-size/2, y-size/2, x+size/2, y+size/2, myPaint);
    }
    public void update() {
        x+=xspeed;
        y+=yspeed;
        if (x-size/2<=0 || x+size/2>= getWidth())
            xspeed=-xspeed;
        if (y-size/2<=0 || y+size/2>= getHeight())
            yspeed=-yspeed;


    }
}
}