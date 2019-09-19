package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.Exception.ExecuteSqlExcetion;
import com.qding.bigdata.ds.Exception.JDBCConnExcetion;
import com.qding.bigdata.ds.Exception.JDBCExeceteExcetion;
import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.SchemeDao;
import com.qding.bigdata.ds.dao.SchemeExecutorDao;
import com.qding.bigdata.ds.enums.OperateType;
import com.qding.bigdata.ds.enums.SchmeType;
import com.qding.bigdata.ds.enums.UpdateType;
import com.qding.bigdata.ds.model.SchemaFiled;
import com.qding.bigdata.ds.model.SchemeInfo;
import com.qding.bigdata.ds.service.SchemeService;
import com.qding.bigdata.ds.util.JdbcTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by QDHL on 2017/7/25.
 */
@Service
public class SchemeServiceImpl extends  BaseServiceImpl<SchemeInfo> implements SchemeService {
    protected Log log = LogFactory.getLog(this.getClass());
    final static String PREFIX = "COMMENT ON COLUMN ";
    @Autowired
    private SchemeDao schemeDao;

    private SchemeExecutorDao schemeExecutorDao;

    @Resource(name = "sqlSessionExecutor")
    public void setSchemeExecutorDao(SqlSession sqlSession) {
        this.schemeExecutorDao = sqlSession.getMapper(SchemeExecutorDao.class);
    }

    @Override
    public void executeCreateTableSql(String sql) {
         schemeExecutorDao.execute(sql);
    }

    @Override
    public SchemeInfo queryByID(String id) {
        return schemeDao.queryById(id);
    }
    @Override
    public SearchResult<SchemeInfo> queryList(SchemeInfo param) {
        SearchResult<SchemeInfo> result = new SearchResult<SchemeInfo>();
        result.setCurrentPage(param.getPage());
        result.setPageCount(param.getPageCount());
        List<SchemeInfo> list = schemeDao.list(param);
        result.setRows(list);
        int totalCount = schemeDao.count(param);
        result.setTotal(totalCount);
        return result;
    }
    @Override
    public SchemeInfo buidGPSchemeFromMysql(SchemeInfo info , OperateType type) {


        try {
            //1  查询mysql的queryMysqlScheme
            List<SchemaFiled> fileds = queryMysqlScheme(info);
            // 2 检查
            checkAndDropScheme(info);
            //3 根据Scheme 类型在具体的表
            createScheme(info,  fileds);
            //4
            savaOrUpdate(info);

        } catch (Exception e) {
            info.setStatus(0);
            info.setMessage(e.getMessage());
        }
        return info;
    }

    public SchemeInfo savaOrUpdate(SchemeInfo info) {
        try {
            info.setStatus(1);
            if (StringUtils.isEmpty(info.getId())) {
                this.save(info);
            } else {
                this.update(info);
            }
        }catch(Exception e){
            log.error(e);
            info.setStatus(0);
            info.setMessage("保存同步scheme记录到数据库异常");
        }
        return info;
    }

    @Override
    public boolean hasSchemeRecoder(SchemeInfo param) {
        return isExistTable(param,SchmeType.ODS.getValue());
    }

    private void createScheme(SchemeInfo info, List<SchemaFiled> fileds) throws ExecuteSqlExcetion, SQLException {
        if (info.getUpdateType()==UpdateType.INCRE.getIndex()) { //TODO  换成枚举
            doCreateGPScheme(info, fileds, SchmeType.ODS.getValue());
            doCreateGPScheme(info, fileds, SchmeType.STG.getValue());
        } else {
            doCreateGPScheme(info, fileds, SchmeType.ODS.getValue());
        }
    }

    // 不执行删除提题
   private void checkAndDropScheme(SchemeInfo info) throws ExecuteSqlExcetion{
       if (info.getUpdateType()==UpdateType.INCRE.getIndex()) {
            deleteGpcheme(info,SchmeType.ODS.getValue());
            deleteGpcheme(info,SchmeType.STG.getValue());
          }else{
               deleteGpcheme(info,SchmeType.ODS.getValue());
          }
        }

   private void  deleteGpcheme(SchemeInfo info,String gpScheme) throws ExecuteSqlExcetion{
        if(isExistTable( info, gpScheme)){
            dropTable(info,gpScheme);
        }
   }

    private boolean isExistTable(SchemeInfo info,String gpScheme) {
        boolean isExist=false;
        gpScheme=gpScheme.substring(0,3);
        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name ='" + gpScheme+"_"+info.getTableName() + "' and table_schema = '" + gpScheme + "'";
        String res= schemeExecutorDao.checkIsExistTable(sql);
        if(res!=null){
            isExist=true;
        }
        return isExist;
    }

    private void dropTable(SchemeInfo info, String scheme_type) throws ExecuteSqlExcetion {
        String fullName = scheme_type + info.getTableName();
        try {
            String sql = "drop table " + fullName;
            schemeExecutorDao.execute(sql);
        } catch (Exception e) {
            String error = "删除表" + fullName + "异常";
            log.error(error, e);
            throw new ExecuteSqlExcetion(error);
        }
    }

    private List<SchemaFiled> queryMysqlScheme(SchemeInfo info) throws JDBCConnExcetion, JDBCExeceteExcetion {
        JdbcTemplate jdbcTemplate =JdbcTemplateUtil.getJdbcTemplate(info);
        List<SchemaFiled> fileds=querySchmeFileds(info,jdbcTemplate);
        return fileds;
    }
    private  List<SchemaFiled> querySchmeFileds(SchemeInfo info,JdbcTemplate jdbcTemplate  ) throws JDBCConnExcetion ,JDBCExeceteExcetion{
        String sql=buidQuerySchemeSql(info.getTableName(),info.getDbName());
        try {
            List<SchemaFiled> fields=jdbcTemplate.query(sql, new BeanPropertyRowMapper<SchemaFiled>(SchemaFiled.class));
            return fields;
        } catch (DataAccessException e) {
            log.error("执行sql异常 sql="+sql);
            log.error("执行sql异常",e);
            throw new JDBCExeceteExcetion("检查传入的表名是否正确");
        }
    }
    public void doCreateGPScheme(SchemeInfo info, List<SchemaFiled> fileds, String scheme_type) throws ExecuteSqlExcetion {
        try {
            String ddl = buidCreateTableSql(fileds, info.getTableName(), scheme_type, info.getUpdateType());
            System.out.println(ddl);
            executeCreateTableSql(ddl);
        } catch (Exception e) {
            String error = "创建" + scheme_type + info.getTableName() + "表错误";
            log.error(error, e);
            throw new ExecuteSqlExcetion(error);
        }

    }

    private String buidQuerySchemeSql(String tableName, String dbName) {
        return "select COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT from information_schema.COLUMNS  where TABLE_NAME='" + tableName + "' and table_schema = '" + dbName + "'";
    }


    // 构建建表语句 ODS
    private String buidCreateTableSql(List<SchemaFiled> fileds, String tableName, String gpScheme, int update_Type) {
        StringBuffer strBuffer = new StringBuffer();
        StringBuffer commmetBuffer = new StringBuffer();
        strBuffer.append("CREATE TABLE ").append(gpScheme).append(tableName).append(" (").append(System.getProperty("line.separator"));
        int index=1;
        String dis_field="";
        try {
            for(SchemaFiled filed: fileds)
            {
                //1读取查询结果
                String field = filed.getColumn_name();
                String data_type = filed.getData_type();
                String commmet = filed.getColumn_comment();
                //2构建表的字段信息
                buidFiled(strBuffer, field, data_type);
                //3 构建注释 信息
                if (!commmet.isEmpty()) {
                    buidComment(commmetBuffer, tableName, gpScheme, field, commmet);
                }
                if(index==1){
                    dis_field= field;
                }
                index++;
            }
            buidOtherField(dis_field, update_Type, strBuffer);
            writeToBuffer(strBuffer, commmetBuffer.toString());
        } catch (SQLException e) {
            log.error("buidCreateTableSql error", e);
        }
        return strBuffer.toString();
    }

    private void buidOtherField(String dis_field, int update_Type, StringBuffer strBuffer) {
        boolean conditon=(update_Type==UpdateType.INCRE.getIndex());
        if (conditon) {
            writeToBuffer(strBuffer, "expire_date text,");
            writeToBuffer(strBuffer, "update_date text,");
        }

        int x = strBuffer.lastIndexOf(",");
        if(x != -1) {
            strBuffer = strBuffer.replace(x, x + 1, "");
        }
        writeToBuffer(strBuffer, ")");
        writeToBuffer(strBuffer, "DISTRIBUTED BY ("+dis_field+");");
    }


    // 构建表注释语句
    private void buidComment(StringBuffer strBuffer, String tableName, String gpScheme, String field, String comment) throws SQLException {
        strBuffer.append(PREFIX).append(gpScheme).append(tableName).append(".").append(field).append(" IS '").append(comment).append("';");
        strBuffer.append(System.getProperty("line.separator"));

    }

    private static void writeToBuffer(StringBuffer strBuffer, String line) {
        strBuffer.append(line);
        strBuffer.append(System.getProperty("line.separator"));// 行与行之间的分割
    }

    private void buidFiled(StringBuffer buffer, String field, String type) {
        type = replaceFiledType(type);
        buffer.append(field).append(" ").append(type).append(",");
        buffer.append(System.getProperty("line.separator"));

    }

    private static String replaceFiledType(String data_type) {
        if (data_type.contains("varchar")) {
            data_type = "text";
        } else if (data_type.contains("bigint")) {
            data_type = "int8";
        } else if (data_type.contains("int")) {
            data_type = "int4";
        } else if (data_type.contains("datetime")) {
            data_type = "timestamp(0)";
        } else if(data_type.contains("decimal")){
            data_type = "decimal(12,2)";
        } else {
            data_type = "text";
        }
        return data_type;
    }
    @Override
    public BaseDao<SchemeInfo> getDao() {
        return schemeDao;
    }
}
