package com.example.openinappassignment.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("recent_links") var recentLinks: ArrayList<LinksData>? = null,
    @SerializedName("top_links") var topLinks: ArrayList<LinksData>? = null,
    @SerializedName("overall_url_chart") var overallUrlChart: HashMap<String,Int>
)
