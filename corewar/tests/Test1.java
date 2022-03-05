package corewar.tests;

import corewar.utils.FileOperation;
import java.io.File;
import java.util.ArrayList;

public class Test1 {
    public static void main(String[] args) {
        file_operation_test();
    }

    // Tests unitaires de corewar.utils.FileOperation
    public static void file_operation_test() {
        File file = new File("tmp.txt");
        ArrayList<String> content = new ArrayList<>();
        content.add("azertyuiop");
        content.add("bodsfvshbofvq");
        content.add(",;:qfugieoz");

        FileOperation.create("tmp.txt");
        if (file.exists())
            System.out.println("FileOperation.java : create() OK");
        else {
            System.out.println("FileOperation.java : create() ERR");
            System.exit(0);
        }
        if (file.exists())
            System.out.println("FileOperation.java : write access OK");
        else {
            System.out.println("FileOperation.java : write access ERR");
            System.exit(0);
        }
        FileOperation.write("tmp.txt", content);
        ArrayList<String> fileContent = FileOperation.read("tmp.txt");
        if (fileContent.size() != content.size()) {
            System.out.println("FileOperation.java : write() or read() ERR");
            System.exit(0);
        }
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).compareTo(fileContent.get(i)) != 0) {
                System.out.println("FileOperation.java : write() or read() ERR");
                System.exit(0);
            }
        }
        System.out.println("FileOperation.java : write() and read() OK");
        file.delete();
        if (file.exists()) {
            System.out.println("FileOperation.java : delete() ERR");
            System.exit(0);
        } else 
            System.out.println("FileOperation.java : delete() OK");
    }
}