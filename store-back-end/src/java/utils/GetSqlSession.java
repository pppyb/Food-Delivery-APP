package com.xzr.glassfishDemo.utils;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLOutput;

public class GetSqlSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetSqlSessionFactory.class);

    private static ThreadLocal<SqlSession> tl = new ThreadLocal<SqlSession>();

    /**
     * 获取SqlSession
     * @return sqlSession
     */
    public static SqlSession getSqlSession(){
        SqlSession sqlSession = tl.get();
        if (sqlSession == null){
            sqlSession = GetSqlSessionFactory.getSqlSessionFactory().openSession();
            tl.set(sqlSession);
        }
        LOGGER.info("Get SqlSession hashCode : {}." , sqlSession.hashCode());
        return sqlSession;
    }

    /**
     *  提交事物
     */
    public static void commit(){
        if (tl.get() != null){
            tl.get().commit();
            tl.get().close();
            tl.set(null);
            LOGGER.info("SqlSession commit.");
        }
    }

    /**
     *  回滚事务
     */
    public static void rollback(){
        if (tl.get() != null){
            tl.get().rollback();
            tl.get().close();
            tl.set(null);
            LOGGER.info("SqlSession rollback.");
        }
    }

}
