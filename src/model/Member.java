package model;

public class Member {
    private int memberId; // Mendefinisikan variabel untuk ID anggota.
    private String name; // Mendefinisikan variabel untuk nama anggota.
    private boolean active; // Mendefinisikan variabel untuk status aktif anggota.

    // Konstruktor untuk inisialisasi objek Member dengan informasi awal.
    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.active = true; // Mengatur status anggota sebagai aktif.
    }

    // Metode getter untuk mendapatkan ID anggota.
    public int getMemberId() {
        return memberId;
    }

    // Metode getter untuk mendapatkan nama anggota.
    public String getName() {
        return name;
    }

    // Metode getter untuk mendapatkan status aktif anggota.
    public boolean isActive() {
        return active;
    }

    // Metode setter untuk mengatur status aktif anggota.
    public void setActive(boolean active) {
        this.active = active;
    }
}