import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MenuBox(
    items: List<T>,
    selected: T?,
    selectedItemString: (T?) -> String,
    onItemSelected: (T?) -> Unit,
    label: String = "",
    itemTemplate: @Composable (T) -> Unit,
    isErrored: Boolean
){
    var expanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(selected ?: " ") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded}
    )
    {
        OutlinedTextField(
            value = selectedItemString(selected),
            onValueChange = { },
            readOnly = true,
            label = { Text(text = label)},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            isError = isErrored,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            items.forEach { item ->
                DropdownMenuItem(
                    text = {itemTemplate(item)},
                    onClick = {
                        expanded = false
                        textFieldValue = item.toString()
                        onItemSelected(item)
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun MyExposedDropdownMenuBoxPreview(){

    var selected by remember { mutableStateOf<String?>(null) }

    MenuBox(
        items = listOf("Item 1", "Item 2", "Item 3"),
        selected = selected,
        selectedItemString = { selected.toString() },
        onItemSelected = { selected = it },
        label = "Label",
        itemTemplate = { Text(text = it) },
        isErrored = false
    )
}