
blocks = new HashSet();

long area = 0;

maxX = 0;
maxY = 0;
maxZ = 0;

new File("input.txt").eachLine { line ->
	String[] pos = line.split(",");
	
	long x = Long.valueOf(pos[0])+1;
	long y = Long.valueOf(pos[1])+1;
	long z = Long.valueOf(pos[2])+1;
	
	maxX = Math.max(x,maxX);
	maxY = Math.max(y,maxY);
	maxZ = Math.max(z,maxZ);
	
	blocks.add(x+","+y+","+z);
	
	area = area + 6;
	
	if (check(x-1,y,z)) area = area - 2;
	if (check(x+1,y,z)) area = area - 2;
	if (check(x,y-1,z)) area = area - 2;
	if (check(x,y+1,z)) area = area - 2;
	if (check(x,y,z-1)) area = area - 2;
	if (check(x,y,z+1)) area = area - 2;
	
}

System.out.println(area);

System.out.println(maxX+","+maxY+","+maxZ);

List queue = new ArrayList();
queue.add("0,0,0");

while (queue.size() > 0) {
	String next = queue.remove(0);
	
	blocks.add(next);
	
	List neighbours = getNeighbours(next);
	neighbours.removeAll(queue);
	
	queue.addAll(neighbours);	
}

System.out.println(blocks.size());

Set holes = new HashSet();

for (x = 0 ; x <= maxX+1 ; ++x) {
	for (int y = 0 ; y <= maxY+1 ; ++y) {
		for (int z = 0 ; z <= maxZ+1; ++z) {
			String pos = x+","+y+","+z;
			
			if (!blocks.contains(pos))
				holes.add(pos);
		}	
	}
}


blocks.clear();

long internal = 0;

for (String hole : holes) {
	String[] pos = hole.split(",");
	
	long x = Long.valueOf(pos[0]);
	long y = Long.valueOf(pos[1]);
	long z = Long.valueOf(pos[2]);
	
	blocks.add(x+","+y+","+z);
	
	internal = internal + 6;
	
	if (check(x-1,y,z)) internal = internal - 2;
	if (check(x+1,y,z)) internal = internal - 2;
	if (check(x,y-1,z)) internal = internal - 2;
	if (check(x,y+1,z)) internal = internal - 2;
	if (check(x,y,z-1)) internal = internal - 2;
	if (check(x,y,z+1)) internal = internal - 2;
}

System.out.println(internal);
System.out.println(area-internal);


public List getNeighbours(String next) {
	List queue = [];

	String[] pos = next.split(",");
	
	long x = Long.valueOf(pos[0]);
	long y = Long.valueOf(pos[1]);
	long z = Long.valueOf(pos[2]);
	
	if (withinAndClear(x-1,y,z)) queue.add((x-1)+","+y+","+z);
	if (withinAndClear(x+1,y,z)) queue.add((x+1)+","+y+","+z);
	if (withinAndClear(x,y-1,z)) queue.add(x+","+(y-1)+","+z);
	if (withinAndClear(x,y+1,z)) queue.add(x+","+(y+1)+","+z);
	if (withinAndClear(x,y,z-1)) queue.add(x+","+y+","+(z-1));
	if (withinAndClear(x,y,z+1)) queue.add(x+","+y+","+(z+1));

	return queue;
}

public boolean withinAndClear(long x, long y, long z) {
	if (x < 0 || x > maxX+1) return false;
	if (y < 0 || y > maxY+1) return false;
	if (z < 0 || z > maxZ+1) return false;
	
	String pos = x+","+y+","+z;
	
	return !blocks.contains(pos);	
}

public boolean check(long x, long y, long z) {
	String pos = x+","+y+","+z;
	
	return blocks.contains(pos);
}
