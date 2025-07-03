package com.fetch.rewards.exercise.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fetch.rewards.exercise.db.ListItem

@Composable
fun ListByGroupListView(groupedData : Map<Int, List<ListItem>>, onListByIdCardClicked : (listId : Int) -> Unit){
    LazyColumn(modifier = Modifier
        .fillMaxSize()) {

        items(groupedData.keys.toList()) { key ->
            ListGroupCardView(
                listId = key,
                count = groupedData.get(key)!!.size,
                onCardClicked = { listId ->
                    onListByIdCardClicked(listId)
                }
            )
        }
    }
}