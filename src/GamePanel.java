import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS =
            (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;

    int DELAY = 250;
    int TOP_MARGIN = 60;

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    int bodyParts = 6;
    int applesEaten;
    int level = 1;
    int highScore = 0;
    int appleX;
    int appleY;
    int goldenAppleX;
    int goldenAppleY;

    boolean goldenAppleVisible = false;
    int[] obstacleX = new int[5];
    int[] obstacleY = new int[5];


    char direction = 'R';

    boolean gameStarted = false;
    boolean running = false;
    boolean paused = false;
    boolean levelComplete = false;
    boolean waitingNextLevel = false;

    Timer timer;
    Random random;

    GamePanel() {

        random = new Random();

        this.setPreferredSize(
                new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        this.setBackground(Color.black);

        this.setFocusable(true);

        this.addKeyListener(new MyKeyAdapter());

        loadHighScore();

    }

    public void startGame() {

        x[0] = 100;
        y[0] = TOP_MARGIN + UNIT_SIZE;

        newApple();

        newObstacle();

        running = true;

        timer = new Timer(DELAY, this);

        timer.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g) {

        if(!gameStarted) {

            // Background
            g.setColor(Color.black);
            g.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);

            // Outer glow border
            g.setColor(new Color(150,100,255));
            g.fillRoundRect(
                    65,
                    80,
                    470,
                    430,
                    30,
                    30
            );

            // Main popup
            g.setColor(new Color(15,15,35));

            g.fillRoundRect(
                    70,
                    85,
                    460,
                    420,
                    30,
                    30
            );

            // Title
            g.setColor(new Color(50,255,50));

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            48
                    )
            );

            FontMetrics title=
                    getFontMetrics(g.getFont());

            g.drawString(
                    "SNAKE GAME",
                    (SCREEN_WIDTH-title.stringWidth(
                            "SNAKE GAME"))/2,
                    150
            );

            // Decorative line
            g.setColor(new Color(0,255,150));

            g.drawLine(
                    180,
                    170,
                    420,
                    170
            );

            // Instructions
            g.setFont(
                    new Font(
                            "Consolas",
                            Font.BOLD,
                            22
                    )
            );

            // Start
            g.setColor(Color.green);

            g.drawString(
                    "▶ Press ENTER to Start",
                    130,
                    230
            );

            // Pause
            g.setColor(Color.yellow);

            g.drawString(
                    "⏸ Press P to Pause",
                    130,
                    280
            );

            // Exit
            g.setColor(Color.red);

            g.drawString(
                    "✖ Press ESC to Exit",
                    130,
                    330
            );

            // Move section title
            g.setColor(
                    new Color(0,180,255)
            );

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            24
                    )
            );

            g.drawString(
                    "MOVE SNAKE",
                    200,
                    390
            );

            // Arrow controls
            g.setColor(Color.white);

            g.setFont(
                    new Font(
                            "Consolas",
                            Font.BOLD,
                            20
                    )
            );

            g.drawString("↑ Move Up",170,430);

            g.drawString("↓ Move Down",170,460);

            g.drawString("← Move Left",330,430);

            g.drawString("→ Move Right",330,460);

            return;
        }


        if(levelComplete){

            levelCompleteScreen(g);
        }
        else if(paused){

            pauseScreen(g);
        }
        else if(running) {

            g.setColor(new Color(255,182,193));

            g.fillRect(
                    0,
                    0,
                    SCREEN_WIDTH,
                    TOP_MARGIN
            );

            // Boundary line
            g.setColor(Color.white);
            g.drawLine(0, TOP_MARGIN, SCREEN_WIDTH, TOP_MARGIN);

            //APPLE
            g.setColor(Color.red);

            g.fillOval(
                    appleX,
                    appleY,
                    UNIT_SIZE,
                    UNIT_SIZE
            );

            //GOLDEN APPLE
            if(goldenAppleVisible) {

                g.setColor(Color.yellow);

                g.fillOval(
                        goldenAppleX,
                        goldenAppleY,
                        UNIT_SIZE,
                        UNIT_SIZE
                );
            }

            //OBSTACLE
            if(level >= 1) {

                g.setColor(Color.gray);

                for(int i = 0; i < 5; i++) {

                    g.fillRect(
                            obstacleX[i],
                            obstacleY[i],
                            UNIT_SIZE,
                            UNIT_SIZE
                    );
                }
            }

            //SNAKE
            for(int i = 0; i < bodyParts; i++) {

                if(i == 0) {
                    // HEAD
                    g.setColor(new Color(0, 255, 100));
                    g.fillRoundRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE, 15, 15);

                    // EYES 👀
                    g.setColor(Color.black);

                    if(direction == 'R' || direction == 'L') {
                        g.fillOval(x[i] + 12, y[i] + 5, 5, 5);
                        g.fillOval(x[i] + 12, y[i] + 15, 5, 5);
                    } else {
                        g.fillOval(x[i] + 5, y[i] + 12, 5, 5);
                        g.fillOval(x[i] + 15, y[i] + 12, 5, 5);
                    }

                } else {
                    // BODY
                    g.setColor(new Color(0, 200 - (i * 5), 0));
                    g.fillRoundRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE, 15, 15);
                }
            }

            // HUD AREA (Top)
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.BOLD, 20));

            // Level
            g.drawString("Level: " + level, 20, 30);

            // Score
            g.drawString("Score: " + applesEaten, SCREEN_WIDTH/2 - 60, 30);

            // High Score
            g.drawString("High Score: " + highScore, SCREEN_WIDTH - 200, 30);

        }
        else{

            gameOver(g);
        }
    }

    //new apple
    public void newApple() {

        appleX =
                random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))
                        * UNIT_SIZE;

        appleY = random.nextInt((SCREEN_HEIGHT - TOP_MARGIN) / UNIT_SIZE) * UNIT_SIZE + TOP_MARGIN;
    }

    public void newGoldenApple() {

        goldenAppleX =
                random.nextInt(SCREEN_WIDTH / UNIT_SIZE)
                        * UNIT_SIZE;

        goldenAppleY =
                random.nextInt((SCREEN_HEIGHT - TOP_MARGIN)
                        / UNIT_SIZE)
                        * UNIT_SIZE + TOP_MARGIN;

        // Prevent overlap with normal apple
        while(goldenAppleX == appleX &&
                goldenAppleY == appleY) {

            goldenAppleX =
                    random.nextInt(SCREEN_WIDTH / UNIT_SIZE)
                            * UNIT_SIZE;

            goldenAppleY =
                    random.nextInt((SCREEN_HEIGHT - TOP_MARGIN)
                            / UNIT_SIZE)
                            * UNIT_SIZE + TOP_MARGIN;
        }

        goldenAppleVisible = true;
    }


    public void newObstacle() {

        for(int i = 0; i < 5; i++) {

            do {

                obstacleX[i] =
                        random.nextInt(SCREEN_WIDTH / UNIT_SIZE)
                                * UNIT_SIZE;

                obstacleY[i] =
                        random.nextInt((SCREEN_HEIGHT - TOP_MARGIN)
                                / UNIT_SIZE)
                                * UNIT_SIZE + TOP_MARGIN;

            }
            while(obstacleX[i] < 200 &&
                    obstacleY[i] < TOP_MARGIN + 200);
        }
    }

    public void move() {

        for(int i = bodyParts; i > 0; i--) {

            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch(direction) {

            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;

            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;

            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;

            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }


    public void checkApple() {

            // NORMAL RED APPLE
            if ((x[0] == appleX) && (y[0] == appleY)) {

                bodyParts++;
                applesEaten++;

                playSound("assets/eat.wav");

                if(applesEaten > highScore) {
                    highScore = applesEaten;
                    saveHighScore();
                }

                if(applesEaten % 10 == 0) {

                    levelComplete = true;
                    waitingNextLevel = true;

                    timer.stop();
                }

                newApple();

                if(!goldenAppleVisible && random.nextInt(5) == 0) {
                    newGoldenApple();
                }
            }

            // GOLDEN APPLE
        
            if(goldenAppleVisible &&
                    x[0] == goldenAppleX &&
                    y[0] == goldenAppleY) {

                applesEaten += 5;

                bodyParts += 2;
                goldenAppleVisible = false;
                goldenAppleX = -100;
                goldenAppleY = -100;

                playSound("assets/eat.wav");

                if(applesEaten > highScore) {
                    highScore = applesEaten;
                    saveHighScore();
                }
            }
    }


    public void checkCollisions() {

        for(int i = bodyParts; i > 0; i--) {

            if((x[0] == x[i]) && (y[0] == y[i])) {

                running = false;
            }
        }

        if(x[0] < 0) {

            running = false;
        }

        if(x[0] > SCREEN_WIDTH) {

            running = false;
        }

        if(y[0] < TOP_MARGIN) {

            running = false;
        }

        if(y[0] > SCREEN_HEIGHT) {

            running = false;
        }

        //Boundary collision
        if(level >= 1) {

            for(int i = 0; i < 5; i++) {

                if(x[0] == obstacleX[i] &&
                        y[0] == obstacleY[i]) {

                    running = false;
                }
            }
        }

        if(!running) {
            playSound("assets/gameover.wav");

            timer.stop();
        }
    }

    //restart game
    public void restartGame() {

        bodyParts = 6;

        applesEaten = 0;

        level = 1;

        direction = 'R';

        for(int i = 0; i < GAME_UNITS; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        // New starting position
        x[0] = 100;
        y[0] = TOP_MARGIN + UNIT_SIZE;

        running = true;

        timer.setDelay(DELAY);

        newApple();

        timer.start();

    }


    public void loadHighScore() {

        try {

            File file = new File("highscore.txt");

            Scanner scanner = new Scanner(file);

            highScore = scanner.nextInt();

            scanner.close();

        } catch (Exception e) {

            highScore = 0;
        }
    }


    public void saveHighScore() {

        try {

            FileWriter writer =
                    new FileWriter("highscore.txt");

            writer.write(String.valueOf(highScore));

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public void playSound(String soundFile) {

        try {

            AudioInputStream audioInput =
                    AudioSystem.getAudioInputStream(
                            new File(soundFile)
                    );

            Clip clip = AudioSystem.getClip();

            clip.open(audioInput);

            clip.start();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public void gameOver(Graphics g) {

        // Dark background
        g.setColor(Color.black);
        g.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);

        // Top HUD remains visible
        g.setColor(Color.darkGray);
        g.fillRect(0,0,SCREEN_WIDTH,TOP_MARGIN);

        g.setColor(Color.white);
        g.setFont(new Font("Consolas",Font.BOLD,18));

        g.drawString("Level : " + level,20,30);

        g.drawString(
                "Score : " + applesEaten,
                SCREEN_WIDTH/2-50,
                30
        );

        g.drawString(
                "High Score : " + highScore,
                SCREEN_WIDTH-180,
                30
        );

        // Popup box
        g.setColor(new Color(220,220,240));

        g.fillRoundRect(
                110,
                180,
                380,
                170,
                30,
                30
        );

        g.setColor(Color.red);

        g.drawRoundRect(
                110,
                180,
                380,
                170,
                30,
                30
        );

        // Game Over title
        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        40
                )
        );

        g.drawString(
                "GAME OVER",
                165,
                240
        );

        // Restart text
        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        g.setColor(Color.black);

        g.drawString(
                "Press ENTER to Restart",
                155,
                290
        );
    }

    //level complete screen
    public void levelCompleteScreen(Graphics g){

        g.setColor(new Color(200,200,255));

        g.fillRoundRect(
                100,
                180,
                400,
                180,
                30,
                30
        );

        g.setColor(Color.blue);

        g.setFont(
                new Font("Arial",
                        Font.BOLD,
                        35)
        );

        g.drawString(
                "LEVEL COMPLETE!",
                145,
                250
        );

        g.setColor(Color.black);

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        g.drawString(
                "Press ENTER for Next Level",
                150,
                310
        );
    }


    //pause screen
    public void pauseScreen(Graphics g){

        g.setColor(new Color(200,200,255));

        g.fillRoundRect(
                100,
                180,
                400,
                180,
                30,
                30
        );

        g.setColor(Color.red);

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        40
                )
        );

        g.drawString(
                "PAUSED",
                190,
                250
        );

        g.setColor(Color.black);

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        g.drawString(
                "Press P to Resume",
                175,
                310
        );
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {

            move();

            checkApple();

            checkCollisions();
        }

        repaint();
    }


    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            // START GAME
            if(!gameStarted && e.getKeyCode() == KeyEvent.VK_ENTER) {

                gameStarted = true;

                startGame();
            }

            // EXIT GAME
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {

                System.exit(0);
            }

            // SWITCH FOR MOVEMENT
            switch(e.getKeyCode()) {
                case KeyEvent.VK_ENTER:

                    if(waitingNextLevel){

                        level++;

                        waitingNextLevel = false;

                        levelComplete = false;

                        newObstacle();

                        newApple();

                        if(DELAY > 50){
                            timer.setDelay(
                                    timer.getDelay()-10
                            );
                        }

                        timer.start();
                    }

                    else if(!running){

                        restartGame();
                    }

                    break;

                case KeyEvent.VK_LEFT:

                    if(direction != 'R') {

                        direction = 'L';
                    }

                    break;

                case KeyEvent.VK_RIGHT:

                    if(direction != 'L') {

                        direction = 'R';
                    }

                    break;

                case KeyEvent.VK_UP:

                    if(direction != 'D') {

                        direction = 'U';
                    }

                    break;

                case KeyEvent.VK_DOWN:

                    if(direction != 'U') {

                        direction = 'D';
                    }

                    break;

            }
            // Pause / Resume
            if(e.getKeyCode() == KeyEvent.VK_P && running) {

                paused = !paused;

                if(paused){

                    timer.stop();

                    repaint();

                }
                else{

                    timer.start();
                }
            }
        }
    }
}