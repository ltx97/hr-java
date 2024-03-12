package com.forme.alan.controller.hr;

import com.forme.alan.model.department.DepartmentModel;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/depart")
public class DepartmentController {
    private static DepartmentModel rootDepart;
    static {
        rootDepart = initData();
    }

    //删除部门
    @PostMapping("/delete/{id}")
    public Map deleteDepart(@PathVariable("id") String id) {
        Iterator<DepartmentModel> iterator = rootDepart.getChildren().iterator();
        while (iterator.hasNext()) {
            DepartmentModel next = iterator.next();
            if (next.getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
        Map map = new HashMap();
        map.put("success", true);
        map.put("message", "成功");
        map.put("data", id);
        return map;
    }
    //新增部门
    @PostMapping("/insert")
    public Map insertDepart(@RequestBody DepartmentModel departmentModel) {
        departmentModel.setId(creatId());
        rootDepart.getChildren().add(departmentModel);
        Map map = new HashMap();
        map.put("success", true);
        map.put("message", "成功");
        map.put("data", departmentModel);
        return map;
    }
    //编辑部门
    @PostMapping("/update")
    public Map updateDepart(@RequestBody DepartmentModel departmentModel) {
        for (DepartmentModel child : rootDepart.getChildren()) {
            if (child.getId().equals(departmentModel.getId())) {
                child.setCode(departmentModel.getCode());
                child.setIntroduce(departmentModel.getIntroduce());
                child.setManager(departmentModel.getManager());
                child.setName(departmentModel.getName());
            }
        }
        Map map = new HashMap();
        map.put("success", true);
        map.put("message", "成功");
        map.put("data", departmentModel);
        return map;
    }

    //查询所有部门
    @PostMapping("/queryAll")
    public Map queryAllDepart() {
        Map map = new HashMap();
        map.put("success", true);
        map.put("message", "成功");
        map.put("data", rootDepart);
        return map;
    }
    //查询单个部门
    @PostMapping("/query/{id}")
    public Map queryDepartById(@PathVariable("id")String id) {
        for (DepartmentModel child : rootDepart.getChildren()) {
            if (child.getId().equals(id)) {
                Map map = new HashMap();
                map.put("success", true);
                map.put("message", "成功");
                map.put("data", child);
                return map;
            }
        }
        Map map = new HashMap();
        map.put("success", false);
        map.put("message", "没找到");
        map.put("data", null);
        return map;
    }

    public static DepartmentModel initData() {
        // 初始化数据
        String pid = creatId();
        DepartmentModel rootDepart = DepartmentModel
                .builder()
                .name("总公司")
                .manager("总裁")
                .introduce("最高的单位")
                .code("ZGS")
                .id(pid)
                .build();
        // 子部门
        List<DepartmentModel> childs = new ArrayList<>();

        childs.addAll(initZCB(pid));
        childs.addAll(initXZB(pid));
        childs.addAll(initRSB(pid));
        childs.addAll(initCWB(pid));
        childs.addAll(initJSB(pid));
        childs.addAll(initYYB(pid));
        childs.addAll(initSCB(pid));

        rootDepart.setChildren(childs);
        return rootDepart;
    }

    private static List<DepartmentModel> initSCB(String pid) {
        List<DepartmentModel> models = new ArrayList<>();

        String myId = creatId();
        DepartmentModel rootDepart = DepartmentModel
                .builder()
                .name("市场部")
                .manager("")
                .introduce("这是市场部")
                .code("SCB")
                .id(myId)
                .pid(pid)
                .children(Collections.emptyList())
                .build();

        DepartmentModel child1 = DepartmentModel
                .builder()
                .name("北京事业部")
                .manager("")
                .introduce("这是北京事业部")
                .code("BJSYB")
                .id(creatId())
                .pid(myId)
                .children(Collections.emptyList())
                .build();

        DepartmentModel child2 = DepartmentModel
                .builder()
                .name("上海事业部")
                .manager("")
                .introduce("这是上海事业部")
                .code("SHSYB")
                .id(creatId())
                .pid(myId)
                .children(Collections.emptyList())
                .build();

        models.add(child1);
        models.add(child2);
        models.add(rootDepart);
        return models;
    }

    private static List<DepartmentModel> initYYB(String pid) {
        List<DepartmentModel> models = new ArrayList<>();
        String myId = creatId();
        DepartmentModel rootDepart = DepartmentModel
                .builder()
                .name("运营部")
                .manager("")
                .introduce("这是运营部")
                .code("YYB")
                .id(myId)
                .children(Collections.emptyList())
                .pid(pid)
                .build();
        models.add(rootDepart);
        return models;
    }

    private static List<DepartmentModel> initJSB(String pid) {
        List<DepartmentModel> models = new ArrayList<>();

        String myId = creatId();
        DepartmentModel rootDepart = DepartmentModel
                .builder()
                .name("技术部")
                .manager("")
                .introduce("这是技术部")
                .code("JSB")
                .id(myId)
                .pid(pid)
                .children(Collections.emptyList())
                .build();

        DepartmentModel child1 = DepartmentModel
                .builder()
                .name("Java研发部")
                .manager("")
                .introduce("这是Java研发部")
                .code("JavaYFB")
                .id(creatId())
                .pid(myId)
                .children(Collections.emptyList())
                .build();

        DepartmentModel child2 = DepartmentModel
                .builder()
                .name("Python研发部")
                .manager("")
                .introduce("这是Python研发部")
                .code("PythonYFB")
                .id(creatId())
                .pid(myId)
                .children(Collections.emptyList())
                .build();

        DepartmentModel child3 = DepartmentModel
                .builder()
                .name("Php研发部")
                .manager("")
                .introduce("这是Php研发部")
                .code("PhpYFB")
                .id(creatId())
                .pid(myId)
                .children(Collections.emptyList())
                .build();
        models.add(child1);
        models.add(child2);
        models.add(child3);
        models.add(rootDepart);
        return models;
    }

    private static List<DepartmentModel> initCWB(String pid) {
        List<DepartmentModel> models = new ArrayList<>();

        String myId = creatId();
        DepartmentModel rootDepart = DepartmentModel
                .builder()
                .name("财务部")
                .manager("")
                .introduce("这是财务部")
                .code("CWB")
                .id(myId)
                .pid(pid)
                .children(Collections.emptyList())
                .build();

        DepartmentModel child1 = DepartmentModel
                .builder()
                .name("财务核算部")
                .manager("")
                .introduce("这是财务核算部")
                .code("CWHSB")
                .id(creatId())
                .pid(myId)
                .children(Collections.emptyList())
                .build();

        DepartmentModel child2 = DepartmentModel
                .builder()
                .name("财务管理部")
                .manager("")
                .introduce("这是财务管理部")
                .code("CWGLB")
                .id(creatId())
                .pid(myId)
                .children(Collections.emptyList())
                .build();

        DepartmentModel child3 = DepartmentModel
                .builder()
                .name("薪资管理部")
                .manager("")
                .introduce("这是薪资管理部")
                .code("XZGLB")
                .id(creatId())
                .pid(myId)
                .children(Collections.emptyList())
                .build();
        models.add(child1);
        models.add(child2);
        models.add(child3);
        models.add(rootDepart);
        return models;
    }

    private static List<DepartmentModel> initRSB(String pid) {
        List<DepartmentModel> models = new ArrayList<>();
        DepartmentModel rootDepart = DepartmentModel
                .builder()
                .name("人事部")
                .manager("文吉星")
                .introduce("这是人事部")
                .code("RSB")
                .id(creatId())
                .pid(pid)
                .children(Collections.emptyList())
                .build();
        models.add(rootDepart);
        return models;
    }

    private static List<DepartmentModel> initXZB(String pid) {
        List<DepartmentModel> models = new ArrayList<>();
        DepartmentModel rootDepart = DepartmentModel
                .builder()
                .name("行政部")
                .manager("罗晓晓")
                .introduce("这是行政部")
                .code("XZB")
                .id(creatId())
                .pid(pid)
                .children(Collections.emptyList())
                .build();
        models.add(rootDepart);
        return models;
    }

    private static List<DepartmentModel> initZCB(String pid) {
        List<DepartmentModel> models = new ArrayList<>();
        DepartmentModel rootDepart = DepartmentModel
                .builder()
                .name("总裁办")
                .manager("孙财")
                .introduce("这是总裁办")
                .code("ZCB")
                .id(creatId())
                .pid(pid)
                .children(Collections.emptyList())
                .build();
        models.add(rootDepart);
        return models;
    }

    public static String creatId() {
        return UUID.randomUUID().toString().substring(0, 15);
    }

}
