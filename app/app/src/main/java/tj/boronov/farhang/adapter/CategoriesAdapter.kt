package tj.boronov.farhang.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tj.boronov.farhang.R
import tj.boronov.farhang.database.Categories

class CategoriesAdapter :
    PagingDataAdapter<Categories, CategoriesAdapter.WordViewHolder>(
        CategoriesComparator
    ) {

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        // Get data
        val category = getItem(position)

        // Set data to item
        holder.itemView.findViewById<TextView>(R.id.category_name).text = category?.name
        holder.itemView.findViewById<TextView>(R.id.category_description).text =
            category?.description

        Glide.with(holder.itemView.context)
            .load(Uri.parse("file:///android_asset/icon/" + category?.image))
            .into(holder.itemView.findViewById(R.id.category_icon))

        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, "work", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_list_item, parent, false)
        )
    }

    class WordViewHolder(view: View) : RecyclerView.ViewHolder(view)

    //  Categories comparator
    object CategoriesComparator : DiffUtil.ItemCallback<Categories>() {
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.name == newItem.name
        }
    }
}