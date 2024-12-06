package hu.gde.marathon.marathon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.gde.marathon.AppDatabase
import hu.gde.marathon.databinding.ActivityMarathonDetailBinding
import java.util.Date

class RecipeDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        binding = ActivityMarathonDetailBinding
        setContentView(binding.root)

        val id = intent.getLongExtra(key_id, -1L)

        val dao = AppDatabase.buildDatabase(applicationContext).sectionDao()

        val section = dao.getOne(id)

        if (section == null) {
            binding.name.text = "Error: Section not found with id: $id"
        } else {
            binding.name.text = section.name
            binding.duration.text = section.duration
            binding.difficulty.text = section.difficulty
            binding.ingredients.text = section.ingredients
            binding.description.text = section.description
        }
    }

    companion object {
        const val key_id = "arg_idasdf"
    }
}