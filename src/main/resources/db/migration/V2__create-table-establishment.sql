CREATE TABLE establishment (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18) NOT NULL,
    whatsapp VARCHAR(11) NOT NULL,
    cep VARCHAR(9),
    FOREIGN KEY (cep) REFERENCES address(cep)
);