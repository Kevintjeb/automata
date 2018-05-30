//package fileservice;
//
//import automata.Automata;
//import automata.Transition;
//import okhttp3.*;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.*;
//import java.nio.file.attribute.FileAttribute;
//import java.util.Iterator;
//import java.util.Set;
//import java.util.SortedSet;
//
//public class FileIO {
//    public static String BASE_PATH = "output/";
//    private static String SERVER_URL = "http://127.0.0.1:3001/graph";
//
//    public static void writeToFile(Automata automata) {
//        Path automataDot = Paths.get(BASE_PATH + "automataDot.dot");
//
//        try {
//            if (!Files.exists(automataDot.getParent()))
//                Files.createDirectory(automataDot.getParent());
//            if (Files.exists(automataDot))
//                clearFile(automataDot);
//
//            Comparable finalState = automata.getFinalState();
//            SortedSet startStates = automata.getStartStates();
//            SortedSet states = automata.getStates();
//            Set allTransitions = automata.getAllTransitions();
//
//            write(automataDot, "digraph g {\n\nrankdir=LR; \n\nNOTHING [label=\"\", shape=none];\n");
//
//            states.forEach(state -> {
//                try {
//                    String stateId = state.toString();
//                    String text = getFormatForNode(stateId);
//
//                    Files.write(automataDot, text.getBytes(), StandardOpenOption.APPEND);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            Iterator iterator = allTransitions.iterator();
//
//            allTransitions.forEach(transition -> {
//                try {
//                    Transition<String> trans = (Transition<String>) transition;
//                    String text = getFormatForTransition(trans.getFromState(), trans.getToState(), trans.getSymbol());
//                    Files.write(automataDot, text.getBytes(), StandardOpenOption.APPEND);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            startStates.forEach(transition -> {
//                try {
//                    String text = getFormatForStartNode(transition.toString());
//                    Files.write(automataDot, text.getBytes(), StandardOpenOption.APPEND);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            write(automataDot, "\n}");
//        } catch (FileAlreadyExistsException x) {
//            System.err.format("file named %s" +
//                    " already exists%n", automataDot);
//            x.printStackTrace();
//        } catch (IOException x) {
//            System.err.format("createFile error: %s%n", x);
//        }
//
//        try {
//            PostToServerForImage(automataDot.toFile());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String getFormatForTransition(String from, String to, char label) {
//        return String.format("\"%s\" -> \"%s\" [ label = \"%s\" ]; \n\n", from, to, label);
//    }
//
//    private static void clearFile(Path automataDot) throws IOException {
//        Files.write(automataDot, "".getBytes(),
//                StandardOpenOption.CREATE,
//                StandardOpenOption.TRUNCATE_EXISTING);
//    }
//
//    private static String getFormatForNode(String tag) {
//        return String.format("\"%s\" [ label = \"%s\" ]; \n\n", tag, tag);
//    }
//
//    private static String getFormatForStartNode(String tag) {
//        String startNode2 = String.format("NOTHING -> \"%s\";\n", tag);
//        return startNode2;
//    }
//
//    private static void write(Path path, String text) {
//        try {
//            Files.write(path, text.getBytes(), StandardOpenOption.APPEND);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void PostToServerForImage(File file) throws IOException {
//        MultipartBody.Builder requestBody = new MultipartBody.Builder();
//        OkHttpClient client = new OkHttpClient();
//
//
//
//        MultipartBody file1 = requestBody.setType(MultipartBody.FORM)
//                .addFormDataPart("graph", file.getName(),
//                        RequestBody.create(MediaType.parse("text/vnd.graphviz"), file))
//                .build();
//
//        Request request = new Request.Builder()
//                .url(SERVER_URL)
//                .post(file1)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        Files.write(Paths.get("output/" + response.headers("file-name").get(0)), response.body().bytes());
//        System.out.println();
//    }
//}
