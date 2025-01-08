CREATE TABLE IF NOT EXISTS Users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Locations
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(255)   NOT NULL,
    user_id    INTEGER        NOT NULL,
    latitude  DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(10, 8) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Sessions
(
    id        uuid PRIMARY KEY,
    user_id   INTEGER   NOT NULL,
    expiresAt TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (User_id) REFERENCES Users (id) ON DELETE CASCADE
);

