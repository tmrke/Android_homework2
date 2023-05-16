package ru.ageev.android_homework2.ui.profile_screen.profile

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.cachedIn
import androidx.recyclerview.widget.ConcatAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.ageev.android_homework2.R
import ru.ageev.android_homework2.databinding.FragmentProfileBinding
import ru.ageev.android_homework2.ui.insets.Inset
import ru.ageev.android_homework2.ui.post_screen.PostViewModel
import ru.ageev.android_homework2.ui.profile_screen.posts.PostsAdapter
import ru.ageev.android_homework2.ui.profile_screen.posts.PostsViewModel

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val postsViewModel by viewModels<PostsViewModel>()
    private val postViewModel by viewModels<PostViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        Inset.setInsets(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottomMenuFeed -> {
                    navController.navigate(R.id.feedFragment)
                    true
                }

                else -> {
                    true
                }
            }

        }


        profileViewModel.getProfile(
            profileViewModel.getUsername()
            //"evo"
        )

        profileViewModel.profileLiveData.observe(viewLifecycleOwner) { profile ->
            postsViewModel.loadPosts(profile.id)

            val profileAdapter = ProfileAdapter(profile)
            val postsAdapter = PostsAdapter()

            profileAdapter.onClick = { profileId ->
                profileViewModel.subscribe(profileId)
            }

            postsAdapter.onClick = { postId ->
                postViewModel.getPost(postId)

                navController.navigate(
                    ProfileFragmentDirections.actionProfileFragmentToPostFragment(postId)
                )
            }

            val concatAdapter = ConcatAdapter(profileAdapter, postsAdapter)

            postsViewModel.postsLiveData.cachedIn(viewLifecycleOwner.lifecycleScope)
                .observe(viewLifecycleOwner) { posts ->
                    postsAdapter.submitData(viewLifecycleOwner.lifecycle, posts)
                }

            binding.recyclerView.adapter = concatAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            postsViewModel.loadPosts(profileViewModel.getUsername())
            binding.swipeRefreshLayout.isRefreshing = false
        }

//        val collageAdapter = CollageAdapter(listOf(CollageData()))
//
//        collageAdapter.onClick = {
//            navController.navigate(R.id.imagesFragment)
//        }


        binding.bottomNavigationView.menu.findItem(R.id.bottomMenuProfile).isChecked = true

        binding.floatingActionButton.setOnClickListener {
            navController.navigate(R.id.createPostFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().moveTaskToBack(true)
        }

        binding.toolBar.menu.findItem(R.id.actionExit).setOnMenuItemClickListener {
            profileViewModel.deleteToken()
            navController.navigate(R.id.authFragment)

            true
        }
    }
}