CREATE TABLE activity (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    time_service TIME,
    description VARCHAR(255)
);