package fileservice;

import automata.Automata;
import automata.Transition;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.Set;
import java.util.SortedSet;

public class FileIO {
    public static String BASE_PATH = "output/";

    public static void writeToFile(Automata automata) {
        Path automataDot = Paths.get(BASE_PATH+ "automataDot.dot");

        try {
            if(!Files.exists(automataDot.getParent()))
                Files.createDirectory(automataDot.getParent());
            if(Files.exists(automataDot))
                clearFile(automataDot);

            Comparable finalState = automata.getFinalState();
            SortedSet startStates = automata.getStartStates();
            SortedSet states = automata.getStates();
            Set allTransitions = automata.getAllTransitions();

            write(automataDot, "digraph g {\n\n");

            states.forEach(state -> {
                try {
                    String stateId = state.toString();
                    String text =  getFormatForNode(stateId);

                    Files.write(automataDot, text.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            allTransitions.forEach(transition -> {
                try {
                    Transition<String> trans = (Transition<String>) transition;
                    String text = getFormatForTransition(trans.getFromState(), trans.getToState(),trans.getSymbol());
                    Files.write(automataDot, text.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                });

            write(automataDot, "\n}");
        } catch (FileAlreadyExistsException x) {
            System.err.format("file named %s" +
                    " already exists%n", automataDot);
            x.printStackTrace();
        } catch (IOException x) {
            System.err.format("createFile error: %s%n", x);
        }
    }

    private static String getFormatForTransition(String from, String to, char label) {
        return String.format("\"%s\" -> \"%s\" [ label = \"%s\" ]; \n\n", from, to, label);
    }

    private static void clearFile(Path automataDot) throws IOException {
        Files.write(automataDot, "".getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING );
    }

    private static String getFormatForNode(String tag) {
        return String.format("\"%s\" [ label = \"%s\" ]; \n\n", tag, tag);
    }

    private static String getFormatForStartNode(String tag) {
        return String.format("\"start\" -> \"%s\" [ label = \"%s\" ]; \n\n", tag, tag);
    }

    private static void write(Path path, String text) {
        try {
            Files.write(path, text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
