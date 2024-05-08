package com.github.springprac.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuyOrder {
    private Integer itemId;
    private Integer itemNums;

}