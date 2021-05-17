package com.parser.power.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsvNodeDto {
    private Integer column;
    private String fullName;
    private String name;
    private String parentName;
}
