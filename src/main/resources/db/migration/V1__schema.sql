-- "" 表示字符串，不支持 ``
CREATE TABLE IF NOT EXISTS "flow" (
  "id" BIGINT PRIMARY KEY,
  "name" VARCHAR NOT NULL,
  "json_data" BLOB NOT NULL,
  "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE IF NOT EXISTS "flow_his" (
  "id" BIGINT PRIMARY KEY,
  "flow_id" BIGINT NOT NULL,
  "seq_no" INTEGER NOT NULL,
  "json_data" BLOB NOT NULL,
  "start_at" TIMESTAMP NULL, -- 开始时间
  "end_at" TIMESTAMP NULL, -- 结束时间
  "exec_st" VARCHAR NOT NULL DEFAULT 'INIT', -- 执行状态: { INIT: 初始化, RUNNING: 执行中, SUCCESS: 成功, FAILED: 失败 }
  "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
);

CREATE UNIQUE INDEX unique_idx_flow_id_seq_no ON "flow_his" ("flow_id", "seq_no");
CREATE INDEX idx_flow_id ON "flow_his" ("flow_id");

CREATE TABLE IF NOT EXISTS "node" (
  "id" BIGINT PRIMARY KEY,
  "flow_his_id" BIGINT NOT NULL,
  "seq_no" INTEGER NOT NULL,
  "type" VARCHAR NOT NULL,
  "data" BLOB NOT NULL,
  "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
);

CREATE INDEX idx_flow_his_id ON "node" ("flow_his_id");

-- flow_seq_no_view
CREATE VIEW flow_seq_no_view AS 
SELECT flow_id, MAX(seq_no) as max_seq_no from flow_his group by flow_id;