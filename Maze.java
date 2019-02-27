import java.util.*;
import java.io.*;
public class Maze{


    private char[][]maze;
    private boolean animate;//false by default
    private int rowSize;
    private int colSize;

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
        ArrayList<String> map = new ArrayList<String>();
        rowSize = 0;
        colSize = 0;
        File text = new File("input.txt");
        Scanner info = new Scanner(text);
        while (info.hasNextLine()){
          map.add(info.nextLine());
          rowSize++;
          colSize = info.nextLine().length();
        }
        maze = new char[rowSize][colSize];
        //map stored the maze from the file, now transfer it to the maze array
        for (int row = 0; row < rowSize; row++){
          for (int col = 0; col < colSize; col++){
            maze[row][col] = map.get(row).charAt(col); //extract the individual char from the string arraylist
          }
        }
        animate = false;
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
      }
      return ans;

    }

    public boolean takeStep(int row, int col){
      if (maze[row][col] == ' ' || maze[row][col] == 'S'){
        maze[row][col] = '@';
        return true;
      }
      return false;
    }



    /*Wrapper Solve Function returns the helper function

      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.

    */
    public int solve(){

      //find the location of the S.
      for (int row = 0; row < rowSize; row++){
        for (int col = 0; col < colSize; col++){
          if (maze[row][col] == 'S'){
            takeStep(row,col);
          }
        }
      }

      //erase the S


            //and start solving at the location of the s.

            //return solve(???,???);

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
    private int solve(int row, int col){ //you can add more parameters since this is private


        //automatic animation! You are welcome.
        if(animate){

            clearTerminal();
            System.out.println(this);

            wait(20);
        }

        //COMPLETE SOLVE



        return -1; //so it compiles
    }


}
