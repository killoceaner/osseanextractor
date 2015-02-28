package extension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadCursor {
	private Properties properties;
	private String file_path;

	public ReadCursor(String file_path) {
		this.file_path = file_path;
		this.properties = new Properties();
		try {
			FileInputStream input_file = new FileInputStream(new File(
					this.file_path));
			this.properties.load(input_file);
			input_file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getValue(String key) {
		if (this.properties.containsKey(key))
			return this.properties.getProperty(key);
		return null;
	}

	public void setValue(String key, String value) {
		try {
			FileOutputStream out_file = new FileOutputStream(
					new File(file_path));			
			this.properties.setProperty(key, value);
			this.properties.store(out_file, "update key-value");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
