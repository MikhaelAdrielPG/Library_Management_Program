package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Library {
    private ArrayList<Book> books; // Daftar buku dalam perpustakaan.
    private ArrayList<Member> members; // Daftar anggota perpustakaan.
    private ArrayList<Borrowing> borrowings; // Daftar peminjaman buku.

    private ArrayList<String> transactionLog; // Log transaksi.

    // Konstruktor untuk inisialisasi objek Library dengan array kosong.
    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        borrowings = new ArrayList<>();
        transactionLog = new ArrayList<>();
    }

    // Metode untuk menampilkan log transaksi peminjaman dan pengembalian secara terpisah.
    public void displaySeparatedTransactionLog() {
        boolean hasBorrowLogs = false;
        boolean hasReturnLogs = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        System.out.println("\n=== Log Peminjaman ===");
        for (String logEntry : transactionLog) {
            if (logEntry.contains("Peminjaman")) {
                System.out.println(dateFormat.format(new Date()) + " - " + logEntry);
                hasBorrowLogs = true;
            }
        }
        if (!hasBorrowLogs) {
            System.out.println("Tidak ada log peminjaman.");
        }
        System.out.println("--------------------");

        System.out.println("\n=== Log Pengembalian ===");
        for (String logEntry : transactionLog) {
            if (logEntry.contains("Pengembalian")) {
                System.out.println(dateFormat.format(new Date()) + " - " + logEntry);
                hasReturnLogs = true;
            }
        }
        if (!hasReturnLogs) {
            System.out.println("Tidak ada log pengembalian.");
        }
        System.out.println("--------------------");
    }

    // Metode untuk menambahkan log transaksi ke transactionLog.
    public void addTransactionLog(String log) {
        transactionLog.add(log);
    }

    // Metode untuk memeriksa apakah ISBN valid.
    public boolean isValidISBN(String ISBN) {
        return ISBN.length() == 13 && ISBN.matches("\\d+");
    }

    // Metode untuk menambahkan buku ke daftar buku perpustakaan.
    public void addBook(Book book) {
        books.add(book);
    }

    // Metode untuk menambahkan anggota ke daftar anggota perpustakaan.
    public void addMember(Member member) {
        members.add(member);
    }

    // Metode untuk meminjam buku berdasarkan ISBN dan ID anggota.
    public void borrowBook(String ISBN, int memberId) {
        Book book = findBookByISBN(ISBN);
        Member member = findMemberById(memberId);

        if (book == null || member == null) {
            System.out.println("Buku dengan ISBN " + ISBN + " atau anggota dengan ID " + memberId + " tidak ditemukan.");
            return;
        }

        if (book.getAvailableCopies() > 0) {
            book.borrowBookCounter();
            book.setBorrower(member);
            borrowings.add(new Borrowing(book, member));
            System.out.println("Buku berhasil dipinjam oleh " + member.getName() + ".");

            // Menambahkan log transaksi peminjaman ke transactionLog
            String logEntry = "Peminjaman - " + member.getName() + " meminjam buku " + book.getTitle() + " (" + ISBN + ")";
            addTransactionLog(logEntry);
        } else {
            System.out.println("Buku tidak tersedia saat ini.");
        }
    }

    // Metode untuk mengembalikan buku berdasarkan ISBN dan ID anggota.
    public void returnBook(String ISBN, int memberId) {
        Book book = findBookByISBN(ISBN);
        Member member = findMemberById(memberId);

        if (book == null || member == null) {
            System.out.println("Buku dengan ISBN " + ISBN + " atau anggota dengan ID " + memberId + " tidak ditemukan.");
            return;
        }

        Borrowing borrowingToRemove = null;

        for (Borrowing borrowing : borrowings) {
            if (borrowing.getBook() == book && borrowing.getMember() == member) {
                borrowingToRemove = borrowing;
                break;
            }
        }

        if (borrowingToRemove != null) {
            book.returnBookCounter();
            book.setBorrower(null);
            borrowings.remove(borrowingToRemove);
            System.out.println("Buku berhasil dikembalikan oleh " + member.getName() + ".");

            // Menambahkan log transaksi pengembalian ke transactionLog
            String logEntry = "Pengembalian - " + member.getName() + " mengembalikan buku " + book.getTitle() + " (" + ISBN + ")";
            addTransactionLog(logEntry);
        } else {
            System.out.println("Buku tidak sedang dipinjam oleh anggota dengan ID " + memberId + ".");
        }
    }

    // Metode untuk menampilkan daftar buku yang tersedia.
    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("Daftar buku kosong.");
            return;
        }

        System.out.println("Daftar Buku:");
        for (Book book : books) {
            if (book.isActive()) {
                System.out.println("Judul: " + book.getTitle());
                System.out.println("Penulis: " + book.getAuthor());
                System.out.println("ISBN: " + book.getISBN());
                System.out.println("Jumlah Tersedia: " + book.getAvailableCopies());
                System.out.println();
            }
        }
        System.out.println("----------------------");
    }

    // Metode untuk menampilkan daftar anggota perpustakaan.
    public void listMembers() {
        if (members.isEmpty()) {
            System.out.println("Daftar anggota kosong.");
            return;
        }

        System.out.println("Daftar Anggota:");
        for (Member member : members) {
            if (member.isActive()) {
                System.out.println("ID Anggota: " + member.getMemberId());
                System.out.println("Nama: " + member.getName());

                // Menampilkan buku yang sedang dipinjam oleh anggota
                boolean hasBorrowedBooks = false;
                for (Borrowing borrowing : borrowings) {
                    if (borrowing.getMember() == member) {
                        System.out.println("Buku yang Dipinjam:");
                        System.out.println("- " + borrowing.getBook().getTitle() + " " + borrowing.getBook().getISBN());
                        System.out.println();
                        hasBorrowedBooks = true;
                    }
                }
                if (!hasBorrowedBooks) {
                    System.out.println("Tidak ada buku yang sedang dipinjam.");
                }
                System.out.println("----------------------");
            }
        }
    }

    // Metode untuk menghapus buku berdasarkan ISBN.
    public void deleteBook(String ISBN) {
        Book book = findBookByISBN(ISBN);
        if (book != null) {
            if (isBookBorrowed(book)) {
                System.out.println("Buku dengan ISBN " + ISBN + " sedang dipinjam dan tidak dapat dihapus.");
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Anda yakin ingin menghapus buku dengan ISBN " + ISBN + "? (y/n)");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("y")) {
                    book.setActive(false);
                    System.out.println("Buku dengan ISBN " + ISBN + " berhasil dihapus.");
                } else {
                    System.out.println("Penghapusan buku dengan ISBN " + ISBN + " dibatalkan.");
                }
            }
        } else {
            System.out.println("Buku dengan ISBN " + ISBN + " tidak ditemukan.");
        }
    }

    // Metode untuk menghapus anggota berdasarkan ID anggota.
    public void deleteMember(int memberId) {
        Member member = findMemberById(memberId);
        if (member != null) {
            if (isMemberBorrowing(member)) {
                System.out.println("Anggota dengan ID " + memberId + " sedang meminjam buku dan tidak dapat dihapus.");
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Anda yakin ingin menghapus anggota dengan ID " + memberId + "? (y/n)");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("y")) {
                    member.setActive(false);
                    System.out.println("Anggota dengan ID " + memberId + " berhasil dihapus.");
                } else {
                    System.out.println("Penghapusan anggota dengan ID " + memberId + " dibatalkan.");
                }
            }
        } else {
            System.out.println("Anggota dengan ID " + memberId + " tidak ditemukan.");
        }
    }


    // Metode untuk memeriksa apakah buku sedang dipinjam.
    public boolean isBookBorrowed(Book book) {
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getBook() == book) {
                return true;
            }
        }
        return false;
    }

    // Metode untuk memeriksa apakah anggota sedang meminjam buku.
    public boolean isMemberBorrowing(Member member) {
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getMember() == member) {
                return true;
            }
        }
        return false;
    }

    // Metode untuk mencari buku berdasarkan ISBN.
    public Book findBookByISBN(String ISBN) {
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }

    // Metode untuk mencari anggota berdasarkan ID anggota.
    public Member findMemberById(int memberId) {
        for (Member member : members) {
            if (member.getMemberId() == memberId) {
                return member;
            }
        }
        return null;
    }
}