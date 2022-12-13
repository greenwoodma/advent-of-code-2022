import groovy.json.JsonSlurper

JsonSlurper slurper = new JsonSlurper();



List start = [[2]];
List end = [[6]];

List packets = [start,end];

int total = 0;

String[] input = new File("input.txt");
for (int i = 0 ; i < input.length ; i = i + 3) {

	List left = slurper.parseText(input[i]);
	List right = slurper.parseText(input[i+1]);

	packets.add(left);
	packets.add(right);
}

Collections.sort(packets, new Comparator() {

public int compare(left, right) {

	//System.out.println(left.toString()+" --> "+right.toString());

	if (left instanceof Integer && right instanceof Integer)
		return left-right;
		
	if (left instanceof Integer)
		return compare([left],right);
		
	if (right instanceof Integer)
		return compare(left,[right]);

	int i = 0;
	while(i < left.size() && i < right.size()) {
		int value = compare(left.get(i),right.get(i));
		if (value != 0) return value;
		++i;
	}
	
	return left.size() - right.size();
}
});

System.out.println((packets.indexOf(start)+1)*(packets.indexOf(end)+1));
