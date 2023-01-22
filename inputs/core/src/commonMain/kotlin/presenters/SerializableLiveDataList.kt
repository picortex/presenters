package presenters

import kollections.List

interface SerializableLiveDataList<D> : SerializableLiveData<List<D>>, LiveDataList<D>