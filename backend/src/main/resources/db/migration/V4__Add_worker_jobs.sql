CREATE TABLE IF NOT EXISTS
  worker_jobs (
    id SERIAL,
    type VARCHAR NOT NULL,
    data JSONB,
    CONSTRAINT PK_worker_jobs PRIMARY KEY (id)
  );
