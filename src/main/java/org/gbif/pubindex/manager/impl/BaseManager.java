/**
 *
 */
package org.gbif.pubindex.manager.impl;

import org.gbif.pubindex.model.Identifiable;

import java.io.IOException;
import java.util.List;

import com.google.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base of all manager implementations that use iBatis for ORM
 * Utility methods provided to ensure that sessions are handled gracefully
 *
 * @author tim
 */
public class BaseManager<T extends Identifiable> {
  protected Logger log = LoggerFactory.getLogger(getClass());
  @Inject
  protected SqlSessionFactory sqlSessionFactory;
  protected final String sqlKeySuffix;

  public BaseManager(String sqlKeyPrefix) {
    this.sqlKeySuffix = sqlKeyPrefix;
  }

  public T get(Integer id){
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return (T) session.selectOne("get"+ sqlKeySuffix, id);
    } finally {
      session.close();
    }
  }

  public void insert(T object){
    insert("insert"+sqlKeySuffix, object);
  }

  public void delete(int id){
    delete("delete" + sqlKeySuffix, id);
  }

  public void update(T object){
    update("update" + sqlKeySuffix, object);
  }

  public List<T> list(){
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList("list" + sqlKeySuffix);
    } finally {
      session.close();
    }
  }

  protected void delete(String sqlKey, int id) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      session.delete(sqlKey, id);
      session.commit();
    } finally {
      session.close();
    }
  }

  protected void insert(String sqlKey, Object object) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      session.insert(sqlKey, object);
      session.commit();
    } finally {
      session.close();
    }
  }

  @SuppressWarnings("unchecked")
  protected <E> List<E> list(Class<E> type, String sqlKey) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList(sqlKey);
    } finally {
      session.close();
    }
  }

  @SuppressWarnings("unchecked")
  protected <E> List<E> list(Class<E> type, String sqlKey, int count) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList(sqlKey, count);
    } finally {
      session.close();
    }
  }

  @SuppressWarnings("unchecked")
  protected <E> List<E> list(Class<E> type, String sqlKey, Object parameter) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList(sqlKey, parameter);
    } finally {
      session.close();
    }
  }

  /**
   * @param type The return type
   * @param sqlKey The iBatis SQL key
   * @param parameter To select with
   * @return The casted result
   * @throws IOException On error
   */
  @SuppressWarnings("unchecked")
  protected <T> T selectOne(Class<T> type, String sqlKey, Object parameter) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return (T) session.selectOne(sqlKey, parameter);
    } finally {
      session.close();
    }
  }

  /**
   * @param type      The return type
   * @param sqlKey    The iBatis SQL key
   *
   * @return The casted result
   *
   * @throws IOException On error
   */
  @SuppressWarnings("unchecked")
  protected <T> T selectOne(Class<T> type, String sqlKey) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return (T) session.selectOne(sqlKey);
    } finally {
      session.close();
    }
  }

  protected Integer selectOneAsInt(String sqlKey) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return (Integer) session.selectOne(sqlKey);
    } finally {
      session.close();
    }
  }

  protected void update(String sqlKey, Object object) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      session.update(sqlKey, object);
      session.commit();
    } finally {
      session.close();
    }
  }

}
