package com.escodro.alkaa.ui.task.detail.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.escodro.alkaa.common.extension.notify
import com.escodro.alkaa.data.local.model.Task
import com.escodro.alkaa.ui.task.alarm.notification.TaskNotificationScheduler
import com.escodro.alkaa.ui.task.detail.TaskDetailProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.Calendar

/**
 * [ViewModel] responsible to provide information to [com.escodro.alkaa.databinding
 * .FragmentDetailBinding].
 */
class TaskDetailViewModel(
    private val contract: TaskDetailContract,
    taskProvider: TaskDetailProvider
) : ViewModel() {

    val taskData = taskProvider.taskData

    private val compositeDisposable = CompositeDisposable()

    /**
     * Updates the task title.
     *
     * @param title the task title
     */
    fun updateTitle(title: String) {
        Timber.d("updateTitle() - $title")

        if (title.isEmpty()) {
            return
        }

        taskData.value?.let {
            taskData.value?.title = title
            updateTask(it)
        }
    }

    /**
     * Updates the task description.
     *
     * @param description the task description
     */
    fun updateDescription(description: String) {
        Timber.d("updateDescription() - $description")

        taskData.value?.let {
            taskData.value?.description = description
            updateTask(it)
        }
    }

    private fun updateTask(task: Task) {
        Timber.d("updateTask() - $task")

        val disposable = contract.updateTask(task).subscribe()
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}
