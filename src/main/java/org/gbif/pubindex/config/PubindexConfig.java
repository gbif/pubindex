package org.gbif.pubindex.config;

import java.io.File;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.beust.jcommander.internal.Lists;

public class PubindexConfig {

  /**
   * If not configured articles will not be indexed, just downloaded and parsed.
   */
  @Parameter(names = {"-ws", "--webservice"})
  public String nameFinderWs;

  @Parameter(names = {"-r", "--repo"})
  @NotNull
  public File repo;

  @Parameter(names = {"-a", "--articles"})
  public List<Integer> articles = Lists.newArrayList();

  @ParametersDelegate
  @Valid
  @NotNull
  public ClbConfiguration clb = new ClbConfiguration();
}
