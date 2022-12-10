cycle = 0;
X = 1;
total = 0;

new File("input.txt").eachLine { line ->
	String[] instructions = line.split("\\s+");
	
	if (instructions[0] == "noop") {
		nextCycle(line);
	} else {
		int value = Integer.valueOf(instructions[1]);
		nextCycle(line);
		nextCycle(line);
		X += value;
	}	
}

System.out.println(total);

public void nextCycle(String line) {
	++cycle;
	
	if ((cycle+20) % 40 == 0) {
		//System.out.println("");
		//System.out.println(line);
		//System.out.println(cycle);
		//System.out.println(X);
		
		total += cycle * X;
	}
}



