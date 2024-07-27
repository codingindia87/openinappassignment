package com.example.openinappassignment.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openinappassignment.R
import com.example.openinappassignment.model.LinksData


class LinksAdapters(
    private val context: Context,
    private val linksList: ArrayList<LinksData>
): RecyclerView.Adapter<LinksAdapters.LinksVH>() {

    class LinksVH(itemView: View):RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.image)
        val imgLinkCopy: ImageView = itemView.findViewById(R.id.img_link_copy)
        val title: TextView = itemView.findViewById(R.id.title)
        val time: TextView = itemView.findViewById(R.id.text_time)
        val click: TextView = itemView.findViewById(R.id.text_click)
        val link: TextView = itemView.findViewById(R.id.text_link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.links_layout,parent,false)
        return LinksVH(view)
    }

    override fun getItemCount(): Int {
        return linksList.size
    }

    override fun onBindViewHolder(holder: LinksVH, position: Int) {
        val links = linksList[position]

        Glide.with(context.applicationContext)
            .load(links.originalImage)
            .into(holder.image)

        holder.title.text = links.app

        holder.time.text = links.timesAgo

        holder.click.text = links.totalClicks.toString()

        holder.link.text = links.webLink

        holder.imgLinkCopy.setOnClickListener {
            val clipboard: ClipboardManager? =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("Web link", links.webLink)
            clipboard?.setPrimaryClip(clip)
        }
    }
}