package org.gbif.pubindex.cli;

import org.gbif.cli.BaseCommand;
import org.gbif.pubindex.config.GuiceConfig;
import org.gbif.pubindex.config.PubindexConfig;

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
  protected final Injector injector;

  public PubindexBaseCommand(String name) {
    super(name);
    injector = Guice.createInjector(new GuiceConfig(cfg));
  }

  @Override
  protected Object getConfigurationObject() {
    return cfg;
  }

}
