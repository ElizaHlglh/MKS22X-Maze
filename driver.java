import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class driver{
    public static void main(String[]args){
      String filename = "data1.dat";
      String filename2 = "data2.dat";
      String filename3 = "data3.dat";
      try{
        Maze f;
        f = new Maze(filename);//true animates the maze.

        f.setAnimate(true);
        int ans = f.solve();
        System.out.println(f);
        System.out.println(ans);

        Maze x;
        x = new Maze(filename2);//true animates the maze.

        x.setAnimate(true);
        int ans2 = x.solve();
        System.out.println(x);
        System.out.println(ans2);

        Maze y;
        y = new Maze(filename3);//true animates the maze.

        y.setAnimate(true);
        int ans3 = y.solve();
        System.out.println(y);
        System.out.println(ans3);

      }catch(FileNotFoundException e){
        System.out.println("Invalid filename: "+filename);
      }
    }
}
