package br.com.mobicare.viajabessa.viajebem.adapters;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.mobicare.viajabessa.viajebem.R;
import br.com.mobicare.viajabessa.viajebem.activities.PacoteActivity;
import br.com.mobicare.viajabessa.viajebem.models.Pacote;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class PacotesAdapter extends ArrayAdapter<Pacote> {

	private List<Pacote> pacotes;
	private Context context;

	public PacotesAdapter(Context context, List<Pacote> objects) {
		super(context, 0, objects);
		this.pacotes = objects;
		this.context = context;
	}

	@Override
	public int getCount() {
		return pacotes.size();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final CompleteListViewHolder viewHolder;

		Pacote currentPacote = pacotes.get(position);

		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.adapter_pacote, null);
			viewHolder = new CompleteListViewHolder(v);
			v.setTag(viewHolder);

			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					CompleteListViewHolder viewCompleteListViewHolder = (CompleteListViewHolder) view
							.getTag();
					Pacote pacote = new Pacote();
					pacote.byteArrayImage = viewCompleteListViewHolder.image;
					pacote.idPacote = viewCompleteListViewHolder.idPacote;
					Intent intent = new Intent(context, PacoteActivity.class);
					Bundle extra = new Bundle();
					extra.putSerializable(PacoteActivity.BUNDLE_PACOTE, pacote);
					intent.putExtra(PacoteActivity.EXTRA_PACOTE, extra);
					context.startActivity(intent);
				}
			});
		} else {
			viewHolder = (CompleteListViewHolder) v.getTag();
		}

		viewHolder.titulo.setText(currentPacote.titulo);
		viewHolder.preco.setText(currentPacote.pagamento);
		viewHolder.idPacote = currentPacote.idPacote;
		ImageLoader.getInstance().loadImage(currentPacote.urlImagem,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						viewHolder.imageview.setImageBitmap(loadedImage);
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						loadedImage.compress(Bitmap.CompressFormat.PNG, 100,
								stream);
						viewHolder.image = stream.toByteArray();
					}
				});
		return v;
	}

	class CompleteListViewHolder {
		public TextView titulo;
		public TextView preco;
		public ImageView imageview;
		public int idPacote;
		public byte[] image;

		public CompleteListViewHolder(View base) {
			titulo = (TextView) base
					.findViewById(R.id.adapter_pacote_titulo_textview);
			preco = (TextView) base
					.findViewById(R.id.adapter_pacote_preco_textview);
			imageview = (ImageView) base
					.findViewById(R.id.adapter_pacote_imageview);
		}
	}
}
