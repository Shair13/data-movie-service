CREATE TABLE movies (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         director VARCHAR(255) NOT NULL,
                         description VARCHAR(500),
                         rate DECIMAL(3, 1)
);

INSERT INTO movies (title, description, director, rate) VALUES
                                                   ('Return of the Jedi', 'The film follows the ongoing struggle between the malevolent Galactic Empire and the freedom fighters of the Rebel Alliance. As the Rebels attempt to destroy the Empire''s second Death Star, Luke Skywalker tries to bring his father, Darth Vader, back from the dark side of the Force.', 'Richard Marquand', 8.1),
                                                   ('Interstellar', 'Set in a dystopian future where Earth is suffering from catastrophic blight and famine, the film follows a group of astronauts who travel through a wormhole near Saturn in search of a new home for humankind.', 'Christopher Nolan', 8.00),
                                                   ('Rubber (2010)', 'A homicidal car tire, discovering it has destructive psionic power, sets its sights on a desert town once a mysterious woman becomes its obsession.', 'Quentin Dupieux', 5.2),
                                                   ('The Return of the King', 'Gandalf and Aragorn lead the World of Men against Sauron''s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.', 'Peter Jackson', 8.4),
                                                   ('Tenet', 'Armed with only the word "Tenet," and fighting for the survival of the entire world, CIA operative, The Protagonist, journeys through a twilight world of international espionage on a global mission that unfolds beyond real time.', 'Christopher Nolan', 6.7),
                                                   ('Inception', 'a corporate spy who steals secrets via a technology that allows him to enter people''s dreams. The film turns on this character''s attempt to move past the boundaries of the technology in order to actually plant an idea in a dreamer''s head.', 'Christopher Nolan', 8.2),
                                                   ('Rise of Skywalker', 'Set one year after The Last Jedi, The Rise of Skywalker follows Rey and... Nevermind... Just turn it off.', 'J.J. Abrams', 1.8);