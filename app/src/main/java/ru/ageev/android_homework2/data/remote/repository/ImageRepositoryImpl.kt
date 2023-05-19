package ru.ageev.android_homework2.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ageev.android_homework2.data.mappers.ImagesMapper
import ru.ageev.android_homework2.data.model.Image
import ru.ageev.android_homework2.data.paging.ImagePagingSource
import ru.ageev.android_homework2.data.remote.NanopostApiService
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val apiService: NanopostApiService,
    private val imagesMapper: ImagesMapper,
) : ImageRepository {
    override suspend fun getAllImages(profileId: String): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = { ImagePagingSource(apiService, profileId) }
        ).flow.map { pagingData ->
            pagingData.map { apiImage ->
                imagesMapper.toImages(apiImage)
            }
        }
    }

    override suspend fun getImage(imageId: String): Image {
        return imagesMapper.toImages(apiService.getImage(imageId))
    }

    override suspend fun deleteImage(imageId: String) {
        apiService.deleteImage(imageId)
    }
}