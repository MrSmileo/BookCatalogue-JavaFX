package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Catalogue {

    private ArrayList<Publication> publications =
            new ArrayList<>();

    public void addPublication(Publication publication) {
        publications.add(publication);
    }

    public void removePublicationByTitle(String title)
            throws BookNotFoundException {

        Publication publication =
                findPublicationByTitle(title);

        if (publication == null) {
            throw new BookNotFoundException(
                    "Книгу з назвою \"" +
                            title +
                            "\" не знайдено.");
        }

        publications.remove(publication);
    }

    public Publication findPublicationByTitle(
            String title) {

        for (Publication publication :
                publications) {

            if (publication.getTitle()
                    .equalsIgnoreCase(title)) {

                return publication;
            }
        }

        return null;
    }

    public List<Publication> searchPublications(
            String searchText) {

        ArrayList<Publication> result =
                new ArrayList<>();

        for (Publication publication :
                publications) {

            if (publication.getTitle()
                    .toLowerCase()
                    .contains(
                            searchText.toLowerCase())) {

                result.add(publication);
            }
        }

        return result;
    }

    public void updateBook(Book updatedBook)
            throws BookNotFoundException {

        Publication publication =
                findPublicationByTitle(
                        updatedBook.getTitle());

        if (publication == null) {

            throw new BookNotFoundException(
                    "Книгу з назвою \"" +
                            updatedBook.getTitle() +
                            "\" не знайдено.");
        }

        int index =
                publications.indexOf(publication);

        publications.set(index, updatedBook);
    }

    public List<Publication> getAllPublications() {
        return publications;
    }

    public void saveToFile(String filename)
            throws IOException {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(
                                     filename))) {

            out.writeObject(publications);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename)
            throws IOException,
            ClassNotFoundException {

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(
                                     filename))) {

            publications =
                    (ArrayList<Publication>)
                            in.readObject();
        }
    }
}