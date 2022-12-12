List<Monkey> monkeys = new ArrayList<Monkey>();

String[] input = new File("input.txt");
for (int i = 0 ; i < input.length ; i = i + 7) {
	
	// convert the items into a list of integers
	List<Integer> items = new ArrayList();
	for (String item : input[i+1].split(":")[1].split(",")) {
		items.add(Double.valueOf(item.trim()));
	}
	
	// pull the operation into an array [left, modifier, right]
	String[] op = input[i+2].split("=")[1].trim().split("\\s+");
	
	// the number to do the divisible test
	Integer test = Integer.valueOf(input[i+3].split("\\s+").last());
	
	// the true and false monkeys
	Integer tMonkey = Integer.valueOf(input[i+4].trim().split("\\s+").last());
	Integer fMonkey = Integer.valueOf(input[i+5].trim().split("\\s+").last());
	
	// add the monkey to the list
	monkeys.add(new Monkey(items,op,test,tMonkey,fMonkey));
	
	// display it for debugging
	System.out.println(monkeys.last());
}

for (int round = 0 ; round < 20 ; ++round) {

	for (Monkey monkey : monkeys) {
	
		while (monkey.items.size() > 0) {
			
			// monkey is inspecting an item
			++monkey.inspected;
			
			// get the next item
			double item = monkey.items.remove(0);
			
			// calc the new worry value
			double worry = monkey.worry(item);
			
			// if the worry value is divisible by the test then pass to the right monkey
			monkeys[worry % monkey.test == 0 ? monkey.tMonkey : monkey.fMonkey].items.add(worry);
		}
	}

}

// put the inspected values into a list
List<Integer> inspected = [];
for (Monkey monkey : monkeys) {
	inspected.add(monkey.inspected);
}

//sort it
Collections.sort(inspected);

//58080 too low
//58056 too low
// multiply the top two together
System.out.println(inspected[-1]*inspected[-2]);

class Monkey {
	
	int test;
	List items = [];
	int tMonkey;
	int fMonkey;
	String[] op;
	int inspected = 0;

	public Monkey(List items, String[] op, int test, int tMonkey, int fMonkey) {
		this.items = items;
		this.op = op;
		this.test = test;
		this.tMonkey = tMonkey;
		this.fMonkey = fMonkey;
	}
	
	public double worry(double old) {

		// get the left hand side and the right hand sode of the operation
		// putting in the old value where needed			
		double left = op[0].equals("old") ? old : Double.valueOf(op[0]);
		double right = op[2].equals("old") ? old : Double.valueOf(op[2]);
		
		// start with the left hand side
		double worry  = left;
		
		// then inclue the + or * and the right hand side
		if (op[1].equals("+"))
			worry = worry + right;
		else if (op[1].equals("*"))
			worry = worry * right;
			
		// divide by 3 and round down
		return Math.floor(worry/3f);
	
	}
	
	public String toString() {
		String result = "\n"+items.toString()+"\n";
		result += op.toString() +"\n";
		result += test +"\n";
		result += tMonkey +":"+ fMonkey;
		
		return result; 
		
	}
}
