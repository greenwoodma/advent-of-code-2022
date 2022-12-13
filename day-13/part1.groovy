import groovy.json.JsonSlurper

JsonSlurper slurper = new JsonSlurper();



int total = 0;

String[] input = new File("input.txt");
for (int i = 0 ; i < input.length ; i = i + 3) {

	List left = slurper.parseText(input[i]);
	List right = slurper.parseText(input[i+1]);

	if (check(left,right) > 0)
		total += (i/3)+1;
}

System.out.println(total);

public int check(left, right) {

	//System.out.println(left.toString()+" --> "+right.toString());

	if (left instanceof Integer && right instanceof Integer)
		return right-left;
		
	if (left instanceof Integer)
		return check([left],right);
		
	if (right instanceof Integer)
		return check(left,[right]);

	while(left.size() > 0 && right.size() > 0) {
		int value = check(left.remove(0),right.remove(0));
		if (value != 0) return value;
	}
	
	return right.size() - left.size();
}
