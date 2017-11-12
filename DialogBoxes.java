import javax.swing.*;

public class DialogBoxes {

    private void newGameDialog(String wordToGuess) {
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
}
