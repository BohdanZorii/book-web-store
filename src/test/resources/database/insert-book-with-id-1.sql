INSERT INTO categories (id, name) VALUES (1, 'Fantasy');

INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (1, 'The Hobbit', 'J.R.R. Tolkien', '978-0-261-10221-4', 25.99,
        'A fantasy novel by J.R.R. Tolkien.',
        'hobbit_cover.jpg');

INSERT INTO books_categories (book_id, category_id) VALUES (1, 1);
