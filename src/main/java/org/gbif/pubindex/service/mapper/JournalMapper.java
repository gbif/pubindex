package org.gbif.pubindex.service.mapper;

import org.gbif.pubindex.model.Journal;

import org.apache.ibatis.annotations.Param;

public interface JournalMapper {

  String get(@Param("key") int key);

  void update(@Param("j") Journal j);

  Journal getByFeed(@Param("url") String url);

  Journal getNextJournalForHarvesting();

}
