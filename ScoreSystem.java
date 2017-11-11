///***
// * Score System
// * this will provide the logic for the HackMan point system.
// * it will keep track of highscores, current scores, and all the logic for updating these scores
// * @author yassine
// * @version 1.0
// */
//public class ScoreSystem {
//
//    private int currentScore; // stores the current score of the user
//    private int highScore; // stores the highest score of any user
//    private int levelBonus; // stores the points gained after the end of a level
//    private int efficiencyBonus; // stores the points gained from being efficient
//    private int levelMultiplier; //the same as the current level.
//    private Game game;
//
//    public ScoreSystem(){
//        game = new Game();
//        levelMultiplier = 1;
//        currentScore = 0;
//        highScore = 0;
//        levelBonus = 100; // levelBonus will be multiplied by the same integer as the level. e.g. level 2 completion = 2*100 points;
//
//    }
//
//    public int getCurrentScore() {
//        return currentScore;
//    }
//
//    public int getHighScore() {
//        return highScore;
//    }
//
//    public int getLevelBonus() {
//        return levelBonus;
//    }
//
//    public int getEfficiencyBonus() {
//        return efficiencyBonus;
//    }
//
//    public void updateCurrentScore(){
//        currentScore = currentScore + getLevelBonus()*levelMultiplier;
//    }
//
//    public void updateHighScore(){
//        if (getCurrentScore() > getHighScore()) {
//            highScore = getCurrentScore();
//        }
//    }
//
//    //public void calculateEfficiencyScore(){
//      //  efficiencyBonus = 3*(26 - (lettersGreyedOut))
//    //}
//
//    public void levelComplete (){
//        boolean isLevelCompleted = true;
//        if(isLevelCompleted == true){
//            updateCurrentScore();
//            updateHighScore();
//        }
//    }
//}
