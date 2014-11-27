package org.gbif.pubindex.cli;

import org.gbif.cli.Command;
import org.gbif.pubindex.model.Article;
import org.gbif.pubindex.service.ArticleIndexer;
import org.gbif.pubindex.service.ArticleService;

import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * (Re-)index given articles and quit
 */
@MetaInfServices(Command.class)
public class ArticleIndexingCommand extends PubindexBaseCommand {
  private static final Logger LOG = LoggerFactory.getLogger(ArticleIndexingCommand.class);

  public ArticleIndexingCommand() {
    super("article");
  }

  @Override
  protected void doRun() {
    super.doRun();
    ArticleIndexer indexer = injector.getInstance(ArticleIndexer.class);
    ArticleService articleService = injector.getInstance(ArticleService.class);
    LOG.info("(Re-)indexing {} requested articles", cfg.articles.size());
    for (Integer artID : cfg.articles) {
      Article a = articleService.get(artID);
      indexer.index(a);
    }
  }
}
