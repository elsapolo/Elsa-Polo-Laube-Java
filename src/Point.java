public class Point {

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
