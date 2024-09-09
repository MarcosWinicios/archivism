package com.marcos.archivism.service;

import com.marcos.archivism.utils.ArchivismUtil;
import com.marcos.archivism.utils.CsvUtil;
import com.marcos.archivism.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class GenerateFilesService {
    private final static String OUTPUT_DIRECTORY = "outputFiles/";

    public void generateFile(List<String> originalList, List<String> targetList) {
        this.generateFile(originalList, targetList, "organizedFiles.csv");
    }

        public void generateFile(List<String> originalList, List<String> targetList, String fileName) {
        int listSize = getListSize(originalList, targetList);

        List<String[]> data = generateData(originalList, targetList, listSize);

        CsvUtil.printCsvLines(data);

        CsvUtil.generateCsvFile(data, OUTPUT_DIRECTORY, fileName);

    }

    private List<String[]> generateData(List<String> originalList, List<String> targetList, int lines){
        List<String[]> result =  new ArrayList<>();
        String[] header = new String[]{"Lista original", "Lista ordenada"};

        for(int i = 0; i < lines; i++){
            result.add(new String[]{
                    originalList.get(i),
                    ArchivismUtil.addSeparator(targetList.get(i), " ")
            });
        }
        result.addFirst(header);
        return result;
    }

    private int getListSize(List<String> originalList, List<String> targetList){
        if(originalList.size() == targetList.size()){
            return originalList.size();
        }

        throw new RuntimeException("As lista gerada possue tamanho diferente da original");
    }

}
