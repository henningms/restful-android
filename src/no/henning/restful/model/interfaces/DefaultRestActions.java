package no.henning.restful.model.interfaces;

import no.henning.restful.callback.*;
import no.henning.restful.model.Model;

public interface DefaultRestActions
{
	/**
	 * GET methods
	 */
	public void get();
	public void get(final Callback<? extends Model> callback);
	
	/**
	 * POST methods
	 */
	public void save();
	
	/**
	 * PUT methods
	 */
	public void update();
	
	/**
	 * DELETE methods
	 */
	public void delete();
}
