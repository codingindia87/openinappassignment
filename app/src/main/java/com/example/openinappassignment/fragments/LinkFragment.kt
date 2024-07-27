package com.example.openinappassignment.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openinappassignment.AppApplication
import com.example.openinappassignment.R
import com.example.openinappassignment.adapters.LinksAdapters
import com.example.openinappassignment.databinding.FragmentLinkBinding
import com.example.openinappassignment.fragments.viewmodels.LinkViewModel
import com.example.openinappassignment.fragments.viewmodels.LinkViewModelFactory
import com.example.openinappassignment.model.Dashboard
import com.example.openinappassignment.model.LinksData
import com.example.openinappassignment.utils.AccessToken
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat

class LinkFragment : Fragment() {

    private lateinit var binding: FragmentLinkBinding

    private lateinit var linkViewModel: LinkViewModel

    private lateinit var linksAdapters: LinksAdapters

    private var linksList: ArrayList<LinksData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = (context?.applicationContext as AppApplication).openInAppRepository

        val accessToken = AccessToken(requireContext())

        linkViewModel = ViewModelProvider(this,LinkViewModelFactory(repository,
            accessToken.getAccessToken()!!
        )).get(LinkViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLinkBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linkViewModel.dashboard.observe(viewLifecycleOwner) { setData(it) }

        binding.viewAllLinks.setOnClickListener {  }

        binding.settingIcon.setOnClickListener {  }

        binding.viewAnalytics.setOnClickListener {  }

    }

    fun checkWhatsapp(context: Context): Boolean {
        val install: Boolean = try {
            val pm: PackageManager = context.packageManager
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return install
    }

    private fun open(phoneNumber:String){
        if (checkWhatsapp(requireContext())) {
            val uri =
                Uri.parse("https://api.whatsapp.com/send?phone=${phoneNumber}&text=Hi")
            val i = Intent(Intent.ACTION_VIEW)
            i.setPackage("com.whatsapp")
            i.setData(uri)
            context?.startActivity(i)
        } else {
            Toast.makeText(context, "Whatsapp not found", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n", "SimpleDateFormat")
    private fun setData(dashboard: Dashboard){

        binding.layoutW.setOnClickListener {
            open("+91${dashboard.supportWhatsappNumber}")
        }

        binding.textTodayClick.text = dashboard.todayClicks?.toString()
        binding.textTopLocation.text = dashboard.topLocation
        binding.textTopSource.text = dashboard.topSource
        binding.textTotalLinks.text = dashboard.totalLinks.toString()
        binding.textTotalClick.text = dashboard.totalClicks.toString()
        binding.textExtraIncome.text = "${dashboard.extraIncome.toString()}$"

        binding.linkRecyclerView.layoutManager = LinearLayoutManager(context)

        linksAdapters = LinksAdapters(requireContext(),linksList)

        binding.linkRecyclerView.adapter = linksAdapters

        binding.btnRecentLink.isChecked = true

        linksList.addAll(dashboard.data?.recentLinks!!)

        val formatter = SimpleDateFormat("HH:mm")
        val formattedDate = formatter.format(System.currentTimeMillis())

        binding.textTime.text = "Today ${dashboard.startTime} - ${formattedDate}"

        binding.linkCGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId){
                R.id.btn_recent_link ->{
                    linksList.clear()
                    linksList.addAll(dashboard.data?.recentLinks!!)
                    linksAdapters.notifyDataSetChanged()
                }
                R.id.btn_top_link ->{
                    linksList.clear()
                    linksList.addAll(dashboard.data?.topLinks!!)
                    linksAdapters.notifyDataSetChanged()
                }
            }
        }

        setDataInLineChart(dashboard)
    }

    private fun setDataInLineChart(dashboard: Dashboard){

        val hashMap: HashMap<String,Int> = dashboard.data?.overallUrlChart!!

        val xData = ArrayList<String>()
        val values = ArrayList<Int>()

        for (key in hashMap.keys){
            xData.add(key)
            values.add(hashMap[key]!!)
        }

        val xAxis = binding.lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xData)
        xAxis.setLabelCount(10)
        xAxis.granularity = 1f

        val yAxis = binding.lineChart.axisLeft
        yAxis.setAxisMaxValue(0f)
        yAxis.setAxisMaxValue(100f)
        yAxis.axisLineWidth = 4f
        yAxis.axisLineColor = Color.GRAY
        yAxis.setLabelCount(10)

        val entrirs = ArrayList<Entry>()
        var count = 0
        for (value in values){
            entrirs.add(Entry(count.toFloat(),value.toFloat()))
            count += 1
        }

       val dataset = LineDataSet(entrirs,"Clicks")
        dataset.color = Color.GRAY

        val lineData = LineData(dataset)

        binding.lineChart.data = lineData

        binding.lineChart.invalidate()

    }
}