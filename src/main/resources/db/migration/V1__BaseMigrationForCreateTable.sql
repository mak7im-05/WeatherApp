CREATE TABLE Users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL  CHECK(length(password) > 7)
);

CREATE TABLE Locations
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(255)  NOT NULL,
    user_id   INTEGER       NOT NULL,
    latitude  DECIMAL NOT NULL,
    longitude DECIMAL NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE
);

CREATE TABLE Sessions
(
    id        uuid PRIMARY KEY,
    user_id   INTEGER                  NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (User_id) REFERENCES Users (id) ON DELETE CASCADE
);