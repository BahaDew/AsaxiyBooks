package com.sudo_pacman.asaxiybooks.domain.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sudo_pacman.asaxiybooks.data.model.BookData
import com.sudo_pacman.asaxiybooks.utils.myLog
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class RepositoryAddBook {
    private val fireStorage = Firebase.firestore
    private var books = mutableListOf<BookData>()

    init {
        "mylog".myLog()
        books = mutableListOf(
            BookData(
                name = "Jimjitlik",
                author = "Said Ahmad",
                description = "«Jimjillik» romani 1986—1987 yillarda «Sharq yulduzi» jumalida\n" +
                        "davomli e’ion qilindi. G ‘afur G'ulom nomidagi Adabiyot va san’at\n" +
                        "nashriyoti uni 1989 yilda 150 000 nusxada bosib chiqardi.\n" +
                        "Biroq, senzura muallifni ogohlantirmay, hatto unga sezdirmay,\n" +
                        "asarning uchdan bir qismini olib tashladi. Oqibatda, «mayib» asar\n" +
                        "o‘quvchi qo'liga tegdi.\n" +
                        "Muallif o'tgan o'n to'rt yil davomida romanni qayta tiklash uchun\n" +
                        "ko‘p urindi. U asami qayta ishlash jarayonida bugungi kun talablarini\n" +
                        "nazardan qochirmadi.\n" +
                        "Roman yovuzlik bilan ezgulikning abadiy kurashiga bag'ishlangan.",
                totalSize = "8.06",
                coverImage = "",
                filePath = "",
                type = "pdf"
            ),

            BookData(
                name = "ҲАЗРАТИ УМАР ИБН ҲАТТОБ",
                author = "Аҳмад Лутфий Қозончи",
                description = "Ушбу асар ўзбек китобхонлари орасида «Саодат асри \u0014иссалари», «Ўгай она», «\u0018айнона»\n" +
                        "каби асарлари билан \u0019урмат \u0014озонган А\u0019мад Лутфий \u0018озончи \u0019азратларининг \u0014аламига\n" +
                        "мансубдир. Унда мусулмонларга энг суюкли инсонлардан бири, адолати \u0019ануз \u0014албларда \u0014уёш\n" +
                        "бўлиб порлаётган мўътабар зот - Умар ибн xаттобнинг \u0019аёти биз билган-билмаган \u0014ирралари\n" +
                        "билан \u0014айно\u0014, жонли бўё\u0014ларда чизилган. Замин узра \u0019идоят \u0014уёши бал\u0014\u0014ан, оламлар нурга\n" +
                        "чўмилган кунлар эркини маърифатга чан\u0014о\u0014 \u0014албларга \u0019узур бағишлайди, деган умиддамиз. ",
                totalSize = "1.54",
                coverImage = "",
                filePath = "",
                type = "pdf"
            ),
            BookData(
                name = "MEN – BILOL",
                author = "Garri Kreyg",
                description = "Bilol roziyallohu anhu odamlarning qalblaridan\n" +
                        "o‘chmas o‘rin olgan, shu bois, u zot doimo muhabbat\n" +
                        "bilan eslab kelinadi. Bu suyukli shaxs kishilar yuragidan shunchalik chuqur o‘rin egallaganidan ko‘plar\n" +
                        "uning hayoti haqida anchayin narsa yozish mumkin\n" +
                        "deb bilgan. U doimo Payg‘ambar alayhissalom bilan birga bo‘lganligi va u zot tomonidan sevilganligi\n" +
                        "haqida gapirishga, albatta, ular haqli. Ushbu tarixiy\n" +
                        "davrga oid bir nechta suratlarga, asosan o‘tmishdagi\n" +
                        "qo‘lyozma bezaklarga nazar tashlanganida, Bilol roziyallohu anhuni tanib olish qiyin emas: hazrati Bilol\n" +
                        "qora tanli edilar.\n" +
                        "U Makkada tug‘ilgan va Raboh ismli habash qulning o‘g‘li edi.\n" +
                        "U butlarga sig‘iniladigan shaharda Alloh borligiga iymon keltirgani uchun aziyatga uchradi.\n" +
                        "U Muhammad sollallohu alayhi vasallamning\n" +
                        "yaqin do‘stlari Abu Bakr tomonidan sotib olindi va\n" +
                        "ozod qilindi.\n" +
                        "U birinchi muazzin qilib tayinlandi.\n" +
                        "U Islomdagi birinchi urushlarda qoʻshinlarni\n" +
                        "oziq-ovqat bilan taʼminlashga masʼul bo‘ldi.\n" +
                        "U Payg‘ambar alayhissalomga shu qadar yaqin\n" +
                        "bo‘lganki, Rasulullohni tongda uyg‘otish uning zimmasida edi. ",
                totalSize = "4.4",
                coverImage = "",
                filePath = "",
                type = "pdf"
            ),
            BookData(
                name = "МУСО " +
                        "ФИРЪАВН " +
                        "ЎЛДИРА ОЛМАГАН " +
                        "ПАЙҒАМБАР",
                author = "ҲАКИМЎҒЛИ ИСМОИЛ",
                description = "Ушбу асар орқали Мусо пайғамбар ва Бани\n" +
                        "Исроил халқининг бундан минг йиллар аввалги\n" +
                        "ҳаёти ҳақида кўплаб маълумотларга эга бўласиз,\n" +
                        "айни пайтда ўзгармас инсоний ҳақиқатни ўша\n" +
                        "давр замон ва маконига мослаштирасиз.",
                totalSize = "4.26",
                coverImage = "",
                filePath = "",
                type = "pdf"
            )
        )

    }

     fun addBook() = callbackFlow<String> {
        var size = books.size
        "books size ${books.size}".myLog()

        for (index in books.indices) {
            fireStorage
                .collection("book_data")
                .document()
                .set(books[index])
                .addOnSuccessListener {
                    --size

                    "add book: $size".myLog()
                }
                .addOnFailureListener { "add book error ${it.message}".myLog() }
        }

        awaitClose()
    }
}