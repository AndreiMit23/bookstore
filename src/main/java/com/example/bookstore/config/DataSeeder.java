package com.example.bookstore.config;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.AuthorProfile;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookProfile;
import com.example.bookstore.entity.Publisher;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Seeds the in-memory database on startup so every endpoint returns data immediately.
 * Exercises all relationship types: one-to-one (author profiles + book profiles),
 * many-to-one / one-to-many (publisher -> books), and many-to-many (authors <-> books,
 * including a co-authored title).
 */
@Component
class DataSeeder implements CommandLineRunner {

    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    DataSeeder(PublisherRepository publisherRepository, AuthorRepository authorRepository,
            BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (bookRepository.count() > 0) {
            return; // already seeded
        }

        Publisher orbit = publisherRepository.save(new Publisher("Orbit Books", "London"));
        Publisher pragmatic = publisherRepository.save(new Publisher("Pragmatic Press", "Raleigh"));

        Author asimov = newAuthor("Isaac", "Asimov",
                "Prolific science-fiction and popular-science author.", "https://asimovonline.com");
        Author gaiman = newAuthor("Neil", "Gaiman",
                "British author of fiction, comics, and screenplays.", "https://neilgaiman.com");
        Author pratchett = newAuthor("Terry", "Pratchett",
                "English author best known for the Discworld series.", "https://terrypratchettbooks.com");

        saveBook("Foundation", "The first Foundation novel.", 1951, "Science Fiction", 244, orbit, asimov);
        saveBook("I, Robot", "Linked robot short stories.", 1950, "Science Fiction", 253, orbit, asimov);
        saveBook("American Gods", "Old gods in modern America.", 2001, "Fantasy", 465, orbit, gaiman);
        saveBook("Good Omens", "An angel and a demon avert the apocalypse.", 1990, "Comic Fantasy", 288,
                pragmatic, gaiman, pratchett);
        saveBook("Mort", "Death takes an apprentice.", 1987, "Comic Fantasy", 272, pragmatic, pratchett);
    }

    private Author newAuthor(String firstName, String lastName, String biography, String website) {
        Author author = new Author(firstName, lastName);
        author.setAuthorProfile(new AuthorProfile(biography, website));
        return authorRepository.save(author);
    }

    private void saveBook(String title, String description, int year, String genre, int pages,
            Publisher publisher, Author... authors) {
        Book book = new Book(title, description, year);
        book.setPublisher(publisher);
        for (Author author : authors) {
            book.addAuthor(author);
        }
        book.setBookProfile(new BookProfile(genre, pages, "English"));
        bookRepository.save(book);
    }
}
