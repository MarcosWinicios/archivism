package com.marcos.archivism.utils;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

@Component
public class CsvUtil {

    private static final String INPUT_BASE_PATH = "inputFiles/";
    private static final String OUTPUT_BASE_PATH = "outputFiles/";

    public static String getInputBasePath() {
        return INPUT_BASE_PATH;
    }

    public static String getOutputBasePath() {
        return OUTPUT_BASE_PATH;
    }

    /**
     * <b>Use this method when you want to get only the rows that contain a certain value in a certain column</b>
     *
     * @param fileName      Name of the CSV file to be read
     * @param filterValue   Value to be used in the filter.
     * @param indexPosition Position of the column where the <b>filterValue</b> value is located
     */
    public static List<String[]> readCsvFile(String fileName, String filterValue, int indexPosition) {
        return readCsvFileWithFilterByColumnValue(fileName, filterValue, indexPosition);
    }

    /**
     * <b>Use this method when you want to get the entire contents of a CSV file</b>
     *
     * @param fileName Name of the CSV file to be read
     */
    public static List<String[]> readCsvFile(String fileName) {
        return readCsvFileWithFilterByColumnValue(fileName, null, -1);
    }

    /**
     * @param data          Informações que irão compor o conteúdo do arquivo CSV
     * @param directoryPath Diretório alvo onde o arquivo será salvo
     * @param fileName      Nome do arquivo a ser gerado
     */
    public static void generateCsvFile(List<String[]> data, String directoryPath, String fileName) {

        try {
            directoryPath = directoryPath.endsWith("/") ? directoryPath : directoryPath + "/";
            fileName = (fileName.endsWith(".csv") ? fileName.replace(".csv", "") : fileName);
            String fileFullName = directoryPath + fileName + ".csv";

            System.out.println("Gerando arquivo CSV: " + fileFullName);

            File directory = new File(fileFullName).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Criar o arquivo se não existir
            File file = new File(fileFullName);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(fileFullName);
            CSVWriter cw = new CSVWriter(fw,
                    ';',//Delimitador
                    '\'',//Caractere de aspas simples
                    CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            // Processar os dados para remover aspas duplas
            for (String[] line : data) {
                for (int i = 0; i < line.length; i++) {
                    if (line[i] != null) {
                        line[i] = line[i].replace("\"", "\"");  // Remove aspas duplas
                    }
                }
                cw.writeNext(line);
            }

//            cw.writeAll(data);

            System.err.println("\nArquivo CSV gerado com sucesso em: " + file.getAbsolutePath() + "\n");

            cw.close();
            fw.close();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //Usefull methods to print data

    public static void printCsvFile(String fileName) {
        printCsvLines(Objects.requireNonNull(readCsvFile(fileName)));
    }

    public static void printCsvLines(List<String[]> lines) {


//        System.out.println("-------------");
//        IntStream.range(0, lines.size())
//                .mapToObj((index) -> { //Utilizar o mapToObj pois ao contrário do map(), aceita uma função Function. o Map só aceita UnaryOperation
//                    String[] array = lines.get(index);
//                    return "[" + index + "] = " + Arrays.toString(array);
//                })
//                .forEach(System.out::println);

        /*Podem ser utilizados caso não queira printar os indices da Lista*/
        /*
        lines.stream()
                .map((x) -> String.join(", ", x)
                ).forEach(System.out::println);

*/
        lines.stream()
                .map(Arrays::toString)
                .forEach(System.out::println);


    }

    public static void printLine(String[] line) {
        System.out.println(String.join(", ", line));
    }

    @Deprecated
    public static void printCsvLinesOld(List<String[]> lines) {
        for (String[] line : lines) {
            for (String field : line) {
                System.out.print(field + " | ");
            }
            System.out.println(); // Pula para a próxima linha
        }
    }

    //private methods

    private static String removeFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(0, dotIndex);
        } else {
            return fileName; // Retorna o nome completo se não houver ponto ou se estiver no início/final
        }
    }

    private static Map<String, Character> detectDelimiterAndSeparator(String fileName) {

        String pathName = getInputFilePath(fileName);

        char[] possibleSeparators = {',', ';', '\t', '|', ' '};
        char[] possibleDelimiters = {'"', '\'', ' '};
        Map<String, Character> result = new HashMap<>();
        Map<Character, Integer> separatorCounts = new HashMap<>();
        Map<Character, Integer> delimiterCounts = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            String line = br.readLine(); // Lê a primeira linha para análise

            if (line != null) {
                // Conta a frequência de cada possível separador na linha
                for (char sep : possibleSeparators) {
                    int count = line.length() - line.replace(String.valueOf(sep), "").length();
                    separatorCounts.put(sep, count);
                }

                // Conta a frequência de cada possível delimitador na linha
                for (char delim : possibleDelimiters) {
                    int count = line.length() - line.replace(String.valueOf(delim), "").length();
                    delimiterCounts.put(delim, count);
                }

                // Determina o separador mais provável (maior contagem)
                char probableSeparator = separatorCounts.entrySet().stream()
                        .max(Map.Entry.comparingByValue()).get().getKey();
                result.put("separator", probableSeparator);

                // Determina o delimitador mais provável (maior contagem), excluindo espaço em branco
                char probableDelimiter = delimiterCounts.entrySet().stream()
                        .filter(entry -> entry.getKey() != ' ')
                        .max(Map.Entry.comparingByValue()).get().getKey();
                result.put("delimiter", probableDelimiter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static List<String[]> readCsvFileWithFilterByColumnValue(String fileName, String filterValue, int indexPosition) {
        String pathName = getInputFilePath(fileName);
        CSVParser parser = getParserFile(fileName);

        System.err.println("\n>> Lendo arquivo: " + fileName + "\n");

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(pathName))
                .withCSVParser(parser)
                .build()) {
            List<String[]> lines = reader.readAll();


            if (filterValue == null && indexPosition == -1) {
                return lines;
            }
            String[] header = lines.get(0);
            List<String[]> result = new ArrayList<>();
            result.add(header);
            result.addAll(filterRowsFromCsvFiles(lines, filterValue, indexPosition));

            return result;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getInputFilePath(String fileName) {
        return INPUT_BASE_PATH + removeFileExtension(fileName) + ".csv";
    }

    private static List<String[]> filterRowsFromCsvFiles(List<String[]> lines, String filterValue, int indexPosition) {

        return lines.stream()
                .skip(1)
                .filter(line -> line[indexPosition].equals(filterValue))
                .toList();
    }

    private static CSVParser getParserFile(String fileName) {
        Map<String, Character> separatorAndDelimiter = detectDelimiterAndSeparator(fileName);

        printFileFormat(separatorAndDelimiter);
        return new CSVParserBuilder()
                .withSeparator(separatorAndDelimiter.get("separator"))
                .withQuoteChar(separatorAndDelimiter.get("delimiter"))
                .build();
    }

    private static void printFileFormat(Map<String, Character> fileFormat) {
        System.err.println("\n>> Formater CSV File: " + fileFormat);
    }

}