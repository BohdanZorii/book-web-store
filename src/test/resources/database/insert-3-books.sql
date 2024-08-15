INSERT INTO books (id, title, author, isbn, price, description)
VALUES
    (1, 'The Lord of the Rings', 'J.R.R. Tolkien', '9780261102385', 29.99, 'Epic fantasy novel'),
    (2, 'The Hobbit', 'J.R.R. Tolkien', '9780261102217', 19.99, 'Fantasy novel'),
    (3, 'Harry Potter and the Philosophers Stone', 'J.K. Rowling', '9780747532743', 15.99, 'Fantasy novel');

INSERT INTO categories (id, name, description, is_deleted)
VALUES
(1, 'Fantasy', 'Fantasy genre', false),
(2, 'Adventure', 'Adventure genre', false);

INSERT INTO books_categories (book_id, category_id)
VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 1);
