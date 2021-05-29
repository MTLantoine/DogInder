package com.example.dog_inder.utils

import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import java.lang.IllegalStateException
import kotlin.reflect.KProperty

class FragmentDataBinding<T: Any>(val fragment: Fragment): ReadWriteProperty<Fragment, T> {
    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object: DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(owner, Observer {
                    it.lifecycle.addObserver(object: DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            _value = null
                        }
                    })
                })
            }
        })
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException("Can't access value if null")
    }
}

fun <T: Any> Fragment.fragmentAutoCleared() = FragmentDataBinding<T>(fragment = this)