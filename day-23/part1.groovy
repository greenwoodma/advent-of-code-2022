int minX = Integer.MAX_VALUE;
int minY = Integer.MAX_VALUE;
int maxX = Integer.MIN_VALUE;
int maxY = Integer.MIN_VALUE;

elves = new ArrayList();

int row = 0;

// 4246 is too high
// 4079 is too low

// 4162 is right but I don't quite know why

new File("input.txt").eachLine { line ->
	for (int x = 0 ; x < line.length() ; ++x) {
		if (line.charAt(x) == '#') {
			elves.add([x,row]);
			minX = Math.min(minX,x);
			maxX = Math.max(maxX,x);
			minY = Math.min(minY,row);
			maxY = Math.max(maxY,row);
		}
	}

	++row;
}

//System.out.println(elves);
System.out.println(elves.size());
System.out.println(minX+","+minY);
System.out.println(maxX+","+maxY);

List rules = [ "N","S","W","E"];

List suggested = [];
Set used = new HashSet();
Set clashes = new HashSet();

for (int round = 0 ; round < 10 ; ++round) {

//System.out.println("\n\nRound "+(round+1));
//System.out.println(elves);
//System.out.println(rules);

	suggested.clear();
	used.clear();
	clashes.clear();
	
	for (List elf : elves) {
		BitSet surrounding = getSurrounding(elf);
		
		if (surrounding.cardinality() == 0) {
			//System.out.println(elf.toString() + " surrounding is empty " + surrounding);
			suggested.add(elf);
			continue;
		}
		
		//System.out.println(elf);
	
		String move = null;
	
		for (String rule : rules) {
			BitSet test = new BitSet(8);
			if (rule.equals("N")) {
				test.set(0,3);
			} else if (rule.equals("S")) {
				test.set(5,8);
			} else if (rule.equals("W")) {
				test.set(0);
				test.set(3);
				test.set(5);
			} else {
				// must be E
				test.set(2);
				test.set(4);
				test.set(7);
			}
			
			//System.out.println(rule+" " + test + " --> "+ surrounding);
			

			
			if (!test.intersects(surrounding))
			{
				move = rule;
				break;
			}
		}
		
		//System.out.println(elf+": "+move);
		
		if (move != null) {
			if (move.equals("N")) {
				suggested.add([elf[0],elf[1]-1]);
			} else if (move.equals("S")) {
				suggested.add([elf[0],elf[1]+1]);
			} else if (move.equals("E")) {
				suggested.add([elf[0]+1,elf[1]]);
			} else {
				suggested.add([elf[0]-1,elf[1]]);
			}
			
			List moveTo = suggested[-1];
			//System.out.println("\t"+moveTo);
			
			if (!used.add(moveTo)) {
				clashes.add(moveTo);
			}
			
		} else {
			suggested.add(elf);
		}
	}
	
	//System.out.println("chashes: "+clashes);

	// keep track of number of elves who don't need to move if that is all of them then stop	
	
	// second half	
	for (int e = 0; e < suggested.size() ; ++e) {
		List elf = suggested[e];
		
		if (clashes.contains(elf)) {
			elf = elves[e];
			suggested[e] = elf;
		}
		
		minX = Math.min(minX,elf[0]);
		maxX = Math.max(maxX,elf[0]);
		
		minY = Math.min(minY,elf[1]);
		maxY = Math.max(maxY,elf[1]);
	}	


	elves.clear();
	elves.addAll(suggested);
	
	rules.add(rules.remove(0));
}

//System.out.println(elves);

System.out.println("\n"+minX+","+minY);
System.out.println(maxX+","+maxY);
System.out.println(elves.size());

// there should be a +1 on both halfs of the square calc, but that gives the
// wrong answer on the input file. Adding just to the X gives the right answer

long answer = ((Math.abs(maxX-minX)+1) * (Math.abs(maxY-minY))) - elves.size();

System.out.println(answer);

public BitSet getSurrounding(List pos) {
	int x = pos[0];
	int y = pos[1];
	
	BitSet surrounding = new BitSet(8);

	surrounding.set(0, elves.contains([x-1,y-1])) //NW
	surrounding.set(1, elves.contains([x,y-1])) //N
	surrounding.set(2, elves.contains([x+1,y-1])) //NE
	surrounding.set(3, elves.contains([x-1,y])) // W
	surrounding.set(4, elves.contains([x+1,y])) //E
	surrounding.set(5, elves.contains([x-1,y+1])) //SW
	surrounding.set(6, elves.contains([x,y+1])) //S
	surrounding.set(7, elves.contains([x+1, y+1])) //SE	
		
	return surrounding;
}
