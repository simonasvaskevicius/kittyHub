package com.vaskevicius.android.kittyhub.ui.explore

import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaskevicius.android.kittyhub.R
import com.vaskevicius.android.kittyhub.data.models.Image
import com.vaskevicius.android.kittyhub.data.prefs.Preferences
import com.vaskevicius.android.kittyhub.framework.base.mvp.BaseActivity
import com.vaskevicius.android.kittyhub.framework.di.component.ActivityComponent
import com.vaskevicius.android.kittyhub.ui.explore.preview.PreviewFragment
import kotlinx.android.synthetic.main.activity_explore.*
import javax.inject.Inject


class ExploreActivity : BaseActivity(), ExploreView {

    @Inject
    lateinit var presenter: ExploreMVPPresenter<ExploreView>

    @Inject
    lateinit var prefs: Preferences

    private var favoriteAdapter: FavoriteAdapter? = null
    private var popularAdapter: PopularAdapter? = null
    private var popularImages: MutableList<Image> = mutableListOf()
    private var favoriteImages: MutableList<Image> = mutableListOf()

    private var page = 1
    private var totalPages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)
        presenter.onAttach(this)
        favoriteImages = prefs.getFavorites().toMutableList()
        setupComponents()
        presenter.onViewPrepared()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        swipeRefresh.visibility = View.VISIBLE
    }

    /*
    * UI Components setup
    */

    private fun setupComponents() {
        setupFavoriteRecyclerView()
        setupPopularRecyclerView()
        setupSwipeRefresh()
        setupPageButtons()
        setupBackToTop()
    }

    private fun setupPageButtons() {
        pageNext.setOnClickListener { presenter.loadPopularImages(page + 1) }
        pageBack.setOnClickListener { presenter.loadPopularImages(page - 1) }
    }

    private fun setupBackToTop() {
        scrollView.setOnScrollChangeListener(
            View.OnScrollChangeListener { v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
                fab.visibility = if (scrollY > 1000) View.VISIBLE else View.GONE
            })
        fab.setOnClickListener { v -> scrollView.smoothScrollTo(0, 0) }
    }

    private fun setupFavoriteRecyclerView() {
        favoriteAdapter = FavoriteAdapter()
        favoriteRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        favoriteRecyclerView.adapter = favoriteAdapter

        favoriteAdapter?.setImageList(prefs.getFavorites())
        favoriteAdapter!!.onImageDoubleClick = { image: Image ->
            Handler().postDelayed({
                addFavoriteImage(image)
            }, 1100)
        }

        favoriteAdapter!!.onImageSingleClick = { image ->
            openPreviewFragment(image)
        }

        if (favoriteImages.isNotEmpty()) {
            tutorialText.visibility = View.GONE
            favoriteRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun setupPopularRecyclerView() {
        popularAdapter = PopularAdapter(popularImages)
        popularAdapter!!.onImageDoubleClick = { image ->
            addFavoriteImage(image)
        }
        popularAdapter!!.onImageSingleClick = { image ->
            openPreviewFragment(image)
        }
        popularRecyclerView.layoutManager = GridLayoutManager(this, 2)
        popularRecyclerView.adapter = popularAdapter
    }

    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            presenter.loadPopularImages(page)
        }
    }

    /*
    * Data setting
    */

    private fun setPopularImages(images: List<Image>) {
        popularImages.clear()
        popularImages.addAll(images)
        popularRecyclerView.adapter?.notifyDataSetChanged()
        popular.visibility = View.VISIBLE
        popularRecyclerView.visibility = View.VISIBLE
    }

    private fun addFavoriteImage(favoriteImage: Image) {

        if (prefs.getFavorites().firstOrNull { it.id == favoriteImage.id } == null) {
            //if there is no matching id's in list
            tutorialText.visibility = View.GONE
            favoriteRecyclerView.visibility = View.VISIBLE

            favoriteImages.add(favoriteImage)
            favoriteRecyclerView.smoothScrollToPosition(favoriteImages.size)

        } else {
            //removes duplicates from list
            favoriteImages = favoriteImages.filter { it.id != favoriteImage.id }.toMutableList()

            if (favoriteImages.isEmpty()) {
                favoriteRecyclerView.visibility = View.GONE
                tutorialText.visibility = View.VISIBLE
            }
        }
        prefs.setFavorites(favoriteImages)
        favoriteAdapter?.setImageList(favoriteImages)
    }

    override fun refreshItemList(images: List<Image>, page: Int, totalPages: Int) {
        swipeRefresh.isRefreshing = false
        this.totalPages = totalPages
        if (images.isNotEmpty()) {
            setPopularImages(images)
            this.page = page
            checkPage()
        }
    }

    /*
    *   Loading
    */

    override fun showLoading() {
        popularLayout.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        popularLayout.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    /*
    *   Utils
    */

    private fun checkPage() {
        pageBack.isEnabled = page != 1
        pageNext.isEnabled = totalPages != page

        if (!pageBack.isEnabled) pageBack.setColorFilter(
            getColor(R.color.gray),
            PorterDuff.Mode.SRC_IN
        )
        else pageBack.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN)

        if (!pageNext.isEnabled) pageNext.setColorFilter(
            getColor(R.color.gray),
            PorterDuff.Mode.SRC_IN
        )
        else pageNext.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN)

        pageText.text = page.toString()
    }

    private fun openPreviewFragment(image: Image) {
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit)
            .replace(R.id.frameContainer, PreviewFragment.newInstance(image)).addToBackStack(null)
            .commit()
        swipeRefresh.visibility = View.GONE
    }

    /*
    *   Dependency injection
    */

    override fun injectComponent(component: ActivityComponent) {
        component.inject(this)
    }
}