package org.example;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntryPoint {

    public static void main(String[] args) {
        String argument;
        String filePathName;

        if (args.length > 1) {
            argument = args[0];
            filePathName = args[1];
        } else if (args.length > 0 && args[0].equalsIgnoreCase("-l")) {
            argument = "-l2";
            filePathName = "";
        } else {
            argument = "";
            filePathName = args[0];
        }

        var filename = getFilename(filePathName);
        File file = new File(filePathName);

        switch (argument) {
            case "-c" -> print(getFileSizeInBytes(file), filename);
            case "-l" -> print(getLinesCountInFile(file), filename);
            case "-w" -> print(getWordsCountInFile(file), filename);
            case "-m" -> print(getCharactersCountInFile(file), filename);
            case "-l2" -> System.out.println(getNumberOfLinesFromStdin());

            default -> printDefault(file, filename);
        }

    }

    private static String getFilename(String filePathName) {
        var filename = "";

        String regexFileName = "([^/]+)$";
        Pattern pattern = Pattern.compile(regexFileName);
        Matcher matcher = pattern.matcher(filePathName);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return filename;
    }

    private static void printDefault(File file, String filename) {
        StringBuilder sb = new StringBuilder();
        sb.append(getLinesCountInFile(file)).append("  ")
        .append(getWordsCountInFile(file)).append("  ")
        .append(getFileSizeInBytes(file)).append("  ")
        .append(filename);

        System.out.println(sb);
    }

    private static int getCharactersCountInFile(File file) {
        var charCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while (br.read() != -1) {
                charCount++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return charCount;
    }

    private static int getWordsCountInFile(File file) {
        var wordCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                var words = line.trim().split("\\s+");
                wordCount += words.length;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordCount;
    }

    private static int getLinesCountInFile(File file) {
        var lineCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                lineCount++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    private static long getFileSizeInBytes(File file) {
        return file.length();
    }

    public static void print(Number number, String filename) {
        System.out.println(number + " " + filename);
    }

    public static int getNumberOfLinesFromStdin() {
        int lineCount = 0;

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (br.readLine() != null) {
                lineCount++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineCount;
    }

}
