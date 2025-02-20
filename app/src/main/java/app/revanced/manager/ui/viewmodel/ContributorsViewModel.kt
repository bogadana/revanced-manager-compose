package app.revanced.manager.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.revanced.manager.domain.repository.ReVancedRepositoryImpl
import app.revanced.manager.network.dto.ReVancedContributor
import app.revanced.manager.network.utils.getOrNull
import app.revanced.manager.util.*
import kotlinx.coroutines.launch

class ContributorsViewModel(
    private val app: Application, private val reVancedAPI: ReVancedRepositoryImpl
) : ViewModel() {
    val patcherContributorsList = mutableStateListOf<ReVancedContributor>()
    val patchesContributorsList = mutableStateListOf<ReVancedContributor>()
    val cliContributorsList = mutableStateListOf<ReVancedContributor>()
    val managerContributorsList = mutableStateListOf<ReVancedContributor>()
    val integrationsContributorsList = mutableStateListOf<ReVancedContributor>()

    private fun loadContributors() {
        viewModelScope.launch {
            val contributors = reVancedAPI.getContributors().getOrNull() ?: return@launch
            contributors.repositories.forEach { repo ->
                when (repo.name) {
                    ghCli -> {
                        repo.contributors.sortedByDescending {
                            it.username
                        }
                        cliContributorsList.addAll(repo.contributors)
                    }
                    ghPatcher -> {
                        repo.contributors.sortedByDescending {
                            it.username
                        }
                        patcherContributorsList.addAll(repo.contributors)
                    }
                    ghPatches -> {
                        repo.contributors.sortedByDescending {
                            it.username
                        }
                        patchesContributorsList.addAll(repo.contributors)
                    }
                    ghIntegrations -> {
                        repo.contributors.sortedByDescending {
                            it.username
                        }
                        integrationsContributorsList.addAll(repo.contributors)
                    }
                    ghManager -> {
                        repo.contributors.sortedByDescending {
                            it.username
                        }
                        managerContributorsList.addAll(repo.contributors)
                    }
                }
            }
        }
    }

    fun openUserProfile(username: String) {
        app.openUrl("https://github.com/${username}")
    }

    init {
        viewModelScope.launch {
                loadContributors()
        }
    }
}