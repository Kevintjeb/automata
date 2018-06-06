package fileservice ;

import automata.Automata;
import automata.Transition;
import okhttp3.*;
import regex.RegExp;
import regex.Thompson;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;


public class FileIO {

    private static String BASE_PATH = "output/";
    private static String SERVER_URL = "http://vpn.kevinvandenbroek.nl:3001/graph";
    private static List<Character> OPERANDS = Arrays.asList('|', '(', '.');
    private static List<Character> SINGLE_EFFECT_OPERANDS = Arrays.asList('+', '*');
    private static List<Character> OPERANDS_WITH_DOT = Arrays.asList(')', '*', '+');
    private static List<Character> OPERANDS_NO_PARENTHESIS = Arrays.asList('*', '+', '.', '|');

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
            SortedSet<?> finalStates = automata.getFinalStates();

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

            finalStates.forEach(o -> {
                try {
                    String text = getFormatForEndState(o.toString());
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

    private static String getFormatForEndState(Comparable<String> endState) {

        //, peripheries=2, style=filled, color=yellowgreen]
        return String.format("\"%s\" [ peripheries=2, style=filled, color=yellowgreen ]", endState);
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

    public static List<RegExp> readRegexFromFile(Path filepath) {
        try {
            //Each line is ONE regexp
            List<String> lines = Files.readAllLines(filepath);

            List<RegExp> regexps = lines.stream().map(FileIO::readRegexFromString).collect(Collectors.toList());

            return regexps;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public static RegExp readRegexFromString(String regex) {
        final List<Character> tokens = new ArrayList<>();

        char[] chars = regex.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            if (isOperator(chars[j])) {
                tokens.add(chars[j]);
                if ((j > 0 && j != chars.length - 1) && (isOpeningParenthesis(chars[j + 1])) && !isOrOperator(chars[j])) {
                    tokens.add('.');
                }
            } else {
                if ((j > 0) && (OPERANDS_WITH_DOT.contains(chars[j - 1]) || !isOperator(chars[j - 1]))) {
                    tokens.add('.');
                }

                tokens.add(chars[j]);
                if((j != chars.length -1) && isOpeningParenthesis(chars[j + 1])){
                    tokens.add('.');
                }
            }
        }

        System.out.println("---------------------");
        System.out.println("regex : " + regex);
        System.out.println("---------------------");
        System.out.println("tokens : " + tokens);
        System.out.println("---------------------");

        List<RegExp> regexps = new ArrayList<>();

        Stack<Character> operators = new Stack<>();
        Stack<RegExp> operands = new Stack<>();

        for (Character token : tokens) {
            if (isOperatorNoParenthesis(token)) {

                if (isSingleEffectToken(token)) {
                    operators.push(token);
                    processOperator(operators, operands);
                    continue;
                }

                while (!operators.isEmpty() && isOperatorNoParenthesis(operators.peek())) {
                    processOperator(operators, operands);
                }

                operators.push(token);
            } else if (isOpeningParenthesis(token)) {
                operators.push(token);
            } else if (isClosingParenthesis(token)) {

                while (!isOpeningParenthesis(operators.peek()))
                    processOperator(operators, operands);

                operators.pop();
            } else {
                operands.push(new RegExp(token));
            }
        }

        while (!operators.isEmpty()) {
            processOperator(operators, operands);
        }

        regexps.add(operands.pop());


        Thompson thompson = new Thompson();


        Automata automata = thompson.parseAutomata(regexps.get(0));
        automata.printInfo();

        Automata automata1 = automata.NDFAtoDFA();

        automata1.printInfo();

        FileIO.writeToFile(automata);


        return regexps.get(0);

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
        String filename = response.headers("Content-Disposition").get(0).substring(21);
        System.out.println("Writing to file with filename :\n" + filename);
        Files.write(Paths.get("output/" + filename), response.body().bytes());
    }

    public static boolean isOrOperator(Character token) {
        return token.equals('|');
    }

    private static boolean isSingleEffectToken(Character token) {
        return SINGLE_EFFECT_OPERANDS.contains(token);
    }

    private static boolean isOperatorNoParenthesis(Character token) {
        return OPERANDS_NO_PARENTHESIS.contains(token);
    }


    private static boolean isOpeningParenthesis(Character token) {
        return token.equals('(');
    }

    private static boolean isClosingParenthesis(Character token) {
        return token.equals(')');
    }

    private static void processOperator(Stack<Character> operators, Stack<RegExp> operands) {

        Character operator = operators.pop();
        System.out.println("processing " + operator);

        switch (operator) {
            case '*':
                operands.push(operands.pop().star());
                break;
            case '+':
                operands.push(operands.pop().plus());
                break;
            case '|':
                RegExp leftOr = operands.pop();
                RegExp rightOr = operands.pop();

                operands.push(rightOr.or(leftOr));
                break;
            case '.':
                RegExp left = operands.pop();
                RegExp right = operands.pop();

                operands.push(right.dot(left));
                break;
        }
    }


    private static boolean isOperator(char aChar) {
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
