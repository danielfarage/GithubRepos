package com.daniel.farage.githubrepos.features.repolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.daniel.farage.githubrepos.databinding.ItemListrepositoryBinding
import com.daniel.farage.githubrepos.domain.model.Repository

class RepoAdapter : PagingDataAdapter<Repository, RepoAdapter.RepoViewHolder>(compare) {

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        getItem(position)?.let { repository ->
            holder.bind(repository)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemListrepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class RepoViewHolder(private val view: ItemListrepositoryBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(repository: Repository) {
            with(view) {
                textViewUserName.text = repository.authorName
                textViewUserRepo.text = repository.nameRepo
                textViewStarsCount.text = repository.stars.toString()
                textViewForksCount.text = repository.forkCount.toString()
                Glide
                    .with(view.root.context)
                    .load(repository.authorPic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
                    .into(view.imageViewUserAvatar)
            }
        }
    }

    companion object {
        private val compare = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(
                oldItem: Repository,
                newItem: Repository
            ): Boolean {
                return oldItem.nameRepo == newItem.nameRepo
            }

            override fun areContentsTheSame(
                oldItem: Repository,
                newItem: Repository
            ): Boolean {
                return oldItem.stars == newItem.stars && oldItem.forkCount == newItem.forkCount
            }

        }
    }

}