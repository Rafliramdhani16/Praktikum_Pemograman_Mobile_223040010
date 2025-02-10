class IndeksNilaiMatkul {
    // Data class untuk menyimpan hasil konversi nilai
    data class HasilKonversi(
            val nilai: Int,
            val indeks: String,
            val keterangan: String
    )

    // Enum class untuk mendefinisikan rentang nilai
    enum class RentangNilai(val min: Int, val max: Int, val indeks: String, val keterangan: String) {
        A(80, 100, "A", "Sangat Baik"),
        AB(70, 79, "AB", "Baik Sekali"),
        B(60, 69, "B", "Baik"),
        BC(50, 59, "BC", "Cukup Baik"),
        C(40, 49, "C", "Cukup"),
        D(30, 39, "D", "Kurang"),
        E(0, 29, "E", "Sangat Kurang")
    }

    // Fungsi untuk konversi nilai ke indeks
    fun konversiNilai(nilai: Int): HasilKonversi {
        // Validasi range nilai
        if (nilai !in 0..100) {
            return HasilKonversi(nilai, "Invalid", "Nilai di luar jangkauan (0-100)")
        }

        // Mencari rentang nilai yang sesuai
        val rentang = RentangNilai.values().find { nilai in it.min..it.max }
        return rentang?.let {
            HasilKonversi(nilai, it.indeks, it.keterangan)
        } ?: HasilKonversi(nilai, "Error", "Terjadi kesalahan dalam konversi")
    }

    // Fungsi untuk menampilkan hasil
    fun tampilkanHasil(hasil: HasilKonversi) {
        println("\n=== Hasil Konversi Nilai ===")
        println("Nilai Angka    : ${hasil.nilai}")
        println("Nilai Indeks   : ${hasil.indeks}")
        println("Keterangan     : ${hasil.keterangan}")
        println("========================")
    }
}

fun main() {
    val converter = IndeksNilaiMatkul()

    while (true) {
        println("\nProgram Konversi Nilai Mata Kuliah")
        println("Masukkan nilai (0-100) atau ketik 'exit' untuk keluar:")

        val input = readLine()

        // Cek apakah user ingin keluar
        if (input?.lowercase() == "exit") {
            println("Program selesai.")
            break
        }

        // Validasi input
        when {
            input.isNullOrEmpty() -> println("Error: Nilai harus diisi!")
            input.toIntOrNull() == null -> println("Error: Input harus berupa angka!")
            else -> {
                val nilai = input.toInt()
                val hasil = converter.konversiNilai(nilai)
                converter.tampilkanHasil(hasil)
            }
        }
    }
}