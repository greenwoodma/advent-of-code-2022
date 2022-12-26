def values = [
	"0": 0,
	"1": 1,
	"2": 2,
	"-": -1,
	"=": -2
];

long total = 0;

new File("input.txt").eachLine { line ->

	//System.out.println(line);

	def digits = (line.toCharArray() as List).reverse();
	
	long value = 0;
	
	for (int i = 0 ; i < digits.size() ; ++i) {
		long multiplier = Math.pow(5,i);
		
		long n = values[digits[i].toString()];
		
		//System.out.println("\t"+digits[i]+" " + multiplier + " " + n + " " +(multiplier*n));
		
		value += multiplier * n;
	}
	
	//System.out.println(value+"\n");
	total += value;
}

System.out.println(total);
System.out.println(Long.toString(total,5));
def base5 = (Long.toString(total,5).toCharArray() as List).collect {Integer.valueOf(it.toString())}.reverse();

String answer = "";

//System.out.println(base5);

for (int i = 0 ; i < base5.size() ; ++i) {

	while (base5[i] >= 5) {
		base5[i] = base5[i]-5;
		base5[i+1] = base5[i+1] + 1;
	}

	if (base5[i] < 3) {
		answer = answer+base5[i];
	} else if (base5[i] == 3) {
		answer = answer+"=";
		base5[i+1] = base5[i+1]+1;
	} else if (base5[i] == 4) {
		answer = answer+"-";
		base5[i+1] = base5[i+1]+1;
	}
	
}

System.out.println((answer.toCharArray() as List).reverse().join(""));
