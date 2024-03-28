package com.dev.foodappchallengebinar.data.datasource.menu

import com.dev.foodappchallengebinar.data.models.Menu

class DummyFoodDataSource : FoodDataSource {
    override fun getFoodDetail(): List<Menu> {
        return mutableListOf(
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_honey_grilled_chicken.jpg?raw=true?raw=true",
                name = "Lalapan Ayam Bakar Madu",
                price = 25000.0,
                description = "Ayam bakar madu adalah hidangan khas yang terdiri dari potongan ayam yang dipanggang dengan sempurna dan direndam dalam campuran madu, kecap manis, dan rempah-rempah, menciptakan kombinasi rasa manis, gurih, dan sedikit pedas yang menggoda pada setiap gigitannya."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_fried_chicken.jpg?raw=true?raw=true",
                name = "Lalapan Ayam Goreng",
                price = 23000.0,
                description = "Ayam Goreng adalah hidangan populer yang terdiri dari potongan ayam yang dicelupkan ke dalam adonan rempah-rempah, kemudian digoreng hingga kecokelatan dan renyah di luar, sementara tetap juicy di dalam, menyajikan cita rasa gurih dan lezat yang tak tertandingi."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_ayam_geprek.jpg?raw=true?raw=true",
                name = "Lalapan Ayam Geprek",
                price = 23000.0,
                description = "Ayam Geprek adalah hidangan khas Indonesia yang terdiri dari potongan ayam goreng yang digeprek dan dilumuri sambal pedas, disajikan dengan nasi putih dan lalapan segar seperti mentimun dan tomat, menciptakan kombinasi cita rasa pedas, gurih, dan krispi yang menggugah selera."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_vegetable_meatballs.jpg?raw=true?raw=true",
                name = "Bakso Sayur",
                price = 12000.0,
                description = "Bakso Sayur adalah hidangan lezat yang terdiri dari bakso yang lembut dan beraroma, disajikan dalam kuah kaldu yang gurih dan kaya rasa, dengan tambahan sayuran segar seperti sawi, wortel, dan kubis, menciptakan kombinasi yang sehat dan memuaskan untuk dinikmati sebagai hidangan utama atau camilan yang menyegarkan."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_bakso_beranak.jpg?raw=true?raw=true",
                name = "Bakso Beranak",
                price = 20000.0,
                description = "Bakso Beranak adalah hidangan Indonesia yang terdiri dari bakso utama yang besar dan beberapa bakso kecil yang disajikan bersama dalam satu mangkuk kuah panas, memberikan kombinasi tekstur yang kenyal dan lembut serta cita rasa yang kaya dan gurih, menjadikannya pilihan yang menyenangkan untuk dinikmati sebagai hidangan utama atau camilan."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_cwie_mie.jpg?raw=true?raw=true",
                name = "Cwie Mie",
                price = 8000.0,
                description = "Cwie Mie adalah hidangan mie khas Tionghoa-Indonesia yang terdiri dari mie kuning yang disajikan bersama irisan daging sapi, ayam, atau seafood, dicampur dengan sayuran segar seperti sawi, wortel, dan kubis, disiram dengan kuah kaldu yang kaya rasa, serta diberi tambahan telur rebus dan bawang goreng untuk menambah cita rasa dan tekstur yang lezat."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_mie_gacoan.jpg?raw=true?raw=true",
                name = "Mie Gacoan",
                price = 13000.0,
                description = "Mie Gacoan adalah hidangan mie goreng khas Indonesia yang menggugah selera, terbuat dari mie kuning yang digoreng dengan sempurna, dicampur dengan berbagai bumbu dan bahan tambahan seperti telur, potongan daging ayam atau sapi, sayuran, dan saus khas, menciptakan cita rasa yang gurih, pedas, dan lezat dalam setiap suapan."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_dimsum.jpeg?raw=true?raw=true",
                name = "Dimsum",
                price = 12000.0,
                description = "Dimsum adalah hidangan tradisional Cina yang terdiri dari berbagai macam makanan kecil yang disajikan dalam porsi kecil, seperti dumpling, bakpao, siomay, dan variasi lainnya, biasanya dikukus, direbus, atau digoreng, sering disajikan sebagai hidangan pembuka atau untuk dimakan bersama teh pada jam makan siang atau makan malam."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_fried_fries.jpg?raw=true?raw=true",
                name = "Kentang Goreng",
                price = 10000.0,
                description = "Kentang Goreng adalah hidangan klasik yang terdiri dari potongan kentang yang dipotong panjang, digoreng hingga kecokelatan dan renyah di luar, serta lembut di dalamnya, menyajikan cita rasa gurih yang nikmat dan menjadi camilan yang sangat disukai oleh banyak orang di seluruh dunia."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_sweet_and_sour_prawns.jpg?raw=true?raw=true",
                name = "Udang Asam Manis",
                price = 40000.0,
                description = "Hidangan Udang Asam Manis adalah perpaduan sempurna antara udang segar yang digoreng dengan sempurna hingga krispi, dilumuri dengan saus asam manis yang lezat, menciptakan harmoni cita rasa yang memikat dengan sentuhan manis, asam, dan sedikit pedas, menghasilkan pengalaman makan yang menggugah selera dan tak terlupakan."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_sweet_and_sour_squid.jpg?raw=true?raw=true",
                name = "Cumi Asam Manis",
                price = 40000.0,
                description = "Hidangan Cumi Asam Manis adalah perpaduan sempurna antara cumi segar yang digoreng dengan tepung hingga renyah dan disajikan dengan saus asam manis yang kaya akan rasa gurih, manis, dan asam, menciptakan cita rasa yang kontras namun harmonis yang akan memanjakan lidah Anda dalam setiap suapan."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_drink.jpg?raw=true?raw=true",
                name = "Jus Buah",
                price = 10000.0,
                description = "Nikmati kesegaran alami dalam setiap tegukan dengan jus buah kami yang beragam varian, mulai dari segarinya jus jeruk, kelezatan jus apel, hingga kombinasi menyegarkan dari jus campuran buah-buahan eksotis, menyajikan cita rasa alami yang menyehatkan dan memikat selera untuk menemani setiap momen."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_smooth_ice.jpg?raw=true?raw=true",
                name = "Smooth Ice (All Variant)",
                price = 15000.0,
                description = "Smoothice, dengan segala varian rasanya, adalah minuman segar dan lezat yang disajikan dalam bentuk smoothie es yang menyegarkan. Dengan kombinasi buah-buahan segar, es batu, dan pilihan tambahan seperti susu, yogurt, atau madu, setiap tegukan Smoothice menghadirkan sensasi yang memuaskan dan menyehatkan. Dari varian buah tropis yang eksotis hingga buah-buahan musiman yang segar, setiap variasi Smoothice menawarkan pengalaman unik yang memanjakan lidah dan menyegarkan tenggorokan. Ideal untuk menemani hari yang panas atau sebagai camilan sehat kapan pun Anda menginginkannya. Nikmati sensasi segar dan manis dengan beragam varian es krim kami yang menyegarkan dan memikat, mulai dari varian klasik seperti vanilla, cokelat, dan strawberry, hingga pilihan eksotis seperti matcha green tea, salted caramel, dan cookies and cream, setiap gigitan adalah petualangan rasa yang menggugah selera dan memuaskan hasrat untuk kenikmatan es krim yang tak terlupakan. Nikmati sensasi segar dan manis dengan beragam varian es krim kami yang menyegarkan dan memikat, mulai dari varian klasik seperti vanilla, cokelat, dan strawberry, hingga pilihan eksotis seperti matcha green tea, salted caramel, dan cookies and cream, setiap gigitan adalah petualangan rasa yang menggugah selera dan memuaskan hasrat untuk kenikmatan es krim yang tak terlupakan."
            ),
            Menu(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/menu_img/img_ice_cream.jpg?raw=true?raw=true",
                name = "Ice Cream",
                price = 10000.0,
                description = "Nikmati sensasi segar dan manis dengan beragam varian es krim kami yang menyegarkan dan memikat, mulai dari varian klasik seperti vanilla, cokelat, dan strawberry, hingga pilihan eksotis seperti matcha green tea, salted caramel, dan cookies and cream, setiap gigitan adalah petualangan rasa yang menggugah selera dan memuaskan hasrat untuk kenikmatan es krim yang tak terlupakan."
            )
        )
    }
}