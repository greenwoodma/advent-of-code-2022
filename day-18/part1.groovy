
blocks = new HashSet();

long area = 0;

new File("sample.txt").eachLine { line ->
	String[] pos = line.split(",");
	
	long x = Long.valueOf(pos[0]);
	long y = Long.valueOf(pos[1]);
	long z = Long.valueOf(pos[2]);
	
	blocks.add(line);
	
	area = area + 6;
	
	if (check(x-1,y,z)) area = area - 2;
	if (check(x+1,y,z)) area = area - 2;
	if (check(x,y-1,z)) area = area - 2;
	if (check(x,y+1,z)) area = area - 2;
	if (check(x,y,z-1)) area = area - 2;
	if (check(x,y,z+1)) area = area - 2;
	
}

System.out.println(area);

public boolean check(long x, long y, long z) {
	String pos = x+","+y+","+z;
	
	return blocks.contains(pos);
}
