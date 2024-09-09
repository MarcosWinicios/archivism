package com.marcos.archivism.model;

import lombok.Data;

import java.util.Objects;

@Data
public class TerminalDigitItem {

    private String value;
    private Integer referenceValue;

    public TerminalDigitItem(String value) {
        String referenceValue = value.substring(5, 7);
        this.value = value.replace(".", "");
        this.referenceValue = Integer.parseInt(referenceValue);
    }


    @Override
    public String toString() {
        return "TerminalDigitItem{" +
                "value='" + value + '\'' +
                ", referenceValue=" + referenceValue +
                '}';
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
