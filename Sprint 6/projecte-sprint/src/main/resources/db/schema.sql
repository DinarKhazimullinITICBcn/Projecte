CREATE TABLE Empresa (
    id INT PRIMARY KEY,
    name VARCHAR(255)
);
CREATE TABLE Oferta (
    id INT PRIMARY KEY,
    titul VARCHAR(255),
    empresa_id INT,
    FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);
