package digio.coding.task;

import digio.coding.task.file.impl.CustomFileReaderImpl;

public class App {

	public static void main(String[] args) {
		Solution sol = new Solution(new CustomFileReaderImpl());
		sol.printResult();
	}

}
