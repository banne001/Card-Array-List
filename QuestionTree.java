import java.util.*;
import java.io.*;
/**
  *QuestionTree Class playes the guessing game
  * where the computer tries to guess what the user 
  * is guessing by asking questions of yes and no answers
  * if it is wrong the object is added to its possible answers with a corresponding 
  * question
  *
  *@author Blezyl Santos
  *@version 3.17 (March 17 2019)
  */
public class QuestionTree
{
   ////////////////////////////////////////////////////////////////////////
   /**
    *QuestionNode Class pointers to create binary tree
    */
   private class QuestionNode{
      public String data;//question or answer
      //answer to the question
      public QuestionNode yes;
      public QuestionNode no;
   /**
    * QuestionNode Constructor where question or answer is passed
    * in the parameter and the yes an no pointers is null
    */
      public QuestionNode(String Q){
         data = Q;
         yes = null;
         no = null; 
      }  
   } 
 //////////////////////////////////////////////////////////////////////////
   //fields
   private QuestionNode root;
   UserInterface my;
   private int games;
   private int wins;
   /**
    * QuestionTree Constructor where UI is passed
    * in the parameter. Computer as an answer is initiated 
    * for the tree, UI, games, and wins are all initiated.
    *@param ui user interface interaction with the user to answer question and 
    * print lines.
    */
   public QuestionTree (UserInterface ui)
   {
      root = new QuestionNode("Computer");//starts with computer as an answer
      my = ui;
      games = 0;
      wins = 0;
   }
   /**
    * plays the Question Game tries to determine the object you are thinking of 
    * by asking yes or no questions. Also adds 1 for the games played if called
    */  
   public void play()
   {
      my.println("Play Game here");
      root = play(root);
      games++;
   }
   /**
    * Actual method that does the deducing to determine the object you are thinking of 
    * by asking yes or no questions. current QuestionNode is the node it is question or 
    * answer it is looking at. if it is an answer it asks if it is the object the user is 
    * thinking of, if it is not the object it adds a question and the object to the tree.
    * If it a question it asks the question and goes to the correnponding yes or no node. 
    * returns the node that it is currently. only returns a different node if a node has been added
    * @param current node to check if it is an answer or question and asks if it is the answer or asks the question
    * @return current the current node it is pointing at
    * @return q the question that has been added if object is new
    */ 
   private QuestionNode play(QuestionNode current){
      if(current.yes == null && current.no == null){
         my.println("Would your object happen to be " + current.data + "?");
         if (my.nextBoolean()){
            my.println("I win!");
            wins++;
            return current;
         } else {//adds a new node to the tree
            my.println("I Lose. What is your Object?");
            String answer = my.nextLine(); // answer has to become a node 
            my.println("Type a yes/no question to distinguish your item from the " + current.data+": ");
            String question = my.nextLine();
            my.println("And what is the answer for your object?");
            QuestionNode q = new QuestionNode(question);
            QuestionNode a = new QuestionNode(answer);
            if (my.nextBoolean()){
               q.no = current;
               q.yes = a;
               return q;
            } else {
               q.yes = current;
               q.no = a;
               return q;
            }
         }
      } else {//current node is a question
         my.println(current.data);
         if (my.nextBoolean()){
            current.yes = play(current.yes);
         } else {
            current.no = play(current.no);
         }
      } return current;
  }
   /**
    * saves the Question Game tree in a file that the user picked 
    * calls the private save method which actually does the work
    */
   public void save(PrintStream output)
   {
      root = save(root, output);
   }
 /**
  * saves the Question Game tree recursively. if the node is an answer it has "A: " in front
  * if the node is a question then it has a "Q: " in front and print the yes node and the no node
  * the saving is in pre-order  
  * @param current node to check if it is an answer or question 
  * @param output to print out the line in the file
  * @return current the current node that it is pointing at
  */
  private QuestionNode save(QuestionNode current, PrintStream output){
   if (current.yes == null && current.no == null){//this is a leaf, answer
      output.println("A: " + current.data);
   } else {
      output.println("Q: " + current.data);
      current.yes = save(current.yes, output);
      current.no = save(current.no, output);
   }return current;
  }
  /**
  * loads the Question Game tree from a gicen file that the user picked 
  * calls the private load method which actually does the work
  */
  public void load(Scanner input)
  {
    if(!input.hasNextLine()) throw new IllegalArgumentException("File is empty");
    root = load(root, input);
  }
  /**
  * loads the Question Game tree recursively. if the node is an answer it has "A: " in front
  * if the node is a question then it has a "Q: " in front of it. The method creates the binary tree 
  * from a previous game and creates the corrosponding nodes for the question and answer.  
  * @param current node to add to as an answer or question 
  * @param input to read the line in the file
  * @return current the current node that it is pointing at
  * @return question the node that it is pointing at
  * @return answer the  node that it is pointing at
  */
  private QuestionNode load(QuestionNode current, Scanner input){
   if(input.hasNextLine()){
      String line = input.nextLine();
      if (line.contains("Q")){
         String q = line.substring(2);
         QuestionNode question = new QuestionNode(q);
         question.yes = load(question.yes, input);
         question.no = load(question.no, input);
         return question;
      }else if (line.contains("A")){
         String a = line.substring(2);
         QuestionNode answer = new QuestionNode(a);
         return answer;
      }
   }return current;
  }
 /**
  *@returns games the number games played by the user
  */
  public int totalGames()
  {
    return games;
  }
 /**
  *@returns wins the number of times computer won
  */
  public int gamesWon()
  {
    return wins;
  }
  
}