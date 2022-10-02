-- ----------------------------
-- Table structure for img
-- ----------------------------
DROP TABLE IF EXISTS "img";
CREATE TABLE "img" (
                       "id" integer NOT NULL,
                       "name" text(255),
                       "path" text(255) NOT NULL,
                       "size" integer,
                       "public" integer NOT NULL,
                       "limit_date" text,
                       "limit_times" integer,
                       "owner" text(255),
                       "deleted" integer NOT NULL,
                       "storage" text,
                       PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
                        "id" text(255) NOT NULL,
                        "data" text,
                        "updateTime" text,
                        PRIMARY KEY ("id")
);

-- ----------------------------
-- Indexes structure for table img
-- ----------------------------
CREATE INDEX "img"
    ON "img" (
               "id" ASC
        );

-- ----------------------------
-- Indexes structure for table user
-- ----------------------------
CREATE INDEX "id"
    ON "user" (
               "id" ASC
        );

PRAGMA foreign_keys = true;