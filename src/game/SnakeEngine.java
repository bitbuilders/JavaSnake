package game;

import java.awt.Button;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class SnakeEngine {

	private static Timer timer;
	private static JFrame frame;
	private static Button snake;
	private static ArrayList<Button> body;
	private static Button food;
	private static ArrayList<Point> locations;
	private static String direction;
	private static int tileSize = 20;
	private static int frameDimensions = 500;
	private static int frameX = 100;
	private static int frameY = 100;
	private static int time = 0;
	private static int seconds = 0;
	private static int score = 0;
	private static int attempt = 1;

	public static void run() {
		loadGame();
		createTimer();
	}

	public static void startGame() {
		createFood();
	}

	public static void loadGame() {
		locations = new ArrayList<>();
		body = new ArrayList<>();
		createFrame();
		createSnake();
		startGame();
	}

	public static void timerTick() {
		time++;

		if (time % 10 == 0) {
			seconds++;
		}
		
		trackLocation();
		
		moveSnake();
		moveCopies();
		checkPickup();
		checkLose();
	}
	
	public static void trackLocation() {
		locations.add(new Point(snake.getX(), snake.getY()));
	}
	
	public static void gameOver() {
		direction = "right";
		System.out.println("Attempt " + attempt + ", Score: " + score * seconds + "\n");
		attempt++;
		score = 0;
		time = 0;
		seconds = 0;
		frame.dispose();
		
		loadGame();
	}
	
	public static void checkLose() {
		if (snake.getX() > (frameDimensions / tileSize - 2) * 20 || snake.getX() < 0 ||
				snake.getY() > (frameDimensions / tileSize - 4) * 20 || snake.getY() < 0) {
			gameOver();
		}
		
		for (int i = 0; i < body.size(); i++) {
			if (snake.getX() == body.get(i).getX() && snake.getY() == body.get(i).getY()) {
				gameOver();
			}
		}
	}
	
	public static void checkPickup() {
		if (snake.getX() == food.getX() && snake.getY() == food.getY()) {
			score++;
			moveFood();
			createCopy();
		}
	}
	
	public static void moveCopies() {
		for (int i = 0; i < body.size(); i++) {
			int x = locations.get(locations.size() - 1 - i).x;
			int y = locations.get(locations.size() - 1 - i).y;
			body.get(i).setBounds(x, y, tileSize, tileSize);
			if (body.get(i).getX() == food.getX() && body.get(i).getY() == food.getY()) {
				body.get(i).setBackground(Color.YELLOW);
			}
			else {
				body.get(i).setBackground(Color.WHITE);
			}
		}
	}
	
	public static void moveSnake() {
		if (direction.equalsIgnoreCase("UP")) {
			snake.setBounds(snake.getX(), snake.getY() - tileSize, snake.getWidth(), snake.getHeight());
		}
		else if (direction.equalsIgnoreCase("DOWN")) {
			snake.setBounds(snake.getX(), snake.getY() + tileSize, snake.getWidth(), snake.getHeight());
		}
		else if (direction.equalsIgnoreCase("LEFT")) {
			snake.setBounds(snake.getX() - tileSize, snake.getY(), snake.getWidth(), snake.getHeight());
		}
		else if (direction.equalsIgnoreCase("RIGHT")) {
			snake.setBounds(snake.getX() + tileSize, snake.getY(), snake.getWidth(), snake.getHeight());
		}
	}
	
	public static void moveFood() {
		Random rand = new Random();
		int x = rand.nextInt(frameDimensions / tileSize - 2);
		int y = rand.nextInt(frameDimensions / tileSize - 4);
		
		x *= tileSize;
		y *= tileSize;
		
		food.setBounds(x, y, tileSize, tileSize);
	}
	
	public static void createFood() {
		food = new Button();

		food.setBackground(Color.YELLOW);
		food.setEnabled(false);
		
		frame.getContentPane().add(food);
		
		moveFood();
	}

	public static void createTimer() {
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timerTick();
			}
		};

		direction = "RIGHT";
		timer = new Timer(100, action);
		timer.setRepeats(true);
		timer.setInitialDelay(0);
		timer.start();
	}

	public static void createFrame() {
		KeyListener keyPress = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED) {
					
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						direction = "UP";
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						direction = "DOWN";
					}
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						direction = "LEFT";
					}
					if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						direction = "RIGHT";
					}
				}
			}
		};
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		frame.setBounds(frameX, frameY, frameDimensions, frameDimensions);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.BLACK);

		frame.setVisible(true);
		frame.addKeyListener(keyPress);
	}
	
	public static void createCopy() {
		Button snake = new Button();

		snake.setBackground(Color.WHITE);
		snake.setEnabled(false);

		body.add(snake);
		frame.getContentPane().add(snake);
	}

	public static void createSnake() {
		snake = new Button();

		snake.setBackground(Color.WHITE);
		snake.setEnabled(false);
		snake.setBounds(20, 20, 20, 20);

		frame.getContentPane().add(snake);
	}
}
