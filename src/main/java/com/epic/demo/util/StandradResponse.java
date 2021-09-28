package com.epic.demo.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StandradResponse {
    private String code;
    private String message;
    private Object data;
}
