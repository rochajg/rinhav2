CREATE TABLE IF NOT EXISTS users (
	id INTEGER PRIMARY KEY,
	balance INTEGER,
	credit_limit INTEGER
);

CREATE TABLE IF NOT EXISTS transactions (
	id uuid PRIMARY KEY,
	user_id INTEGER,
	"value" INTEGER,
	operation VARCHAR(1),
	description VARCHAR(10),
	created_at TIMESTAMP,
	
	CONSTRAINT fk_users
      FOREIGN KEY(user_id) 
        REFERENCES users("id")
);


INSERT INTO users (id, credit_limit, balance)
VALUES
(1, 100000, 0),
(2, 80000, 0),
(3, 1000000, 0),
(4, 10000000, 0),
(5, 500000, 0);
