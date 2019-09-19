package com.qding.bigdata.ds.util;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLAggregateExpr;
import com.alibaba.druid.sql.ast.expr.SQLAllColumnExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectQueryBlock;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGOutputVisitor;
import com.alibaba.druid.util.JdbcConstants;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.deparser.StatementDeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author yanpf
 * @date 2018/2/2 10:51
 * @description
 */
public class SQLCountUtil {


    private static Logger logger = LoggerFactory.getLogger(SQLCountUtil.class);
    /**
     * 只能转换GP
     * @param sql
     * @return
     */
    @Deprecated
    private static String getCountSqlDruid(String sql){
        String dbType = JdbcConstants.POSTGRESQL;

        String format = SQLUtils.format(sql, dbType);
        List<SQLStatement> sqlStatements =
                SQLUtils.parseStatements(format, dbType);
        for (SQLStatement statement : sqlStatements) {
            StringBuilder sb = new StringBuilder();
            PGOutputVisitor visitor = new PGOutputVisitor(sb);
            SQLSelectStatement real = (SQLSelectStatement) statement;
            SQLSelectQuery selectQuery = real.getSelect().getQuery();
            PGSelectQueryBlock block = (PGSelectQueryBlock) selectQuery;
            block.setOrderBy(null);
            block.setLimit(null);
            block.setOffset(null);
            SQLAggregateExpr countItem = new SQLAggregateExpr("COUNT");
            countItem.getArguments().add(new SQLAllColumnExpr());
            block.getSelectList().clear();
            block.getSelectList().add(new SQLSelectItem(countItem));
            real.getSelect().setQuery(block);
            real.accept(visitor);

            return sb.toString();
        }

        return null;
    }

    public static String getCountSql(String sql) {
        Statement statement = null;
        logger.info("解析sql为count结构");
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            logger.error("解析sql失败, e");
        }
        Select select = ((Select) statement);
        SelectBody selectBody = select.getSelectBody();
        //SelectVisitor visitor = new
        if(selectBody instanceof PlainSelect) {
            PlainSelect plainSelect = ((PlainSelect) selectBody);
            plainSelect.setLimit(null);
            plainSelect.setOffset(null);
            plainSelect.setOrderByElements(null);
            StringBuilder sb = new StringBuilder();
            StatementDeParser parser = new StatementDeParser(sb);
            select.setSelectBody(plainSelect);

            if (plainSelect.getGroupByColumnReferences() == null) {
                plainSelect.getSelectItems().clear();
                Function count = new Function();
                count.setName("count");
                count.setAllColumns(true);
                plainSelect.getSelectItems().add(new SelectExpressionItem(count));
                select.accept(parser);
                return parser.getBuffer().toString();
            } else {
                select.accept(parser);
                return "select count(*) from (" + parser.getBuffer().toString() + ") t";
            }
        }else{
            logger.error("不支持的sql查询类型， sql:{}", sql);
            return null;
        }
    }
}
