package hu.gde.marathon.marathon

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.gde.marathon.Api
import hu.gde.marathon.AppDatabase
import hu.gde.marathon.Network
import hu.gde.marathon.RecipeDTO
import hu.gde.marathon.SectionEntity
import hu.gde.marathon.databinding.RowRecipeBinding
import hu.gde.marathon.databinding.ActivityRecipeDetailBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeListActivity : AppCompatActivity() {

    lateinit var binding:  ActivityRecipeDetailBinding
    lateinit var adapter: RecipeListAdapter

    private val api: Api = Network().api()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater) //ActivityRecipeListBinding
        setContentView(binding.root)

        adapter = RecipeListAdapter { section ->
            // onClick
            Toast.makeText(this, section.name, Toast.LENGTH_SHORT).show()
            val intent = Intent(this@RecipeListActivity, RecipeDetailActivity::class.java)
            intent.putExtra(RecipeDetailActivity.key_id, section.id)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        loadData()
    }

    fun loadData() {
        val db = AppDatabase.buildDatabase(applicationContext)
        api.loadSections().enqueue(object : Callback<List<RecipeDTO>> {
            override fun onResponse(
                call: Call<List<RecipeDTO>>,
                response: Response<List<RecipeDTO>>
            ) {
                if (response.isSuccessful) {
                    binding.retry.visibility = View.GONE
                    val responseItems = response.body().orEmpty()
                    val entityList = responseItems.map { it -> it.toEntity() }

                    db.sectionDao().insert(entityList)
                    adapter.submitList(entityList)
                } else {
                    Toast.makeText(this@RecipeListActivity, "Error tortent", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<RecipeDTO>>, t: Throwable) {
                Toast.makeText(this@RecipeListActivity, "Failure tortent", Toast.LENGTH_SHORT)
                    .show()

                val entities = db.sectionDao().getAll()
                if (entities.isEmpty()) {
                    binding.retry.visibility = View.VISIBLE
                    binding.retry.setOnClickListener {
                        loadData()
                    }
                } else {
                    adapter.submitList(entities)
                }
            }
        })
    }
}

fun RecipeDTO.toEntity(): SectionEntity {
    return SectionEntity(
        id = id,
        name = name,
        duration = duration,
        difficulty = difficulty,
        ingredients = ingredients,
        description = description
    )
}

class RecipeListAdapter(val onClick: (SectionEntity) -> Unit) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    private var items: List<SectionEntity> = listOf()

    class ViewHolder(val binding: RowRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowRecipeBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.root.setOnClickListener {
            onClick.invoke(item)
        }
        holder.binding.title.text = item.name
        holder.binding.subTitle.text = item.distance
    }

    fun submitList(data: List<SectionEntity>) {
        this.items = data
        notifyDataSetChanged()
    }

}