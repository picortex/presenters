package presenters

interface SerializableLiveFormattedData<I, O> : InputField, SerializableLiveData<O>, LiveDataFormatted<I, O>