package fileservice;

import automata.Automata;
import automata.Transition;
import okhttp3.*;
import regex.RegExp;
import regex.Thompson;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileIO {
    private static String BASE_PATH = "output/";
    private static String SERVER_URL = "http://vpn.kevinvandenbroek.nl:3001/graph";
    private static List<Character> OPERANDS = Arrays.asList('|', '(');
    private static List<Character> OPERANDS_WITH_DOT = Arrays.asList(')', '*', '+');
    public static void writeToFile(Automata automata) {
        Path automataDot = Paths.get(BASE_PATH + "automataDot.dot");

        try {
            if (!Files.exists(automataDot.getParent()))
                Files.createDirectory(automataDot.getParent());
            if (Files.exists(automataDot))
                clearFile(automataDot);

            SortedSet<?> startStates = automata.getStartStates();
            SortedSet<?> states = automata.getStates();
            Set<?> allTransitions = automata.getAllTransitions();

            write(automataDot, "digraph g {\n\nrankdir=LR; \n\nNOTHING [label=\"\", shape=none];\n");

            states.forEach(state -> {
                try {
                    String stateId = state.toString();
                    String text = getFormatForNode(stateId);

                    Files.write(automataDot, text.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Iterator iterator = allTransitions.iterator();

            allTransitions.forEach(transition -> {
                try {
                    Transition<?> trans = (Transition<?>) transition;
                    String text = getFormatForTransition(trans.getFromState(), trans.getToState(), trans.getSymbol());
                    Files.write(automataDot, text.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            startStates.forEach(transition -> {
                try {
                    String text = getFormatForStartNode(transition.toString());
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

        try {
            PostToServerForImage(automataDot.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFormatForTransition(Comparable from, Comparable to, char label) {
        return String.format("\"%s\" -> \"%s\" [ label = \"%s\" ]; \n\n", from, to, label);
    }

    private static void clearFile(Path automataDot) throws IOException {
        Files.write(automataDot, "".getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static String getFormatForNode(String tag) {
        return String.format("\"%s\" [ label = \"%s\" ]; \n\n", tag, tag);
    }

    private static String getFormatForStartNode(String tag) {
        String startNode2 = String.format("NOTHING -> \"%s\";\n", tag);
        return startNode2;
    }

    private static void write(Path path, String text) {
        try {
            Files.write(path, text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void PostToServerForImage(File file) throws IOException {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        OkHttpClient client = new OkHttpClient();


        MultipartBody file1 = requestBody.setType(MultipartBody.FORM)
                .addFormDataPart("graph", file.getName(),
                        RequestBody.create(MediaType.parse("text/vnd.graphviz"), file))
                .build();

        Request request = new Request.Builder()
                .url(SERVER_URL)
                .post(file1)
                .build();

        Response response = client.newCall(request).execute();
        Files.write(Paths.get("output/" + response.headers("Content-Disposition").get(0).substring(21)), response.body().bytes());
        System.out.println();
    }

    public static List<RegExp> readRegexFromFile(Path filepath) {
        try {
            //Each line is ONE regexp
            List<String> lines = Files.readAllLines(filepath);
            final List<Stack<RegExp.Operator>> operands = new ArrayList<>();
            final List<List<Character>> terminals = new ArrayList<>();
            Stack<Character> regexStack = new Stack<>();

            for (int i = 0; i < lines.size(); i++) {
                operands.add(new Stack<>());
                terminals.add(new ArrayList<>());
                String line = lines.get(i);
                char[] chars = line.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    if (isOperand(chars[j])){
                        regexStack.push(chars[j]);
                        operands.get(i).push(getOperator(chars[j]));
                    }
                    else {
                        if ((j > 0) && (OPERANDS_WITH_DOT.contains(chars[j-1]) || !isOperand(chars[j-1])) ) {
                            regexStack.push('.');
                            operands.get(i).push(RegExp.Operator.DOT);
                        }
                        terminals.get(i).add(chars[j]);
                        regexStack.push(chars[j]);
                    }

                }

                System.out.println("---------------------");
                System.out.println("regex : "+ line);
                System.out.println("regex-stack : "+ regexStack);
                System.out.println("---------------------");
                System.out.println("regex-operands : " + operands.get(i));
                System.out.println("regex-terminals : " + terminals.get(i));
                System.out.println("---------------------");
            }


            Stack<RegExp.Operator> operandsRegex = operands.get(0);

            Stack<RegExp.Operator> operandsRegexCopy = (Stack<RegExp.Operator>) operandsRegex.clone();

            operandsRegex.push(RegExp.Operator.STAR);

            List<Character> charactersRegex = terminals.get(0);

            Stack<RegExp> regexps = new Stack<>();

            charactersRegex.stream()
                    .map(RegExp::new)
                    .forEach(regexps::push);

            while(operandsRegexCopy.size() > 0){
                RegExp.Operator operator = operandsRegexCopy.pop();
                switch (operator){
                    case PLUS:
                        RegExp regexPlus = regexps.pop();
                        regexps.push(regexPlus.plus());
                        break;
                    case STAR:
                        RegExp regexStart = regexps.pop();
                        regexps.push(regexStart.star());
                        break;
                    case DOT:
                        RegExp regexLeftDot = regexps.pop();
                        RegExp regexRightDot = regexps.pop();
                        regexps.push(regexRightDot.dot(regexLeftDot));
                        break;
                    case LEFTPARENTHESES:
                        break;
                    case RIGHTPARENTHESES:
                        break;
                }
            }

            Thompson thompson = new Thompson();

            writeToFile(thompson.parseAutomata(regexps.get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    private static boolean isOperand(char aChar) {
        return OPERANDS.contains(aChar) || OPERANDS_WITH_DOT.contains(aChar);
    }

    private static RegExp.Operator getOperator(char aChar) {
        switch (aChar) {
            case '*':
                return RegExp.Operator.STAR;
            case '+':
                return RegExp.Operator.PLUS;
            case '.':
                return RegExp.Operator.DOT;
            case '|':
                return RegExp.Operator.OR;
            case '(':
                return RegExp.Operator.LEFTPARENTHESES;
            case ')':
                return RegExp.Operator.RIGHTPARENTHESES;
            default:
                System.out.println("Supplied : " + aChar);
                return RegExp.Operator.ONE;

        }
    }

}
