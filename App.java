    import java.util.*;

    class Book {
        private String title;
        private String author;
        private double price;

        public Book(String title, String author, double price) {
            this.title = title;
            this.author = author;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return title + " by " + author + " - $" + price;
        }
    }

    class BookCategory {
        private String categoryName;
        private List<Book> books;

        public BookCategory(String categoryName) {
            this.categoryName = categoryName;
            this.books = new ArrayList<>();
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void addBook(Book book) {
            books.add(book);
        }

        public List<Book> getBooks() {
            return books;
        }
    }

    class BookStore {
        private Map<String, BookCategory> categories;
        private Map<String, Book> bookLookup;
        private PriorityQueue<Book> popularBooksHeap;

        public BookStore() {
            this.categories = new HashMap<>();
            this.bookLookup = new HashMap<>();
            this.popularBooksHeap = new PriorityQueue<>(Comparator.comparingDouble(Book::getPrice));
        }

        public void addBook(String category, String title, String author, double price) {
            Book book = new Book(title, author, price);
            bookLookup.put(title, book);

            if (!categories.containsKey(category)) {
                categories.put(category, new BookCategory(category));
            }

            categories.get(category).addBook(book);
            popularBooksHeap.add(book);
        }

        public List<BookCategory> getAllCategories() {
            return new ArrayList<>(categories.values());
        }

        public Book findBook(String title) {
            return bookLookup.get(title);
        }

        public List<Book> getPopularBooks(int count) {
            List<Book> popularBooks = new ArrayList<>();
            while (count-- > 0 && !popularBooksHeap.isEmpty()) {
                popularBooks.add(popularBooksHeap.poll());
            }
            return popularBooks;
        }
    }

    public class App {
        public static void main(String[] args) {
            BookStore bookStore = new BookStore();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Online Book Store Menu:");
                System.out.println("1. Add a Book");
                System.out.println("2. Search for a Book");
                System.out.println("3. Display Popular Books");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter book category: ");
                        String category = scanner.nextLine();
                        System.out.print("Enter book title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter price: ");
                        double price = scanner.nextDouble();
                        bookStore.addBook(category, title, author, price);
                        System.out.println("Book added successfully!\n");
                        break;
                    case 2:
                        System.out.print("Enter book title to search: ");
                        String searchTitle = scanner.nextLine();
                        Book searchedBook = bookStore.findBook(searchTitle);
                        if (searchedBook != null) {
                            System.out.println("Book found:\n" + searchedBook + "\n");
                        } else {
                            System.out.println("Book not found.\n");
                        }
                        break;
                    case 3:
                        System.out.print("Enter the number of popular books to display: ");
                        int popularCount = scanner.nextInt();
                        List<Book> popularBooks = bookStore.getPopularBooks(popularCount);
                        System.out.println("Popular Books:");
                        for (Book book : popularBooks) {
                            System.out.println(book);
                        }
                        System.out.println();
                        break;
                    case 4:
                        System.out.println("Exiting program.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.\n");
                }
            }
        }
    }
