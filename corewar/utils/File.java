package corewar.utils;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class File {
	public static ArrayList<String> read(String path) {
		ArrayList<String> array = new ArrayList<>();
		String str;
		int c;

		str = "";
		try (FileInputStream in = new FileInputStream(path)) {
			while ((c = in.read()) != -1) {
				str = str + (char)c;
				if (c == '\r' || c == '\n') {
					array.add(str.substring(0, str.length() - 1));
					str = "";
					if (c == '\r')
						in.read(); // skip du '\n' sur windaube
				}
			}
			array.add(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	public static void write(String path, ArrayList<String> content) {
		int i;
		int j;

		try (FileOutputStream out = new FileOutputStream(path, true)) {
			for (i = 0; i < content.size(); i++) {
				for (j = 0; j < content.get(i).length(); j++)
					out.write(content.get(i).charAt(j));
				if (i < content.size() - 1)	
					out.write('\n');
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// delete
	// create
}
