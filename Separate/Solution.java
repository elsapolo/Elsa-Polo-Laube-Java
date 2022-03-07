import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

  public static Point parsePoint(String str) {
    String[] xy = str.split(" ");
    return new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
  }

  public static List<Moves> parseMove(String str) {
    String[] move = str.split(" ");
    List<Moves> moves = new ArrayList<>();
    switch (move[0]) {
      case "L" -> moves.add(Moves.L);
      case "R" -> moves.add(Moves.R);
      case "M" -> {
        for (int i = 0; i < Integer.parseInt(move[1]); i++) {
          moves.add(Moves.M);
        }
      }
      default -> throw new RuntimeException("wrong move");
    }
    return moves;
  }

  public static Walk parseFile(String filename) {
    Point initialP = null;
    Direction initialD = null;
    List<Point> obstacles = new ArrayList<>();
    List<Moves> moves = new ArrayList<>();
    ;
    try {
      File myObj = new File(filename);
      Scanner myReader = new Scanner(myObj);

      String initial = myReader.nextLine();
      String[] xyd = initial.split(" ");
      initialP = new Point(Integer.parseInt(xyd[0]),
          Integer.parseInt(xyd[1]));
      initialD = switch (xyd[2]) {
        case "N" -> Direction.N;
        case "W" -> Direction.W;
        case "S" -> Direction.S;
        case "E" -> Direction.E;
        default -> throw new RuntimeException("wrong direction");
      };

      String lines = myReader.nextLine();
      String[] obNmovN = lines.split(" ");
      obstacles = new ArrayList<>();

      for (int i = 0; i < Integer.parseInt(obNmovN[0]); i++) {
        obstacles.add(parsePoint(myReader.nextLine()));
      }

      for (int i = 0; i < Integer.parseInt(obNmovN[1]); i++) {
        moves.addAll(parseMove(myReader.nextLine()));
      }

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        System.out.println(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    return new Walk(initialP, initialD, obstacles, moves);
  }

  public static void main(String[] args) {
    Walk walk = parseFile("src/file.txt");
    walk.walkAll();
    System.out.println(walk.getMaxDistance());

  }
}
