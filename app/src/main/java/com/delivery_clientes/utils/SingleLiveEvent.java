package com.delivery_clientes.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class SingleLiveEvent<T> extends MutableLiveData<T> {
    private static final String TAG = "SingleLiveEvent";

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                if (hasActiveObservers()) {
                    // Evita que los eventos sean observados más de una vez
                    setValue(null);
                }
                observer.onChanged(t);
            }
        });
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }

    public void call() {
        setValue(null);
    }
}


