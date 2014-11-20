package org.gbif.pubindex.config;

import java.io.File;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

public class PubindexConfig {

  @Parameter(names = {"-ws", "--webservice"}, required = true)
  public String nameFinderWs;

  @Parameter(names = {"-r", "--repo"}, required = true)
  public File repo;

  @Parameter(names = {"-a", "--articles"}, required = false)
  public List<Integer> articles = Lists.newArrayList();
}
