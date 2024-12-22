package com.kai.githubmanager.ui.screens.userdetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kai.githubmanager.R
import com.kai.githubmanager.domain.models.User
import com.kai.githubmanager.ui.screens.LoadingItem
import com.kai.githubmanager.ui.screens.UserCard

@Composable
fun UserDetailScreen(
    userName: String,
    onBackClicked: () -> Unit,
    viewModel: UserDetailViewModel = hiltViewModel()
) {

    val userDetailState by viewModel.userDetailState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserDetail(userName)
    }

    when (userDetailState) {
        is UserDetailUIState.Loading -> {
            LoadingItem(modifier = Modifier.fillMaxSize())
        }
        is UserDetailUIState.Success -> {
            UserDetailContent((userDetailState as UserDetailUIState.Success).data, onBackClicked)
        }
        is UserDetailUIState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Toast.makeText(LocalContext.current, (userDetailState as UserDetailUIState.Error).message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailContent(user: User, onBackClicked: () -> Unit) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.user_detail_title), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
        ) {

            UserCard(
                user = user,
                showLocation = true,
            )

            Spacer(modifier = Modifier.padding(bottom = 24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .background(color = Color.White, shape = CircleShape)
                            .padding(16.dp)
                    )
                    Text(
                        text = "${user.followers}+",
                        style = typography.bodySmall,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.user_detail_followers),
                        style = typography.bodyMedium,
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .background(color = Color.White, shape = CircleShape)
                            .padding(16.dp)
                    )
                    Text(text = "${user.following}+", style = typography.bodySmall)
                    Text(
                        text = stringResource(id = R.string.user_detail_following),
                        style = typography.bodyMedium,
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.user_detail_blog),
                style = typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = user.htmlUrl,
                style = typography.bodyMedium,
            )
        }
    }

}