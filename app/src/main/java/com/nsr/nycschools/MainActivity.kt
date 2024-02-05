package com.nsr.nycschools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nsr.nycschools.ui.theme.NYCSchoolsTheme
import com.nsr.nycschools.viewmodel.NycSchoolsViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.nsr.nycschools.network.ApiHelper
import com.nsr.nycschools.network.NYCAPIClient
import com.nsr.nycschools.viewmodel.NycSchoolsUiModel
import com.nsr.nycschools.viewmodel.NycViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: NycSchoolsViewModel by viewModels { NycViewModelFactory(ApiHelper(NYCAPIClient.apiService)) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProvider(this)[NycSchoolsViewModel::class.java]
        setContent {
            NYCSchoolsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowSchoolsList(viewModel = viewModel)
                }
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowSchoolsList(
    modifier: Modifier = Modifier,
    viewModel: NycSchoolsViewModel
) {
    val schoolItems by viewModel.nycSchoolsList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.nyc_schools))
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier
            .padding(it)) {
            items(items = schoolItems, itemContent = { schoolItem ->
                SchoolItem(schoolItem)
            }
            )
        }
    }
}


@Composable
fun SchoolItem(item: NycSchoolsUiModel, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(Modifier
        .padding(all = 16.dp)
        .fillMaxWidth()
        .clickable {
            expanded = !expanded
        }
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {

        Text(
            text = item.dbn.orEmpty(),
            modifier = modifier,

            )
        Text(
            text = item.schoolName.orEmpty(),
            modifier = modifier
        )
        if(expanded) {
            Text(
                text = item.overView.orEmpty(),
                modifier = modifier
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NYCSchoolsPreview() {
    NYCSchoolsTheme {
        SchoolItem(NycSchoolsUiModel(
            dbn = "02M260",
            schoolName = "Clinton School Writers & Artists",
            overView = "Students who are prepared for college must have " +
                    "an education that encourages them to take risks as they produce and perform. "
        )
        )
    }
}