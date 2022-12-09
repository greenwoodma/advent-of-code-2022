
Set<String> visited = new HashSet<String>();

def head = [0,0];
def tail = [0,0];

visited.add(tail.toString());

new File("input.txt").eachLine { line ->
	String[] instructions = line.split("\\s+");
	String direction = instructions[0];
	int distance = Integer.valueOf(instructions[1]);
	
	while (distance > 0) {
	
		if (direction.equals("U")) head[1] = head[1]+1;
		else if (direction.equals("D")) head[1] = head[1]-1;
		else if (direction.equals("L")) head[0] = head[0]-1;
		else if (direction.equals("R")) head[0] = head[0]+1;
		
		--distance;
		
		// TODO move the tail
		
		if (head[0] == tail[0]) {
			// same row
			offset = head[1] - tail[1];
			if (offset == 2) tail[1] = tail[1]+1;
			else if (offset == -2) tail[1] = tail[1]-1;
		} else if (head[1] == tail[1]) {
			// same column
			offset = head[0] - tail[0];
			if (offset == 2) tail[0] = tail[0]+1;
			else if (offset == -2) tail[0] = tail[0]-1;
		} else {
			// the dreaded diagonal
			
			offset = [head[0]-tail[0],head[1]-tail[1]];
			
			if (Math.abs(offset[0]) == 2 || Math.abs(offset[1]) == 2) {
				if (offset[0] > 0) tail[0] = tail[0]+1;
				else if (offset[0] < 0) tail[0] = tail[0]-1;
				
				if (offset[1] > 0) tail[1] = tail[1]+1;
				else if (offset[1] < 0) tail[1] = tail[1]-1;
			}
		}
		
		visited.add(tail.toString());
	}
}

System.out.println(visited.size());

