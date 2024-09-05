import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class HokeemonHelper {

    public static Hokeemon[] getData(String url) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(url));
        Hokeemon[] hokeemonList = new Hokeemon[8];
        String line = br.readLine();
        int i=0;
        while(line != null) {
            String [] hokeemonData = line.split(" ");
            Hokeemon hokeemon = new Hokeemon(hokeemonData[0], hokeemonData[1], Integer.parseInt(hokeemonData[2]));
            hokeemonList[i] = hokeemon;
            line = br.readLine();
            i++;
        }
        return hokeemonList;
    }

    public static String getBio(Hokeemon[] list, Hokeemon hokeemon) {
        return "I am " + hokeemon.getName() +
                ": Type " + hokeemon.getType() +
                ": Age " + hokeemon.getAge() +
                ", I live in the " + Hokeemon.liveIn(hokeemon) + ".\n" +
                "My friends are: " + returnFriends(list,hokeemon);

    }

    public static String returnFriends(Hokeemon[] list, Hokeemon hokeemon) {
        String friends = "";
        for(int i=0; i<list.length; i++) {
            if(hokeemon.areFriends(list[i])) {
                friends += list[i].getName() + " ";
            }
        }
        return friends;
    }

}