import java.util.List;

public class Walk {

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

  public void walk() {
    for (Moves move : moves) {
      move(move);
    }
  }

  public void move(Moves m) {
    switch (m) {
      case M -> moveStraight();
      case L -> turnLeft();
      case R -> {
        for (int i = 0; i < 3; i++) {
          turnLeft();
        }
      }
    }

    if (currentPos.distanceFromO() > maxDistance) {
      maxDistance = currentPos.distanceFromO();
    }

  }

  private void moveStraight() {
    switch (facing) {
      case N -> moveTo(new Point(currentPos.getX(), currentPos.getY() + 1));
      case W -> moveTo(new Point(currentPos.getX() - 1, currentPos.getY()));
      case S -> moveTo(new Point(currentPos.getX(), currentPos.getY() - 1));
      case E -> moveTo(new Point(currentPos.getX() + 1, currentPos.getY()));
    }
  }

  private void moveTo(Point point) {
    if (!obstacles.contains(point)) {
      currentPos = point;
    }
  }

  private void turnLeft() {
    facing = switch (facing) {
      case N -> Direction.W;
      case W -> Direction.S;
      case S -> Direction.E;
      case E -> Direction.N;
    };
  }

}
