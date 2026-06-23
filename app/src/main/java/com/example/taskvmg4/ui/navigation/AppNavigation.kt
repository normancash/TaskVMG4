package com.example.taskvmg4.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.taskvmg2.ui.screen.TaskDetailScreen
import com.example.taskvmg2.ui.screen.TaskListScreen
import com.example.taskvmg4.ui.repository.ServiceLocator
import com.example.taskvmg4.ui.viewmodel.TaskDetailViewModel
import com.example.taskvmg4.ui.viewmodel.TaskDetailViewModelFactory
import com.example.taskvmg4.ui.viewmodel.TaskListViewModel
import com.example.taskvmg4.ui.viewmodel.TaskListViewModelFactory

@Composable
fun AppNavigation(modifier: Modifier)
{
    val navController = rememberNavController()

    NavHost(navController = navController
        , startDestination = TaskList
    )
    {
        composable<TaskList>
        {
            val listViewModel : TaskListViewModel =
                viewModel<TaskListViewModel>(
                    factory = TaskListViewModelFactory(
                        ServiceLocator.taskRepository
                    )
                )
            TaskListScreen(navController = navController,listViewModel)
        }
        composable<TaskDetail>{ backStackEntry ->
            val detailviewModel : TaskDetailViewModel =
                viewModel<TaskDetailViewModel>(
                    factory = TaskDetailViewModelFactory(
                        ServiceLocator.taskRepository
                    )
                )
            val route = backStackEntry.toRoute<TaskDetail>()
            TaskDetailScreen(navController = navController,
                viewModel = detailviewModel,
                taskId = route.taskId)
        }

    }
}

