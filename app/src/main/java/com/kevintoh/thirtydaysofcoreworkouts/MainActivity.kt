package com.kevintoh.thirtydaysofcoreworkouts

import android.os.Bundle
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kevintoh.thirtydaysofcoreworkouts.model.Workout
import com.kevintoh.thirtydaysofcoreworkouts.model.WorkoutRepository
import com.kevintoh.thirtydaysofcoreworkouts.ui.theme.ThirtyDaysOfCoreWorkoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirtyDaysOfCoreWorkoutsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ThirtyDaysOfCoreApp()
                }
            }
        }
    }
}

@Composable
private fun AppBar(modifier: Modifier) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .height(40.dp)
                .padding(start = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "App Icon",
            )
        }
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
private fun WorkoutListView(
    workouts: List<Workout>, 
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(workouts) { index, workout ->
            WorkoutListItem(
                index = index,
                workout = workout
            )
        }
    }
}

@Composable
private fun WorkoutListItem(
    index: Int,
    workout:Workout, 
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        elevation = 10.dp,
        modifier = modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Text(
                text = String.format(
                    "Day %d: %s",
                    index + 1,
                    stringResource(id = workout.nameRes)
                ),
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
            Image(
                painter = painterResource(id = workout.imageRes), 
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            if (workout.numberOfReps != null) {
                Text(
                    text = String.format(stringResource(R.string.one_set_format), workout.numberOfReps),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
            }
            if (workout.durationInSeconds != null) {
                Text(
                    text = String.format(stringResource(R.string.duration_format), workout.durationInSeconds),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.how_to_do_exercise),
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                WorkoutItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
            if (expanded) {
                Text(
                    text = stringResource(id = workout.instructionRes),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun WorkoutItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = MaterialTheme.colors.onSurface,
            contentDescription = stringResource(id = R.string.expand_button_content_description)
        )
    }
}

@Composable
fun ThirtyDaysOfCoreApp() {
    Scaffold(
        topBar = {
            AppBar(modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
            )
        }
    ) {
        WorkoutListView(workouts = WorkoutRepository.workouts)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ThirtyDaysOfCoreWorkoutsTheme {
        ThirtyDaysOfCoreApp()
    }
}