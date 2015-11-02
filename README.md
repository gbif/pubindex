# PubIndex

Publication names index. Scientific publications are indexed for scientific names and their occurrences stored.
The idea is based around the older [uBioRSS service](http://bioinformatics.oxfordjournals.org/content/23/11/1434.full).

Many modern journals provide an RSS feed for their content. This project uses a simple postgres table to register journal feeds which are then continously monitored for a change. New RSS feeds are then stored and if accessible the full article text downloaded. Both the RSS article summary and the entire article if available is then indexed for scientific names.

**Development on the project has been stalled and it is currently not in use by GBIF!**

There is currently no user or machine interface to the indexed content. All feeds need to manually registered in the SQL tables.

### pubindex cli
The project uses the regular GBIF CLI library to expose a shaded jar which can be run to continously index RSS feeds.

