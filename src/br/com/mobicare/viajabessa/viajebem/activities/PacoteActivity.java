package br.com.mobicare.viajabessa.viajebem.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import br.com.mobicare.viajabessa.viajebem.R;
import br.com.mobicare.viajabessa.viajebem.helpers.ErrorTryAgainHelper;
import br.com.mobicare.viajabessa.viajebem.helpers.ErrorTryAgainHelper.OnClickToTryAgain;
import br.com.mobicare.viajabessa.viajebem.models.Pacote;
import br.com.mobicare.viajabessa.viajebem.models.ws.PacoteHandler;
import br.com.mobicare.viajabessa.viajebem.models.ws.ViajeBemServices;

import com.nostra13.universalimageloader.core.ImageLoader;

public class PacoteActivity extends ActionBarActivity implements
		OnClickToTryAgain {

	public static final String EXTRA_PACOTE = PacoteActivity.class.getName()
			+ "#extra_pacote";

	public static final String BUNDLE_PACOTE = PacoteActivity.class.getName()
			+ "#bundle_pacote";

	private ProgressBar spinner;

	private Pacote pacote;
	private ImageView imageView;
	private TextView nomePacote;
	private TextView valorPacote;
	private TextView descricaoPacote;
	private ViewStub internetFailureViewStub;
	private ScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pacote);
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle(getString(R.string.app_name));

		Bundle extra = getIntent().getBundleExtra(EXTRA_PACOTE);
		pacote = (Pacote) extra.getSerializable(BUNDLE_PACOTE);

		getViews();

		Bitmap bmp = BitmapFactory.decodeByteArray(pacote.byteArrayImage, 0,
				pacote.byteArrayImage.length);
		setImageViewSize(imageView);
		imageView.setImageBitmap(bmp);

		setStubViewsError();
		loadPacote();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		this.onBackPressed();
		return true;
	}

	private void getViews() {
		spinner = (ProgressBar) findViewById(R.id.pacote_progressBar);
		imageView = (ImageView) findViewById(R.id.pacote_imageview);
		nomePacote = (TextView) findViewById(R.id.pacote_textview_nome);
		valorPacote = (TextView) findViewById(R.id.pacote_textview_valor);
		descricaoPacote = (TextView) findViewById(R.id.pacote_textview_descricao);
		scrollView = (ScrollView) findViewById(R.id.pacote_scrollView);
	}

	private void setImageViewSize(ImageView imageView) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;
		int height = (width * 360) / 640;
		imageView.getLayoutParams().height = height;
	}

	private void setStubViewsError() {
		internetFailureViewStub = (ViewStub) findViewById(R.id.pacote_internet_failure);
		internetFailureViewStub.setLayoutResource(R.layout.view_system_error);
		((ErrorTryAgainHelper) internetFailureViewStub.inflate())
				.setOnClickAndMessage(this, getString(R.string.error_pacote));
	}

	private void loadPacote() {
		spinner.setVisibility(View.VISIBLE);
		ViajeBemServices.getPacote(pacote.idPacote, new PacoteHandler() {
			@Override
			public void setPacote(Pacote pacoteFromHandler) {
				pacote = pacoteFromHandler;
				showPacote();
			}

			@Override
			public void setErro(Throwable e) {
				showError();
			}
		});
	}

	private void showPacote() {
		spinner.setVisibility(View.GONE);
		internetFailureViewStub.setVisibility(View.GONE);
		scrollView.setVisibility(View.VISIBLE);
		nomePacote.setText(pacote.nome);
		valorPacote.setText(pacote.valor);
		descricaoPacote.setText(pacote.descricao);
		ImageLoader.getInstance().displayImage(pacote.urlImagem, imageView);
	}

	private void showError() {
		spinner.setVisibility(View.GONE);
		scrollView.setVisibility(View.GONE);
		internetFailureViewStub.setVisibility(View.VISIBLE);
	}

	@Override
	public void tryAgain() {
		loadPacote();
	}

}
