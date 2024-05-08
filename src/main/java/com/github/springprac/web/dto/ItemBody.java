package com.github.springprac.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemBody {

    private String name;
    private String type;
    private Integer price;
    private Spec spec;


}