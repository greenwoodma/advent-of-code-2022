Long current = 0;

Long max = 0;

new File("input.txt").eachLine { line ->

	if (line.equals("")) {
		max = Math.max(max,current);
		current = 0;
	}
	else {
		current += Long.valueOf(line);
	}
}

max = Math.max(max,current);

System.out.println(max);
