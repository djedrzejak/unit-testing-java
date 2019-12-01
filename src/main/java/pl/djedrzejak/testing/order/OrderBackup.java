package pl.djedrzejak.testing.order;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class OrderBackup {

	public Writer writer;

	public Writer getWriter() {
		return writer;
	}
	
	public void createFile() throws FileNotFoundException {
		File file = new File("orderBackup.txt");
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		writer = new BufferedWriter(osw);
	}
	
	public void backupOrder(Order order) throws IOException {
		writer.append(order.toString());
	}
	
	public void closeFile() throws IOException {
		writer.close();
	}
	
}
