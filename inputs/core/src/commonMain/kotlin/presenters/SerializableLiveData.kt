package presenters

interface SerializableLiveData<D> : InputField, Serializable<D>, LiveData<D>