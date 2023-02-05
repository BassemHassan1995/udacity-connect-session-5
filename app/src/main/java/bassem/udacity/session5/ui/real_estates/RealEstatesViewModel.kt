package bassem.udacity.session5.ui.real_estates

import androidx.lifecycle.*
import bassem.udacity.session5.data.model.RealEstate
import bassem.udacity.session5.data.model.Type
import bassem.udacity.session5.data.repo.RealEstateRepository
import kotlinx.coroutines.launch

class RealEstatesViewModel constructor(private val repository: RealEstateRepository) : ViewModel() {

    private val type = MutableLiveData(Type.ALL)

    val realEstates: LiveData<List<RealEstate>> = Transformations.switchMap(type) { type ->
        when (type) {
            Type.RENT -> repository.getRealEstateList(Type.RENT)
            Type.BUY -> repository.getRealEstateList(Type.BUY)
            Type.ALL -> repository.getRealEstateList(Type.ALL)
        }
    }

    init {
        refreshRealEstates()
    }

    fun setType(currentType: Type) {
        type.value = currentType
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun refreshRealEstates() {
        viewModelScope.launch {
            repository.refreshRealEstates()
            setType(Type.ALL)
        }
    }

}

class RealEstateViewModelFactory(private val repository: RealEstateRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RealEstatesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RealEstatesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}