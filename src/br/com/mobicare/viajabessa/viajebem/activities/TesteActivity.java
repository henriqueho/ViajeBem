package br.com.mobicare.viajabessa.viajebem.activities;

import java.util.ArrayList;

import android.content.Intent;
import br.com.hhw.startapp.activities.StartAppActivity;
import br.com.hhw.startapp.activities.helpers.SharedPreferencesHelper;
import br.com.how.hhwslidemenu.HHWMenuItem;
import br.com.how.hhwslidemenu.HHWSlideMenu;
import br.com.mobicare.viajabessa.viajebem.R;
import br.com.mobicare.viajabessa.viajebem.fragments.TesteFragment;

public class TesteActivity extends StartAppActivity {

	protected void createMenu() {

		if (menuItems == null) {

			TesteFragment fragment = new TesteFragment();
			
			ArrayList<HHWMenuItem> menuItems = new ArrayList<HHWMenuItem>();
			
			menuItems.add(new HHWMenuItem("Início", fragment.newInstance("inicio"), getResources().getDrawable(R.drawable.ic_launcher)));
			menuItems.add(new HHWMenuItem("Meus pontos", fragment
					.newInstance("Meus pontos")));
			menuItems.add(new HHWMenuItem("Minhas rotas", fragment
					.newInstance("Minhas rotas")));
			menuItems.add(new HHWMenuItem("Saiba mais", fragment
					.newInstance("Saiba mais")));
			menuItems.add(new HHWMenuItem("Como usar?", MainActivity.class));
			menuItems.add(new HHWMenuItem("Configurações", fragment
					.newInstance("Configurações")));
			menuItems.add(new HHWMenuItem("Sobre", fragment.newInstance("Sobre")));

			slideMenu = new HHWSlideMenu(this, menuItems);
		} else {
			slideMenu = new HHWSlideMenu(this, menuItems);
		}
	}
	
	protected void showTutorial() {
	if (SharedPreferencesHelper.getInstance(this).hasToShowTutorial()) {
		SharedPreferencesHelper.getInstance(this).setHasToShowTutorial(
				false);
		startActivity(new Intent(this, MainActivity.class));
	}
}
}
