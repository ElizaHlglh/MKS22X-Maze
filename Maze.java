import java.util.*;
import java.io.*;
public class Maze{


    private char[][]maze;
    private boolean animate;//false by default
    private int rowSize;
    private int colSize;
    private int[] moveRow;
    private int[] moveCol;
    private ArrayList<String> map;
    private int steps;

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)

      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!


      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:

         throw a FileNotFoundException or IllegalStateException

    */

    public Maze(String filename) throws FileNotFoundException{
        //COMPLETE CONSTRUCTOR
        map = new ArrayList<String>();
        rowSize = 0;
        colSize = 0;
        File text = new File(filename);
        Scanner info = new Scanner(text);
        while (info.hasNextLine()){
          map.add(info.nextLine());
          rowSize++;
        }
        colSize = map.get(0).length();
        maze = new char[rowSize][colSize];
        //map stored the maze from the file, now transfer it to the maze array
        for (int row = 0; row < rowSize; row++){
          for (int col = 0; col < colSize; col++){
            maze[row][col] = map.get(row).charAt(col); //extract the individual char from the string arraylist
          }
        }
        if (!hasSE()){
          throw new FileNotFoundException();
        }
        animate = false;
        moveRow = new int[]{1,-1};
        moveCol = new int[]{1,-1};
    }


    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }


    public void setAnimate(boolean b){
        animate = b;
    }


    public void clearTerminal(){

        //erase terminal, go to top left of screen.

        System.out.println("\033[2J\033[1;1H");

    }


   /*Return the string that represents the maze.

     It should look like the text file with some characters replaced.

    */
    public String toString(){
      String ans = "";
      for (int row = 0; row < rowSize; row++){
        for (int col = 0; col < colSize; col++){
          ans += maze[row][col];
        }
        ans += "\n";
      }
      return ans;

    }

    public void clear(){
      for (int row = 0; row < rowSize; row++){
        for (int col = 0; col < colSize; col++){
          maze[row][col] = map.get(row).charAt(col); //extract the individual char from the string arraylist
        }
      }
      steps = 0;
    }

    public boolean takeStep(int row, int col){
      if (maze[row][col] == ' ' || maze[row][col] == 'S'){
        maze[row][col] = '@';
        return true;
      }
      if (maze[row][col] == 'E'){
        return true;
      }
      return false;
    }

    public boolean backStep(int row, int col){
      maze[row][col] = '.';
      return true;
    }

    public boolean isEmpty(int row, int col){
      return maze[row][col] == ' ';
    }

    public boolean isClean(){
      for (int row = 0; row < rowSize; row++){
        for (int col = 0; col < colSize; col++){
          if (maze[row][col] != map.get(row).charAt(col)){
            return false;
          }
        }
      }
      return true;
    }

    private boolean hasSE(){
      boolean yesE = false;
      boolean yesS = false;
      for (int row = 0; row < rowSize; row++){
        for (int col = 0; col < colSize; col++){
          if (maze[row][col] == 'E'){
            yesE = true;
          }
          if (maze[row][col] == 'S'){
            yesS = true;
          }
        }
      }
      return yesE && yesS;
    }



    /*Wrapper Solve Function returns the helper function

      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.

    */
    public int solve(){
      if (!isClean()){
        return 0;
      }
      steps = 0;
      int startingR = 0;
      int startingC = 0;
      //find the location of the S.
      for (int row = 0; row < rowSize; row++){
        for (int col = 0; col < colSize; col++){
          if (maze[row][col] == 'S'){
            startingR = row;
            startingC = col;
          }
        }
      }
      takeStep(startingR,startingC); //erase the S
      //and start solving at the location of the s.
      //return solve(???,???);
      solve2(startingR,startingC,0);
      int ans = steps;
      steps = 0;
      return ans;
    }

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.


      Postcondition:

        The S is replaced with '@' but the 'E' is not.

        All visited spots that were not part of the solution are changed to '.'

        All visited spots that are part of the solution are changed to '@'
    */
    private int solve(int row, int col, int step){ //you can add more parameters since this is private
      //automatic animation! You are welcome.
      if(animate){
        clearTerminal();
        System.out.println(this);
        wait(200);
      }
      //COMPLETE SOLVE
      if (maze[row][col] == 'E'){
        System.out.println("" + step);
        return step-1; //walking into E count as one so reduce one step
      }
      //if doesn't reach a solution, return -1
      else{
        //find the possible future moves
        ArrayList<Integer> RowList = new ArrayList<Integer>();
        ArrayList<Integer> ColList = new ArrayList<Integer>();
        for (int i = 0; i < 2; i++){
          if (/*row + moveRow[i] >= 0 && row + moveRow[i] < rowSize &&*/ (maze[row+moveRow[i]][col] == ' ' || maze[row+moveRow[i]][col] == 'E')){
            RowList.add(row+moveRow[i]);
            ColList.add(col);
          }
          if (/*col + moveCol[i] >= 0 && col + moveCol[i] < colSize &&*/ (maze[row][col+moveCol[i]] == ' ' || maze[row][col+moveCol[i]] == 'E')) {
            RowList.add(row);
            ColList.add(col+moveCol[i]);
          }
        }
        System.out.println(RowList);
        System.out.println(ColList);
        //after constructing the future moves, go on to try taking steps on each valid moves
        for (int l = 0; l < RowList.size(); l++){
          if (takeStep(RowList.get(l), ColList.get(l))){
            if (solve(RowList.get(l), ColList.get(l), step+1) == -1){
              backStep(RowList.get(l), ColList.get(l));
            }
            else{
              solve(RowList.get(l), ColList.get(l), step+1);
            }
          }
        }
        return -1;
      }
    }

    private int solve2(int row, int col, int step){ //you can add more parameters since this is private
      //automatic animation! You are welcome.
      if(animate){
        clearTerminal();
        System.out.println(this);
        wait(200);
      }
      //COMPLETE SOLVE
      if (maze[row][col] == 'E'){
        //System.out.println("Currently at the answer: " + step);
        steps = step;
        return step; //walking into E count as one so reduce one step
      }
      else{
        //find the possible future moves
        ArrayList<Integer> RowList = new ArrayList<Integer>();
        ArrayList<Integer> ColList = new ArrayList<Integer>();
        for (int i = 0; i < 2; i++){
          if ((maze[row+moveRow[i]][col] == ' ' || maze[row+moveRow[i]][col] == 'E')){
            RowList.add(row+moveRow[i]);
            ColList.add(col);
          }
          if ((maze[row][col+moveCol[i]] == ' ' || maze[row][col+moveCol[i]] == 'E')) {
            RowList.add(row);
            ColList.add(col+moveCol[i]);
          }
        }
        if (RowList.size() == 0){
          return -1;
        }
        //after constructing the future moves, go on to try taking steps on each valid moves
        for (int l = 0; l < RowList.size(); l++){
          /*if (takeStep(RowList.get(l), ColList.get(l))){
            if (solve2(RowList.get(l), ColList.get(l), step+1) >= 0){
              backStep(RowList.get(l), ColList.get(l));
            }
            else{
              solve2(RowList.get(l), ColList.get(l), step+1);
            }
          }*/
          if (takeStep(RowList.get(l), ColList.get(l))){
            if (solve2(RowList.get(l), ColList.get(l), step+1) >= 0){
              return step;
            }
            else{
              backStep(RowList.get(l), ColList.get(l));
            }
          }
        }
        return -1;
      }
    }




}
