package com.kai.githubmanager.ui.screens.userlist

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kai.githubmanager.R
import com.kai.githubmanager.ui.screens.LoadingItem
import com.kai.githubmanager.ui.screens.UserCard


@Composable
fun UserListScreen(
    viewModel: UserListViewModel,
    onUserClick: (String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    UserListContent(uiState, onUserClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListContent(
    uiState: UserListUIState,
    onUserClick: (String) -> Unit
) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.user_list_title), fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        when (uiState) {
            is UserListUIState.Loading -> {
                CircularProgressIndicator()
            }

            is UserListUIState.Success -> {
                val pagingData = uiState.data
                val lazyPagingItems = pagingData.collectAsLazyPagingItems()

                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(lazyPagingItems.itemCount) { index ->
                        lazyPagingItems[index]?.let { user ->
                            UserCard(modifier = Modifier.padding(horizontal = 16.dp), user = user, onClick = { onUserClick(user.userName) })
                        }
                    }
                    lazyPagingItems.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item { LoadingItem() }
                            }

                            loadState.append is LoadState.Loading -> {
                                item { LoadingItem() }
                            }

                            loadState.refresh is LoadState.Error -> {
                                val errorMessage = (loadState.refresh as LoadState.Error).error.message
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

            is UserListUIState.Error -> {
                val errorMessage = uiState.message
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}