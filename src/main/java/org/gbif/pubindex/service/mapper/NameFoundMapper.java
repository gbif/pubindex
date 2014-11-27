package org.gbif.pubindex.service.mapper;

import org.gbif.pubindex.model.NameFound;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NameFoundMapper {

  void insert(@Param("n") NameFound n);

  void deleteByArticle(@Param("articleID") int articleID);

  List<NameFound> listByArticle(@Param("articleID") int articleID);

}
