package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookGUI extends Application {

    private Catalogue catalogue = new Catalogue();

    private ObservableList<String> books =
            FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {

        TextField titleField = new TextField();
        titleField.setPromptText("Назва");

        TextField authorField = new TextField();
        authorField.setPromptText("Автор");

        TextField publisherField = new TextField();
        publisherField.setPromptText("Видавництво");

        TextField genreField = new TextField();
        genreField.setPromptText("Жанр");

        TextField yearField = new TextField();
        yearField.setPromptText("Рік");

        Button addButton =
                new Button("Додати книгу");

        Button deleteButton =
                new Button("Видалити книгу");

        Button updateButton =
                new Button("Оновити книгу");

        Button saveButton =
                new Button("Зберегти");

        Button loadButton =
                new Button("Завантажити");

        Button searchButton =
                new Button("Пошук");

        Button resetButton =
                new Button("Скинути пошук");

        ListView<String> listView =
                new ListView<>(books);

        // ДОДАТИ КНИГУ
        addButton.setOnAction(e -> {

            try {

                int year =
                        Integer.parseInt(
                                yearField.getText());

                Book book = new Book(
                        titleField.getText(),
                        year,
                        authorField.getText(),
                        publisherField.getText(),
                        genreField.getText()
                );

                catalogue.addPublication(book);

                refreshList();

                titleField.clear();
                authorField.clear();
                publisherField.clear();
                genreField.clear();
                yearField.clear();

            } catch (Exception ex) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setContentText(
                        "Некоректний рік!");

                alert.showAndWait();
            }
        });

        // ВИДАЛИТИ КНИГУ
        deleteButton.setOnAction(e -> {

            try {

                catalogue.removePublicationByTitle(
                        titleField.getText());

                refreshList();

            } catch (BookNotFoundException ex) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setContentText(
                        ex.getMessage());

                alert.showAndWait();
            }
        });

        // ОНОВИТИ КНИГУ
        updateButton.setOnAction(e -> {

            try {

                int year =
                        Integer.parseInt(
                                yearField.getText());

                Book updatedBook =
                        new Book(
                                titleField.getText(),
                                year,
                                authorField.getText(),
                                publisherField.getText(),
                                genreField.getText()
                        );

                catalogue.updateBook(
                        updatedBook);

                refreshList();

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION);

                alert.setContentText(
                        "Книгу оновлено!");

                alert.showAndWait();

            } catch (BookNotFoundException ex) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR);

                alert.setContentText(
                        ex.getMessage());

                alert.showAndWait();

            } catch (Exception ex) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR);

                alert.setContentText(
                        "Некоректний рік!");

                alert.showAndWait();
            }
        });

        // ЗБЕРЕГТИ
        saveButton.setOnAction(e -> {

            try {

                catalogue.saveToFile(
                        "books.dat");

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION);

                alert.setContentText(
                        "Каталог збережено!");

                alert.showAndWait();

            } catch (Exception ex) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR);

                alert.setContentText(
                        "Помилка збереження!");

                alert.showAndWait();
            }
        });

        // ЗАВАНТАЖИТИ
        loadButton.setOnAction(e -> {

            try {

                catalogue.loadFromFile(
                        "books.dat");

                refreshList();

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION);

                alert.setContentText(
                        "Каталог завантажено!");

                alert.showAndWait();

            } catch (Exception ex) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR);

                alert.setContentText(
                        "Помилка завантаження!");

                alert.showAndWait();
            }
        });

        // ПОШУК
        searchButton.setOnAction(e -> {

            books.clear();

            for (Publication p :
                    catalogue.searchPublications(
                            titleField.getText())) {

                books.add(
                        p.toString());
            }
        });

        // СКИНУТИ ПОШУК
        resetButton.setOnAction(e -> {

            refreshList();

            titleField.clear();
            authorField.clear();
            publisherField.clear();
            genreField.clear();
            yearField.clear();
        });

        VBox root = new VBox(
                10,
                titleField,
                authorField,
                publisherField,
                genreField,
                yearField,
                addButton,
                deleteButton,
                updateButton,
                saveButton,
                loadButton,
                searchButton,
                resetButton,
                listView
        );

        Scene scene =
                new Scene(root, 550, 650);

        stage.setTitle("Каталог книг");

        stage.setScene(scene);

        stage.show();
    }

    private void refreshList() {

        books.clear();

        for (Publication p :
                catalogue.getAllPublications()) {

            books.add(
                    p.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}