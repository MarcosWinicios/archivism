package com.marcos.archivism.service;

import com.marcos.archivism.utils.ArchivismUtil;
import com.marcos.archivism.utils.CsvUtil;
import com.marcos.archivism.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class GenerateFilesService {
    private final static String OUTPUT_DIRECTORY = "outputFiles/";

    public void generateFile(List<String> startList, List<String> simpleNumeric, List<String> terminalDigitNumeric, List<String> chronologic) {
        this.generateFile(startList, simpleNumeric, terminalDigitNumeric, chronologic, "organizedFiles.csv");
    }

        public void generateFile(List<String> startList, List<String> simpleNumeric, List<String> terminalDigitNumeric, List<String> chronologic, String fileName) {
        int listSize = getListSize(startList, simpleNumeric, terminalDigitNumeric, chronologic);
        List<String[]> data = generateData(startList, simpleNumeric, terminalDigitNumeric, chronologic, listSize);

        CsvUtil.printCsvLines(data);

        CsvUtil.generateCsvFile(data, OUTPUT_DIRECTORY, fileName);


    }

    private List<String[]> generateData(List<String> startList, List<String> simpleNumeric, List<String> numericTerminalDigit, List<String> chronologic, int lines){
        List<String[]> result =  new ArrayList<>();
        String[] header = new String[]{"Lista Inicial", "Simples", "Dígito terminal", "Cronológico"};

        for(int i = 0; i < lines; i++){
            result.add(new String[]{
                    startList.get(i),
                    ArchivismUtil.addSeparator(simpleNumeric.get(i), " "),
                    numericTerminalDigit.get(i),
                    chronologic.get(i)
            });
        }
        result.addFirst(header);
        return result;
    }

    private int getListSize(List<String> startList, List<String> simpleNumeric, List<String> numericTerminalDigit, List<String> chronologic){
        if(startList.size() == simpleNumeric.size() && startList.size() == numericTerminalDigit.size() && startList.size() == chronologic.size()){
            return startList.size();
        }

        throw new RuntimeException("As listas geradas possuem tamanho diferente da orginal");
    }

}
