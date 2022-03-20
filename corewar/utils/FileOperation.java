package corewar.utils;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

public class FileOperation {
	//	Retourne une ArrayList<String> correspondant à chaque lignes du fichier
	public static ArrayList<String> read(String path) {
		ArrayList<String> array = new ArrayList<>();
		String str;
		int c;

		array.add(new File(path).getName());
		str = "";
		try (FileInputStream in = new FileInputStream(path)) {
			while ((c = in.read()) != -1) {
				str = str + (char)c;
				if (c == '\r' || c == '\n') {
					array.add(str.substring(0, str.length() - 1));
					str = "";
					if (c == '\r')
						in.read(); // skip du '\n' sur Windows
				}
			}
			array.add(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	//	Reçoit une ArrayList<String> "content" correspondant à chaque lignes du fichier à écrire
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

	//	Création d'un fichier
	public static void create(String path) {
		File file = new File(path);

		try {
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	Suppression d'un fichier
	public static void delete(String path) {
		File file = new File(path);

		try {
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
