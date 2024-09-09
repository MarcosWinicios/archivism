package com.marcos.archivism.test;

import com.marcos.archivism.service.GenerateFilesService;
import com.marcos.archivism.utils.ArchivismUtil;
import com.marcos.archivism.utils.CsvUtil;
import com.marcos.archivism.utils.ListUtils;

import java.util.List;

public class TerminalDigitTest {
    public static void main(String[] args) {
        List<String[]> readData = CsvUtil.readCsvFile("file1.csv");
        System.out.println("Tamanho da lista inicial: " + readData.size());

        List<String> originalList = ListUtils.handleListArrayToList(readData);

        List<String> terminalNumericList = ArchivismUtil.terminalDigitNumericMethod(originalList);
        System.out.println("Tamanho da lista Ordenada: " + terminalNumericList.size());

//        ArchivismUtil.printTerminalNumericList(terminalNumericList);

        GenerateFilesService service = new GenerateFilesService();
        service.generateFile(originalList, terminalNumericList);
    }






}
