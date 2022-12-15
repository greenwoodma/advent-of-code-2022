
def p = ~"Sensor at x=(-?[0-9]+), y=(-?[0-9]+): closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)";

// use 10 for the sample file
//long row = 2000000;




String[] input = new File("input.txt");

int maxDim = 4000000;
long frequency = 4000000; 

BitSet cannot = new BitSet(maxDim+1);

for (int row = maxDim ; row >= 0 ; --row) {

	cannot.clear();

	System.out.println(row)

	for (String line : input) {
		def m = line =~ p;
		m.find();

		int x1 = Long.valueOf(m.group(1));
		int y1 = Long.valueOf(m.group(2));
		
		int x2 = Long.valueOf(m.group(3));
		int y2 = Long.valueOf(m.group(4));
		
		long distance = Math.abs(x1-x2)+Math.abs(y1-y2);
		
		long min = y1 - (distance);
		long max = y1 + (distance);
		
		if (min <= row && row <= max) {
			//System.out.println(line);
			//System.out.println("\t"+distance+"="+min+"->"+max);
			long height = Math.abs(y1-row);
			long width = distance - height;
			//System.out.println("\t"+height+","+width);
			
			int left = Math.max(0,x1-width);
			int right = Math.min(maxDim, x1+width);
			
			cannot.set(left,right+1);
			
			/*for (int i = 0 ; i <= width ; ++i) {
			
			
				if (x1-i >= 0 && x1-i <= maxDim) cannot.set(x1-i);
				if (x1+i >= 0 && x1+i <= maxDim) cannot.set(x1+i);
			}*/
		}
		
	}
	
	//System.out.println("\n"+row +": "+cannot.size());
	//System.out.println(cannot);
	
	if (cannot.cardinality() != maxDim+1) {
		//long sum = cannot.sum();
		//System.out.println(summation);
		//System.out.println(sum);
		//long column = summation-sum;
		

		int column = cannot.nextClearBit(0);
		System.out.println("\n"+column+","+row);
		long answer = (frequency * column)+row;
		
		System.out.println("\n"+answer);
		
		System.exit(0);
	}
	
}

