CREATE TABLE address (
    cep VARCHAR(9) NOT NULL PRIMARY KEY,
    public_place VARCHAR(255) NOT NULL,
    complement VARCHAR(255),
    neighborhood VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    ddd INT NOT NULL
);