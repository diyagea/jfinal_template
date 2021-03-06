package com.momathink.tpp;

import java.util.ArrayList;
import java.util.List;

public class _TppTest {

    public static void main(String[] args) {
        String className = "User";
        String tableName = "tb_user";

        JfGenerator.me
        .setSrcFolder("src/main/java")
        .setViewFolder("src/main/webapp")
        .setPackageBase("com")
        .setBasePath("demo")
        //.tableSql(getSqlList())
        .javaRender(className, tableName)
        .htmlRender(className, tableName);

        System.out.println("---------OK-刷新一下项目吧---------");
    }
    
    private static List<String> getSqlList() {
        ArrayList<String> sqlList = new ArrayList<String>();
        
        sqlList.add("DROP TABLE IF EXISTS `t_test`;");
        sqlList.add("CREATE TABLE `t_test` ( " +
                "  `id` int(11) NOT NULL AUTO_INCREMENT, " +
                "  `test` varchar(255) DEFAULT NULL, " +
                "  PRIMARY KEY (`id`) " +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        
        return sqlList;
    }


}
