package presenters

interface SerializableLiveFormattedData<I, O> : SerializableLiveData<O>, LiveDataFormatted<I, O>