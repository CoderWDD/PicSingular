package com.example.picsingular.ui.home.community

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.picsingular.ui.home.community.collect.CollectPage
import com.example.picsingular.ui.home.community.recommend.RecommendPage
import com.example.picsingular.ui.home.community.saved.SavedPage
import com.example.picsingular.ui.home.community.shared.SharedPage
import com.example.picsingular.ui.home.community.subscription.SubscriptionPage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(): ViewModel() {
}