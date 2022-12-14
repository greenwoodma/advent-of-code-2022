// 2935 too high 
// That's not the right answer; your answer is too high. Curiously, it's the right answer for someone else; you might be logged in to the wrong account or just unlucky. 

lines = (new File("input.txt")).collect { it.toCharArray().collect { c -> (c as int) - 48 } }

height = lines.size()
width = lines[0].size();

larger = new int[height*5][width*5]

System.out.println(larger.size())
System.out.println(larger[0].size())

/*for (int y = 0 ; y < 1 ; ++y) {
	for (int x = 0 ; x < width ; ++x) {
		for (m = 0 ; m < 5 ; ++m) {
			int value = lines[y][x] + m;
			if (value > 9) value = 1;
			
			System.out.println(x+","+y+" "+m +" --> " + (x+(m*height))+","+(y+(m*width)));
			
			larger[y+(m*width)][x+(m*height)] = value
		}
	}
}*/

start = new Tuple2(0,0);

Comparator comparator = new Comparator() {
	public int compare(v1, v2) {
		return v1.value.compareTo(v2.value);
	}
	
};

List vertices = new ArrayList();

for (int y = 0 ; y < larger.size() ; ++y) {
	for (int x = 0 ; x < larger[0].size() ; ++x) {
		xadd = (int)(x/width)
		yadd = (int)(y/height);
			
		xorig = x % width;
		yorig = y % height;
		
		int value = lines[yorig][xorig]+xadd+yadd
		
		if (value > 9) value = value % 9
		
		larger[y][x]=value
		
		w = larger[0].size()-x;
		h = larger.size()-y;
		
		//vertices.add(new Vertex(new Tuple2(x,y), 500+(int)Math.sqrt(w*w + h*h)))
		vertices.add(new Vertex(new Tuple2(x,y)));//, 500+(int)Math.sqrt(w*w + h*h)))
	}
	
}

//vertices.put(new Tuple2(0,0),0);

lines = larger;

//System.out.println(lines[-1]);

height = lines.size()
width = lines[0].size();

end = new Tuple2(width-1, height-1)

System.out.println(vertices);

while (vertices.size() > 0) {

	System.out.println(vertices.size());

	Collections.sort(vertices,comparator);

	Vertex u = vertices.remove(0);

	
	if (u.pos.equals(end)) {
		System.out.println(u.value);
		System.exit(0);
	}
	
	Set neighbours = getNeighbours(u.pos);
	neighbours.retainAll(vertices)
	
	
	
	for (Vertex n : neighbours) {
		n = vertices.get(vertices.indexOf(n));
		value = u.value + lines[n.pos.second][n.pos.first];
		
		if (value < n.value) {
			n.value = value;
		}
	}
}
/*

 1  function Dijkstra(Graph, source):
 2
 3      create vertex set Q
 4
 5      for each vertex v in Graph:            
 6          dist[v] ← INFINITY                 
 7          prev[v] ← UNDEFINED                
 8          add v to Q                     
 9      dist[source] ← 0                       
10     
11      while Q is not empty:
12          u ← vertex in Q with min dist[u]   
13                                             
14          remove u from Q
15         
16          for each neighbor v of u still in Q:
17              alt ← dist[u] + length(u, v)
18              if alt < dist[v]:              
19                  dist[v] ← alt
20                  prev[v] ← u
21
22      return dist[], prev[]
If we are only interested in a shortest path between vertices source and target, we can terminate the search after line 15 if u = target. Now we can read the shortest path from source to target by reverse iteration:

*/


//.sort{a,b -> a.value <=> b.value}

public Set getNeighbours(Tuple2 pos) {
//System.out.println(pos)
	Set neighbours = new HashSet()
	/*for (int x = pos.first-1 ; x <= pos.first+1 ; ++x) {
		if (x >= 0 && x < width) {
			for (int y = pos.second-1 ; y <= pos.second+1 ; ++y) {
				if (y >= 0 && y < height) {
					neighbours.add(new Tuple2(x,y));
				}		
			}
		}
	}
	
	neighbours.remove(pos)*/
	
	if (pos.first < width-1) neighbours.add(new Vertex(new Tuple2(pos.first+1,pos.second)));
	if (pos.first > 0) neighbours.add(new Vertex(new Tuple2(pos.first-1,pos.second)));
	if (pos.second < height-1) neighbours.add(new Vertex(new Tuple2(pos.first,pos.second+1)));
	if (pos.second > 0 ) neighbours.add(new Vertex(new Tuple2(pos.first,pos.second-1)));
	
	return neighbours
}

public class Vertex {
	Tuple2 pos;
	Integer value
	static Tuple2 start = new Tuple2(0,0);
	
	public Vertex(Tuple2 pos) {
		this.pos = pos;
		
		if (pos.equals(start)) {
			value = 0;
		} else {
			value = Integer.MAX_VALUE;
		}
	}
	
	public Vertex(Tuple2 pos, Integer value) {
		this.pos = pos;
		
		if (pos.equals(start)) {
			this.value = 0;
		} else {
			this.value = value;
		}
	}
	
	public int hashCode() {
		return pos.hashCode();
	}
	
	public boolean equals(v) {
		//if (v instanceof Tuple2) return pos.equals(v)
		return pos.equals(v.pos)
	}
	
	public String toString() {
		return pos.toString()+":"+value
	}
}

