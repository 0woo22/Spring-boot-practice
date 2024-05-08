package com.github.springprac.web.dto;


import com.github.springprac.respository.items.ItemEntity;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    private String id;
    private String name;
    private String type;
    private Integer price;
    private Spec spec;
}
