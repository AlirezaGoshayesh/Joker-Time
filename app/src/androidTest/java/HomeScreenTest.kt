import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.jokertime.ui.CategoriesSection
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCategoryChipClicked(){
        val categories = listOf("Programming", "Misc", "Dark", "Pun")
        var selectedCategories = listOf<String>()
        val onSelectionChanged = { category: String ->
            selectedCategories = if (selectedCategories.contains(category))
                selectedCategories.filter { it != category }
            else
                selectedCategories + category
        }
        composeTestRule.setContent {
            CategoriesSection(
                categories = categories,
                selectedCategories = selectedCategories,
                onSelectionChanged = onSelectionChanged,
                onDoneClicked = {  }
            )
        }
        composeTestRule.onNodeWithText(categories[0]).performClick()
        assert(selectedCategories.contains(categories[0]))
    }

    @Test
    fun testNavigateToJokeScreen(){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val categories = listOf("Programming", "Misc", "Dark", "Pun")
        var selectedCategories = listOf<String>()
        val onSelectionChanged = { category: String ->
            selectedCategories = if (selectedCategories.contains(category))
                selectedCategories.filter { it != category }
            else
                selectedCategories + category
        }
        val onDoneClicked = { navController.navigate("joke/$selectedCategories") }
        composeTestRule.setContent {
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavHost(navController = navController, startDestination = "home") {
                composable(route = "home") {
                    CategoriesSection(
                        categories = categories,
                        selectedCategories = selectedCategories,
                        onSelectionChanged = onSelectionChanged,
                        onDoneClicked = onDoneClicked
                    )
                }
                composable(route = "joke/{categories}") { entry ->
                    val categories = entry.arguments?.getString("categories").toString()
                    assert(categories == selectedCategories.joinToString(","))
                }
            }
        }
        composeTestRule.onNodeWithText(categories[0]).performClick()
        composeTestRule.onNodeWithTag("Done").performClick()
    }
}