INSERT INTO tb_authors (name, nationality, birth_date) VALUES
    ('Ernest Cline', 'American', '1972-03-29'),
    ('J. K. Rowling', 'British', '1965-07-31'),
    ('J. R. R. Tolkien', 'British', '1892-01-03');

INSERT INTO tb_categories (name, description) VALUES
    ('Fantasy', 'Fantasy literature is literature set in an imaginary universe, often but not always without any locations, events, or people from the real world.'),
    ('Science fiction', 'Science fiction is a genre of speculative fiction, which typically deals with imaginative and futuristic concepts such as advanced science and technology, space exploration, time travel, parallel universes, and extraterrestrial life.'),
    ('Mystery', 'Mystery is a fiction genre where the nature of an event, usually a murder or other crime, remains mysterious until the end of the story.'),
    ('Romance novel', 'A romance novel or romantic novel generally refers to a type of genre fiction novel which places its primary focus on the relationship and romantic love between two people, and usually has an "emotionally satisfying and optimistic ending.');

INSERT INTO tb_books (title, description, number_of_pages, language, publisher, publication_date, author_id, category_id) VALUES
    ('Ready Player One', 'Ready Player One is a 2011 science fiction novel, and the debut novel of American author Ernest Cline. The story, set in a dystopia in 2045, follows protagonist Wade Watts on his search for an Easter egg in a worldwide virtual reality game, the discovery of which would lead him to inherit the game creator''s fortune',
        374, 'English', 'Crown Publishing Group', '2011-08-16', 1, 2),
    ('Armada', 'Armada is a science fiction novel by Ernest Cline, published on July 14, 2015 by Crown Publishing Group (a division of Penguin Random House). The story follows a teenager who plays an online video game about defending against an alien invasion, only to find out that the game is a simulator to prepare him and people around the world for defending against an actual alien invasion.',
        368, 'English', 'Crown Publishing Group', '2015-07-14', 1, 2),
    ('Harry Potter and the Philosopher''s Stone', 'Harry Potter and the Philosopher''s Stone is a 1997 fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling''s debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry.',
        223, 'English', 'Bloomsbury (UK)', '1997-05-26', 2, 1),
    ('The Lord of the Rings', 'The Lord of the Rings is an epic high-fantasy novel by English author and scholar J. R. R. Tolkien. Set in Middle-earth, the story began as a sequel to Tolkiens 1937 childrens book The Hobbit, but eventually developed into a much larger work. Written in stages between 1937 and 1949, The Lord of the Rings is one of the best-selling books ever written, with over 150 million copies sold.',
        1137, 'English', 'Allen & Unwin', '1954-07-29', 3, 1);

INSERT INTO tb_roles(name) VALUES('USER');
INSERT INTO tb_roles(name) VALUES('ADMIN');
INSERT INTO tb_users(username, password) VALUES('user', '$2a$10$SB91siH2CZ6ySkGvk5l9xuy7GpJo20fQJ7B9SHBU14lp0WsmgozLW');
INSERT INTO tb_users(username, password) VALUES('admin', '$2a$10$SB91siH2CZ6ySkGvk5l9xuy7GpJo20fQJ7B9SHBU14lp0WsmgozLW');
INSERT INTO tb_roles_users(role_id, user_id) VALUES(1, 1);
INSERT INTO tb_roles_users(role_id, user_id) VALUES(2, 2);

