package tj.boronov.farhang.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tj.boronov.farhang.R
import tj.boronov.farhang.data.model.Categories
import tj.boronov.farhang.ui.phrasebook.category.CategoryActivity

class CategoriesAdapter :
    PagingDataAdapter<Categories, CategoriesAdapter.ViewHolder>(
        CategoriesComparator
    ) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get data
        val category = getItem(position)

        // Set data to item
        holder.itemView.findViewById<TextView>(R.id.category_name).text = category?.name
        holder.itemView.findViewById<TextView>(R.id.category_description).text =
            category?.description

        // Set category icon
        Glide.with(holder.itemView.context)
            .load(Uri.parse("file:///android_asset/icon/" + category?.image))
            .into(holder.itemView.findViewById(R.id.category_icon))

        // On category click
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CategoryActivity()::class.java)

            intent.putExtra("categoryID", category?.id)
            intent.putExtra("categoryName", category?.name)
            intent.putExtra("categoryDescription", category?.description)
            intent.putExtra("categoryImage", category?.image)

            it.context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_list_item, parent, false)
        )
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // Categories comparator
    object CategoriesComparator : DiffUtil.ItemCallback<Categories>() {
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.name == newItem.name
        }
    }
}