package com.example.task1businesscard.presentation.contact



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1businesscard.data.repository.ContactPreferenceManager
import com.example.task1businesscard.domain.model.Contact
import com.example.task1businesscard.domain.model.sampleContacts
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val manager = ContactPreferenceManager(application)

    private val _allContacts = MutableStateFlow(sampleContacts)

    private val _favorites = manager.favoriteContacts

    val contacts: StateFlow<List<Contact>> = combine(_allContacts, _favorites) { contacts, favorites ->
        contacts.map { contact ->
            contact.copy(isFavorite = favorites.contains(contact.name))
        }.sortedWith(
            compareByDescending<Contact> { it.isFavorite }.thenBy { it.name }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun toggleFavorite(contact: Contact) {
        viewModelScope.launch {
            if (contact.isFavorite) {
                manager.removeFavorite(contact.name)
            } else {
                manager.saveFavorite(contact.name)
            }
        }
    }
    fun refreshContacts() {
        // Simulate reloading logic or fetch again if needed
        _allContacts.value = sampleContacts.shuffled() // or actual data
    }
    fun deleteContact(contact: Contact) {
        _allContacts.value = _allContacts.value.filterNot { it.name == contact.name }
    }
    //   Update contact by ID and preserve favorite state
    fun updateContact(updated: Contact) {
        _allContacts.value = _allContacts.value.map {
            if (it.id == updated.id) {
                updated.copy(isFavorite = it.isFavorite) // preserve favorite flag
            } else it
        }
    }





}