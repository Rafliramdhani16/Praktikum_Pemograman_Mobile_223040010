class PersegiPanjang(private val panjang: Double, private val lebar: Double) {
    // Fungsi untuk menghitung luas
    fun hitungLuas(): Double {
        return panjang * lebar
    }

    // Fungsi untuk menghitung keliling
    fun hitungKeliling(): Double {
        return 2 * (panjang + lebar)
    }

    // Fungsi untuk menampilkan hasil
    fun tampilkanHasil() {
        println("Panjang: $panjang")
        println("Lebar: $lebar")
        println("Luas: ${hitungLuas()}")
        println("Keliling: ${hitungKeliling()}")
    }
}

fun main() {
    println("Masukkan panjang:")
    val panjang = readLine()!!.toDouble()

    println("Masukkan lebar:")
    val lebar = readLine()!!.toDouble()

    val persegiPanjang = PersegiPanjang(panjang, lebar)
    persegiPanjang.tampilkanHasil()
}