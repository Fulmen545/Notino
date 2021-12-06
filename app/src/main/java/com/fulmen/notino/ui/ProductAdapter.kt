package com.fulmen.notino.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fulmen.notino.R
import com.fulmen.notino.data.model.Products
import com.fulmen.notino.other.Constants.IMG_PREFIX

class ProductAdapter(context: Context): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    val context = context

    inner class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var brandName: TextView = itemView.findViewById(R.id.brandNameTv)
        var productName: TextView = itemView.findViewById(R.id.productNameTv)
        var productDesc: TextView = itemView.findViewById(R.id.productDescTv)
        var price: TextView = itemView.findViewById(R.id.priceTv)
        var productImg: ImageView = itemView.findViewById(R.id.productImageIv)
        var heartImg: ImageView = itemView.findViewById(R.id.heartImg)
        var ratingBar: RatingBar = itemView.findViewById(R.id.simpleRatingBar)
    }

    private val diffCallback = object: DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Products>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.notino_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.apply {
            brandName.text = item.brand.name
            productName.text = item.name
            productDesc.text = item.annotation
            price.text = readablePrice(item.price.value.toString(), item.price.currency)
            if (item.reviewSummary.score == 0) {
                ratingBar.visibility = View.GONE
            } else {
                ratingBar.rating = item.reviewSummary.score.toFloat()
                ratingBar.visibility = View.VISIBLE
            }
            Glide.with(context)
                .load(IMG_PREFIX + item.imageUrl)
                .fitCenter()
                .into(productImg)

            heartImg.apply {
                setTag(R.drawable.ic_heart_empty)
                setBackgroundResource(R.drawable.ic_heart_empty)
                setOnClickListener {
                    if (it.getTag() == R.drawable.ic_heart_empty) {
                        it.setBackgroundResource(R.drawable.ic_heart_fill)
                        it.setTag(R.drawable.ic_heart_fill)
                    } else {
                        it.setBackgroundResource(R.drawable.ic_heart_empty)
                        it.setTag(R.drawable.ic_heart_empty)
                    }
                }
            }

        }
    }

    private fun readablePrice(value: String, curr: String): String {
        when (curr) {
            "CZK" -> return value + " Kč"
            "EUR" -> return value + "€"
        }
        return value + " " + curr
    }
}