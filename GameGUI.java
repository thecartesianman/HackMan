import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.*;
import java.util.*;

/**
 * Displays a Hangman game board to the screen for interaction with the player.
 */
public class GameGUI extends JFrame {

    private final int WIDTH;
    private final int HEIGHT;
    private int maxIncorrect;

    private final String HANGMAN_IMAGE_DIRECTORY;
    private final String HANGMAN_IMAGE_TYPE;
    private final String HANGMAN_IMAGE_BASE_NAME;
    private final String LETTER_IMAGE_DIRECTORY;
    private final String LETTER_IMAGE_TYPE;
    private LetterRack gameRack;
    private Hangman gameHangman;
    private int numIncorrect;
    private JLabel correct;
    private JLabel incorrect;
    private String wordToGuess;
    private StringBuilder passwordHidden;

    private int levelNumber = 1;

    /**
     * The default constructor.
     */
    public GameGUI() {
        setTitle("HackMan: Version 1.0");
        WIDTH = 500;
        HEIGHT = 550;
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        addCloseWindowListener();

        maxIncorrect = 11;

        HANGMAN_IMAGE_DIRECTORY = LETTER_IMAGE_DIRECTORY = "res/images/";
        HANGMAN_IMAGE_TYPE = LETTER_IMAGE_TYPE = ".png";
        HANGMAN_IMAGE_BASE_NAME = "hangman";

        initialise(levelNumber);
    }

    /**
     * Initializes all elements of the GameGUI that must be refreshed upon
     * the start of a new game.
     */
    private void initialise(int levelNumber) {
        numIncorrect = 0;

        correct = new JLabel("Word: ");
        incorrect = new JLabel("Incorrect: " + numIncorrect);
        wordToGuess = new String();
        passwordHidden = new StringBuilder();

        getWord(levelNumber);
        addTextPanel();
        addLetterRack();
        addHangman();

        // set board slightly above middle of screen to prevent dialogs
        //     from blocking images
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2 - 200);
        setVisible(true);
    }

    /**
     * Prompts the user to confirm before quitting out of the window.
     */
    private void addCloseWindowListener() {
        // NOTE: Must be DO_NOTHING_ON_CLOSE for prompt to function correctly
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int prompt = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to quit?",
                        "Quit?",
                        JOptionPane.YES_NO_OPTION);

                if (prompt == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });
    }

    /**
     * Adds the correct and incorrect labels to the top of the GameGUI
     */
    private void addTextPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1, 3));
        textPanel.add(correct);
        textPanel.add(incorrect);
        // use BorderLayout to set the components of the gameboard in
        //     "visually agreeable" locations
        add(textPanel, BorderLayout.NORTH);
    }

    /**
     * Adds the LetterRack to the bottom of the GameGUI and attaches
     * the LetterTile TileListeners to the LetterTiles.
     */
    private void addLetterRack() {
        gameRack = new LetterRack(wordToGuess, LETTER_IMAGE_DIRECTORY, LETTER_IMAGE_TYPE, 4, 8);
        gameRack.attachListeners(new TileListener());
        add(gameRack, BorderLayout.SOUTH);
    }

    /**
     * Adds a panel that contains the hangman images to the middle of the
     * GameGUI.
     */
    private void addHangman() {
        JPanel hangmanPanel = new JPanel();
        gameHangman = new Hangman(HANGMAN_IMAGE_BASE_NAME,
                HANGMAN_IMAGE_DIRECTORY,
                HANGMAN_IMAGE_TYPE);
        hangmanPanel.add(gameHangman);
        add(hangmanPanel, BorderLayout.CENTER);
    }

    /**
     *
     *
     */
    private void getWord(int levelNumber) {
        List<String> words = new ArrayList();
        Scanner x;
        try {
            x = new Scanner(new File("res/Words/words_level_" + levelNumber + ".txt"));
            while (x.hasNext()) {
                String w = x.next();
                words.add(w);
            }
            x.close();
        } catch (Exception e) {
            System.out.println("could not get words database");
        }
        wordToGuess = words.get(new Random().nextInt(words.size()));
        passwordHidden.append(wordToGuess.replaceAll(".", "_ "));
        correct.setText(correct.getText() + passwordHidden.toString());
    }

//    private void newGameDialog(String newInterface) {
//        int dialogResult = JOptionPane.showConfirmDialog(null,
//                "Are you sure you want to end the current game,\n" +
//                        "and view " + newInterface + "?",
//                "End Current Game?",
//                JOptionPane.YES_NO_OPTION);
//        if (dialogResult == JOptionPane.YES_OPTION)
//            //initialise(); // re-initialise the GameGUI
//        else
//            System.exit(0);
//    }

    /**
     * Prompts the user for a new game when one game ends.
     */
    private void newGameDialog() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Your Score: " + Integer.toString(((11 - numIncorrect) * 100)) +
                        "\nThe word was: " + wordToGuess +
                        "\nWould You Like to Start a New Game?",
                "Play Again?",
                JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            initialise(++levelNumber);
        } else {
            System.exit(0);
        }
    }

    /**
     * A custom MouseListener for the LetterTiles that detects when the user
     * "guesses" (clicks on) a LetterTile and updates the game accordingly.
     */
    private class TileListener implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            Object source = e.getSource();
            if (source instanceof LetterTile) {
                char c = ' ';
                int index = 0;
                boolean updated = false;

                // cast the source of the click to a LetterTile object
                LetterTile tilePressed = (LetterTile) source;
                c = tilePressed.guess();

                // reveal each instance of the character if it appears in the
                //     the wordToGuess
                while ((index = wordToGuess.toLowerCase().indexOf(c, index)) != -1) {
                    passwordHidden.setCharAt(index, wordToGuess.charAt(index));
                    index++;
                    updated = true;
                }

                // if the guess was correct, update the GameGUI and check
                //     for a win
                if (updated) {
                    correct.setText("Word: " + passwordHidden.toString());

                    if (passwordHidden.toString().equals(wordToGuess)) {
                        gameRack.removeListeners();
                        gameHangman.winImage();
                        newGameDialog();
                    }
                }

                // otherwise, add an incorrect guess and check for a loss
                else {
                    incorrect.setText("Incorrect: " + ++numIncorrect);

                    if (numIncorrect >= maxIncorrect) {
                        gameHangman.loseImage();
                        gameRack.removeListeners();
                        newGameDialog();
                    } else
                        gameHangman.nextImage(numIncorrect);
                }
            }
        }

        // These methods must be implemented in every MouseListener
        //     implementation, but since they are not used in this application, 
        //     they contain no code

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}