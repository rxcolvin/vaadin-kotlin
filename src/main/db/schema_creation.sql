drop table if exists todos;

CREATE TABLE todos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    title VARCHAR(255) NULL,
    description VARCHAR(255) NULL,
    due_at DATE NULL,
    completed_at DATE NULL,
    user_id INTEGER NULL,
    CONSTRAINT todos_user_fk FOREIGN KEY (user_id) REFERENCES Users(id),
    CONSTRAINT todos_user_unique UNIQUE (user_id)
);
  
drop table if exists users; 
CREATE TABLE Users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL
);