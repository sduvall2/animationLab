package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * A basic example JavaFX program for the first lab.
 * 
 * @author Shannon Duvall
 * 
 * This program animates two squares.  The top is the "mover" and 
 * the bottom is the "grower".
 * 
 * Changes for you to make in the code:
 * 
 * 1. Currently the mover is rotating counter-clockwise.  Add code so the grower
 * 	  rotates in the opposite direction (clockwise).
 * 2. If the mover and grower ever intersect, the mover should change color to be HIGHLIGHT,
 *    and change back to the MOVER_COLOR if not intersecting.
 * 3. If any of the bouncing blue balls intersect the grower, the grower should change color
 *    to be HIGHLIGHT, and change back when not intersecting.
 * 4. The mover can currently only move up and down.  Enable left and right movement with arrow keys.
 * 5. The grower currently only grows horizontally, changing the square to a rectangle.  Change
 *    this to grow in both directions equally so the square shape is preserved.
 */
public class ExampleBounce extends Application {
    public static final String TITLE = "Example JavaFX";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final String BOUNCER_IMAGE = "resources/ball.gif";
    public static final Paint MOVER_COLOR = Color.PLUM;
    public static final int MOVER_SIZE = 50;
    public static final int MOVER_SPEED = 5;
    public static final Paint GROWER_COLOR = Color.BISQUE;
    public static final double GROWER_RATE = 1.1;
    public static final int GROWER_SIZE = 50;
    public static final int NUM_BOUNCERS = 5;


    // some things we need to remember during our game
    private Scene myScene;
    private List<Bouncer> myBouncers;
    private Rectangle myMover;
    private Rectangle myGrower;


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // make some shapes and set their properties
        try {
	        Image image = new Image(new FileInputStream(BOUNCER_IMAGE));
	        myBouncers = new ArrayList<>();
	        for (int k = 0; k < NUM_BOUNCERS; k++) {
	            Bouncer b = new Bouncer(image, width, height);
	            myBouncers.add(b);
	            root.getChildren().add(b.getView());
	        }
        }
	    catch (FileNotFoundException e) {}
        myMover = new Rectangle(width / 2 - MOVER_SIZE / 2, height / 2 - 100, MOVER_SIZE, MOVER_SIZE);
        myMover.setFill(MOVER_COLOR);
        myGrower = new Rectangle(width / 2 - GROWER_SIZE / 2, height / 2 + 50, GROWER_SIZE, GROWER_SIZE);
        myGrower.setFill(GROWER_COLOR);
        // order added to the group is the order in which they are drawn
        root.getChildren().add(myMover);
        root.getChildren().add(myGrower);
        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    // Change properties of shapes in small ways to animate them over time
 
    private void step (double elapsedTime) {
        // update "actors" attributes
        for (Bouncer b : myBouncers) {
            b.move(elapsedTime);
        }
        myMover.setRotate(myMover.getRotate() - 1);
     

        // check for collisions
        // with shapes, can check precisely
        Shape intersection = Shape.intersect(myMover, myGrower);
        if (intersection.getBoundsInLocal().getWidth() != -1) {
          
        }
        else {

        }
        // with images can only check bounding box
        boolean hit = false;
        for (Bouncer b : myBouncers) {
            if (myGrower.getBoundsInParent().intersects(b.getView().getBoundsInParent())) {
                
                hit = true;
            }
        }
        if (! hit) {
           
        }

        // bounce off all the walls
        for (Bouncer b : myBouncers) {
            b.bounce(myScene.getWidth(), myScene.getHeight());
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
    
    	if (code == KeyCode.UP) {
            myMover.setY(myMover.getY() - MOVER_SPEED);
        }
        else if (code == KeyCode.DOWN) {
            myMover.setY(myMover.getY() + MOVER_SPEED);
        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
        if (myGrower.contains(x, y)) {
            myGrower.setScaleX(myGrower.getScaleX() * GROWER_RATE);
            
        }
    }


    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
