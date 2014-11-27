package org.gbif.pubindex.cli;

import org.gbif.cli.BaseCommand;
import org.gbif.pubindex.config.PubindexConfig;
import org.gbif.pubindex.config.PubindexMybatisModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base pubindex command that does bootstrapping.
 */
public abstract class PubindexBaseCommand extends BaseCommand {
  private static final Logger LOG = LoggerFactory.getLogger(PubindexBaseCommand.class);

  protected final PubindexConfig cfg = new PubindexConfig();
  protected Injector injector;

  public PubindexBaseCommand(String name) {
    super(name);
  }

  @Override
  protected Object getConfigurationObject() {
    return cfg;
  }

  @Override
  protected void doRun() {
    injector = Guice.createInjector(new PubindexMybatisModule(cfg));
  }
}
