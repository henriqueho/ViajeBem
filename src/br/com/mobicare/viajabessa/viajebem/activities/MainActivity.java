package br.com.mobicare.viajabessa.viajebem.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.mobicare.viajabessa.viajebem.R;
import br.com.mobicare.viajabessa.viajebem.adapters.PacotesAdapter;
import br.com.mobicare.viajabessa.viajebem.helpers.ErrorTryAgainHelper;
import br.com.mobicare.viajabessa.viajebem.helpers.ErrorTryAgainHelper.OnClickToTryAgain;
import br.com.mobicare.viajabessa.viajebem.models.Pacote;
import br.com.mobicare.viajabessa.viajebem.models.ws.PacoteHandler;
import br.com.mobicare.viajabessa.viajebem.models.ws.ServiceRestClient;
import br.com.mobicare.viajabessa.viajebem.models.ws.ViajeBemServices;

public class MainActivity extends ActionBarActivity implements
		OnRefreshListener, OnClickToTryAgain {

	private ListView listView;
	private ArrayList<Pacote> pacotes = new ArrayList<Pacote>();
	private SwipeRefreshLayout swipeLayout;

	private ViewStub internetFailureViewStub;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setSwipeRefresh();
		setStubViewsError();
		listView = (ListView) findViewById(R.id.pacotes_listview);

		loadPacotes();
	}

	private void setSwipeRefresh() {
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener((OnRefreshListener) this);
//		swipeLayout.setColorScheme(R.color.holo_blue_light,
//				R.color.holo_green_light, R.color.holo_orange_light,
//				R.color.holo_red_light);
	}

	private void setStubViewsError() {
		internetFailureViewStub = (ViewStub) findViewById(R.id.pacotes_internet_failure);
		internetFailureViewStub.setLayoutResource(R.layout.view_system_error);
		((ErrorTryAgainHelper) internetFailureViewStub.inflate())
				.setOnClickAndMessage(this, getString(R.string.error_pacotes));
	}

	private void loadPacotes() {
		swipeLayout.setEnabled(false);
		swipeLayout.setRefreshing(true);
		swipeLayout.setVisibility(View.VISIBLE);
		ViajeBemServices.getPacotes(new PacoteHandler() {
			@Override
			public void setPacotes(ArrayList<Pacote> pacotesHandler) {
				pacotes = pacotesHandler;
				showPacotes();
				swipeLayout.setRefreshing(false);
				swipeLayout.setEnabled(true);

			}
			
			@Override
			public void setErro(Throwable e) { 
				showError();
			}
		});
	}
	
	private void showError() {
		swipeLayout.setRefreshing(false);
		swipeLayout.setVisibility(View.GONE);
		internetFailureViewStub.setVisibility(View.VISIBLE);
	}

	private void showPacotes() {
		ArrayAdapter<Pacote> adapter = new PacotesAdapter(this, pacotes);
		listView.setAdapter(adapter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ServiceRestClient.cancelRequests(this);
	}

	@Override
	public void onRefresh() {
		loadPacotes();
	}

	@Override
	public void tryAgain() {
		loadPacotes();
	}
}
