CREATE TABLE author (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        date_of_birth DATE NOT NULL,
                        version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE book (
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      genre VARCHAR(255) NOT NULL,
                      price DECIMAL(10,2) NOT NULL CHECK (price > 0),
                      author_id BIGINT NOT NULL,
                      version BIGINT NOT NULL DEFAULT 0,
                      FOREIGN KEY (author_id) REFERENCES author(id),
                      CONSTRAINT uk_book_title_author UNIQUE (title, author_id)
);

CREATE TABLE member (
                        id BIGSERIAL PRIMARY KEY,
                        username VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        address TEXT NOT NULL,
                        phone_number VARCHAR(255) NOT NULL,
                        version BIGINT NOT NULL DEFAULT 0,
                        CONSTRAINT uk_member_username UNIQUE (username),
                        CONSTRAINT uk_member_email UNIQUE (email)
);

CREATE TABLE loan (
                      id BIGSERIAL PRIMARY KEY,
                      member_id BIGINT NOT NULL,
                      book_id BIGINT NOT NULL,
                      lend_date DATE NOT NULL,
                      return_date DATE,
                      version BIGINT NOT NULL DEFAULT 0,
                      FOREIGN KEY (member_id) REFERENCES member(id),
                      FOREIGN KEY (book_id) REFERENCES book(id)
);