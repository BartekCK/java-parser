package com.parser.power.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Converter implements Serializable {
    //TODO: Model fields
    private String name;
    private String surname;

    @Override
    public String toString() {
        return "Converter{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
