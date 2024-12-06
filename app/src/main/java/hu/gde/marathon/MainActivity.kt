package hu.gde.marathon

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.gde.marathon.databinding.ActivityMainBinding
import hu.gde.marathon.databinding.RowRecipeBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        val db: AppDatabase = AppDatabase.buildDatabase(context = applicationContext)

        setContentView(binding.root)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = RecipeAdapter(
            onClick = { recipe ->
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.key_id, recipe.id)
                startActivity(intent)
            }
        )
        binding.recyclerView.adapter = adapter

        adapter.submitList(db.recipeDao().getAll())

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

@Entity(tableName = "Recipes")
class Recipe(
    @PrimaryKey val id: Long,
    val name: String,
    val duration: Int,
    val difficulty: String,
    val ingredients: List<String>,
    val description: String
)

class RecipeAdapter(val onClick: (Recipe) -> Unit) :
    RecyclerView.Adapter<RecipeAdapter.NameViewHolder>() {

    private var recipes: List<RecipeDTO> = listOf()

    class NameViewHolder(val binding: RowRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val binding = RowRecipeBinding.inflate(LayoutInflater.from(parent.context))
        return NameViewHolder(binding)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val item = recipes[position]
        holder.binding.root.setOnClickListener {
            onClick.invoke(Recipe)
        }
        holder.binding.firstName.text = item.firstName
        holder.binding.lastName.text = item.lastName
    }

    fun submitList(data: List<Name>) {
        this.items = data
        notifyDataSetChanged()
    }

}
