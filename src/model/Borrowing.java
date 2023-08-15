package model;

public class Borrowing {
    private Book book; // Mendefinisikan variabel untuk buku yang dipinjam dalam peminjaman.
    private Member member; // Mendefinisikan variabel untuk anggota yang melakukan peminjaman.

    // Konstruktor untuk inisialisasi objek Borrowing dengan buku dan anggota terkait.
    public Borrowing(Book book, Member member) {
        this.book = book;
        this.member = member;
    }

    // Metode getter untuk mendapatkan objek buku yang terkait dengan peminjaman.
    public Book getBook() {
        return book;
    }

    // Metode getter untuk mendapatkan objek anggota yang terkait dengan peminjaman.
    public Member getMember() {
        return member;
    }
}