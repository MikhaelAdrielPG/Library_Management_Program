package controller;

import view.Menu;
import model.Book;
import model.Library;
import model.Member;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryManagementSystem {
    // Metode untuk menjalankan program utama pengelolaan perpustakaan.
    public void mainProgram() {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        int memberIdCounter = 1; // Penghitung ID anggota.

        while (true) {
            try {
                //  memanggil kelas menu di package view untuk tampilan menu
                Menu menu = new Menu();
                menu.menuView();
                int choice = scanner.nextInt();

                // Menangani pilihan menu yang dipilih oleh pengguna.
                switch (choice) {
                    case 1 -> {
                        // Menambahkan buku baru ke dalam perpustakaan.
                        System.out.print("Judul: ");
                        scanner.nextLine();
                        String title = scanner.nextLine();
                        System.out.print("Penulis: ");
                        String author = scanner.nextLine();
                        String ISBN;
                        do {
                            System.out.print("ISBN (13 angka): ");
                            ISBN = scanner.nextLine();
                            if (!library.isValidISBN(ISBN)) {
                                System.out.println("ISBN tidak valid. Harus terdiri dari 13 angka.");
                            }
                        } while (!library.isValidISBN(ISBN));
                        System.out.print("Jumlah Tersedia: ");
                        int availableCopies = scanner.nextInt();
                        Book newBook = new Book(title, author, ISBN, availableCopies);
                        library.addBook(newBook);
                        System.out.println("Buku berhasil ditambahkan.");
                    }
                    case 2 -> {
                        // Meminjam buku dari perpustakaan.
                        System.out.print("Masukkan ISBN buku yang ingin dipinjam: ");
                        scanner.nextLine();
                        String borrowISBN = scanner.nextLine();
                        System.out.print("Masukkan ID Anggota: ");
                        int borrowMemberId = scanner.nextInt();
                        library.borrowBook(borrowISBN, borrowMemberId);
                    }
                    case 3 -> {
                        // Mengembalikan buku yang telah dipinjam.
                        System.out.print("Masukkan ISBN buku yang dikembalikan: ");
                        scanner.nextLine();
                        String returnISBN = scanner.nextLine();
                        System.out.print("Masukkan ID Anggota: ");
                        int returnMemberId = scanner.nextInt();
                        library.returnBook(returnISBN, returnMemberId);
                    }
                    case 4 -> library.listBooks(); // Menampilkan daftar buku.
                    case 5 -> {
                        // Menambahkan anggota baru ke dalam perpustakaan.
                        scanner.nextLine();
                        System.out.print("Nama Anggota: ");
                        String memberName = scanner.nextLine();
                        Member newMember = new Member(memberIdCounter, memberName);
                        library.addMember(newMember);
                        System.out.println("Anggota berhasil ditambahkan. ID Anggota: " + memberIdCounter);
                        memberIdCounter++;
                    }
                    case 6 -> library.listMembers(); // Menampilkan daftar anggota.
                    case 7 -> {
                        // Menghapus buku dari perpustakaan.
                        System.out.print("Masukkan ISBN buku yang ingin dihapus: ");
                        scanner.nextLine();
                        String deleteISBN = scanner.nextLine();
                        library.deleteBook(deleteISBN);
                    }
                    case 8 -> {
                        // Menghapus anggota dari perpustakaan.
                        System.out.print("Masukkan ID Anggota yang ingin dihapus: ");
                        int deleteMemberId = scanner.nextInt();
                        library.deleteMember(deleteMemberId);
                    }
                    case 9 -> {
                        // Menampilkan log transaksi peminjaman dan pengembalian.
                        library.displaySeparatedTransactionLog();
                    }
                    case 0 -> {
                        // Keluar dari program.
                        System.out.println("Terima kasih!");
                        System.exit(0);
                    }
                    default -> System.out.println("Opsi tidak valid.");
                }
            } catch (InputMismatchException e) {
                // Menangani input yang tidak valid.
                System.out.println("Input tidak valid. Mohon masukkan input yang sesuai.");
                scanner.nextLine(); // Membersihkan buffer input.
            }
        }
    }
}