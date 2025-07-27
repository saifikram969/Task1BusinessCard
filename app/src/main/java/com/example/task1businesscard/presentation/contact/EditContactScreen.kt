package com.example.task1businesscard.presentation.contact

import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.task1businesscard.domain.model.Contact

@Composable
fun EditContactScreen(
    contact: Contact,
    onSave: (Contact) -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf(contact.name) }
    var company by remember { mutableStateOf(contact.company) }
    var phone by remember { mutableStateOf(contact.phone) }
    var email by remember { mutableStateOf(contact.email) }
    var notes by remember { mutableStateOf(contact.notes) }

    // Validation states
    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    // Animated colors for error states
    val errorColor = MaterialTheme.colorScheme.error
    val normalColor = MaterialTheme.colorScheme.onSurfaceVariant
    val nameColor by animateColorAsState(
        if (nameError) errorColor else normalColor,
        animationSpec = tween(300)
    )
    val emailColor by animateColorAsState(
        if (emailError) errorColor else normalColor,
        animationSpec = tween(300)
    )
    val phoneColor by animateColorAsState(
        if (phoneError) errorColor else normalColor,
        animationSpec = tween(300)
    )

    fun validateForm(): Boolean {
        nameError = name.isBlank()
        emailError = email.isNotBlank() && !isEmailValid(email)
        phoneError = phone.isNotBlank() && !isPhoneValid(phone)

        return !nameError && !emailError && !phoneError
    }

    fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = false
            },
            label = { Text("Name *") },
            isError = nameError,
            supportingText = {
                if (nameError) {
                    Text(
                        text = "Name is required",
                        color = errorColor
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = nameColor,
                unfocusedIndicatorColor = nameColor
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Name",
                    tint = nameColor
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = company,
            onValueChange = { company = it },
            label = { Text("Company") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.Business,
                    contentDescription = "Company"
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                phoneError = false
            },
            label = { Text("Phone") },
            isError = phoneError,
            supportingText = {
                if (phoneError) {
                    Text(
                        text = "Please enter a valid phone number",
                        color = errorColor
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = phoneColor,
                unfocusedIndicatorColor = phoneColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = "Phone",
                    tint = phoneColor
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            label = { Text("Email") },
            isError = emailError,
            supportingText = {
                if (emailError) {
                    Text(
                        text = "Please enter a valid email",
                        color = errorColor
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = emailColor,
                unfocusedIndicatorColor = emailColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Email",
                    tint = emailColor
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            leadingIcon = {
                Icon(
                    Icons.Default.Notes,
                    contentDescription = "Notes"
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (validateForm()) {
                    onSave(
                        contact.copy(
                            name = name,
                            company = company,
                            phone = phone,
                            email = email,
                            notes = notes
                        )
                    )
                    Toast.makeText(context, "Contact saved", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                Icons.Default.Save,
                contentDescription = "Save",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Save Contact")
        }
    }
}

private fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun isPhoneValid(phone: String): Boolean {
    return Patterns.PHONE.matcher(phone).matches()
}