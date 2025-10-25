package service;

import model.Book;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibraryManager {
    private List<Book> books;
    private final Scanner scanner;
    private static final String FILE_NAME = "data/library.dat";

    public LibraryManager() {
        books = new ArrayList<>();
        scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    }

    public void start() {
        System.out.println("=== Библиотечный менеджер ===");
        while (true) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: addBook(); break;
                    case 2: editBook(); break;
                    case 3: displayBooks(); break;
                    case 4: searchBooks(); break;
                    case 5: saveToFile(); break;
                    case 6: loadFromFile(); break;
                    case 0: System.out.println("Exiting..."); return;
                    default: System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nМеню менеджера библиотеки:");
        System.out.println("1. Добавить книгу");
        System.out.println("2. Редактировать книгу");
        System.out.println("3. Показать все книги");
        System.out.println("4. Поиск книг");
        System.out.println("5. Сохранить в файл");
        System.out.println("6. Загрузить из файла");
        System.out.println("0. Выход");
        System.out.print("Введите ваш выбор: ");
    }

    private void addBook() {
        try {
            System.out.print("Введите ISBN: ");
            String isbn = scanner.nextLine();
            if (books.stream().anyMatch(book -> book.getIsbn().equals(isbn))) {
                System.out.println("Книга с этим ISBN уже существует.");
                return;
            }
            System.out.print("Введите название: ");
            String title = scanner.nextLine();
            System.out.print("Введите автора: ");
            String author = scanner.nextLine();
            System.out.print("Введите год публикации: ");
            int year = Integer.parseInt(scanner.nextLine());
            if (year < 0 || year > java.time.Year.now().getValue()) {
                System.out.println("Неверный год.");
                return;
            }
            books.add(new Book(isbn, title, author, year));
            System.out.println("Книга добавлена успешно.");
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод для года.");
        }
    }

    private void editBook() {
        System.out.print("Введите ISBN книги для редактирования: ");
        String isbn = scanner.nextLine();
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Книга не найдена.");
            return;
        }
        try {
            System.out.print("Введите новое название (оставьте пустым, чтобы сохранить '" + book.getTitle() + "'): ");
            String title = scanner.nextLine();
            if (!title.isEmpty()) book.setTitle(title);

            System.out.print("Введите нового автора (оставьте пустым, чтобы сохранить '" + book.getAuthor() + "'): ");
            String author = scanner.nextLine();
            if (!author.isEmpty()) book.setAuthor(author);

            System.out.print("Введите новый год (оставьте пустым, чтобы сохранить " + book.getYear() + "): ");
            String yearInput = scanner.nextLine();
            if (!yearInput.isEmpty()) {
                int year = Integer.parseInt(yearInput);
                if (year < 0 || year > java.time.Year.now().getValue()) {
                    System.out.println("Неверный год.");
                    return;
                }
                book.setYear(year);
            }
            System.out.println("Книга обновлена успешно.");
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод для года.");
        }
    }

    private void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("В библиотеке нет книг.");
            return;
        }
        System.out.println("\nСписок всех книг:");
        for (Book book : books) {
            System.out.println("ISBN: " + book.getIsbn() + 
                             ", Title: " + book.getTitle() + 
                             ", Author: " + book.getAuthor() + 
                             ", Year: " + book.getYear());
        }
    }

    private void searchBooks() {
        System.out.print("Введите запрос для поиска (название, автор или ISBN): ");
        String query = scanner.nextLine().toLowerCase();
        List<Book> results = books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query) ||
                        book.getAuthor().toLowerCase().contains(query) ||
                        book.getIsbn().toLowerCase().equals(query))
                .collect(Collectors.toList());
        if (results.isEmpty()) {
            System.out.println("Книги не найдены.");
        } else {
            System.out.println("\nРезультаты поиска:");
            for (Book book : results) {
                System.out.println("ISBN: " + book.getIsbn() + 
                                 ", Title: " + book.getTitle() + 
                                 ", Author: " + book.getAuthor() + 
                                 ", Year: " + book.getYear());
            }
        }
    }

    private Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    private void saveToFile() {
        new File("data").mkdirs();
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(books);
            System.out.println("Книги успешно сохранены в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения в файл: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            books = (List<Book>) ois.readObject();
            System.out.println("Книги успешно загружены из файла.");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. Начало с пустой библиотеки.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка загрузки из файла: " + e.getMessage());
        }
    }
}