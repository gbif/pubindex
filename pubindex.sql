/* SQLEditor (Postgres)*/

CREATE TABLE "journal"
(
"id" SERIAL,
"title" VARCHAR(512),
"description" TEXT,
"keywords" TEXT,
"language" VARCHAR(2),
"publisher" VARCHAR(256),
"issn" VARCHAR(128),
"homepage" VARCHAR(256),
"feed_url" VARCHAR(256),
"error" VARCHAR(256),
"logo_url" VARCHAR(256),
"kingdom" VARCHAR(64),
"phylum" VARCHAR(64),
"class" VARCHAR(64),
"order" VARCHAR(64),
"family" VARCHAR(64),
"genus" VARCHAR(64),
"species" VARCHAR(64),
"created" TIMESTAMP  default current_timestamp,
"last_harvest" TIMESTAMP,
"indexing_interval" INT default 1440,
"disabled" boolean default false,
PRIMARY KEY ("id")
);


CREATE TABLE "article"
(
"id" SERIAL,
"journal_fk" INT,
"title" TEXT,
"authors" TEXT,
"abstract" TEXT,
"keywords" TEXT,
"published_in" TEXT,
"published_date" TIMESTAMP,
"url" VARCHAR(1024),
"url_content_type" VARCHAR(128),
"error" VARCHAR(256),
"guid" VARCHAR(1024)
"doi" VARCHAR(128),
"created" TIMESTAMP default current_timestamp,
"last_indexed" TIMESTAMP,
"hashcode" INT,
PRIMARY KEY ("id")
);

CREATE TABLE "name_found"
(
"id" SERIAL,
"article_fk" INT,
"nub_id" INT,
"source" smallint,
"name" VARCHAR(512),
"name_verbatim" VARCHAR(512),
"novum" BOOL DEFAULT false,
"offset_start" INT,
"offset_end" INT,
"confidence" INT,
PRIMARY KEY ("id")
);


CREATE UNIQUE INDEX "journal_feed_url_idx"  ON "journal"("feed_url");

CREATE INDEX "article_journal_fk_idx"  ON "article"("journal_fk");
CREATE INDEX "article_unique_idx"  ON "article"("journal_fk", "hashcode");
ALTER TABLE "article" ADD FOREIGN KEY ("journal_fk") REFERENCES "journal" ("id");

CREATE INDEX "name_found_article_fk_idx"  ON "name_found"("article_fk");
CREATE INDEX "name_found_nub_id_idx"  ON "name_found"("nub_id");
CREATE INDEX "name_found_name_idx"  ON "name_found"("name");
ALTER TABLE "name_found" ADD FOREIGN KEY ("article_fk") REFERENCES "article" ("id");
