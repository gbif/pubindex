package org.gbif.pubindex.cli;

import org.gbif.cli.Command;
import org.gbif.pubindex.indexer.ContinousJournalIndexing;

import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts up a continuous journal feed indexer that sequentially harvests feeds one by one, extracting and indexing its articles.
 * The service can be shut down and revived any time.
 * Multiple instances working on the same database are tolerated.
 */
@MetaInfServices(Command.class)
public class ContinousIndexingCommand extends PubindexBaseCommand {
  private static final Logger LOG = LoggerFactory.getLogger(ContinousIndexingCommand.class);

  public ContinousIndexingCommand() {
    super("indexing");
  }

  @Override
  protected void doRun() {
    ContinousJournalIndexing service = injector.getInstance(ContinousJournalIndexing.class);
    LOG.info("Starting continous indexing service");
    service.start();
  }
}
