

Long score = 0;

new File("input.txt").eachLine { line ->
int size = line.length()/2
	String p1 = line.substring(0,size);
	String p2 = line.substring(size);
	
	Set<Character> s1 = new HashSet<Character>(Arrays.asList(p1.toCharArray()));
	Set<Character> s2 = new HashSet<Character>(Arrays.asList(p2.toCharArray()));
	
	for (Character c : s1.intersect(s2)) {
		//System.out.println((int)c);
		score += c.isUpperCase() ? 26 + ((int)c)-64 : ((int)c)-96;
	}
	
	//System.out.println("\n");
	//System.out.println(p1);
	//System.out.println(p2);
	//System.out.println(s1.intersect(s2));

}

System.out.println(score);
