
heatmap = [];

// 505 too high

// 504 (a guess in case I was off by 1) is also too high

start = null
end = null;
minPath = Integer.MAX_VALUE;

List vertices = new ArrayList();

List starts = [];

int shortest = Integer.MAX_VALUE;

new File("input.txt").eachLine { line ->
	String[] row = line.toCharArray() as String[];

	if (line.indexOf("S") != -1) {
		start = new Tuple2(line.indexOf("S"),heatmap.size());
		row[start.first] = "a";
	}
		
	if (line.indexOf("E") != -1) {
		end = new Tuple2(line.indexOf("E"), heatmap.size());
		row[end.first] = "z";
	}
		
	heatmap.add(row);
}

Vertex.start = start;

for (int y = 0 ; y < heatmap.size() ; ++y) {
	for (int x = 0 ; x < heatmap[y].size() ; ++x) {
		vertices.add(new Vertex(new Tuple2(x,y)));
	}
}

//System.out.println(start);
//System.out.println(end);
//System.out.println(vertices);

Comparator comparator = new Comparator() {
	public int compare(v1, v2) {
		return v1.value.compareTo(v2.value);
	}
	
};

while (vertices.size() > 0) {

	//System.out.println(vertices.size());

	Collections.sort(vertices,comparator);

	Vertex u = vertices.remove(0);

	if (u.pos.equals(end)) {
		//System.out.println(end);
		System.out.println(u.value);
		//System.out.println(vertices);
		
		System.exit(0);
	}
	
	Set neighbours = getNeighbours(u.pos);
	neighbours.retainAll(vertices)

	for (Vertex n : neighbours) {

		n = vertices.get(vertices.indexOf(n));
		value = u.value + 1;
		
		if (value < n.value) {
			n.value = value;
		}
	}
	
	
}

public Set getNeighbours(Tuple2 pos) {
//System.out.println(pos)
	Set neighbours = new HashSet()
	
	int x = pos.first;
	int y = pos.second;
	char ch = heatmap[y][x].charAt(0);
	
	if (check(x-1,y,ch)) neighbours.add(new Vertex(new Tuple2(x-1,y)));
	if (check(x+1,y,ch)) neighbours.add(new Vertex(new Tuple2(x+1,y)));
	if (check(x,y-1,ch)) neighbours.add(new Vertex(new Tuple2(x,y-1)));
	if (check(x,y+1,ch)) neighbours.add(new Vertex(new Tuple2(x,y+1)));
	
	return neighbours
}

private boolean check(int x, int y, char height) {
	if (x < 0 || x >= heatmap[0].size()) return false;
	if (y < 0 || y >= heatmap.size()) return false;
	
	int diff = (int)heatmap[y][x].charAt(0) - (int)height;
	
	if (diff > 1) return false;
	
	return true;
}


public class Vertex {
	Tuple2 pos;
	Integer value
	public static Tuple2 start;
	
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
