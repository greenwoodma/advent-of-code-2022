data = new HashMap();

new File("input.txt").eachLine { line ->
	info = line.split(":\\s*")
	
	data.put(info[0],info[1].split("\\s+"));
	
}

System.out.println(getValue("root"));

public long getValue(String monkey) {
	List rule = data.get(monkey)

	if (rule.size() == 1) return Long.valueOf(rule[0]);
	
	long left = getValue(rule[0]);
	long right = getValue(rule[2]);
	
	if (rule[1].equals("+")) return left+right;
	if (rule[1].equals("-")) return left-right;
	if (rule[1].equals("*")) return left*right;
	if (rule[1].equals("/")) return left/right;
	
	throw new RuntimeException(rule);
}
