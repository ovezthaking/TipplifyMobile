package com.example.tipplify.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            //JSON/SQLITE logic?

            _recipes.value = listOf(
                Recipe(
                    id = 1,
                    name = "Mojito",
                    ingredients = listOf("40 ml białego rumu \uD83E\uDD43", "8-10 liści mięty \uD83C\uDF3F", "4 ząbki limonki \uD83C\uDF4B\u200D\uD83D\uDFE9", "2 łyżeczki (barmańskie) cukru trzcinowego \uD83C\uDF6C", "Squirt wody sodowej \uD83D\uDCA7✨"),
                    description = "Skruszyć lód, limonkę wyszorować, pokroić na ćwiartki i wrzucić do szklanki typu highball/long drink. Zasypać cukrem i dokładnie ugnieść. Następnie dodać listki mięty i znowu ugnieść. Do połowy wysokości szklanki dodać kruszony lód, a następnie rum i znów lód (kruszony). Zamieszać. Na wierzch dodać wodę gazowaną i delikatnie zmieszać. Szklankę udekorować limonką i listkami mięty."
                ),
                Recipe(
                    id = 2,
                    name = "Whisky Sour",
                    ingredients = listOf("45 ml whisky (najlepiej Bourbonu) 🥃","25 ml soku z cytryny 🍋","20 ml syropu cukrowego 🍬","20 ml oddzielonego białka jaja kurzego 🥚","2 dashe (kilka kropel) Angostura Bitter 🍼","10 kostek lodu 🧊"),
                    description = "Do shakera wrzuć 5 kostek lodu, a następnie wlej whisky, sok z cytryny, syrop, białko oraz Angosturę. Energicznie wstrząśnij i oddziel lód od reszty składników, wysypując go do zlewu. Następnie zamknij shaker i znowu wstrząśnij w celu uzyskania gęstszej piany. Do szklanki wrzuć 5 kostek lodu, przelej delikatnie zawartość shakera. Koktajl udekoruj słomką do picia oraz skórką z cytryny. I gotowe!"
                ),
            )
        }
    }
    fun addRecipe(recipe: Recipe) {
        _recipes.value = _recipes.value + recipe
    }
}