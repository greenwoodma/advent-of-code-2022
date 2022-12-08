List forest = new ArrayList();;

new File("input.txt").eachLine { line ->
	List row = new ArrayList();
	for (int t ; t < line.length() ; ++t)
		row.add(Integer.valueOf(line.charAt(t).toString()));	

	forest.add(row);
}

int width = forest[0].size();
int height = forest.size();

long scenic = 0;

for (int x = 1 ; x < width-1 ; ++x) {

	List column = new ArrayList();
	for (int y = 0 ; y < height ; ++y) {
		column.add(forest[y][x]);
	}

	for (int y = 1 ; y < height-1 ; ++y) {
		current = forest[y][x];
	
	
		left = forest[y].subList(0,x).reverse();
		right = forest[y].subList(x+1,width);

		above = column.subList(0,y).reverse();
		below = column.subList(y+1,height);
		
		scenic = Math.max(scenic, score(current,left)*score(current,right)*score(current,above)*score(current,below));	
	}
}

System.out.println(scenic);

public int score(int current, List view) {

	for (int i = 0 ; i < view.size() ; ++i) {
		if (view[i] >= current) return i+1;
	}

	return view.size();
}
