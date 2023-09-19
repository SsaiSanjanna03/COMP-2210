import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Ssai Sanjanna Ganji (szg0161@auburn.edu)
 */
public class Doublets implements WordLadderGame {

    // The word list used to validate words.
    // Must be instantiated and populated in the constructor.
    List<String> LADDER = new ArrayList<>();
    /////////////////////////////////////////////////////////////////////////////
    // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
    // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
    // PURPOSE OF YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
    // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
    // table with chaining).
    /////////////////////////////////////////////////////////////////////////////
     TreeSet<String> lexicon;
    /**
     * Instantiates a new instance of Doublets with the lexicon populated with
     * the strings in the provided InputStream. The InputStream can be formatted
     * in different ways as long as the first string on each line is a word to be
     * stored in the lexicon.
     */
    public Doublets(InputStream in) {
        try {
            //////////////////////////////////////
            // INSTANTIATE lexicon OBJECT HERE  //
            //////////////////////////////////////
            lexicon = new TreeSet<String>();
            Scanner s =
                new Scanner(new BufferedReader(new InputStreamReader(in)));
            while (s.hasNext()) {
                String str = s.next();
                /////////////////////////////////////////////////////////////
                // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
                /////////////////////////////////////////////////////////////
            lexicon.add(str.toLowerCase());
                s.nextLine();
            }
            in.close();
        }
        catch (java.io.IOException e) {
            System.err.println("Error reading from InputStream.");
            System.exit(1);
        }
    }


    //////////////////////////////////////////////////////////////
    // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
    //////////////////////////////////////////////////////////////
     
    /**
    * Method to find the hamming distance between two strings.
    * @param word1 for first string
    * @param word2 for second string
    * @return hamming distance between word1 and word2   
    */
     public int getHammingDistance(String str1, String str2) {
      int distance = 0;
   
      if (str1.length() != str2.length()) {
         return -1; 
      }
   
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            distance++;
         }
      }
      return distance;
   }
    /**
    * Method to get a ladder from first to last.
    * @param first for starting word
    * @param last for ending word
    * @return ladder from first to last  
    */
    public List<String> getFullLadder(String first, String last) {
      List<String> wordLadder = new ArrayList<String>();
        
      if (first.equals(last)) {
         wordLadder.add(first);
         return wordLadder;
      }
      else if (first.length() != last.length()) {
         return LADDER;
      }
      else if (!isWord(first) || !isWord(last)) {
         return LADDER;
      }
          
      TreeSet<String> ladderTree = new TreeSet<>();
      Deque<String> ladderStack = new ArrayDeque<>();
       
      ladderStack.addLast(first);
      ladderTree.add(first);
      while (!ladderStack.isEmpty()) {
       
         String present = ladderStack.peekLast();
         if (present.equals(last)) {
            break;
         }
         List<String> adjacentWord = getNeighbors(present);
         List<String> adjacent = new ArrayList<>();
          
         for (String ladderWord : adjacentWord) {
            if (!ladderTree.contains(ladderWord)) {
               adjacent.add(ladderWord);
            }
         }
         if (!adjacent.isEmpty()) {
            ladderStack.addLast(adjacent.get(0));
            ladderTree.add(adjacent.get(0));
         }
         else {
            ladderStack.removeLast();
         }
      }
      wordLadder.addAll(ladderStack);
      return wordLadder;
   }
/**
    * Method to get a ladder of minimum length from first to last.
    * @param start for starting word
    * @param end for ending word
    * @return minimum ladder from first to last  
    */
    public List<String> getMinLadder(String start, String end) {
      List<String> minLadder = new ArrayList<String>();
      if (start.equals(end)) {
         minLadder.add(start);
         return minLadder;
      }
      else if (start.length() != end.length()) {
         return LADDER;
      }
      else if (!isWord(start) || !isWord(end)) {
         return LADDER;
      }
    
      Deque<Node> queue = new ArrayDeque<>();
      TreeSet<String> ladderTree = new TreeSet<>();
    
      ladderTree.add(start);
      queue.addLast(new Node(start, null));
      while (!queue.isEmpty()) {
       
         Node p = queue.removeFirst();
         String location = p.location;
          
         for (String adjacentWord : getNeighbors(location)) {
            if (!ladderTree.contains(adjacentWord)) {
               ladderTree.add(adjacentWord);
               queue.addLast(new Node(adjacentWord, p));
            }
            if (adjacentWord.equals(end)) {
             
               Node q = queue.removeLast();
               
               while (q != null) {
                  minLadder.add(0, q.location);
                  q = q.previous;
               }
               return minLadder;
            }
         }
      }      
      return LADDER;
   }
    /**
    * Method to get adjacent words with same hamming distance from the parameter word.
    * @param word for parameter
    * @return adjacent words  
    */
     public List<String> getNeighbors(String word) {
      List<String> adjacent = new ArrayList<String>();
      TreeSet<String> ladderTree = new TreeSet<String>();
       
      if (word == null)
         return LADDER;
      
      for (String ladderWord : lexicon) {
         if (getHammingDistance(word, ladderWord) == 1)
            adjacent.add(ladderWord);
      }
      
      return adjacent;
   }
    /**
    * Method to get number of words in lexicon
    * return count 
    */
    public int getWordCount() {
      int count = lexicon.size();
      return count;
   }
/**
    * Method to check if a given string is a word in lexicon 
    * @param word for checking word
    @ return true if present in lexicon, otherwise false
    */
     public boolean isWord(String str) {
      if (lexicon.contains(str))
      {
         return true;
      }
      return false;
   }
    /**
    * Method to check if the given sequence of words forms a word ladder
    * @param sequence for sequence of words
    * return truw if sequence forms a word ladder, otherwise false
    */
     public boolean isWordLadder(List<String> sequence) {
      int count = 0;
      if ((sequence == null) || (sequence.isEmpty())) {
         return false;
      }
      
      for (int i = 0; i < sequence.size() - 1; i++){
         if (isWord(sequence.get(i)) != true || isWord(sequence.get(i + 1)) != true || (getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1))
            count++;  
      }
      boolean seqIsWordLadder = (count == 0);
      return seqIsWordLadder;
   }
   
   private class Node {
      String location;
      Node previous;
   
      public Node(String n, Node prev) {
         location = n;
         previous = prev;
      }
   }
}


