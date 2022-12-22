def p = ~"([0-9]+)"

cache = new HashMap();
best = 0;

long answer = 0;

new File("input.txt").eachLine { line ->
	def m = line =~ p;
	List data = m.findAll()*.first().collect { it as int };
	
	//Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
	
	cache.clear();
	best = 0;
	
	def limit = [
		[data[1],data[2],data[3],data[5]].max()+1,
		data[4]+1,
		data[6]+1
	];
	
	System.out.println("Blueprint:" +data);
	
	int geodes = findMaxGeodeCount(data,[1,0,0,0],[0,0,0,0],limit,1);
	
	answer += geodes * data[0];
}

System.out.println(answer);

// within a blueprint
//  0: ID
//  1: ore robot, ore cost
//  2: clay robot, ore cost
//  3: obsidian robot, ore cost
//  4: obsidian robot, clay cost
//  5: geode robot, ore cost
//  6: gedoe robot, obsidian cost

// within collected and robots
//  0: ore
//  1: clay
//  2: obsidian
//  3: geode

// within limit max values needed any turn
//  0: ore
//  1: clay
//  2: obsidian

public getUpperbound(int robots, int collected, int minute) {
	int left = 26 - minute;

	int upperbound = collected + (robots * left);
	
	upperbound = upperbound + ((left * (left+1))/2);
	
	return upperbound;
}

public int findMaxGeodeCount(List blueprint, List robots, List collected, List limit, int minute) {
	
	// if we've had the final minute then just return what we've collected
	if (minute == 25) {
		best = Math.max(best,collected[-1]);
		return collected[-1];
	}
	
	//System.out.println("==== start of minute " + minute +" ====")
	//System.out.println("Robots: "+ robots);
	//System.out.println("Collected: "+ collected);	
	
	String state = robots.toString()+collected.toString()+minute;
	
	if (cache.containsKey(state)) return cache.get(state);
	
	int upperbound = getUpperbound(robots[3],collected[3],minute);
	
	if (upperbound < best) return collected[-1];

	int max = 0;
	
	boolean built  = false;
		// build a geode robot if we can
	if (collected[0] >= blueprint[5] && collected[2] >= blueprint[6]) {
		built = true;
		stuff = [
			collected[0]-blueprint[5]+robots[0],
			collected[1]+robots[1],
			collected[2]-blueprint[6]+robots[2],
			collected[3]+robots[3]
		];
		
		def helpers = new ArrayList(robots);
		helpers[3] = helpers[3]+1;
		
		max = Math.max(findMaxGeodeCount(blueprint, helpers, stuff, limit, minute+1),max);
	}
	// build an obsidean robot if we can
	
	if (limit[2] >= collected[2] && collected[0] >= blueprint[3] && collected[1] >= blueprint[4]) {
	built = true;
		stuff = [
			collected[0]-blueprint[3]+robots[0],
			collected[1]-blueprint[4]+robots[1],
			collected[2]+robots[2],
			collected[3]+robots[3]
		];
		
		def helpers = new ArrayList(robots);
		helpers[2] = helpers[2]+1;
		
		max = Math.max(findMaxGeodeCount(blueprint, helpers, stuff, limit, minute+1),max);
	}
	 
	// build a clay robot if we can
	if (limit[1] >= collected[1] && collected[0] >= blueprint[2]) {
		built = true;
		stuff = [
			collected[0]-blueprint[2]+robots[0],
			collected[1]+robots[1],
			collected[2]+robots[2],
			collected[3]+robots[3]
		];
		
		def helpers = new ArrayList(robots);
		helpers[1] = helpers[1]+1;
		
		max = Math.max(findMaxGeodeCount(blueprint, helpers, stuff, limit, minute+1),max);
	}
	 	// build an ore robot if we can
	if (limit[0] >= collected[0] && collected[0] >= blueprint[1]) {
	built = true;
		stuff = [
			collected[0]-blueprint[1]+robots[0],
			collected[1]+robots[1],
			collected[2]+robots[2],
			collected[3]+robots[3]
		];
		
		def helpers = new ArrayList(robots);
		helpers[0] = helpers[0]+1;
		
		max = Math.max(findMaxGeodeCount(blueprint, helpers, stuff, limit, minute+1),max);
	}

//	if (!built) {

//	else {
	// don't build anything
	// update the number of things collected
	def stuff = [];
	for (int i = 0 ; i < robots.size() ; ++i) {
		stuff.add(robots[i]+collected[i]);
	}
	max = Math.max(findMaxGeodeCount(blueprint,robots,stuff,limit,minute+1),max);
	
	//}
//}	
	
	cache.put(state,max);

	// work out all robots we could construct and cycle through
	// each option, keep track of the max return value

	return max;
}
