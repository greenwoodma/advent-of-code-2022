import java.math.BigInteger;

List<Monkey> monkeys = new ArrayList<Monkey>();

// we multiply all the tests together and use this to keep the numbers
// low and managable. This works because if we have tests 2 and 3 then
// we can do mod(2*3) to reduce the size of the number as the remainder
// will still be valid for tests of either mod(2) or mod(3)
def combined = new BigInteger("1");

String[] input = new File("input.txt");
for (int i = 0 ; i < input.length ; i = i + 7) {
	
	// convert the items into a list of integers
	ArrayDeque items = new ArrayDeque(50);
	for (String item : input[i+1].split(":")[1].split(",")) {
		items.add(new BigInteger(item.trim()));
	}
	
	// pull the operation into an array [left, modifier, right]
	List op = input[i+2].split("=")[1].trim().split("\\s+") as List<Object>;
	if (!op[0].equals("old")) op[0] = new BigInteger(op[0]);
	if (!op[2].equals("old")) op[2] = new BigInteger(op[2]);
	
	// the number to do the divisible test
	BigInteger test = new BigInteger(input[i+3].split("\\s+").last());
	
	combined = combined.multiply(test);
	
	// the true and false monkeys
	Integer tMonkey = Integer.valueOf(input[i+4].trim().split("\\s+").last());
	Integer fMonkey = Integer.valueOf(input[i+5].trim().split("\\s+").last());
	
	// add the monkey to the list
	monkeys.add(new Monkey(items,op,test,tMonkey,fMonkey));
	
	// display it for debugging
	//System.out.println(monkeys.last());
}

for (int round = 0 ; round < 10000 ; ++round) {

	//System.out.println(round);

	for (Monkey monkey : monkeys) {
	
		while (monkey.items.size() > 0) {
			//System.out.print("\t"+monkey.items.size()+" ");
			
			// monkey is inspecting an item
			++monkey.inspected;
			
			//System.out.print("1");
			
			// get the next item
			BigInteger item = monkey.items.remove();
			
			// make the number smaller using the combined test
			item = item.mod(combined);
			
			//System.out.print("2");
			
			// calc the new worry value
			BigInteger worry = monkey.worry(item);
			
			//System.out.print("3");
			
			//System.out.print(worry);
			
			// if the worry value is divisible by the test then pass to the right monkey
			monkeys[worry.mod(monkey.test).equals(BigInteger.ZERO) ? monkey.tMonkey : monkey.fMonkey].items.add(worry);
			
			//System.out.println("4");
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

System.out.println(inspected);

//58080 too low
//58056 too low
// multiply the top two together


BigInteger m1 = new BigInteger(inspected[-1].toString());
BigInteger m2 = new BigInteger(inspected[-2].toString());
System.out.println(m1.multiply(m2));

class Monkey {
	
	BigInteger test;
	ArrayDeque items;
	int tMonkey;
	int fMonkey;
	List op;
	int inspected = 0;

	public Monkey(ArrayDeque items, List op, int test, int tMonkey, int fMonkey) {
		this.items = items;
		this.op = op;
		this.test = test;
		this.tMonkey = tMonkey;
		this.fMonkey = fMonkey;
	}
	
	public BigInteger worry(BigInteger old) {

		// get the left hand side and the right hand sode of the operation
		// putting in the old value where needed			
		BigInteger left = op[0].equals("old") ? old : op[0];
		BigInteger right = op[2].equals("old") ? old : op[2];
		
		// start with the left hand side
		BigInteger worry  = left;
		
		//System.out.print(op[1]);
		
		// then inclue the + or * and the right hand side
		if (op[1].equals("+")) {
			//System.out.print("+");
			worry = worry.add(right);
		}
		else if (op[0].equals("old") && op[2].equals("old") && op[1].equals("*")) {
			//System.out.print("^");
			worry = worry.pow(2);
		}
		else if (op[1].equals("*")) {
			//System.out.print("*");
			worry = worry.multiply(right);
		}
			
		// divide by 3 and round down
		//return Math.floor(worry/3f);
		return worry;
	
	}
	
	public String toString() {
		String result = "\n"+items.toString()+"\n";
		result += op.toString() +"\n";
		result += test +"\n";
		result += tMonkey +":"+ fMonkey;
		
		return result; 
		
	}
}
