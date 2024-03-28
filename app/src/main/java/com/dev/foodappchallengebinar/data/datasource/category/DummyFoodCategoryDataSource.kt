package com.dev.foodappchallengebinar.data.datasource.category

import com.dev.foodappchallengebinar.data.models.Category

class DummyFoodCategoryDataSource : FoodCategoryDataSource {
    override fun getFoodCategory(): List<Category> {
        return mutableListOf(
            Category(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/category_img/img_rice.jpg?raw=true?raw=true",
                name = "Nasi"
            ),
            Category(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/category_img/img_mie.jpg?raw=true?raw=true",
                name = "Mie"
            ),
            Category(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/category_img/img_meatball.jpeg?raw=true?raw=true",
                name = "Bakso"
            ),
            Category(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/category_img/img_seafood.jpg?raw=true?raw=true",
                name = "Seafood"
            ),
            Category(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/category_img/img_snack.jpg?raw=true?raw=true",
                name = "Snack"
            ),
            Category(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/category_img/img_drink.jpg?raw=true?raw=true",
                name = "Minuman"
            ),
            Category(
                imgUrl = "https://github.com/GigihDC/FoodApp_assets/blob/main/FoodApp_assets/category_img/img_ice_cream.jpg?raw=true?raw=true",
                name = "Ice Cream"
            )
        )
    }
}