import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 */
public class LetterRack extends JPanel
{
    /**
     * The number of columns present in the letter tile rack.
     */
    private final int RACK_COLS;
    /**
     * The number of columns present in the letter tile rack.
     */
    private final int RACK_ROWS;
    /**
     * The layout of the letter rack.
     */
    private final GridLayout LETTER_RACK_LAYOUT;
    /**
     * The capacity of the letter rack.
     */
    private final int CAPACITY;
    /**
     * The directory containing the letter images.
     */
    private final String IMAGE_DIRECTORY;
    /**
     * The type of image (.jpg, .png, etc. to include the period).
     */
    private final String IMAGE_TYPE;
    /**
     * The wordToGuess chosen to be guessed.
     */
    private final String wordToGuess;
    /**
     * An array of letters to be displayed on the GameGUI.
     */
    private final ArrayList<LetterTile> rack;
    /**
     * The default constructor.
     */
    public LetterRack()
    {
        this("wordToGuess", "res/images/", ".png");
    }
    
    /**
     * Creates a new LetterRack given the wordToGuess to be guessed, letter image
     * directory, and letter image type
     * @param inPassword The wordToGuess to be guessed.
     * @param imageDirectory The directory of the letter images.
     * @param imageType The type of the letter images.
     */
    public LetterRack(String inPassword, String imageDirectory, 
            String imageType)
    {
        RACK_COLS = 8;
        RACK_ROWS = 4;
        LETTER_RACK_LAYOUT = new GridLayout(RACK_ROWS, RACK_COLS);
        LETTER_RACK_LAYOUT.setVgap(10);
        CAPACITY = RACK_ROWS * RACK_COLS;
        
        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;
        
        rack = new ArrayList<>();
        wordToGuess = inPassword;
        
        // add a little padding to make sure the letter rack is centered
        setBorder(BorderFactory.createEmptyBorder(10, 17, 10, 10));
        setLayout(LETTER_RACK_LAYOUT);
        loadRack();
    }
    
    /**
     * Builds and loads the letter rack with letter tiles.
     */
    private void loadRack()
    {
        buildRack();
        for (LetterTile tile : rack)
            add(tile);
    }
    
    /**
     * Builds a letter rack from a blend of the wordToGuess and random letters.
     */
    private void buildRack()
    {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(char c : alphabet) {
            rack.add(new LetterTile(c,
                    IMAGE_DIRECTORY,
                    IMAGE_TYPE));
        }
    }
    
    /**
     * Add a TileListener to each LetterTile in the LetterRack
     * @param l The TileListener to be added.
     */
    public void attachListeners(MouseListener l)
    {
        for (LetterTile tile : rack)
            tile.addTileListener(l);
    }
    
    /**
     * Remove all TileListeners from all LetterTiles.
     */
    public void removeListeners()
    {
        for (LetterTile tile : rack)
            tile.removeTileListener();
    }
}