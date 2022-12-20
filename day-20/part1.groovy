def original = (new File("input.txt")).collect{it as int}

def positional = [];

def ZERO;

for (int i = 0 ; i < original.size() ; ++i) {
	positional.add([original[i],i]);
	if (positional[-1][0]==0) ZERO = positional[-1]
}

//System.out.println(ZERO);

def current = new ArrayList(positional);

//display(current);

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

int zi = current.indexOf(ZERO);

int first = ((1000%positional.size())+zi) % positional.size();
first = current[first][0];

int second = ((2000%positional.size())+zi) % positional.size();
second = current[second][0];

int third = ((3000%positional.size())+zi) % positional.size();
third = current[third][0];


System.out.println(first+second+third);


public void display(List list) {
	for (List i : list) {
		System.out.print(i[0]+", ");
	}
	
	System.out.println("");

}
