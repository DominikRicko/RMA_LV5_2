package hr.dominikricko.rma_lv5_2.di

import hr.dominikricko.rma_lv5_2.ui.viewmodel.ActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModel = module {
    viewModel { ActivityViewModel() }
}