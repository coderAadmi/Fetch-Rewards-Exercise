package com.fetch.rewards.exercise.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.fetch.rewards.exercise.R
import com.fetch.rewards.exercise.db.ListItem
import com.fetch.rewards.exercise.ui.theme.FetchRewardsExerciseTheme

@Preview(showSystemUi = true)
@Composable
fun preview2(){
    Column(Modifier.padding(top = 100.dp)) {
        ListGroupCardView(1,2,{})
    }
}

@Composable
fun ListGroupCardView(listId : Int = 0,
                      count : Int = 0,
                      onCardClicked : (lId : Int) -> Unit) {


    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        , onClick = { onCardClicked(listId) }) {

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

            val (mStartIcon, mListIdLabel, mCountLabel,
                mListId, mCount, mNextIcon) = createRefs()

            Icon(
                painterResource(R.drawable.icon_group_id_list),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(mStartIcon) {
                        top.linkTo(parent.top, margin = 12.dp)
                        start.linkTo(parent.start, margin = 12.dp)
                        bottom.linkTo(parent.bottom, margin = 12.dp)
                    })

            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(mNextIcon) {
                        top.linkTo(parent.top, margin = 12.dp)
                        end.linkTo(parent.end, margin = 24.dp)
                        bottom.linkTo(parent.bottom, margin = 12.dp)
                    })

            Text(
                text = "List Id", modifier =
                    Modifier.constrainAs(mListIdLabel) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(mStartIcon.end, margin = 24.dp)
                    })


            Text(
                text = "Items", modifier =
                    Modifier.constrainAs(mCountLabel) {
                        top.linkTo(mListIdLabel.bottom, margin = 8.dp)
                        start.linkTo(mStartIcon.end, margin = 24.dp)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    })



            Text(
                text = "$listId", modifier =
                    Modifier.constrainAs(mListId) {
                        top.linkTo(mListIdLabel.top)
                        bottom.linkTo(mListIdLabel.bottom)
                        end.linkTo(mNextIcon.start, margin = 4.dp)
                        start.linkTo(mListIdLabel.end, margin = 24.dp)
                        width = Dimension.fillToConstraints
                    },
                fontWeight = FontWeight.Bold)


            Text(
                text = "$count", modifier =
                    Modifier.constrainAs(mCount) {
                        top.linkTo(mCountLabel.top)
                        bottom.linkTo(mCountLabel.bottom)
                        end.linkTo(mNextIcon.start, margin = 4.dp)
                        start.linkTo(mListIdLabel.end, margin = 24.dp)
                        width = Dimension.fillToConstraints
                    }, fontWeight = FontWeight.Bold)

        }
    }
}