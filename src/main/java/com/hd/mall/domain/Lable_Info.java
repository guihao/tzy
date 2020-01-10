package com.hd.mall.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lable_info")
@Data
public class Lable_Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "lable_name")
    private String lableName;

    @Column(name = "lable_level")
    private String lableLevel;

    @Column(name = "lable_parentid")
    private String lableParentid;

    @Column(name = "is_dir")
    private String isDir;

    @Column(name = "pic_path")
    private String picPath;


}
