

new File("input.txt").eachLine { line ->

	for (int i = 14 ; i <= line.length() ; ++i) {
		Set symbols = new HashSet(Arrays.asList(line.substring(i-14,i).toCharArray()));
		if (symbols.size()==14){
			System.out.println(i);
			break;
		}
	}	
}
