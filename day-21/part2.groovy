data = new HashMap();

new File("input.txt").eachLine { line ->
	info = line.split(":\\s*")
	
	data.put(info[0],info[1].split("\\s+"));
	
}

locate = new HashMap();

for (String monkey : data.keySet()) {
	locate.put(monkey,containsHumn(monkey));
}

List root = data.get("root");

long value = locate.get(root[0]) ? getValue(root[2]) : getValue(root[0]);


//System.out.println(locate.get(root[0]));
//System.out.println(locate.get(root[2]));
//System.out.println(value);

System.out.println(calcHumn(locate.get(root[0]) ? root[0] : root[2], value));

public long calcHumn(String monkey, long value) {

	if (monkey.equals("humn")) {
		//System.out.println("Found humn: " + value);
		return value;
	}
	
	List rule = data.get(monkey);
	
	//System.out.println(monkey+": "+rule+" "+locate.get(rule[0]));
	
	
	
	if (locate.get(rule[0])) {
		// the human is in the left half of the sum
		
		long right = getValue(rule[2]);
		
		if (rule[1].equals("+")) {
			// value = left + right
			// so...
			// left = value - right
			return calcHumn(rule[0], value - right);
		}
		
		if (rule[1].equals("-")) {
			// value = left - right;
			// so....
			// left = value + right;
			return calcHumn(rule[0], value + right);
		}
		
		if (rule[1].equals("*")) {
			// value = left * right
			// so....
			// left = value / right
			return calcHumn(rule[0], (long)(value / right));
		}
		
		if (rule[1].equals("/")) {
			// value = left / right
			// so....
			// left = value * right
			return calcHumn(rule[0], (long)(value * right));
		}
	}
	else {
		// the human is in the right half of the sum
		
		long left = getValue(rule[0]);
		
		if (rule[1].equals("+")) {
			// value = left + right;
			// so...
			// right = value - left
			return calcHumn(rule[2], value - left);
		}
		
		if (rule[1].equals("-")) {
			// value = left - right
			// so...
			// right = left - value;
			return calcHumn(rule[2], left - value);
		}
		
		if (rule[1].equals("*")) {
			// value = left * right
			// so...
			// right = value / left

			return calcHumn(rule[2], (long)(value / left));
		}
		
		if (rule[1].equals("/")) {
			// value = left / right
			// so...
			// right = left / value
			return calcHumn(rule[2],(long)( left / value));
		}
	}

	throw new RuntimeException(rule);
}

public long getValue(String monkey) {
	List rule = data.get(monkey)
	
	//System.out.println("\t"+monkey+": "+rule);

	if (rule.size() == 1) return Long.valueOf(rule[0]);
	
	long left = getValue(rule[0]);
	long right = getValue(rule[2]);
	
	if (rule[1].equals("+")) return left+right;
	if (rule[1].equals("-")) return left-right;
	if (rule[1].equals("*")) return left*right;
	if (rule[1].equals("/")) return left/right;
	
	throw new RuntimeException(rule);
}

public boolean containsHumn(String monkey) {

	if (monkey.equals("humn")) return true;
	
	List rule = data.get(monkey);
	
	
	
	if (rule.size() == 1) return false;
	
	if (rule[0].equals("humn") || containsHumn(rule[0])) return true;
	
	return rule[2].equals("humn") || containsHumn(rule[2]);
}
