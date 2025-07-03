package com.fetch.rewards.exercise.views

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.fetch.rewards.exercise.db.ListItem

@Preview(showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Default Preview Dark")
@Composable
fun preview(){
    Column(Modifier.padding(top = 100.dp)) {
        ListWithNameCardView(ListItem(1,2,"Xyz"))
        ListWithNameCardView(ListItem(1,2,"Xyz"))
    }
}

@Composable
fun ListWithNameCardView(item : ListItem) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

            val (mStartIcon, mNameLabel, mListIdLabel, mIdLabel,
                mName, mListId, mId) = createRefs()

            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(mStartIcon) {
                        top.linkTo(parent.top, margin = 12.dp)
                        start.linkTo(parent.start, margin = 12.dp)
                        bottom.linkTo(parent.bottom, margin = 12.dp)
                    })

            Text(
                text = "Name", modifier =
                    Modifier.constrainAs(mNameLabel) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(mStartIcon.end, margin = 24.dp)
                    })


            Text(
                text = "List ID", modifier =
                    Modifier.constrainAs(mListIdLabel) {
                        top.linkTo(mNameLabel.bottom, margin = 8.dp)
                        start.linkTo(mStartIcon.end, margin = 24.dp)
                    })

            Text(
                text = "Id", modifier =
                    Modifier.constrainAs(mIdLabel) {
                        top.linkTo(mListIdLabel.bottom, margin = 8.dp)
                        start.linkTo(mStartIcon.end, margin = 24.dp)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    })


            Text(
                text = "${item.listId}", modifier =
                    Modifier.constrainAs(mListId) {
                        top.linkTo(mListIdLabel.top)
                        start.linkTo(mListIdLabel.end, margin = 32.dp)
                        end.linkTo(parent.end, margin = 12.dp)
                        bottom.linkTo(mListIdLabel.bottom)
                        width = Dimension.fillToConstraints
                    },
                fontWeight = FontWeight.Bold
                )



            Text(
                text = "${item.name}", modifier =
                    Modifier.constrainAs(mName) {
                        top.linkTo(mNameLabel.top)
                        bottom.linkTo(mNameLabel.bottom)
                        end.linkTo(parent.end, margin = 12.dp)
                        start.linkTo(mListIdLabel.end, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    },
                fontWeight = FontWeight.Bold)


            Text(
                text = "${item.id}", modifier =
                    Modifier.constrainAs(mId) {
                        top.linkTo(mIdLabel.top)
                        bottom.linkTo(mIdLabel.bottom)
                        end.linkTo(parent.end, margin = 12.dp)
                        start.linkTo(mListIdLabel.end, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    },
                fontWeight = FontWeight.Bold)

        }
    }
}