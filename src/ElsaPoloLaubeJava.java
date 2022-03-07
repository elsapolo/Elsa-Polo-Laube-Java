import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Version: Java 17
// Usually I would have these classes in different files, but this was not
// due to submission format

// Run main class in ElsaPoloLaubeJava class with file path as argument
public class ElsaPoloLaubeJava {
  public static void main(String[] args) {
    assert args[0] != null;
    Walk walk = FileParser.parseFile(args[0]);
    walk.walkAll();
    System.out.println("Max distance Wally will be from origin is: " + walk.getMaxDistance());
  }

}

class Walk {

  private Point currentPos;
  private Direction facing;
  private double maxDistance = 0;
  private final List<Point> obstacles;
  private final List<Moves> moves;


  Walk(Point starting, Direction facing, List<Point> obstacles,
      List<Moves> moves) {
    this.currentPos = starting;
    this.facing = facing;
    this.obstacles = obstacles;
    this.moves = moves;
  }

  public double getMaxDistance() {
    return maxDistance;
  }

  public void walkAll() {
    for (Moves move : moves) {
      move(move);
    }
  }

  public void move(Moves m) {
    switch (m) {
      case M -> moveStraight();
      case L -> {facing = Direction.turnLeft(facing);}
      case R -> {
        for (int i = 0; i < 3; i++) {
          facing = Direction.turnLeft(facing);
        }
      }
    };
  }

  private void moveStraight() {
    Point newPoint = switch (facing) {
      case N -> new Point(currentPos.getX(), currentPos.getY() + 1);
      case W -> new Point(currentPos.getX() - 1, currentPos.getY());
      case S -> new Point(currentPos.getX(), currentPos.getY() - 1);
      case E -> new Point(currentPos.getX() + 1, currentPos.getY());
    };

    if (!obstacles.contains(newPoint)) {
      currentPos = newPoint;
      maxDistance = Math.max(currentPos.distanceFromO(), maxDistance);
    }
  }
}

class FileParser {

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

  private static Point parsePoint(String str) {
    String[] xy = str.split(" ");
    return new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
  }

  private static List<Moves> parseMove(String str) {
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


}

class Point {

  private final int x;
  private final int y;

  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public double distanceFromO() {
    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    return (double) Math.round(distance * 10) / 10;
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }

    if (!(o instanceof Point c)) {
      return false;
    }

    return Double.compare(x, c.x) == 0 && Double.compare(y, c.y) == 0;
  }
}

enum Moves{
  M, L, R
}

enum Direction {
  N, E, S, W;

  public static Direction turnLeft(Direction d) {
    return switch (d) {
      case N -> W;
      case W -> S;
      case S -> E;
      case E -> N;
    };
  }

}