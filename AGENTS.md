# AGENTS.md - Kalkulator Diskon

Aplikasi desktop Java Swing untuk menghitung harga akhir setelah diskon 10%, 20%, 30%, atau 50% dari harga barang. Tanpa database. Kontributor masih pemula: utamakan kode sederhana, bukan arsitektur berlapis-lapis.

## Menjalankan

- Repo tidak menyertakan Maven wrapper dan `mvn` belum tentu tersedia. Cara utama: buka proyek di IntelliJ IDEA, lalu jalankan `src/main/java/com/pbo/Main.java`.
- Build saat ini membutuhkan JDK 27. Project SDK ada di `.idea/misc.xml`; `pom.xml` memakai `target` 27.
- Verifikasi cepat tanpa Maven (PowerShell):
  ```powershell
  javac -d target/classes (Get-ChildItem -Recurse src/main/java -Filter *.java).FullName
  java -cp target/classes com.pbo.Main
  ```
- Tidak ada test framework atau CI. Uji manual dengan harga `100000`: diskon 10/20/30/50 harus menghasilkan `90000`, `80000`, `70000`, `50000`.

## Struktur MVC

Semua kode di `src/main/java/com/pbo/`:

- `Main.java` - entrypoint. Isinya hanya `public static void main(String[] args)` yang memanggil `SwingUtilities.invokeLater`; jangan pakai `IO.println` atau `static void main()`.
- `model/` - logika diskon murni, tanpa `import javax.swing`. Contoh: `DiscountCalculator`.
- `view/` - komponen dan layout Swing, tanpa rumus. Contoh: `CalculatorFrame`.
- `controller/` - `ActionListener`, validasi input, panggil model, update view. Contoh: `CalculatorController`.

Rumus hanya di model. View tidak menghitung; controller tidak membuat komponen UI.

## Perilaku

- Satu window: input harga, pilihan diskon tetap 10/20/30/50 (combo box atau radio), tombol Hitung, label harga akhir.
- Harga wajib angka > 0. Input kosong, bukan angka, atau negatif -> `JOptionPane` error, jangan crash.
- Hasil dalam Rupiah tanpa desimal (`NumberFormat` locale `id-ID`). Cukup `double`; tidak perlu `BigDecimal`.
- Teks UI dalam Bahasa Indonesia.
- Hanya empat diskon tetap. Jangan tambah diskon custom, riwayat, login, export, atau penyimpanan file.

## Batasan

- Hanya Java Swing + JDK. Jangan tambah dependency ke `pom.xml`, dan jangan pakai database, file, atau network.
- Jangan commit `.agents/`, `.opencode/`, atau `.windsurf/`; itu skill tooling, bukan kode proyek. Untuk ponytail, commit `skills-lock.json` dan restore dengan `npx skills experimental_install`.
- Sebelum commit/PR, jalankan aplikasi dan pastikan keempat diskon benar.
