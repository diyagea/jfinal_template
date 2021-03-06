package com.momathink.tpp;

import java.util.List;

import javax.sql.DataSource;

import com.demo.common.DemoConfig;
import com.demo.common.model._JFinalDemoGenerator;
import com.jfinal.config.Constants;
import com.jfinal.config.Plugins;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.generator.DataDictionaryGenerator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;

/**
 * 代码生成器
 * @author dufuzhong
 */
public class JfGenerator {
    
    public static final JfGenerator me  = new JfGenerator();
    protected final JfEnjoy jfEngine    = new JfEnjoy();
    
    protected Kv tablemetaMap       = null;
    protected String packageBase    = "com.momathink";
    protected String srcFolder      = "src";
    protected String viewFolder     = "WebRoot";
    protected String basePath       = "";
    
    public JfGenerator setPackageBase(String packageBase){
        this.packageBase = packageBase;
        return this;
    }
    
    public JfGenerator setBasePath(String basePath){
        this.basePath = basePath;
        return this;
    }
    
    public JfGenerator setSrcFolder(String srcFolder){
        this.srcFolder = srcFolder;
        return this;
    }
    
    public JfGenerator setViewFolder(String viewFolder){
        this.viewFolder = viewFolder;
        return this;
    }
    
    protected class DataGenerator extends DataDictionaryGenerator {
        public DataGenerator(DataSource dataSource, String dataDictionaryOutputDir) {
            super(dataSource, dataDictionaryOutputDir);
        }
        public void rebuildColumnMetas(List<TableMeta> tableMetas) {
            super.rebuildColumnMetas(tableMetas);
        }
    };
    
    public TableMeta getTableMeta(String tableName){
        if( tablemetaMap == null ){
            
            DataSource dataSource = _JFinalDemoGenerator.getDataSource();
            
            MetaBuilder metaBuilder = new MetaBuilder(dataSource);
            //设置数据库方言
            metaBuilder.setDialect(_JFinalDemoGenerator.getDialect());
            //排除不需要生成的表
            metaBuilder.addExcludedTable(_JFinalDemoGenerator.excludedTable);
            List<TableMeta> tableMetas = metaBuilder.build();
            new DataGenerator(dataSource, null).rebuildColumnMetas(tableMetas);
            
            if (tableMetas.size() == 0) {
                System.out.println("TableMeta 数量为 0，不生成任何文件");
                return null;
            }
            Kv kv = Kv.create();
            for (TableMeta tableMeta : tableMetas) {
                kv.set(tableMeta.name, tableMeta);
            }
            tablemetaMap = kv;
        }
        return (TableMeta) tablemetaMap.get(tableName);
    }
    
    /**
     * 生成手脚架代码
     */
    public JfGenerator allRender(String className, String tableName) {
        return javaRender(className, tableName).htmlRender(className, tableName);
    }
    
    /**
     * java 代码生成
     * */
    public JfGenerator javaRender(String className, String tableName) {
        //刷新 映射对象
        _JFinalDemoGenerator.main(null);
        
        return controller(className).validator(className).service(className , tableName);
    }
    
    private String toClassNameSmall(String className) {
        return new StringBuffer(className.substring(0, 1).toLowerCase()).append(className.substring(1)).toString();
    }
    
    private String toPackages(String className) {
        return new StringBuffer(packageBase).append(".").append(basePath)
                .append(".").append(className.toLowerCase()).toString();
    }
    
    /**
     * 生成数据库表
     */
    public JfGenerator tableSql(List<String> sqlList){
        DemoConfig config       = new DemoConfig();
        Plugins plugins         = new Plugins();
        config.configConstant(new Constants());
        config.configPlugin(plugins);
        for (IPlugin iPlugin: plugins.getPluginList())
            iPlugin.start();
        
        Db.batch(sqlList, 200);
        return this;
    }
    
    /**
     * 生成数据库表
     * TODO tableMeta待完成..
     */
    public JfGenerator tableMeta(List<TableMeta> tableMetas){
        return this;
    }
    
    /**
     * 生成Controller
     * @param className         类名称
     */
    public JfGenerator controller(String className){
        String packages = toPackages(className);
        
        String classNameSmall = toClassNameSmall(className);
        
        jfEngine.render("/java/controller.html", 
                Kv.by("package", packages)
                .set("className", className)
                .set("classNameSmall", classNameSmall)
                .set("basePath", basePath )
                , 
                new StringBuilder()
                .append(System.getProperty("user.dir"))
                .append("/")
                .append(srcFolder)
                .append("/")
                .append(packages.replace(".", "/"))
                .append("/")
                .append(className)
                .append("Controller.java")
                );
        return this;
    }
    
    /**
     * 生成validator
     * @param className         类名称
     */
    public JfGenerator validator(String className){
        String packages = toPackages(className);
        
        String classNameSmall = toClassNameSmall(className);
        
        jfEngine.render("/java/validator.html", 
                Kv.by("package", packages)
                .set("className", className)
                .set("classNameSmall", classNameSmall)
                , 
                new StringBuilder()
                .append(System.getProperty("user.dir"))
                .append("/")
                .append(srcFolder)
                .append("/")
                .append(packages.replace(".", "/"))
                .append("/")
                .append(className)
                .append("Validator.java")
                );
        return this;
    }
    
    /**
     * 生成Service
     * @param className         类名称
     * @param tableName         表名
     */
    public JfGenerator service(String className, String tableName){
        String packages = toPackages(className);

        String classNameSmall = toClassNameSmall(className);
        
        jfEngine.render("/java/service.html", 
                Kv.by("package", packages)
                .set("className", className)
                .set("classNameSmall", classNameSmall)
                .set("tableName", tableName)
                , 
                new StringBuilder()
                .append(System.getProperty("user.dir"))
                .append("/")
                .append(srcFolder)
                .append("/")
                .append(packages.replace(".", "/"))
                .append("/")
                .append(className)
                .append("Service.java")
                );
        return this;
    }
    
    /**
     * @param className 
     * @param tableName 
     * */
    public JfGenerator htmlRender(String className, String tableName){
        TableMeta tablemeta = getTableMeta(tableName);
        
        return htmlList(className, tablemeta);
    }
    
    
    public JfGenerator htmlList(String className, TableMeta tablemeta){
        String packages = toPackages(className);
        String classNameSmall = toClassNameSmall(className);
        String basePathUrl = basePath.replace('.', '/');
        
        jfEngine.render("/html/list.html", 
                Kv.by("tablemeta", tablemeta)
                .set("package", packages)
                .set("className", className)
                .set("classNameSmall", classNameSmall)
                .set("basePath", basePathUrl )
                , 
                new StringBuilder()
                .append(System.getProperty("user.dir"))
                .append("/")
                .append(StrKit.isBlank(viewFolder) ? "" : viewFolder)
                .append(StrKit.isBlank(viewFolder) ? "" : "/")
                .append(classNameSmall)
                .append("/")
                .append(classNameSmall)
                .append("List.html")
                );
        return this;
    }

   
    
    
}


