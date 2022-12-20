BigInteger key = new BigInteger("811589153");

def original = (new File("input.txt")).collect{(new BigInteger(it)).multiply(key)}

def positional = [];

def ZERO;

for (int i = 0 ; i < original.size() ; ++i) {
	positional.add([original[i],i]);
	if (positional[-1][0]==0) ZERO = positional[-1]
}

//System.out.println(ZERO);

def current = new ArrayList(positional);

//display(current);


for (int i = 0 ; i < 10 ; ++i) {

	for (List item : positional) {
		int index = current.indexOf(item);
		
		current.remove(index);
		
		int shift = item[0] % positional.size();
		
		int insert = ((index + item[0]) % (positional.size()-1));
		
		if (insert < 0) insert = positional.size()+insert-1;
		
		//if (insert == 0) 
		//	current.add(item)
		//else
			current.add(insert,item);
		
		//System.out.println("\nMove "+item[0]+" from " + index +" to " + insert);

		//display(current);
	}

}

//display(current)

int zi = current.indexOf(ZERO);

def first = ((1000%positional.size())+zi) % positional.size();
first = current[first][0];

def second = ((2000%positional.size())+zi) % positional.size();
second = current[second][0];

def third = ((3000%positional.size())+zi) % positional.size();
third = current[third][0];


System.out.println(first.add(second).add(third));


public void display(List list) {
	for (List i : list) {
		System.out.print(i[0]+", ");
	}
	
	System.out.println("");

}
