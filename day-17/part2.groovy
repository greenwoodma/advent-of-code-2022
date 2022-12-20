
String jets = (new File("input.txt") as String[])[0];

System.out.println(jets.length());

List rocks = [];

// ####
rocks.add([[0,0],[1,0],[2,0],[3,0]]);

// .#.
// ###
// .#.
rocks.add([[1,0],[0,1],[1,1],[2,1],[1,2]]);

// ..#
// ..#
// ###
rocks.add([[0,0],[1,0],[2,0],[2,1],[2,2]]);

// #
// #
// #
// #
rocks.add([[0,0],[0,1],[0,2],[0,3]]);

// ##
// ##
rocks.add([[0,0],[1,0],[0,1],[1,1]]);

columns = []
for (int i = 0 ; i < 7 ; ++i) {
	columns.add(new BitSet());
}

Map modHeights = new HashMap();
List heights = [];

int height = 0;

int jet = 0;
int r = 0;
for (r = 0 ; r < Integer.MAX_VALUE ; ++r) {

	if (r != 0) heights.add(height);

	if (r % rocks.size() == 0) {
		int index = jet % jets.length();
		System.out.println((jet % jets.length())+": "+height + " " + r);
		if (modHeights.containsKey(index)) break;
		
		modHeights.put(index,[height,r]);


	}
	
	List rock = rocks[r % rocks.size()];
		
	int yDiff = height+3;
	int xDiff = 2;	
	
	while (true) {
	
		// be pushed by jet
		char d = jets.charAt(jet % jets.length());
		
		++jet;
		
		//System.out.println(d.toString()+": " +(rock[0][0]+xDiff)+","+(rock[0][1]+yDiff));
		
		boolean push = canMove(rock,xDiff+(d == '<' ? -1 : 1),yDiff);
		if (push) xDiff = xDiff + (d == '<' ? -1 : 1);
		
		// drop down
		boolean drop = canMove(rock,xDiff,yDiff-1);
		if (drop) {
			--yDiff
		}
		else {
			// if you can't drop down then record the block with
			// the current pos into the relevant columns, and
			// update the max height if appropriate
			
			for (List pos : rock) {
				columns[pos[0]+xDiff].set(pos[1]+yDiff);
			}
			
			height = Math.max(height, rock[-1][1]+yDiff+1);
			
			break;
		}	
	}
}


//System.out.println(heights.size());
//System.out.println(jet % jets.length());
//System.out.println(height);
//System.out.println(heights);

List start = modHeights[jet % jets.length()];
BigInteger numRocks = BigInteger.valueOf(heights.size() - start[1]);
BigInteger combinedHeight = BigInteger.valueOf(height - start[0]);

BigInteger massiveNumber = new BigInteger("1000000000000");

//System.out.println("initial layers are " + start[0] +" tall and is " + start[1] + " rocks");
//System.out.println(numRocks + " rocks are " + combinedHeight + " tall");

//int total = start[0] + (Math.floorDiv(2022-start[1],numRocks)*combinedHeight);

BigInteger rest = massiveNumber.minus(BigInteger.valueOf(start[1]));

BigInteger[] split = rest.divideAndRemainder(numRocks);

BigInteger total = BigInteger.valueOf(start[0]).add(split[0].multiply(combinedHeight));

//int extraRocks = (2022-start[1])%numRocks
//System.out.println(extraRocks);

int extraHeight = heights[start[1]+split[1].longValue()-1]-start[0];



System.out.println(total.add(BigInteger.valueOf(extraHeight)));


//System.out.println(height);

//System.out.println(columns);

public boolean canMove(List rock, int xDiff, int yDiff) {
	for (List pos : rock) {
		int x = pos[0]+xDiff;
		int y = pos[1]+yDiff;
		
		// is hitting a wall
		if (x < 0 || x >= columns.size()) return false;

		// is going through the floor		
		if (y < 0) return false;
		
		if (y < columns[x].size() && columns[x].get(y)) return false;
	}
	
	return true;
}

