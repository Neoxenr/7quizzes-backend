CREATE TABLE IF NOT EXISTS rooms_questions (
    room_id TEXT REFERENCES room (id) ON UPDATE CASCADE ON DELETE CASCADE,
    question_id TEXT REFERENCES question (id) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (room_id, question_id)
)