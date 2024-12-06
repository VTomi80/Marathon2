package hu.gde.marathon

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class, SectionEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(
            context, AppDatabase::class.java, "marathon"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    abstract fun recipeDao(): RecipeDao

    abstract fun sectionDao(): SectionDao
}

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(name: Recipe)

    @Query("SELECT * FROM recipes")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun getOne(id: String): Recipe?
}

@Dao
interface SectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(name: SectionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(name: List<SectionEntity>)

    @Query("SELECT * FROM sections")
    fun getAll(): List<SectionEntity>

    @Query("SELECT * FROM sections WHERE id = :id LIMIT 1")
    fun getOne(id: Long): SectionEntity?
}