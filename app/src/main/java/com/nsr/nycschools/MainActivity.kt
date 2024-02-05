package com.nsr.nycschools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.lifecycle.ViewModelProvider
import com.nsr.nycschools.ui.theme.NYCSchoolsTheme
import com.nsr.nycschools.viewmodel.NycSchoolsViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Observer
import com.nsr.nycschools.network.ApiHelper
import com.nsr.nycschools.network.NYCAPIClient
import com.nsr.nycschools.network.NYCApiService
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

@Composable
private fun ShowSchoolsList(
    modifier: Modifier = Modifier,
    viewModel: NycSchoolsViewModel
) {
    val schoolItems by viewModel.nycSchoolsList.collectAsState()
    Text(
        text = "NYC Schools", // move to string resources
        modifier.padding(bottom = 32.dp),
        textAlign = TextAlign.Center,
    )
    schoolItems.let {
        LazyColumn {

            items(items = it, itemContent = { schoolItem ->
                SchoolItem(schoolItem)
            }
            )
        }
    }

}


@Composable
fun SchoolItem(item: NycSchoolsUiModel, modifier: Modifier = Modifier) {
    Column(Modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Text(
            text = item.dbn.orEmpty(),
            modifier = modifier
        )
        Text(
            text = item.schoolName.orEmpty(),
            modifier = modifier
        )
        Text(
            text = item.overView.orEmpty(),
            modifier = modifier
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NYCSchoolsTheme {
        SchoolItem(NycSchoolsUiModel(
            dbn = "sample DBN",
            schoolName = "",
            overView = ""
        )
        )
    }
}