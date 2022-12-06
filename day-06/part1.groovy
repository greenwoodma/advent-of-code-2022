

new File("input.txt").eachLine { line ->

	for (int i = 4 ; i <= line.length() ; ++i) {
		Set symbols = new HashSet(Arrays.asList(line.substring(i-4,i).toCharArray()));
		if (symbols.size()==4){
			System.out.println(i);
			break;
		}
	}	
}
