package com.example.jackrabbit.domain;

import lombok.Data;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;

import java.util.Date;

@Data
@Node
public class Hello {

    @Field(path = true)
    String path;

    @Field(id = true)
    Long id;

    @Field
    String title;

    @Field
    Date pubDate;

    @Field
    String content;

}
