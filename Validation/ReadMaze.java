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

		System.out.println("The value of index is " + index);
		// System.out.println("destination "+ maze[11][12]);
		// printMaze(maze);
		// return (new char[][]{{'a'},{'b'}});
		this.walkMaze(maze, index);
		return maze;
	}
	
	
	
	public void mazeRead1 () throws IOException
	{
		char[][] maze = null;
		File fl = new File(this.fileName);
		Scanner sc = new Scanner(new FileInputStream(fl));
		//String lineSeperator = "\n";
		//sc.useDelimiter(lineSeperator);
		//char LF = 0xA; 
		while (sc.hasNext())
		{
		   	String line =sc.next();
		   	char [] a= line.toCharArray();
		   	System.out.println(line.contains("\n"));
		   	//System.out.println(line.endsWith("\n"));
		   	for (int i=0;i<a.length;i++){
		   		String hex = String.format("%04x", (int) a[i]);
		   		if(a[i]== '\n')
		   			System.out.println("found newline");
		   		System.out.print(hex + " ");
		   	}
		   	System.out.println();
		   	
		}
	}  
	
	
	public void readValidMaze () throws IOException
	{
		
		Scanner scanner = null;
		String text = null;
		boolean foundS = false;
		boolean foundD = false; 
		int inValidmaze = 0;
		
		try {
			File fl = new File(this.fileName);
			scanner = new Scanner(fl);
			text = scanner.useDelimiter("\\A").next();
			
		} finally {
			if (scanner != null) {
				scanner.close();				
			}
		}
		
		String[] parts = text.split("\\r\\n", -1);
		
		int lineSize = -1;
		for (int i = 0, len = parts.length - 1; i < len; i++) {
			
			if (i == 0) {
				lineSize = parts[i].length();
				break;
			}
		}
		
		System.out.println("columns"+ lineSize);
		System.out.println("rows"+(parts.length - 1));
		
	}
	
	
	public char[][] readValidMazeArrayList () throws IOException
	{
		ArrayList <String> lst = new ArrayList<String>();
		
		File fl = new File(this.fileName);
		BufferedReader br = new BufferedReader(new FileReader(fl));
		String line;
		int index = 0;
		int size;
		
		while ((line = br.readLine()) != null) {
			lst.add(line);
		}
		
		System.out.println("columns"+ lst.get(0).length());
		System.out.println("rows"+lst.size());
		
		char[][] maze = new char [lst.size()][lst.get(0).length()];
		
		for(int i=0;i<lst.size();i++)
			maze[i] = lst.get(i).toCharArray();
		
		this.walkMaze_(maze);
		return maze;
	}
	
	
	
	public void mazeRead (boolean includeDigits) throws IllegalArgumentException, IOException
	{
		Scanner scanner = null;
		String text = null;
		boolean foundS = false;
		boolean foundD = false; 
		int inValidmaze = 0;
		
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
		
		//String[] parts = text.split("\\r\\n", -1);
		String[] parts = text.split("\\n", -1);
		
		if (!parts[parts.length - 1].isEmpty()) {
			//throw new IllegalArgumentException("File should end with a LF character.");
			inValidmaze =1;
		}
		
		int lineSize = -1;
		for (int i = 0, len = parts.length - 1; i < len; i++) {
			if (parts[i].isEmpty()) {
				//throw new IllegalArgumentException("Line can not be empty.");
				inValidmaze =1;
			}
			if (!parts[i].matches(linePattern)) {
				//throw new IllegalArgumentException("Invalid line: " + parts[i]);
				inValidmaze =1;
			}
			
			if( foundS == true && parts[i].indexOf('S') >= 0)
			{
				//throw new IllegalArgumentException("Multiple start points found");
				inValidmaze =1;
			}
				
			if(foundS == false && parts[i].indexOf('S') >= 0 )
				foundS = true;
			
			if( foundD == true && parts[i].indexOf('D') >= 0)
			{
				//throw new IllegalArgumentException("Multiple end points found");
				inValidmaze =1;
			}
			
			if( foundD == false && parts[i].indexOf('D') >= 0)
				foundD = true;
			
			if (i == 0) {
				lineSize = parts[i].length();
			} else {
				if (parts[i].length() != lineSize) {
					//throw new IllegalArgumentException("Invalid line size.");
					inValidmaze =1;
				}
			}
		}
		
		if( inValidmaze ==1 || foundS == false || foundD == false)
			System.out.println("NO");
		else
		{
			System.out.println("YES");
			System.out.println("columns"+ lineSize);
			System.out.println("rows"+(parts.length - 1));
		}
	
	}                                             
	
	


	

	public static void printMaze(char[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++)
				System.out.print(maze[i][j]);
			System.out.println();
		}

	}
	
	
	// # = 35
	// S = 83
	// D = 68
	// . = 46
	public static void printMazeAsciiChars(char[][] maze) {
		
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++)
			{
				if(maze[i][j] == ' ')
					System.out.print(8888 + " ");	
				else if(maze[i][j] == '\n')
					System.out.print(9999 + " ");
				else
				System.out.print((int)maze[i][j] + " ");
			}
			System.out.println();
		}
	}
	

	public void printRow(char[] row) {
		for (int i = 0; i < row.length; i++) {
			System.out.print(row[i] + ",");
		}
		if(row[row.length-1]=='\n')
			System.out.print(true);
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
	
	
	void walkMaze_ (char[][] maze) {
		int i = 0, j = 0;
		for (i = 0; i < maze.length; i++)
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
		//ReadMaze read = new ReadMaze(args[0]);
		//ReadMaze read = new ReadMaze("C:\\Users\\rangira\\Downloads\\p538_project1\\testdata\\task1.in.1");
		ReadMaze read = new ReadMaze("C:\\Users\\rangira\\Downloads\\task1.in.1");
		try {
			read.mazeRead(false);
			printMaze(read.readValidMazeArrayList());
			/*printMaze(read.maze());
			
			//printing the ascii values of the characters
			//printMazeAsciiChars(read.maze());
			// read.walkMaze(read.maze());*/
			System.out.println("start point : " + read.getStart().toString());
			System.out.println("end point : " + read.getEnd().toString());
			if(read.getTeleportPoints() != null)
			System.out.println("teleport points : "	+ new PrettyPrintingMap<Integer, LinkedHashSet<Point>>(read
							.getTeleportPoints()));
			read.setTeleportPoints(4, 5, '1');
			
			read.setTeleportPoints(2, 8, '2');
			
			if(read.getTeleportPoints() != null)
			System.out.println("teleport points : "+ new PrettyPrintingMap<Integer, LinkedHashSet<Point>>(read
							.getTeleportPoints()));
			read.setTeleportPoints(3, 4, '3');
			
			if(read.getTeleportPoints() != null)
			System.out.println("teleport points : "	+ new PrettyPrintingMap<Integer, LinkedHashSet<Point>>(read
							.getTeleportPoints()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
