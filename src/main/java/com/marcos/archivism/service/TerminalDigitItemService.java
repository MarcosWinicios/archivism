package com.marcos.archivism.service;

import com.marcos.archivism.model.TerminalDigitItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TerminalDigitItemService {

    public List<TerminalDigitItem> sortList(List<TerminalDigitItem> input) {
        List<TerminalDigitItem> sortedList = new ArrayList<>(input);
//        this.printList(sortedList);

        sortedList.sort(Comparator.comparing(TerminalDigitItem::getReferenceValue));
        return sortedList;
    }

    public List<Integer> toIntegerList(List<TerminalDigitItem> input) {
        return input.stream()
                .map(item -> Integer.parseInt(item.getValue()))
                .toList();
    }

    public List<String> toStringList(List<TerminalDigitItem> input) {
        return input.stream()
//                .map(item -> String.format("%0" + 6 + "d", item.getValue()))
                .map(TerminalDigitItem::getValue)
                .toList();
    }

    public void printList(List<TerminalDigitItem> input){
        input.forEach(System.out::println);
    }
}
