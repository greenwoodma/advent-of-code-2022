

Long score = 0;

new File("input.txt").eachLine { line ->

	int theirs = ((int)line[0])-64;
	int mine = ((int)line[2])-87;
	
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
