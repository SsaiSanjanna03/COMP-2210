import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.Math;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

import java.util.Iterator;
import java.util.Scanner;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Defines the methods needed to play a word search game.
 *
 */
public interface WordSearchGame {
    /**
     * Loads the lexicon into a data structure for later use. 
     * 
     * @param fileName A string containing the name of the file to be opened.
     * @throws IllegalArgumentException if fileName is null
     * @throws IllegalArgumentException if fileName cannot be opened.
     */
    void loadLexicon(String fileName);
    
    /**
     * Stores the incoming array of Strings in a data structure that will make
     * it convenient to find words.
     * 
     * @param letterArray This array of length N^2 stores the contents of the
     *     game board in row-major order. Thus, index 0 stores the contents of board
     *     position (0,0) and index length-1 stores the contents of board position
     *     (N-1,N-1). Note that the board must be square and that the strings inside
     *     may be longer than one character.
     * @throws IllegalArgumentException if letterArray is null, or is  not
     *     square.
     */
    void setBoard(String[] letterArray);
    
    /**
     * Creates a String representation of the board, suitable for printing to
     *   standard out. Note that this method can always be called since
     *   implementing classes should have a default board.
     */
    String getBoard();
    
    /**
     * Retrieves all scorable words on the game board, according to the stated game
     * rules.
     * 
     * @param minimumWordLength The minimum allowed length (i.e., number of
     *     characters) for any word found on the board.
     * @return java.util.SortedSet which contains all the words of minimum length
     *     found on the game board and in the lexicon.
     * @throws IllegalArgumentException if minimumWordLength < 1
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    SortedSet<String> getAllScorableWords(int minimumWordLength);
    
  /**
    * Computes the cummulative score for the scorable words in the given set.
    * To be scorable, a word must (1) have at least the minimum number of characters,
    * (2) be in the lexicon, and (3) be on the board. Each scorable word is
    * awarded one point for the minimum number of characters, and one point for 
    * each character beyond the minimum number.
    *
    * @param words The set of words that are to be scored.
    * @param minimumWordLength The minimum number of characters required per word
    * @return the cummulative score of all scorable words in the set
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */  
    int getScoreForWords(SortedSet<String> words, int minimumWordLength);
    
    /**
     * Determines if the given word is in the lexicon.
     * 
     * @param wordToCheck The word to validate
     * @return true if wordToCheck appears in lexicon, false otherwise.
     * @throws IllegalArgumentException if wordToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    boolean isValidWord(String wordToCheck);
    
    /**
     * Determines if there is at least one word in the lexicon with the 
     * given prefix.
     * 
     * @param prefixToCheck The prefix to validate
     * @return true if prefixToCheck appears in lexicon, false otherwise.
     * @throws IllegalArgumentException if prefixToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    boolean isValidPrefix(String prefixToCheck);
        
    /**
     * Determines if the given word is in on the game board. If so, it returns
     * the path that makes up the word.
     * @param wordToCheck The word to validate
     * @return java.util.List containing java.lang.Integer objects with  the path
     *     that makes up the word on the game board. If word is not on the game
     *     board, return an empty list. Positions on the board are numbered from zero
     *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
     *     board, the upper left position is numbered 0 and the lower right position
     *     is numbered N^2 - 1.
     * @throws IllegalArgumentException if wordToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    List<Integer> isOnBoard(String wordToCheck);

    // My nested Class
   
    public class searchGame implements WordSearchGame {
   //Fields
   private TreeSet<String> lexicon;
   private String[][] board;
   private static final int MAX_CHARACTERS = 8;
   private int breadth;
   private int height;
   private boolean[][] searched;
   private ArrayList<Integer> track1;
   private String partOfWord;
   private SortedSet<String> boardWords;
   private ArrayList<Position> track2;
   
   /** Constructor. **/
   public searchGame() {
      lexicon = null;
      
      //Default board 
      board = new String[4][4];
      board[0][0] = "E"; 
      board[0][1] = "E"; 
      board[0][2] = "C"; 
      board[0][3] = "A"; 
      board[1][0] = "A"; 
      board[1][1] = "L"; 
      board[1][2] = "E"; 
      board[1][3] = "P"; 
      board[2][0] = "H"; 
      board[2][1] = "N"; 
      board[2][2] = "B"; 
      board[2][3] = "O"; 
      board[3][0] = "Q"; 
      board[3][1] = "T"; 
      board[3][2] = "T"; 
      board[3][3] = "Y";  
        
      breadth = board.length;
      height = board[0].length;
   }
   // Overriding loadLexicon method
        public void loadLexicon(String fileName) {
   
      lexicon = new TreeSet<String>(); 
   
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      
      try {
         Scanner scan = new Scanner(new BufferedReader(new FileReader(new File(fileName))));
         while (scan.hasNext()) {
            String letter = scan.next();
            letter = letter.toUpperCase();
            lexicon.add(letter);
            scan.nextLine();
         }
      }
      
      catch (java.io.FileNotFoundException e) {
         throw new IllegalArgumentException();
      } 
   }
    // Overriding setBoard method
        public void setBoard(String[] letterArray) {
    
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      
      int a = (int) Math.sqrt(letterArray.length);
      if (a * a != letterArray.length) {
         throw new IllegalArgumentException();
      }
      
      board = new String[a][a];
      breadth = a;
      height = a;
      int i = 0;
      for (int p = 0; p < height; p++) {
         for (int q = 0; q < breadth; q++) {
            board[p][q] = letterArray[i];
            i++;
         }
      }
   }
     // Overriding getBoard method
        public String getBoard() {
   
      String stringOnBoard = "";
      
      for (int p = 0; p < height; p++) {
         stringOnBoard += "\n";
         
         for (int q = 0; q < breadth; q++) {
            stringOnBoard += board[p][q] + " ";
         }
      }
      
      return stringOnBoard;
   }
    // Overriding SortedSet method
        public SortedSet<String> getAllScorableWords(int minimumWordLength) {
   
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      track2 = new ArrayList<Position>();
      boardWords = new TreeSet<String>();
      partOfWord = "";
      
      for (int p = 0; p < height; p++) {
      
         for (int q = 0; q < breadth; q++) {
            partOfWord = board[p][q];
            
            if (isValidWord(partOfWord) && partOfWord.length() >= minimumWordLength) {
               boardWords.add(partOfWord);
            }
            
            if (isValidPrefix(partOfWord)) {
               Position wordLocated = new Position(p,q);
               track2.add(wordLocated);
               depthFirst2(p, q, minimumWordLength); 
               track2.remove(wordLocated);
            }
         }
      }
      
      return boardWords;
   }
    // Overriding getScoreForWords method
        public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
   
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      int points = 0;
      Iterator<String> itr = words.iterator();
      
      while (itr.hasNext()) {
         String word = itr.next();
         
         if (word.length() >= minimumWordLength && isValidWord(word)
             && !isOnBoard(word).isEmpty()) {
            points += (word.length() - minimumWordLength) + 1;
         }
      }
      
      return points;
   }
      // Overriding isValidWord method
        public boolean isValidWord(String wordToCheck) {
   
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      wordToCheck = wordToCheck.toUpperCase();
      return lexicon.contains(wordToCheck);
   }
    // Overriding isValidPrefix method
        public boolean isValidPrefix(String prefixToCheck) {
   
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      prefixToCheck = prefixToCheck.toUpperCase();
      String word = lexicon.ceiling(prefixToCheck);
      
      if (word != null) {
         return word.startsWith(prefixToCheck);
      }
      
      return false;
   }
// Overriding isOnBoard method
     public List<Integer> isOnBoard(String wordToCheck) {
   
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      track2 = new ArrayList<Position>();
      wordToCheck = wordToCheck.toUpperCase();
      partOfWord = "";
      track1 = new ArrayList<Integer>();
      
      for (int p = 0; p < height; p++) {
      
         for (int q = 0; q < breadth; q++) {
         
            if (wordToCheck.equals(board[p][q])) {
               track1.add(p * breadth + q);
               return track1;
            }
            
            if (wordToCheck.startsWith(board[p][q])) {
               Position wordLocated = new Position(p, q);
               track2.add(wordLocated);
               partOfWord = board[p][q];
               depthFirst1(p, q, wordToCheck);
               
               if (!wordToCheck.equals(partOfWord)) {
                  track2.remove(wordLocated);
               }
               else {
                  for (Position n: track2) {
                     track1.add((n.a * breadth) + n.b);
                  } 
                  
                  return track1;
               }
            }
         }
      }
      return track1;
   }
   
  /**
   * Depth-First Search.
   * @param a  is position in a row
   * @param b is position in column
   * @param checkWord to check for the word
   */
   private void depthFirst1(int a, int b, String checkWord) {
      Position first = new Position(a, b);
      unSearched();
      searched();
       for (Position n: first.otherValues()) {
         if (!isSearched(n)) {
            search(n);            
            if (checkWord.startsWith(partOfWord + board[n.a][n.b])) {
               partOfWord += board[n.a][n.b];
               track2.add(n);
               depthFirst1(n.a, n.b, checkWord);
               
               if (checkWord.equals(partOfWord)) {
                  return;
               }
               else {
                  track2.remove(n);
               
                  int indexOfLast = partOfWord.length() - board[n.a][n.b].length();
                  partOfWord = partOfWord.substring(0, indexOfLast);
               }
            }
         }
      }
      unSearched();
      searched();
   }
   
  /**
 * Depth-First Search.
   * @param a  is position in a row
   * @param b is position in column
   */
      private void depthFirst2(int a, int b, int least) {
      Position first = new Position(a, b);
      unSearched();
      searched();
      for (Position n : first.otherValues()) {
         if (!isSearched(n)) {
            search(n);     
            if (isValidPrefix(partOfWord + board[n.a][n.b])) {
               partOfWord += board[n.a][n.b];
               track2.add(n);
               
               if (isValidWord(partOfWord) && partOfWord.length() >= least) {
                  boardWords.add(partOfWord);
               }
               
               depthFirst2(n.a, n.b, least);
               track2.remove(n);
               int indexOfLast = partOfWord.length() - board[n.a][n.b].length();
               partOfWord = partOfWord.substring(0, indexOfLast);
            }
         }
      }
      
      unSearched();
      searched();
   }

   /**
   * MarksunSearched positions.
   */
   
   private void unSearched() {
      searched = new boolean[breadth][height];
      for (boolean[] line : searched) {
         Arrays.fill(line, false);
      }
   }
   
   /**
   * Creates searched path.
   */
   
   private void searched() {
      for (int p = 0; p < track2.size(); p++) {
         search(track2.get(p));
      }
   }
   
   /**
    * Creates an (a, b) position.
    */
    
   private class Position {
      int a;
      int b;
   
      /** Constructor. */
      public Position(int a, int b) {
         this.a = a;
         this.b = b;
      }
   
      /** Returns a string representation of this Position. */
      @Override
      public String toString() {
         return "(" + a + ", " + b + ")";
      }
   
      /** Returns all the neighbors of this Position. */
      public Position[] otherValues() {
      
         Position[] sideValues = new Position[MAX_CHARACTERS];
         int numOtherValues = 0;
         Position n;
         
         for (int p = -1; p <= 1; p++) {
            for (int q = -1; q <= 1; q++) {
               if (!((p == 0) && (q == 0))) {
                  n = new Position(a + p, b + q);
                  if (isValid(n)) {
                     sideValues[numOtherValues++] = n;
                  }
               }
            }
         }
         return Arrays.copyOf(sideValues, numOtherValues);
      }
   }

   /**
    * Checks for valid position.
    * @param n the position
    */
    
   private boolean isValid(Position n) {
      return (n.a >= 0) && (n.a < breadth) && (n.b >= 0) && (n.b < height);
   }

   /**
    * Checks if a position has been searched.
    * @param n the position
    */
    
   private boolean isSearched(Position n) {
      return searched[n.a][n.b];
   }

   /**
    * Marks this position as searched.
    */
    
   private void search(Position n) {
      searched[n.a][n.b] = true;
   }
}
}


