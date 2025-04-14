package io.jieun.domain.eg4._1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//repository도 하나의 레이어다, dto, 값만 레이어에서 레이어로 전달한다.
public class CoffeeDto {

    private String name;
    private Integer price;

}
