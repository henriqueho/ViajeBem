package br.com.mobicare.viajabessa.viajebem.activities;

import java.util.ArrayList;

import br.com.how.hhwslidemenu.HHWMenuActivity;
import br.com.how.hhwslidemenu.HHWMenuItem;
import br.com.mobicare.viajabessa.viajebem.R;
import br.com.mobicare.viajabessa.viajebem.fragments.TesteFragment;

public class TesteActivity extends HHWMenuActivity {

	public TesteActivity() {
		super();
		
		TesteFragment fragment = new TesteFragment();
		
		ArrayList<HHWMenuItem> menuItems = new ArrayList<HHWMenuItem>();
		menuItems.add(new HHWMenuItem("Meus pontos", fragment
				.newInstance("Meus pontos")));
		menuItems.add(new HHWMenuItem("Minhas rotas", fragment
				.newInstance("Minhas rotas")));
		new TesteActivity(menuItems);
	}

	public TesteActivity(ArrayList<HHWMenuItem> menuItems) {
		super(menuItems);

	}

}
