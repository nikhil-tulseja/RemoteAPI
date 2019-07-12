package com.remoteapi.nikhilkumar.remoteapi.ui

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MyContestAPIElement
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.PlayerData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Product
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.ProductsList
import com.remoteapi.nikhilkumar.remoteapi.utils.PaginationAdapter
import com.remoteapi.nikhilkumar.remoteapi.utils.loadImage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CreateInvoiceAdapter(val clickListener: ItemClickListener) : PaginationAdapter<Product>() {

    var selectedProductsList = mutableListOf<Product>()
    var map = HashMap<Product,Int>()

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.invoice_item_lyt, parent, false)
        return AllProductViewHolder(view,map,clickListener)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is AllProductViewHolder) {
            holder.bind(dataList[position])
        }
    }

    override fun addLoadingViewFooter() {
        addLoadingViewFooter(Product())
    }

    fun updateData(contestList : List<Product>){
        val currentSize = itemCount
        dataList.addAll(contestList)
        notifyItemRangeInserted(currentSize, contestList.size)
    }

    fun getAllSelectedProducts() : Map<Product,Int>{
        return map
    }

     class AllProductViewHolder(itemView: View ,val map: HashMap<Product,Int>,val clickListener :ItemClickListener ) : RecyclerView.ViewHolder(itemView) {

        val context by lazy { itemView.context }
        val priceTv = itemView.findViewById<TextView>(R.id.price_tv)
        val prodTv= itemView.findViewById<TextView>(R.id.prod_tv)
         val prodIv= itemView.findViewById<ImageView>(R.id.prod_iv)
         val minusIv = itemView.findViewById<ImageView>(R.id.minus_iv)
         val plusIv = itemView.findViewById<ImageView>(R.id.plus_iv)
         val countTv = itemView.findViewById<TextView>(R.id.count_tv)

        fun bind(prod : Product) {
            countTv.text = prod.quantity.toString()
            priceTv.text = prod.price.toString()
            prodIv.loadImage(prod.url)
            prodTv.text = prod.name ?: ""
            minusIv.setOnClickListener {
                if(prod.quantity > 0) {
                    prod.quantity--
                }
                countTv.text = prod.quantity.toString()
                clickListener.onQuantityDecreased(0)
                map[prod] = prod.quantity
            }
            plusIv.setOnClickListener {
                prod.quantity++
                clickListener.onQuantityIncreased(0)
                countTv.text = prod.quantity.toString()
                map[prod] = prod.quantity
            }

        }




    }

    interface ItemClickListener{
        fun onQuantityIncreased(id : Int)
        fun onQuantityDecreased(id : Int)
    }
}