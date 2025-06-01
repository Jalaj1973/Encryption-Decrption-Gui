# 🔐 File Encryption and Decryption GUI (Java Swing + AES)

This is a simple yet powerful desktop application built using **Java Swing** that allows users to securely **encrypt and decrypt files** using the **AES (Advanced Encryption Standard)** algorithm. The application provides a graphical user interface (GUI) for easy interaction.

---

## 📦 Features

- 🧊 **AES Encryption/Decryption**
  - Uses AES 128-bit symmetric key encryption.
- 🖥️ **User-Friendly GUI**
  - Built with Java Swing.
  - Easy file path input and password entry.
- 🔐 **Secure Password-Based Key**
  - Converts user-entered password into a secure AES key.
- 🔄 **In-Place File Processing**
  - Reads and writes to the same file with locking mechanism.
- 🧠 **Thread-safe operations**
  - Prevents file corruption with file locks.

---

## 🧰 Technologies Used

| Tool/Library | Purpose                            |
|-------------|------------------------------------|
| Java        | Programming Language               |
| Swing       | GUI Framework                      |
| javax.crypto | Encryption (AES) API              |
| FileChannel | Efficient file read/write          |
| FileLock    | Prevents concurrent access         |

---

## 🚀 How to Run

### 🛠 Prerequisites
- Java JDK 8 or above
- Any Java IDE (IntelliJ, Eclipse, etc.)

### ▶️ Running the App
1. **Clone the repository** or copy the source code into your IDE.
2. Compile and run the `FileEncryptionGUI.java` file.
3. Enter the path of the file you want to encrypt/decrypt and a password.
4. Click `Encrypt` or `Decrypt` as needed.

---

## 🖼️ User Interface

- **Input Fields**
  - File Path
  - Password (used as encryption key)
- **Buttons**
  - Encrypt: Encrypts the selected file.
  - Decrypt: Decrypts the encrypted file.

---

## 🔒 Encryption Logic

- **Algorithm:** AES (Advanced Encryption Standard)
- **Key Size:** 128-bit
- **Key Generation:** Uses the first 16 bytes of the password string
- **Cipher Mode:** Default (AES/ECB/PKCS5Padding)

---

## ⚠️ Notes & Limitations

- The file is overwritten during encryption/decryption. Backup is recommended.
- The key (password) must be the same during encryption and decryption.
- The app does not support very large files due to full memory buffer use.

---



## 📄 License

This project is for educational use. You may modify and use it as needed.


