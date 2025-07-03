package com.fetch.rewards.exercise.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fetch.rewards.exercise.db.ListItem

@Composable
fun ListByIdListView(items : List<ListItem>){
    LazyColumn(modifier = Modifier
        .fillMaxSize()) {
       items(items) { li ->
            ListWithNameCardView(li)
        }
    }
}
