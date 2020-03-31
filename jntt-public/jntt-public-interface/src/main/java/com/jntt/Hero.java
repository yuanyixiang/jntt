package com.jntt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "hero",type = "museum")
public class Hero {
    private Long id;
    private String name;
    private String birth;
    private String avatar;
    private int type;
    private String zgr1;
    private String zgr2;
    private String zgr3;
    private String zgr4;
    private String data;
}
