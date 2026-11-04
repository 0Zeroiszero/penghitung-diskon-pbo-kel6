# AGENTS.md - Kalkulator Diskon

Aplikasi desktop JavaFX untuk menghitung harga akhir setelah diskon 10%, 20%, 30%, atau 50% dari harga barang. Tanpa database. Layout dua kolom: form di kiri, hasil di kanan. Kontributor masih pemula: utamakan kode sederhana dan mudah dibaca.

## Menjalankan

- JDK 27 + JavaFX 27.0.1 (lihat `pom.xml`).
- `mvn javafx:run` untuk menjalankan aplikasi; `mvn -q compile` untuk cek kompilasi.
- Jika `mvn` tidak ada di PATH, gunakan Maven bawaan IntelliJ (Maven tool window) atau pasang Maven. Repo tidak punya Maven wrapper.
- Tidak ada test framework/CI. Uji manual harga `100000`: diskon 10/20/30/50 harus menghasilkan `90000`, `80000`, `70000`, `50000`; cek juga input kosong, `0`, dan negatif.

## Struktur MVC

Semua kode di `src/main/java/com/pbo/`:

- `Main.java` - entrypoint JavaFX (`Application`), memuat `/styles.css`, ukuran scene 880x610.
- `model/` - logika diskon murni, tanpa import JavaFX. `DiscountCalculator.hitungHargaAkhir(harga, persen)`.
- `view/CalculatorView.java` - membangun layout JavaFX; form kiri, kartu hasil kanan. Tidak berisi rumus.
- `controller/CalculatorController.java` - event handler tombol/Enter, validasi, panggil model, update view.

Rumus hanya di model. View tidak menghitung; controller tidak membuat komponen UI.

## UI

- Semua warna, font, radius, shadow, dan state hover ada di `src/main/resources/styles.css`. Jangan hardcode warna di Java.
- Palet: krem `#FBF8F2`/`#F1EBE0`, navy `#1E3A5F`, emas `#B7791F`, teks `#1F2937`, muted `#6B7280`.
- Form: input harga, chip diskon 10/20/30/50, tombol Hitung. Hasil di kartu kanan: harga akhir, detail diskon, hemat.
- Error ditampilkan inline di bawah input; jangan pakai dialog modal.
- Teks UI Bahasa Indonesia. Format Rupiah tanpa desimal (`Locale` `id-ID`).

## Batasan

- Hanya JavaFX + JDK. Jangan tambah dependency selain JavaFX tanpa alasan kuat.
- Hanya empat diskon tetap. Jangan tambah diskon custom, riwayat, login, export, database, file, atau network.
- Jangan commit `.agents/`, `.opencode/`, atau `.windsurf/`; itu skill tooling. Untuk ponytail, commit `skills-lock.json` dan restore dengan `npx skills experimental_install`.
- Sebelum commit/PR, jalankan `mvn javafx:run` dan pastikan keempat diskon benar.
