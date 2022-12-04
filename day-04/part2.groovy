

Long score = 0;

new File("input.txt").eachLine { line ->

	List<Long> data = new ArrayList<Long>();
	
	for (String v : line.split("[^\\d]"))
		data.add(Long.valueOf(v));

	//if ((data[0] >= data[2] && data[1] <= data[3]) || (data[2] >= data[0] && data[3] <= data[1])) {
	//	++score;
	//}
	
	if (data[0] >= data[2] && data[0] <= data[3] ||
		data[1] >= data[2] && data[1] <= data[3] ||
		data[2] >= data[0] && data[2] <= data[1] ||
		data[3] >= data[0] && data[3] <= data[0]) ++score

}

System.out.println(score);
