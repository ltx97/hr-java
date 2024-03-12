package com.forme.alan.model.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentModel {
    // 部门名称
    private String name;
    // 部门编码
    private String code;
    // 部门管理者
    private String manager;
    // 部门介绍
    private String introduce;

    // 部门id
    private String id;
    // 父部门id
    private String pid;
    // 子部门
    private List<DepartmentModel> children = new ArrayList<>();
}
