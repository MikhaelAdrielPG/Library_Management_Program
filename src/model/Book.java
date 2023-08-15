package model;

public class Book {
    private String title; // Mendefinisikan variabel untuk judul buku.
    private String author; // Mendefinisikan variabel untuk penulis buku.
    private String ISBN; // Mendefinisikan variabel untuk nomor ISBN buku.
    private int availableCopies; // Mendefinisikan variabel untuk jumlah salinan buku yang tersedia.
    private Member borrower; // Mendefinisikan variabel untuk anggota peminjam buku.
    private boolean active; // Mendefinisikan variabel untuk status aktif buku.

    // Konstruktor untuk inisialisasi objek Book dengan informasi awal.
    public Book(String title, String author, String ISBN, int availableCopies) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.availableCopies = availableCopies;
        this.active = true; // Mengatur status buku sebagai aktif.
    }

    // Metode getter untuk mendapatkan judul buku.
    public String getTitle() {
        return title;
    }

    // Metode getter untuk mendapatkan penulis buku.
    public String getAuthor() {
        return author;
    }

    // Metode getter untuk mendapatkan nomor ISBN buku.
    public String getISBN() {
        return ISBN;
    }

    // Metode getter untuk mendapatkan jumlah salinan buku yang tersedia.
    public int getAvailableCopies() {
        return availableCopies;
    }

    // Metode getter untuk mendapatkan anggota yang meminjam buku.
    public Member getBorrower() {
        return borrower;
    }

    // Metode setter untuk mengatur anggota yang meminjam buku.
    public void setBorrower(Member borrower) {
        this.borrower = borrower;
    }

    // Metode untuk mengurangi jumlah salinan buku yang tersedia saat dipinjam.
    public void borrowBookCounter() {
        if (availableCopies > 0) {
            availableCopies--;
        } else {
            System.out.println("Buku tidak tersedia saat ini.");
        }
    }

    // Metode untuk meningkatkan jumlah salinan buku yang tersedia saat dikembalikan.
    public void returnBookCounter() {
        availableCopies++;
    }

    // Metode getter untuk mendapatkan status aktif buku.
    public boolean isActive() {
        return active;
    }

    // Metode setter untuk mengatur status aktif buku.
    public void setActive(boolean active) {
        this.active = active;
    }
}