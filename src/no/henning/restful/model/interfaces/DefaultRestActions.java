package no.henning.restful.model.interfaces;

import no.henning.restful.callback.*;
import no.henning.restful.model.Model;

public interface DefaultRestActions
{
	/**
	 * GET methods
	 */
	public void get();
	public void get(final Callback<Model> callback);
	
	/**
	 * POST methods
	 */
	public void save();
	public void save(final Callback<Model> callback);
	
	/**
	 * PUT methods
	 */
	public void update();
	public void update(final Callback<Model> callback);
	
	/**
	 * DELETE methods
	 */
	public void delete();
	public void delete(final Callback<Model> callback);
}
