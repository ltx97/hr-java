package com.forme.alan.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticeleModel {
    private String title;
    private String author;
    private Integer comments;
    private Date publishTime;
    private List<String> imgs;
    private Integer type;
}
