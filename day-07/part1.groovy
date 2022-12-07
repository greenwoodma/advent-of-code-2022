
directory root = null;
directory current = null;

List<directory> directories = new ArrayList<directory>();

new File("input.txt").eachLine { line ->

	String[] data = line.split("\\s+");
	
	if (root == null) {
		root = new directory("/", null);
		current = root;
		directories.add(current);
	} else if (data[0] == '$' ) {
		
		
		if (data[1] == "cd" && data[2] == "..") {
			current = current.parent;
		} else if (data[1] == "cd") {
			child = new directory(data[2], current);
			current.children.put(data[2],child);
			current = child;
			directories.add(current);
		}
	} else if (data[0] != "dir") {
		current.children.put(data[1], Long.valueOf(data[0]));
	}
	
	
	
}

Long total = 0;

for (directory d : directories.reverse()) {
	Long size = d.getSize();
	
	if (size <= 100000) total += size;
}

System.out.println(total);

class directory {

	Map children = new HashMap();
	String name;
	directory parent;
	
	public directory(String name, directory parent) {
		this.name = name;
		this.parent = parent;
	}
	
	Long size = null;
	
	public String toString() {
		return name + " " + getSize().toString();
	}
	
	public Long getSize() {
		if (size != null) return size;
		
		size = 0;
		
		for (Object child : children.values()) {
			if (child instanceof Long) {
				size += (Long)child;
			} else {
				size += ((directory)child).getSize();
			}
		}
		
		return size;
	}
}
