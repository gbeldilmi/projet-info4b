package corewar.tests;

import java.util.ArrayList;
import corewar.utils.File;

public class Test1 {
    public static void main(String[] args) {
        test_file_read("text.txt");
    }

    public static void test_file_read(String path) {
        ArrayList<String> array = File.read(path);
        int i;

        for (i = 0; i < array.size(); i++)
            System.out.println(array.get(i));
    }
}
