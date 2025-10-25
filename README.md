# 📚 Library Book Manager

A simple console-based library management system for storing and managing book information.

## ✨ Features

- ➕ Add new books with ISBN, title, author, and publication year
- ✏️ Edit existing book information
- 📖 Display all books in the library
- 🔍 Search books by title, author, or ISBN
- 💾 Save library data to binary file
- 📥 Load library data from file
- 🚪 Clean exit with data persistence

## 🛠️ Installation & Compilation

### Prerequisites
- Java JDK 8 or higher
- Command line terminal

### Compilation Steps

1. **Navigate to project directory:**
   ```bash
   cd F:\Work\LibraryJavaProject

2. Compile all Java files:

   ```bash
   javac -d bin src/model/Book.java src/service/LibraryManager.java src/main/Main.java
3. Verify compilation:

   ```bash
   dir bin /S

# You should see:

    bin/model/Book.class

    bin/service/LibraryManager.class

    bin/main/Main.class

## 🚀 Running the Application
 - Basic Launch
   ```bash
   java -cp bin main.Main