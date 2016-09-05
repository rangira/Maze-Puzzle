package testString;

import java.io.*;
import java.util.*;

public class ReadMaze {
	private static String fileName;
	private Map<Integer, LinkedHashSet<Point>> teleportPoints;
	private Point start;
	private Point end;
	private boolean isMazeinvalid;
	private int rows;
	private int cols;

	public ReadMaze(String fileName) {
		this.fileName = fileName;
	}

	public char[][] maze() throws IOException {
		char[][] maze = null;
		File fl = new File(this.fileName);
		BufferedReader br = new BufferedReader(new FileReader(fl));
		String line;
		int index = 0;
		int size;
		while ((line = br.readLine()) != null) {
			StringBuilder sb = new StringBuilder();
			/*
			 * for (char ch : line.toCharArray()) sb.append(ch).append(',');
			 */
			char[] row = line.toCharArray();
			if (maze == null) {
				size = row.length;
				maze = new char[size][size];
			}
			maze[index++] = row;

			// printRow(maze[index++]);
		}
		
		validateMaze(maze);

		System.out.println("The value of index is " + index);
		// System.out.println("destination "+ maze[11][12]);
		// printMaze(maze);
		// return (new char[][]{{'a'},{'b'}});
		this.walkMaze(maze, index);
		return maze;
	}
	
	
	public void mazeRead (boolean includeDigits) throws IllegalArgumentException, IOException
	{
		Scanner scanner = null;
		String text = null;
		try {
			File fl = new File(this.fileName);
			scanner = new Scanner(fl);
			text = scanner.useDelimiter("\\A").next();
			
		} finally {
			if (scanner != null) {
				scanner.close();				
			}
		}
		
		
		String linePattern = includeDigits ? "[#.DS0-9]+" : "[#.DS]+"; 
		
		String[] parts = text.split("\\n", -1);
		
		if (!parts[parts.length - 1].isEmpty()) {
			throw new IllegalArgumentException("File should end with a LF character.");
		}
		
		int lineSize = -1;
		for (int i = 0, len = parts.length - 1; i < len; i++) {
			if (parts[i].isEmpty()) {
				throw new IllegalArgumentException("Line can not be empty.");
			}
			if (!parts[i].matches(linePattern)) {
				throw new IllegalArgumentException("Invalid line: " + parts[i]);
			}
			if (i == 0) {
				lineSize = parts[i].length();
			} else {
				if (parts[i].length() != lineSize) {
					throw new IllegalArgumentException("Invalid line size.");
				}
			}
		}
	}                                             
	
	

	public static void printMaze(char[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++)
				System.out.print(maze[i][j]);
			System.out.println();
		}

	}

	public void printRow(char[] row) {
		for (int i = 0; i < row.length; i++) {
			System.out.print(row[i] + ",");
		}
		System.out.println();
	}

	void setStart(int i, int j) {
		if (start == null)
			this.start = new Point(i, j);
		else {
			System.out.println("Maze has Multiple start points");
			isMazeinvalid = true;
		}
	}

	Point getStart() {
		return this.start;
	}

	void setEnd(int i, int j) {
		if (end == null)
			this.end = new Point(i, j);
		else {
			System.out.println("Maze has Multiple end points");
			isMazeinvalid = true;
		}
	}

	Point getEnd() {
		return end;
	}

	Map<Integer, LinkedHashSet<Point>> getTeleportPoints() {
		return teleportPoints;
	}

	void setTeleportPoints(int i, int j, char dig) {
		Point tp = new Point(i, j);
		tp.isTeleportPoint = true;
		int num = (int) dig - 48;
		Integer n = new Integer(num);

		if (teleportPoints == null) {
			teleportPoints = new HashMap<Integer, LinkedHashSet<Point>>();
			LinkedHashSet<Point> lh = new LinkedHashSet<Point>();
			lh.add(tp);
			teleportPoints.put(n, lh);
		} else {

			if (teleportPoints.containsKey(n)) {
				LinkedHashSet<Point> lh = teleportPoints.get(n);
				lh.add(tp);
				teleportPoints.put(n, lh);
			} else {
				LinkedHashSet<Point> lh = new LinkedHashSet<Point>();
				lh.add(tp);
				teleportPoints.put(n, lh);
			}
		}

	}
	
	
	void validateMaze (char maze [][])
	{
		System.out.println("validateMaze");
		String s = new String (maze[0]);
		System.out.println (s);
		boolean endsWithNewline = s.endsWith("\n");
		System.out.println (endsWithNewline);
		
		
		int i = 0, j = 0;
		for (i = 0; i < maze.length; i++)
			for (j = 0; j < maze[i].length; j++) 
			{
				if(String.valueOf(maze[i][j]).equals("\n"))
					System.out.println("newline");
				
				if(String.valueOf(maze[i][j]).matches("\n"))
					System.out.println("newline");
				
				if(maze[i][j] == '\n')
					System.out.println("newline char ");
				
			}
			
	}

	void walkMaze(char[][] maze, int index) {
		int i = 0, j = 0;
		for (i = 0; i < index; i++)
			for (j = 0; j < maze[i].length; j++) {
				if (maze[i][j] == 'S')
					setStart(i, j);
				if (maze[i][j] == 'D')
					setEnd(i, j);
				if (((int) maze[i][j]) >= 48 && ((int) maze[i][j]) <= 57)
					setTeleportPoints(i, j, maze[i][j]);
			}
		this.rows = i;
		this.cols = j;
		/*
		 * System.out.println("i = "+ i); System.out.println("j = "+ j);
		 */
	}

	int getRows() {
		return this.rows;
	}

	int getCols() {
		return this.cols;
	}

	void printTeleportPoints() {
		System.out.println(this.getTeleportPoints().toString());
	}

	public static void main(String args[]) {
		ReadMaze read = new ReadMaze("C:\\Users\\rangira\\Downloads\\p538_project1\\testdata\\task1.in.5");
		try {
			read.mazeRead(false);
			//printMaze(read.maze());
			// read.walkMaze(read.maze());
			/*System.out.println("start point : " + read.getStart().toString());
			System.out.println("end point : " + read.getEnd().toString());
			
			if(read.getTeleportPoints() != null)
			System.out.println("teleport points : "
					+ new PrettyPrintingMap<Integer, LinkedHashSet<Point>>(read
							.getTeleportPoints()));
			
			read.setTeleportPoints(4, 5, '1');
			read.setTeleportPoints(2, 8, '2');
			
			if(read.getTeleportPoints() != null)
			System.out.println("teleport points : "
					+ new PrettyPrintingMap<Integer, LinkedHashSet<Point>>(read
							.getTeleportPoints()));
			read.setTeleportPoints(3, 4, '3');
			
			if(read.getTeleportPoints() != null)
			System.out.println("teleport points : "
					+ new PrettyPrintingMap<Integer, LinkedHashSet<Point>>(read
							.getTeleportPoints()));*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
