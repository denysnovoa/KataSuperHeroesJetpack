package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.domain.usecase.SaveSuperHero
import com.karumi.jetpack.superheroes.ui.presenter.EditSuperHeroPresenter
import com.karumi.jetpack.superheroes.ui.utils.setImageBackground
import kotlinx.android.synthetic.main.edit_super_hero_activity.*

class EditSuperHeroActivity : BaseActivity(), EditSuperHeroPresenter.View {
    companion object {
        private const val SUPER_HERO_ID_KEY = "super_hero_id_key"

        fun open(activity: Activity, superHeroId: String) {
            val intent = Intent(activity, EditSuperHeroActivity::class.java)
            intent.putExtra(SUPER_HERO_ID_KEY, superHeroId)
            activity.startActivity(intent)
        }
    }

    private val presenter: EditSuperHeroPresenter by injector.instance()
    override val layoutId = R.layout.edit_super_hero_activity
    override val toolbarView: Toolbar
        get() = toolbar
    private val superHeroId: String
        get() = intent?.extras?.getString(SUPER_HERO_ID_KEY) ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bt_save_edition.setOnClickListener {
            presenter.onSaveSuperHeroSelected(
                name = et_super_hero_name.text?.toString() ?: "",
                description = et_super_hero_description.text?.toString() ?: "",
                isAvenger = cb_is_avenger.isChecked
            )
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun preparePresenter(intent: Intent?) {
        title = superHeroId
        presenter.preparePresenter(superHeroId)
    }

    override fun close() {
        finish()
    }

    override fun showLoading() {
        et_super_hero_name.isEnabled = false
        et_super_hero_description.isEnabled = false
        bt_save_edition.isEnabled = false
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        et_super_hero_name.isEnabled = true
        et_super_hero_description.isEnabled = true
        bt_save_edition.isEnabled = true
        progress_bar.visibility = View.GONE
    }

    override fun showSuperHero(superHero: SuperHero) {
        et_super_hero_name.setText(superHero.name)
        et_super_hero_description.setText(superHero.description)
        iv_super_hero_photo.setImageBackground(superHero.photo)
        cb_is_avenger.isChecked = superHero.isAvenger
    }

    override val activityModules = Kodein.Module(allowSilentOverride = true) {
        bind<EditSuperHeroPresenter>() with provider {
            EditSuperHeroPresenter(
                this@EditSuperHeroActivity,
                instance(),
                instance()
            )
        }
        bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
        bind<SaveSuperHero>() with provider { SaveSuperHero(instance()) }
    }
}