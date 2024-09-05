
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.io.*;
//import java.util.;

class HokeemonMain {
    public static void main(String[] args) {
        System.out.println("Starting Hokeemon");
        try {
            Hokeemon[] list = HokeemonHelper.getData("Data.txt");
            for(int i=0; i<list.length; i++) {
                System.out.println("Name " + list[i].getName() + ": " +
                                    "Type " + list[i].getType() + ": " +
                                    "Age " + list[i].getAge() + ": ");
            }
            System.out.println();
            Arrays.stream(list).forEach(c ->
                        System.out.println(HokeemonHelper.getBio(list, c))
            );
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}