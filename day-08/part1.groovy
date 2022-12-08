List forest = new ArrayList();;

new File("input.txt").eachLine { line ->
	List row = new ArrayList();
	for (int t ; t < line.length() ; ++t)
		row.add(Integer.valueOf(line.charAt(t).toString()));	

	forest.add(row);
}

int width = forest[0].size();
int height = forest.size();

long total = (width * 2) + ((height-2)*2);

for (int x = 1 ; x < width-1 ; ++x) {

	List column = new ArrayList();
	for (int y = 0 ; y < height ; ++y) {
		column.add(forest[y][x]);
	}

	for (int y = 1 ; y < height-1 ; ++y) {
		current = forest[y][x];
	
	
		left = forest[y].subList(0,x).value.max();
		right = forest[y].subList(x+1,width).value.max();

		above = column.subList(0,y).value.max();
		below = column.subList(y+1,height).value.max();
		
		if (left < current || right < current || above < current || below < current) ++total;
	}
}

System.out.println(total);
