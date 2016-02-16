

import java.util.*;
import java.util.Map.Entry;


class ListNode{
	
	List<Integer> val;
	ListNode next;
	ListNode(List<Integer> x)
	{
		val = x;
	}
}


public class Maze {
	
	Integer width = 101;
	Integer length = 101;
	boolean[][] path;
	boolean[][] visit;
	boolean[][] grid;
	List<Integer> startPoint;
	List<Integer> start;
	List<Integer> targetPoint;
	List<List<Integer>> unBlocked;
	int[][][] tree;
	boolean success = true;
	
	ListNode head;
	
	int[][] search;
	int[][] goal;
	int counter = 0;
	TreeMap<Integer, List<List<Integer>>> open;
	TreeMap <Integer,List<List<Integer>>> close;
	

	public Maze(int width, int length)
	{
		this.width = width;
		this.length = length;
		initMaze(width,length);
	}
	public void initMaze(Integer width,Integer length){
		
		grid = new boolean[width][length];

		path = new boolean[width][length];
		visit = new boolean[width][length];

		Random rand = new Random();
		startPoint = new ArrayList<Integer>();	
		startPoint.add(rand.nextInt(length));
		startPoint.add(rand.nextInt(width));
		start = startPoint;
		grid[startPoint.get(0)][startPoint.get(1)] = true;
		visit[startPoint.get(0)][startPoint.get(1)] = true;
		dfsGenerate();
		targetPoint = new ArrayList<Integer>();
		targetPoint.add(rand.nextInt(width));
		targetPoint.add(rand.nextInt(length));
		while(!grid[targetPoint.get(0)][targetPoint.get(1)] || targetPoint.equals(startPoint))
		{
			//targetPoint = new ArrayList<Integer>();
			targetPoint.set(0,rand.nextInt(width));
			targetPoint.set(1,rand.nextInt(length));
		}
	}
	
	public void printMaze(){
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				if(grid[i][j] == false)
				{
					System.out.print("1");
				}
				else
				{
					System.out.print("0");
				}		
			}	
			System.out.println("");
		}
		
	}
	
	public void printPath(){
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				if(grid[i][j] == false)
				{
					System.out.print("1");
				}
				else if (path[i][j] == true)
				{
					System.out.print("2");
				}		
				else 
				{
					System.out.print("0");
				}
			}	
			System.out.println("");
		}
	}
	
	private void dfsGenerate(){
		Stack<List<Integer>> stack = new Stack<List<Integer>>();
		stack.push(startPoint);
		while(!stack.isEmpty())
		{
			List<Integer> curPoint = stack.pop();
			Random rand = new Random();
			visit[curPoint.get(0)][curPoint.get(1)] = true;
			System.out.println(curPoint);
			int x = curPoint.get(0);
			int y = curPoint.get(1);
			if(curPoint != startPoint)
			{
				if(rand.nextInt(100) > 70){
					grid[x][y] = false;
				}
				else{
					grid[x][y] = true;
				}
			}
			
			List<ArrayList<Integer>>  neiborPoints= new ArrayList<ArrayList<Integer>>();
			if(x + 1 < length)
			{
				if(!visit[x + 1][y])
				{
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(x + 1);
					tmp.add(y);
					neiborPoints.add(tmp);
				}
			}
			if(x - 1 >= 0)
			{
				if(!visit[x - 1][y])
				{
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(x - 1);
					tmp.add(y);
					neiborPoints.add(tmp);
				}
			}
			if(y + 1 < width)
			{
				if(!visit[x][y + 1])
				{
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(x);
					tmp.add(y + 1);
					neiborPoints.add(tmp);
				}
			}
			if(y - 1 > 0)
			{
				if(!visit[x][y - 1])
				{
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(x);
					tmp.add(y - 1);
					neiborPoints.add(tmp);
				}
			}
			
			
			while(neiborPoints.size() > 0)
			{
				int size = neiborPoints.size();
				int index = rand.nextInt(size);
				stack.push(neiborPoints.get(index));
				neiborPoints.remove(index);
				
				
			}
		}
		
		
		
		
	}
	
	private int h(List<Integer> s)
	{
		return Math.abs(targetPoint.get(1) - s.get(1)) + Math.abs(targetPoint.get(0) + s.get(1));
	}
	
	private void ComputePath()
	{
	
		Entry <Integer,List<List<Integer>>> first = open.firstEntry();
		List<List<Integer>> pList = first.getValue();
		List<Integer> s = pList.get(0);
		
		
		while(goal[targetPoint.get(0)][targetPoint.get(1)] >  first.getKey())
		{
			if(!close.containsKey(first.getKey()))
				close.put(first.getKey(),first.getValue());
			//open.remove(first.getKey());
			pList.remove(0);
			if(pList.size() == 0)
			{
				open.remove(first.getKey());
			}
			else
			{
				open.put(first.getKey(),pList);
			}
			
			int x = s.get(0); int y = s.get(1);
			List<ArrayList<Integer>>  tmp = new ArrayList<ArrayList<Integer>>();
			if(x - 1 >= 0)
			{
				if(grid[x - 1][y] == true)
				{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x - 1);
					t.add(y);
						tmp.add(t);
				}
			}
			
			if(x+ 1 <length )
			{
				if(grid[x + 1][y] == true)
				{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x + 1);
					t.add(y);
					
						tmp.add(t);
					
				}
			}
			if(y - 1 >= 0)
			{
				if(grid[x][y - 1] == true)
				{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x);
					t.add(y -1);
					tmp.add(t);
					
				}
			}
			if(y + 1 < width)
			{
				if(grid[x][y + 1] == true)
				{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x );
					t.add(y + 1);
					tmp.add(t);
				}
			}
			
			for(List<Integer> p:tmp)
			{
				if(search[p.get(0)][p.get(1)]  < counter)
				{
					goal[p.get(0)][p.get(1)] = Integer.MAX_VALUE;
					search[p.get(0)][p.get(1)] = counter;
				}
				int cost = visit[p.get(0)][p.get(1)]&& !grid[p.get(0)][p.get(1)] ? Integer.MAX_VALUE:1;
				if(goal[p.get(0)][p.get(1)] > goal[s.get(0)][s.get(1)] + cost)
				{
					goal[p.get(0)][p.get(1)] = goal[s.get(0)][s.get(1)] + cost;
					tree[p.get(0)][p.get(1)][0] = s.get(0);
					tree[p.get(0)][p.get(1)][1] = s.get(1);
					if(open.containsKey(goal[p.get(0)][p.get(1)] - cost))
					{
						List<List<Integer>> l = open.get(goal[p.get(0)][p.get(1)] - cost);
						if(l.contains(p))
						{
							l.remove(p);
						}
					}
					List<List<Integer>> l = new ArrayList<List<Integer>>();
					if(open.containsKey(goal[p.get(0)][p.get(1)]))
					{
						l = open.get(goal[p.get(0)][p.get(1)]);
					}
					l.add(p);
					open.put(goal[p.get(0)][p.get(1)],l);
//					if (open.containsKey(p))
//					{
//						open.remove(p);
//					}
//					open.put(p, goal[p.get(0)][p.get(1)] + h(p));
				}	
			}
			if(open.size() == 0)
				break;
			first = open.firstEntry();
			pList = first.getValue();
			s = pList.get(0);

		}
		
		
	}
	
	private void setCost(List<Integer> s)
	{
		int x = s.get(0);
		int y = s.get(1);
		if(x + 1 < length)
		{
			visit[x + 1][y] = true;
		}
		if(x - 1 >= 0)
		{
			visit[x - 1][y] = true;
		}
		if(y + 1 < width)
		{
			visit[x][y + 1] = true;
		}
		if(y - 1 > 0)
		{
			visit[x][y - 1] = true;
		}
	}
	
	
	public void repeatedFAstar()
	{
		head = new ListNode(startPoint);
		search = new int[width][length];
		goal = new int[width][length];
		visit = new boolean[width][length];
		counter = 0;
		tree = new int[width][length][2];
		setCost(startPoint);
		List<Integer> point;
		while(!startPoint.equals(targetPoint))
		{
			counter = counter + 1;
			search[startPoint.get(0)][startPoint.get(1)] = counter;
			goal[startPoint.get(0)][startPoint.get(1)] = 0;
			search[targetPoint.get(0)][targetPoint.get(1)] = counter;
			goal[targetPoint.get(0)][targetPoint.get(1)] = Integer.MAX_VALUE;
			open = new TreeMap<Integer,List<List<Integer>>>();
			close = new TreeMap();
			List<List<Integer>> L = new ArrayList<List<Integer>> ();
			L.add(startPoint);
			open.put(goal[startPoint.get(0)][startPoint.get(1)] + h(startPoint),L);
			ComputePath();

			if(open.isEmpty())
			{	
				System.out.println("Failed");
				success = false;
				break;
			}
			List<List<Integer>> route= new ArrayList<List<Integer>>();
			point = targetPoint;
			while(!(point.get(0) == startPoint.get(0) && point.get(1) == startPoint.get(1)))
			{
				route.add(point);
				List<Integer>tmp =new ArrayList<Integer>();
				tmp.add(tree[point.get(0)][point.get(1)][0]);
				tmp.add(tree[point.get(0)][point.get(1)][1]);
				point = tmp;
			}
			
			for(int i = route.size() - 1; i >= 0 ; i--)
			{
				List<Integer> p = route.get(i);
				if(grid[p.get(0)][p.get(1)] == false)
				{
					if(i + 1 == route.size())
						System.out.println("out of index");
					startPoint = route.get(i + 1);
					break;
				}
				startPoint = p;
				setCost(p);
			}
		}
		point = targetPoint;
		if(success)
		{
			while(!point.equals(start))
			{
				path[point.get(0)][point.get(1)] = true;
				List<Integer>tmp =new ArrayList<Integer>();
				tmp.add(tree[point.get(0)][point.get(1)][0]);
				tmp.add(tree[point.get(0)][point.get(1)][1]);
				point = tmp;
			}
			System.out.println("I reached the target");
		}
	}
	
	public void repeatedBAstar()
	{
		List<Integer> tmp = startPoint;
		startPoint = targetPoint;
		start = targetPoint;
		targetPoint = tmp;
		repeatedFAstar();
	}
	
}






















