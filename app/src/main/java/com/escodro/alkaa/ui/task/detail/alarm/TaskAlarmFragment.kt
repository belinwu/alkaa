package com.escodro.alkaa.ui.task.detail.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.escodro.alkaa.R
import com.escodro.alkaa.common.extension.showDateTimePicker
import com.escodro.alkaa.common.extension.showToast
import com.escodro.alkaa.databinding.FragmentTaskDetailAlarmBinding
import kotlinx.android.synthetic.main.fragment_task_detail_alarm.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.Calendar

class TaskAlarmFragment : Fragment() {

    private val viewModel: TaskAlarmViewModel by viewModel()

    private var binding: FragmentTaskDetailAlarmBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView()")

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_task_detail_alarm, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated()")

        binding?.setLifecycleOwner(this)
        binding?.viewModel = viewModel
        initListeners()
    }

    private fun initListeners() {
        btn_taskdetail_date.setOnClickListener { showDateTimePicker(::updateTaskWithDueDate) }
        textview_taskdetail_date.setOnClickListener { showDateTimePicker(::updateTaskWithDueDate) }
        chip_taskdetail_date.setOnCloseIconClickListener { removeAlarm() }
    }

    private fun updateTaskWithDueDate(calendar: Calendar) {
        Timber.d("updateTaskWithDueDate() - Calendar = ${calendar.time}")

        viewModel.setAlarm(calendar)
    }

    private fun removeAlarm() {
        Timber.d("removeAlarm()")

        context?.showToast(R.string.task_details_alarm_removed)
        viewModel.removeAlarm()
    }
}
