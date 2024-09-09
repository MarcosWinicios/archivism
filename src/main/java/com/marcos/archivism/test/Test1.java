package com.marcos.archivism.test;

import com.marcos.archivism.service.GenerateFilesService;
import com.marcos.archivism.utils.ArchivismUtil;
import com.marcos.archivism.utils.CsvUtil;
import com.marcos.archivism.utils.ListUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        List<String[]> readData = CsvUtil.readCsvFile("file1.csv");

        System.out.println("Tamanho da lista inicial: " + readData.size());

        List<String> strList = ListUtils.handleListArrayToList(readData);

        List<String> simpleNumericList = ArchivismUtil.simpleNumericMethod(strList);
        List<String> terminalNumericList = ArchivismUtil.terminalNumericMethod(strList);
        List<String> chronologicList = ArchivismUtil.chronologicalMethod(strList);

//        ArchivismUtil.printSimpleNumericList(simpleNumericList);
//        ArchivismUtil.printTerminalNumericList(terminalNumericList);
//        ArchivismUtil.printChronologicList(chronologicList);

        GenerateFilesService service = new GenerateFilesService();

        service.generateFile(strList, simpleNumericList, terminalNumericList, chronologicList);

    }






}
