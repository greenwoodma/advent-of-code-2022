

Long score = 0;

group = new ArrayList<Set<Character>>();

new File("input.txt").eachLine { line ->
	
	group.add(new HashSet<Character>(Arrays.asList(line.toCharArray())));
	
	if (group.size() == 3) {
		score += calc();
	}
}

System.out.println(score);


public Long calc() {
	long total = 0;
	for (Character c : group[0].intersect(group[1]).intersect(group[2])) {
		total += c.isUpperCase() ? 26 + ((int)c)-64 : ((int)c)-96;
	}
	
	group.clear();
	
	return total;
}
