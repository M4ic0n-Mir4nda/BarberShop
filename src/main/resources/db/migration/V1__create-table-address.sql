CREATE TABLE address (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number INT,
    complement VARCHAR(255),
    cep VARCHAR(9) NOT NULL,
    city VARCHAR(255) NOT NULL,
    uf VARCHAR(2) NOT NULL
);