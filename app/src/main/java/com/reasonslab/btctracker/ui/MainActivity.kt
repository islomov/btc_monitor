package com.reasonslab.btctracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.reasonslab.btctracker.data.model.Rate
import com.reasonslab.btctracker.databinding.ActivityMainBinding
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: RateViewModel by viewModel()
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        observeEvents()

        binding?.swRefresh?.setOnRefreshListener {
            viewModel.getCurrentPrice()
        }

        binding?.btnSave?.setOnClickListener {
            val min = binding?.editMin?.text?.toString()?.toDoubleOrNull()
            val max = binding?.editMax?.text?.toString()?.toDoubleOrNull()
            if (min != null && max != null) {
                viewModel.saveLimit(min.toString(), max.toString())
                binding?.editMin?.setText("",TextView.BufferType.EDITABLE)
                binding?.editMax?.setText("",TextView.BufferType.EDITABLE)
            }
        }
        viewModel.getCachedPrice()
        viewModel.mointorPrice(applicationContext)
    }

    private fun observeEvents() {
        viewModel.uiEvent.observe(this, {
            when (it) {
                is RateViewState.RateViewSuccess -> handleSuccess(it.rate)
                is RateViewState.RateViewFail -> handleFail(it.msg)
                is RateViewState.RateViewProgress -> showProgress()
            }
        })
    }

    private fun handleSuccess(rate: Rate) {
        hideProgress()
        binding?.tvRate?.text = rate.rate
        binding?.tvLastUpdate?.text = rate.lastUpdate
    }

    private fun handleFail(msg: String?) {
        hideProgress()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun showProgress() {
        binding?.swRefresh?.isRefreshing = true
    }

    private fun hideProgress() {
        binding?.swRefresh?.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}