
boolean init = true;

List<Character>[] columns = null;

new File("input.txt").eachLine { line ->

	init = init && line.indexOf("[") != -1;
	
	if (init) {
		int column = 0;
		while (true) {
		
			if (columns == null) {
				// init the columns holder
				int num = (line.length()+1)/4;
				columns = new List<Character>[num];
				for (i = 0 ; i < num ; ++ i) columns[i] = new ArrayList<Character>();				 
			}
		
			int pos = 1 + (column * 4);
			
			if (pos > line.length()) break;
		
			char c = line.charAt(pos);
			
			if (c != ' ') {
				columns[column].add(c);
			}
			
			++column;
		}
	} else if (line.startsWith("move")) {
		
		instructions = line.split("\\s+");
		
		int amount = Integer.valueOf(instructions[1]);
		int from = Integer.valueOf(instructions[3])-1;
		int to = Integer.valueOf(instructions[5])-1;
		
		for (int s = 0 ; s < amount ; ++s) {
			char c = columns[from].remove(0);
			columns[to].add(0,c);
		}
		
	}
}

String answer = "";

for (int i = 0 ; i < columns.length ; ++i) {
	if (columns[i].size() > 0) answer += columns[i][0];
}

//System.out.println(columns);

System.out.println(answer);
