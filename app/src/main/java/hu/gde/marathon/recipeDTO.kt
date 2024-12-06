package hu.gde.marathon

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class RecipeDTO(
    val id: Long,                    // azonosito
    val name: String,                // recept neve
    val duration: Int,               // elkészítési idő percben
    val difficulty: String,          // nehézségi szint
    val ingredients: List<String>,   // hozzávalók
    val description: String         // elkészítés leírása
)


@Entity(tableName = "sections")
data class SectionEntity(
    @PrimaryKey() val id: Long,
    val name: String,                // recept neve
    val duration: Int,               // elkészítési idő percben
    val difficulty: String,          // nehézségi szint
    val ingredients: List<String>,   // hozzávalók
    val description: String
)