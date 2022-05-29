package com.example.storyapp.data.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.core.util.Pair
import com.example.storyapp.data.model.story.Story
import com.example.storyapp.databinding.ItemStoryBinding
import com.example.storyapp.ui.activity.detail.DetailActivity

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private val listStory = ArrayList<Story>()

    class ViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(imageView)

                tvJudul.text = story.name
                textDescription.text = story.description

                cardView.setOnClickListener {

                    val intent = Intent(itemView.context, DetailActivity::class.java)

                    intent.putExtra(EXTRA_IMAGE, story.photoUrl)
                    intent.putExtra(EXTRA_DESCRIPTION, story.description)
                    intent.putExtra(EXTRA_NAME, story.name)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imageView, "photo"),
                            Pair(tvJudul, "name"),
                            Pair(textDescription, "description"),
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

    @SuppressLint("NotifyDataSetChanged")
    fun setStory(story: ArrayList<Story>) {
        listStory.clear()
        listStory.addAll(story)
        notifyDataSetChanged()
    }

    companion object{
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESCRIPTION = "extra_description"
    }
}