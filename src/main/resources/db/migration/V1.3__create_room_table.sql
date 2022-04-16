CREATE TABLE IF NOT EXISTS room (
    id TEXT PRIMARY KEY,
    name TEXT,
    questions_id TEXT[],
    users_id TEXT[]
)