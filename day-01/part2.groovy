Long current = 0;

List<Long> totals = new ArrayList<Long>();

new File("input.txt").eachLine { line ->

	if (line.equals("")) {
		totals.add(current);
		current = 0;
	}
	else {
		current += Long.valueOf(line);
	}
}

totals.add(current);

totals = totals.sort().reverse();

System.out.println(totals.subList(0,3).value.sum());
