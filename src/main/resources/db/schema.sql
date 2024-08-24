-- "" 表示字符串，不支持 ``
CREATE TABLE IF NOT EXISTS "flow" (
  "id" BIGINT PRIMARY KEY,
  "name" VARCHAR NOT NULL,
);