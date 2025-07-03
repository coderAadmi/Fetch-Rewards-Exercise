package com.fetch.rewards.exercise.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fetch.rewards.exercise.ListItemViewModel
import com.fetch.rewards.exercise.network.ApiResultFailure
import com.fetch.rewards.exercise.network.ConnectivityStatus
import com.fetch.rewards.exercise.R
import kotlinx.coroutines.flow.StateFlow


@Composable
fun NetworkCallStatus(vm : ListItemViewModel){
    when(vm.apiResultFailure.collectAsState().value) {
        ApiResultFailure.NoInternetConnection -> {
            Toast.makeText(LocalContext.current,"No internet connection available", Toast.LENGTH_SHORT).show()
            DataStatus("No Data available")
        }
        ApiResultFailure.Unknown -> {}
        ApiResultFailure.Error -> {

            if(!vm.listState.collectAsState().value.isNullOrEmpty()){
                    DataStatus("Showing offline data. Network call error")

            }

        }
        ApiResultFailure.Loading -> {
            Text(modifier = Modifier.fillMaxWidth().
            background(MaterialTheme.colorScheme.primary).padding(12.dp), text = "Fetching Data...",
                style = TextStyle(color = Color.White),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp)
        }

        ApiResultFailure.Success -> {

        }
    }
}

@Composable
fun DataStatus(cardTitle : String){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Text(text = cardTitle, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun ConnectivityStatus(connectivityStatus: StateFlow<ConnectivityStatus>,
                       onRefresh : () -> Unit ){
    when(connectivityStatus.collectAsState().value) {
        ConnectivityStatus.Connected -> {
            IconButton(onClick = onRefresh) {
                Icon(
                    painter = painterResource(R.drawable.refresh),
                    contentDescription = "action item for selecting list presentation",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
        ConnectivityStatus.DisConnected -> {
            Icon(
                painter = painterResource(R.drawable.disconnected),
                contentDescription = "action item for selecting list presentation",
                tint = MaterialTheme.colorScheme.errorContainer
            )
        }
        ConnectivityStatus.Unknown -> {
            Icon(
                painter = painterResource(R.drawable.network_check),
                contentDescription = "action item for selecting list presentation",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}