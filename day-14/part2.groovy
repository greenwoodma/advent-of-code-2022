
Map columns = new HashMap();
bottom = 0;

new File("input.txt").eachLine { line ->
	String[] lines = line.split(" -> ");
	
	for (int i = 0 ; i < lines.length - 1 ; ++i) {
		int[] start = lines[i].split(",").collect { c -> Integer.valueOf(c) }
		int[] end = lines[i+1].split(",").collect { c -> Integer.valueOf(c) }

		if (start[0] == end[0]) {
			Set column = columns.getOrDefault(start[0], new HashSet());
			
			for (int r = Math.min(start[1],end[1]) ; r <= Math.max(start[1],end[1]) ; ++ r) {
				column.add(r);
				

			}

			bottom = Math.max(bottom, Math.max(start[1],end[1]));
			
			columns.put(start[0], column);
		}
		else {
			for (int c = Math.min(start[0],end[0]) ; c <= Math.max(start[0],end[0]) ; ++c) {
				Set column = columns.getOrDefault(c, new HashSet());
				
				column.add(start[1]);
				columns.put(c, column);
				
			}
			
			bottom = Math.max(bottom, start[1]);
		}
	}
}


bottom = bottom + 2;

for (Set column : columns.values()) {
	column.add(bottom);
}


total = 0;

while (true) {
	int x = 500;
	int y = 0;
	
	while (true) {
		Set column = columns.get(x);
		if (column == null) {
			column = new HashSet([bottom]);
			columns.put(x, column);
		}
		
		Set columnLeft = columns.get(x-1);
		if (columnLeft == null) {
			columnLeft = new HashSet([bottom]);
			columns.put(x-1,columnLeft);
		}
		
		Set columnRight = columns.getOrDefault(x+1, new HashSet([bottom]));
		if (columnRight == null) {
			columnRight = new HashSet([bottom]);
			columns.put(x+1, columnRight);
		}
		
		if (!column.contains(y+1)) {
			++y
		} else if (!columnLeft.contains(y+1)) {
			x = x-1;
			y = y+1;
		} else if (!columnRight.contains(y+1)) {
			x = x+1;
			y = y+1;
		} else {
			column.add(y)
			++total;
			//System.out.println(total+": "+x+","+y);
			if (x == 500 && y == 0) {
				finished();
			}
			
			break;
		}
		
		//System.out.println("\t"+x+","+y);
	}
}

public void finished() {
	// there is nothing in this column for the sand to fall on
	// so we have finished and can just report the total;
	System.out.println(total);
	System.exit(0);
}
