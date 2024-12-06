package hu.gde.marathon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.gde.marathon.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(key_id) ?: error("Id required!")
//        val a = intent.getStringExtra()
//        val n = intent.getStringExtra("n")
//        val dur = intent.getStringExtra(1)
//        val dif = intent.getStringExtra("dif")
//        val ingr = intent.getStringExtra("ingr")
//        val desc = intent.getStringExtra("desc")

        val db = AppDatabase.buildDatabase(applicationContext)

        val recipe = db.recipeDao().getOne(id)
//        val name = names.find { it.id == id }

        binding.id.text = id
        binding.
        binding.firstName.text = name?.firstName
        binding.lastName.text = name?.lastName
    }

    companion object {
        const val key_id = "arg_id"
        const val key_first_name = "arg_fn"
    }
}
