package net.rickcee.swingxs.model.meta;


public interface IExportable {
	public static final Integer EXPORT_ALL = 1;
	public static final Integer EXPORT_FILTERED = 2;
	public static final Integer EXPORT_SELECTED = 3;

	public void exportAll();

	public void exportFiltered();

	public void exportSelected();
}
