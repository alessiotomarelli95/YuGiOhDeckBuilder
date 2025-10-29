In order for the Deck Builder to function, it will have to be connected to a Database with specific tables.  
For the sake of this project, Beekeper Studio and XAMPP were used.  
The codes required to create said tables are provided below:  

```
CREATE TABLE IF NOT EXISTS carte(
  id_carta int UNIQUE NOT NULL auto_increment,
  codice varchar(8) UNIQUE NOT NULL,
  nome varchar(255) UNIQUE NOT NULL,
  tipo varchar(255) UNIQUE NOT NULL,
  PRIMARY KEY(id_carta)
);

CREATE TABLE IF NOT EXISTS decks(
  id int UNIQUE NOT NULL auto_increment,
  nome varchar(255) UNIQUE NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS composizione_deck(
  id_deck int NOT NULL,
  codice_carta varchar(8) NOT NULL,
  quantità int NOT NULL,
  PRIMARY KEY (id_deck, codice_carta)
  FOREIGN KEY (id_deck) REFERENCES decks(id) ON DELETE CASCADE,
  FOREIGN KEY (codice_carta) REFERENCES carte(codice) ON DELETE RESTRICT
);
```

As the deck-building process requires the user to add cards to their decks from a pre-prepared list,  
it is also necessary to fill the "carte" (representing all available cards) table with a number of entries, before using the program.  
To simplify the process of dealing with Yu-Gi-Oh! cards, only the following fields have been created as requirements:  
• Coice (Code): an 8-digit string (e.g. 59509952);  
• Nome (Name): the name on top of a card (e.g. Archlord Kristya);  
• Tipo (Type): the card's type, representing its properties (Monster, Spell or Trap);  
