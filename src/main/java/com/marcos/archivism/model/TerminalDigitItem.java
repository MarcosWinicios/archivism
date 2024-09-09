package com.marcos.archivism.model;

import com.marcos.archivism.utils.ListUtils;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Data
public class TerminalDigitItem {

    private Integer value;
    private Integer referenceValue;

    public TerminalDigitItem(String value) {
        String referenceValue = value.substring(4, 6);
        this.value = Integer.parseInt(value.replace(".", ""));
        this.referenceValue = Integer.parseInt(referenceValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TerminalDigitItem that = (TerminalDigitItem) o;
        return Objects.equals(referenceValue, that.referenceValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(referenceValue);
    }
}
