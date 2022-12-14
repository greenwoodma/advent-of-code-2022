
Map columns = new HashMap();
Map bottom = new HashMap();

new File("input.txt").eachLine { line ->
	String[] lines = line.split(" -> ");
	
	for (int i = 0 ; i < lines.length - 1 ; ++i) {
		int[] start = lines[i].split(",").collect { c -> Integer.valueOf(c) }
		int[] end = lines[i+1].split(",").collect { c -> Integer.valueOf(c) }

		if (start[0] == end[0]) {
			Set column = columns.getOrDefault(start[0], new HashSet());
			
			for (int r = Math.min(start[1],end[1]) ; r <= Math.max(start[1],end[1]) ; ++ r) {
				column.add(r);
				
				bottom.put(start[0],Math.max(bottom.getOrDefault(start[0],-1),r));
			}
			
			columns.put(start[0], column);
		}
		else {
			for (int c = Math.min(start[0],end[0]) ; c <= Math.max(start[0],end[0]) ; ++c) {
				Set column = columns.getOrDefault(c, new HashSet());
				
				column.add(start[1]);
				columns.put(c, column);
				
				bottom.put(c,Math.max(bottom.getOrDefault(c,-1),start[1]));
			}
		}
	}
}

//System.out.println(bottom);

total = 0;

while (true) {
	int x = 500;
	int y = 0;
	
	while (true) {
		Set column = columns.get(x);
		Set columnLeft = columns.get(x-1);
		Set columnRight = columns.get(x+1);
		
		if (column == null) {
			finished();
		} else if (y > bottom.get(x)) {
			finished();
		} else if (!column.contains(y+1)) {
			++y
		} else if (columnLeft == null) {
			finished();
		} else if (!columnLeft.contains(y+1)) {
			x = x-1;
			y = y+1;
		} else if (columnRight == null) {
			finished();
		} else if (!columnRight.contains(y+1)) {
			x = x+1;
			y = y+1;
		} else {
			column.add(y)
			++total;
			//System.out.println(total);
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
