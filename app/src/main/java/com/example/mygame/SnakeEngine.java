package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;


public class SnakeEngine extends SurfaceView implements Runnable, Util {
    private Thread thread = null;
    private Context context;
    private int screenX;
    private int screenY;
    private int blockSize;


    private final int NUM_BLOCKS_WIDE = 30;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    int numBlocksHigh;

    private Snake snake;
    private int snakeLength;


    private Apple apple;
    private int appleX;
    private int appleY;
    private boolean isPlaying;

    public enum Heading {UP, RIGHT, DOWN, LEFT}
    private Heading heading = Heading.RIGHT;

    public SnakeEngine(MainActivity mainActivity, Point size) {
        super(mainActivity);
        context = mainActivity;
        screenX = size.x;
        screenY = size.y;
        blockSize = screenX / NUM_BLOCKS_WIDE;
        numBlocksHigh = screenY / blockSize;
        surfaceHolder = getHolder();
        paint = new Paint();
        newGame();
    }

    @Override
    public void run() {
        while (isPlaying) {
            //va muy rapido, no es jugable
            try {
                thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            update();
            draw();
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void newGame() {

        Position pos = new Position(NUM_BLOCKS_WIDE / 2, numBlocksHigh / 2);
        snake = new Snake(pos);
        snakeLength = 1;
        snake.setTamaño(snakeLength);
        snake.setPosition(pos);
        spawnApple();
    }

    public void spawnApple() {

        Random random = new Random();
        appleX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        appleY = random.nextInt(numBlocksHigh - 1) + 1;

        Position posApple = new Position(appleX,appleY);
        apple = new Apple(posApple);
        apple.setAplPosition(posApple);
    }
    private void moveSnake(){
        int Wide = snake.getPosition().getPosX();;
        int High = snake.getPosition().getPosY();;
        Position uppos = new Position(0,0);
        /*for (int i = snakeLength; i > 0; i--) {
            Wide = Wide-1;
            High = High-1;
        }*/

        switch (heading) {

            case UP:
                High--;
                break;

            case RIGHT:
                Wide++;
                break;

            case DOWN:
                High++;
                break;


            case LEFT:
                Wide--;
                break;

        }
        uppos.setPosX(Wide);
        uppos.setPosY(High);
        snake.setPosition(uppos);


    }

    private void eatApple() {
        spawnApple();
        snakeLength++;
    }

    private boolean isSnakeDeath() {
        boolean dead = false;
        if(snake.getPosition().getPosX() == -1)dead = true;
        if (snake.getPosition().getPosX() >= NUM_BLOCKS_WIDE) dead = true;
        if (snake.getPosition().getPosY() == -1) dead = true;
        if (snake.getPosition().getPosX() == numBlocksHigh) dead = true;

        /*for (int i = snakeLength - 1; i > 0; i--) {
            if ((i > 4) && (snake.getPosition().getPosX() == i) && (snake.getPosition().getPosY() == (snake.getPosition().getPosY() + i))) {
                dead = true;
            }
        }*/
        return dead;
    }

    public void update() {
        if (snake.getPosition().getPosX() == apple.getAplPosition().getPosX() && snake.getPosition().getPosY() == apple.getAplPosition().getPosY()) {
            eatApple();
        }
        moveSnake();
        if (isSnakeDeath()) {
            //start again
            newGame();
        }
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            for (int i = 0; i < snakeLength; i++) {
                paint.setColor(snake.getColor());
                canvas.drawRect((snake.getPosition().getPosX()+i) * blockSize,
                        ((snake.getPosition().getPosY()+i) * blockSize),
                        ((snake.getPosition().getPosX()+i) * blockSize) + blockSize,
                        ((snake.getPosition().getPosY()+i) * blockSize) + blockSize, paint);
            }

            paint.setColor(apple.getAplColor());
            canvas.drawRect(apple.getAplPosition().getPosX() * blockSize,
                    (apple.getAplPosition().getPosY() * blockSize),
                    (apple.getAplPosition().getPosX() * blockSize) + blockSize,
                    (apple.getAplPosition().getPosY() * blockSize) + blockSize, paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }




            // Unlock the canvas and reveal the graphics for this frame

        }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenX / 2) {
                    switch(heading){
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch(heading){
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;

}
}