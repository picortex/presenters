package presenters

import kollections.List

interface SerializableLiveDataList<D> : InputField, SerializableLiveData<List<D>>, LiveDataList<D>