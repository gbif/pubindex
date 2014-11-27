package org.gbif.pubindex.service.mapper;

import org.gbif.pubindex.model.Article;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ArticleMapper {

  Article get(@Param("key") int key);

  void insert(@Param("a") Article article);

  void update(@Param("a") Article article);

  void delete(@Param("key") int key);

  Article getByUrl(@Param("url") String url);

  Article getByGuidAndJournal(@Param("guid") String guid, @Param("journalId") Integer journalId);

  List<Article> listNotYetIndexed();
}
