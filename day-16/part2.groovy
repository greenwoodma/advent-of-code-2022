def p = ~"([A-Z]{2})|([0-9]+)"

closed = new HashMap();

cache = new HashMap();

neighbours = new HashMap();

results = new HashMap();

new File("input.txt").eachLine { line ->
	def m = line =~ p;
	def data = m.findAll()*.first();
	
	if (!data[1].equals("0"))
		closed.put(data[0], Long.valueOf(data[1]));

	Set connected = neighbours.getOrDefault(data[0],new HashSet());
	connected.addAll(data[2,-1]);
	neighbours.put(data[0],connected);
	
	for (String valve : data[2,-1]) {
		connected = neighbours.getOrDefault(valve,new HashSet());
		connected.add(data[0]);
		neighbours.put(valve,connected);
	}
}

public int findBestPath(String start, List remaining, int stepsLeft, Set visited, int partial) {
	
	int max = 0;
	
	for (int i = 0 ; i < remaining.size() ; ++i) {
		List others = new ArrayList(remaining);
		String next = others.remove(i);
		
		Set seen = new HashSet(visited);
		seen.add(next);
		
		int flowing = stepsLeft - tunnelLength(start,next) - 1;
		
		if (flowing > 0) {
			int score = flowing * closed.get(next);
			
			if (!results.containsKey(seen))
				results.put(seen,partial+score);
			else
				results.put(seen, Math.max(results.get(seen),partial+score));
			
			score += findBestPath(next,others,flowing,seen,partial+score);
			
			max = Math.max(max,score);
		}
	}
	
	return max;
}


List allClosed = new ArrayList(closed.keySet());

System.out.println(findBestPath("AA", allClosed, 26, new HashSet(), 0));



List all = new ArrayList(results.keySet());

System.out.println(all.size());

int max = 0;

int numValves = closed.size();

for (int i = 0 ; i < all.size()-1 ; ++i) {
	//System.out.println(all.size()-i);
	Set first = all.get(i);
	int firstSize = first.size();
	int score1 = results.get(first);
	for (int j = 1+1 ; j < all.size() ; ++j) {
		Set second = all.get(j);
		
		if (firstSize +second.size() <= numValves && first.disjoint(second)) {
			int score2 = results.get(second);
			
			max = Math.max(max,score1+score2);
		}
	}
}

System.out.println(max);


public int tunnelLength(String start, String end) {

	String key = start+"|"+end;
	
	if (cache.containsKey(key)) return cache.get(key);

	Comparator comparator = new Comparator() {
		public int compare(v1, v2) {
			return v1.value.compareTo(v2.value);
		}	
	};

	def vertices = [];
	
	for (String v : neighbours.keySet()) {
		vertices.add(new Vertex(v, v == start ? 0 : Integer.MAX_VALUE));
	}
	
	while (vertices.size() > 0) {

		//System.out.println(vertices.size());

		Collections.sort(vertices,comparator);

		Vertex u = vertices.remove(0);

		if (u.valve.equals(end)) {
			//System.out.println(end);
			//System.out.println(u.value);
			//System.out.println(vertices);
			cache.put(key,u.value);
			return u.value;
		}
		
		Set neighbours = getNeighbours(u.valve);
		neighbours.retainAll(vertices)

		for (Vertex n : neighbours) {

			n = vertices.get(vertices.indexOf(n));
			value = u.value + 1;
			
			if (value < n.value) {
				n.value = value;
			}
		}
		
		
	}
}

public Set getNeighbours(String v) {
	Set connected = new HashSet();
	
	for (String n : neighbours.get(v)) {
		connected.add(new Vertex(n, Integer.MAX_VALUE));
	}
	
	return connected;
}

public class Vertex {
	String valve;
	Integer value
	
	public Vertex(String valve, Integer value) {
		this.valve = valve;
		this.value = value;
	}
	
	public int hashCode() {
		return valve.hashCode();
	}
	
	public boolean equals(v) {
		//if (v instanceof Tuple2) return pos.equals(v)
		return valve.equals(v.valve)
	}
	
	public String toString() {
		return valve+":"+value
	}
}
