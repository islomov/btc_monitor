package com.reasonslab.btctracker.data.channel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.reasonslab.btctracker.data.model.Rate
import java.util.concurrent.locks.ReentrantLock

// TODO: Change LiveData to Flow or RxJava
object GlobalEvent {

    private val _events: MutableLiveData<Events> = MutableLiveData<Events>()
    private val lock = ReentrantLock()

    fun push(event: Events) {
        lock.lock()
        try {
            _events.postValue(event)
        } finally {
            lock.unlock()
        }
    }

    fun add(observer: Observer<in Events>) {
        lock.lock()
        try {
            _events.observeForever(observer)
        } finally {
            lock.unlock()
        }
    }

    fun remove(observer: Observer<in Events>) {
        lock.lock()
        try {
            _events.removeObserver(observer)
        } finally {
            lock.unlock()
        }
    }
}


sealed class Events {
    data class BTCPriceChangedEvent(val rate: Rate) : Events()
}