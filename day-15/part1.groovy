
def p = ~"Sensor at x=(-?[0-9]+), y=(-?[0-9]+): closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)";

// use 10 for the sample file
long row = 2000000;

Set cannot = new HashSet();
Set beacons = new HashSet();

new File("input.txt").eachLine { line ->
	def m = line =~ p;
	m.find();

	long x1 = Long.valueOf(m.group(1));
	long y1 = Long.valueOf(m.group(2));
	
	long x2 = Long.valueOf(m.group(3));
	long y2 = Long.valueOf(m.group(4));
	
	long distance = Math.abs(x1-x2)+Math.abs(y1-y2);
	
	
	
	long min = y1 - (distance);
	long max = y1 + (distance);
	
	//
	
	if (min <= row && row <= max) {
		//System.out.println(line);
		//System.out.println("\t"+distance+"="+min+"->"+max);
		long height = Math.abs(y1-row);
		long width = distance - height;
		//System.out.println("\t"+height+","+width);
		
		for (int i = 0 ; i <= width ; ++i) {
			cannot.add(x1-i);
			cannot.add(x1+i);
		}
	}
	
	if (y2 == row) beacons.add(x2);
}

cannot.removeAll(beacons);

//System.out.println(cannot);
System.out.println(cannot.size());
