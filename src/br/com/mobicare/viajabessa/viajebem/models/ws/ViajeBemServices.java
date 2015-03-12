package br.com.mobicare.viajabessa.viajebem.models.ws;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.mobicare.viajabessa.viajebem.models.Pacote;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ViajeBemServices {

	public static void getPacotes(final PacoteHandler pacoteHandler) {
		final ArrayList<Pacote> pacotes = new ArrayList<Pacote>();
		String url = "http://private-6e81e-projetoviajabessa.apiary-mock.com/pacotes";
		ServiceRestClient.get(url, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray pacotesJsonArray) {
				for (int i = 0; i < pacotesJsonArray.length(); i++) {
					try {
						JSONObject item = (JSONObject) pacotesJsonArray
								.getJSONObject(i);
						JsonParser parser = new JsonParser();
						JsonElement jsonElement = parser.parse(item.toString());
						Gson gson = new Gson();
						Pacote pacote = gson
								.fromJson(jsonElement, Pacote.class);
						pacotes.add(pacote);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				pacoteHandler.setPacotes(pacotes);

			}
			@Override
			public void onFailure(int statusCode,
					org.apache.http.Header[] headers,
					java.lang.String responseString,
					java.lang.Throwable throwable) {
				pacoteHandler.setErro(throwable);
			}
			@Override
			public void onFailure(int statusCode,
					org.apache.http.Header[] headers,
					java.lang.Throwable throwable,
					org.json.JSONObject errorResponse) {
				pacoteHandler.setErro(throwable);
			}
			@Override
			public void onFailure(int statusCode,
					org.apache.http.Header[] headers,
					java.lang.Throwable throwable,
					org.json.JSONArray errorResponse) {
				pacoteHandler.setErro(throwable);
			}

		});
	}

	public static void getPacote(int idPacote, final PacoteHandler pacoteHandler) {
		String url = "http://private-6e81e-projetoviajabessa.apiary-mock.com/pacotes/%s";
		url = String.format(url, idPacote);
		ServiceRestClient.get(url, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject pacotesJson) {
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = parser.parse(pacotesJson.toString());
				Gson gson = new Gson();
				Pacote pacote = gson.fromJson(jsonElement, Pacote.class);
				pacoteHandler.setPacote(pacote);
			}
			@Override
			public void onFailure(int statusCode,
					org.apache.http.Header[] headers,
					java.lang.String responseString,
					java.lang.Throwable throwable) {
				pacoteHandler.setErro(throwable);
			}
			@Override
			public void onFailure(int statusCode,
					org.apache.http.Header[] headers,
					java.lang.Throwable throwable,
					org.json.JSONObject errorResponse) {
				pacoteHandler.setErro(throwable);
			}
			@Override
			public void onFailure(int statusCode,
					org.apache.http.Header[] headers,
					java.lang.Throwable throwable,
					org.json.JSONArray errorResponse) {
				pacoteHandler.setErro(throwable);
			}
		});
	}
}
