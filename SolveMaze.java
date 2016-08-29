import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SolveMaze {
	static ReadMaze mazeObj;
	static char[][] maze;
	Point start;

	void solve(char[][] maze) {
		this.start = this.findStart();
		/*
		 * System.out.println(start.toString());
		 * System.out.println("end point : " + mazeObj.getEnd().toString());
		 * System.out.println("teleport points : "+ new
		 * PrettyPrintingMap<Integer,
		 * LinkedHashSet<Point>>(mazeObj.getTeleportPoints()));
		 * mazeObj.setTeleportPoints(5, 12, '9');
		 * System.out.println("teleport points : "+ new
		 * PrettyPrintingMap<Integer,
		 * LinkedHashSet<Point>>(mazeObj.getTeleportPoints()));
		 */
		LinkedHashSet<Point> pointQueue = new LinkedHashSet<Point>();
		LinkedList<Point> pointList = new LinkedList<Point>();
		this.start.isVisited = true;
		pointQueue.add(this.start);

		while (pointQueue.isEmpty() == false) {
			// Printing the queue
			System.out.println(pointQueue.toString());
			Iterator<Point> it = pointQueue.iterator();
			Point p = null;
			if(it.hasNext())
			{
				p = it.next();
				it.remove();
			}
			
			if (p.equals(this.findEnd()) == false) {
				// examine the up neighbour
				Point dn = new Point(p.x + 1, p.y);
				if (isValid(dn) == true) {
					// if it is a valid point
					// first check if teleport point
					dn.isVisited = true;
					dn.setPredecessor(p, "D");
					isTeleportPoint(dn);
					if (dn.isTeleportPoint == true) {
						// search the other point and teleport there
						Point tp = teleportPair(dn);
						tp.isVisited = true;
						tp.setPredecessor(dn, "T");
						pointQueue.add(tp);

					} else {
						pointQueue.add(dn);
					}
				}

				// examine right neighbour
				Point rn = new Point(p.x, p.y + 1);
				if (isValid(rn) == true) {
					// if it is a valid point
					// first check if teleport point
					rn.isVisited = true;
					rn.setPredecessor(p, "R");
					isTeleportPoint(rn);
					if (rn.isTeleportPoint == true) {
						// search the other point and teleport there
						Point tp = teleportPair(rn);
						tp.isVisited = true;
						tp.setPredecessor(rn, "T");
						pointQueue.add(tp);

					} else {
						pointQueue.add(rn);
					}
				}

				// examine the up neighbour
				Point un = new Point(p.x - 1, p.y);
				if (isValid(un) == true) {
					// if it is a valid point
					// first check if teleport point
					un.isVisited = true;
					un.setPredecessor(p, "U");
					isTeleportPoint(un);
					if (un.isTeleportPoint == true) {
						// search the other point and teleport there
						Point tp = teleportPair(un);
						tp.isVisited = true;
						tp.setPredecessor(un, "T");
						pointQueue.add(tp);

					} else {
						pointQueue.add(un);
					}
				}

				// examine left neighbour
				Point ln = new Point(p.x, p.y - 1);
				if (isValid(ln) == true) {
					// if it is a valid point
					// first check if teleport point
					ln.isVisited = true;
					ln.setPredecessor(p, "L");
					isTeleportPoint(ln);
					if (ln.isTeleportPoint == true) {
						// search the other point and teleport there
						Point tp = teleportPair(ln);
						tp.isVisited = true;
						tp.setPredecessor(ln, "T");
						pointQueue.add(tp);

					} else {
						pointQueue.add(ln);
					}
				}

			}// findEnd() if block
			else {
				break;
			}

		}// empty queue check block
	}

	Point findStart() {
		return mazeObj.getStart();

	}

	
	void isTeleportPoint(Point p)
	{
		if( (int) this.maze[p.x][p.y] >= 48 && (int) this.maze[p.x][p.y] <= 57)
			p.isTeleportPoint =true;
	}
	
	
	Point findEnd() {
		return mazeObj.getEnd();
	}

	boolean isValid(Point p) {
		if (p.x >= 0 && p.x < mazeObj.getRows() && p.y >= 0
				&& p.y < mazeObj.getCols() && maze[p.x][p.y] != '#'
				&& p.isVisited == false)
			return true;
		else
			return false;
	}

	Point teleportPair(Point p) {
		Point teleportTo = null;
		int num = (int) this.maze[p.x][p.y] - 48;
		Integer ind = new Integer(num);
		LinkedHashSet<Point> lh = mazeObj.getTeleportPoints().get(ind);
		Iterator itr = lh.iterator();

		while (itr.hasNext()) {
			Point pt = (Point) itr.next();
			if (pt.equals(p) == false)
				teleportTo = pt;

		}

		return teleportTo;
	}

	public static void main(String args[]) {
		mazeObj = new ReadMaze(args[0]);
		SolveMaze solveObj = new SolveMaze();
		try {
			// mazeObj.printMaze(mazeObj.maze());
			maze = mazeObj.maze();
			solveObj.solve(maze);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
