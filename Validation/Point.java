public class Point {
	int x;
	int y;
	boolean isVisited;
	boolean isTeleportPoint;
	Point predecessor;
	String predStr;

	public Point() {
		this.x = -1;
		this.y = -1;
		this.isVisited = false;
		this.isTeleportPoint = false;
		this.predecessor = null;
		this.predStr = null;

	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		this.isVisited = false;
		this.isTeleportPoint = false;
		this.predecessor = null;
		this.predStr = null;
	}

	public void setPredecessor(Point pred, String str) {
		this.predecessor = pred;
		this.predStr = str;
	}

	public String toString() {
		return "x = " + this.x + ", y = " + this.y + " , isTeleportPoint = "
				+ this.isTeleportPoint + " , isVisited = " + this.isVisited
				+ " , predecessor = " + this.predecessor +" , predMovement = "+this.predStr;
	}

	@Override
	public boolean equals(Object o) {
		if ((o instanceof Point)
				&& (((Point) o).x == this.x && ((Point) o).y == this.y))
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		int val = (this.y + ((this.x + 1) / 2));
		return this.x + (val * val);
	}

}
