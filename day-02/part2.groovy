

Long score = 0;

new File("input.txt").eachLine { line ->

	int theirs = ((int)line[0])-64;
	char outcome = line[2];
	int mine = 0;
	
	if (outcome == 'Y')
		mine = theirs;
	else if (outcome == 'X' && theirs == 1)
		mine = 3;
	else if (outcome == 'X' && theirs == 2)
	    mine = 1;
	else if (outcome == 'X' && theirs == 3)
		mine = 2;
	else if (outcome == 'Z' && theirs == 1)
	    mine = 2;
	else if (outcome == 'Z' && theirs == 2)
		mine = 3;
	else if (outcome == 'Z' && theirs == 3)
		mine = 1;
	
	score += mine;
	
	if (theirs == mine)
		score += 3;
	else if (mine == 1 && theirs == 3)
		score += 6;
	else if (mine == 2 && theirs == 1)
		score += 6;
	else if (mine == 3 && theirs == 2)
		score += 6;
		
}

System.out.println(score);
