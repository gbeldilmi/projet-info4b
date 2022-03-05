package corewar.tests;

import corewar.utils.File;

public class Test1 {
    public static void main(String[] args) {
        
        test_file_read_write("copy.txt", "text.txt");
    }

    public static void test_file_read_write(String target, String source) {
        File.write(target, File.read(source));
    }
}
