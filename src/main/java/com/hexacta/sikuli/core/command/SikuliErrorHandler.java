package com.hexacta.sikuli.core.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.Utils;

public class SikuliErrorHandler<PFRML> {
	private static String HTML_TEMPLATE;
	private static String ERROR_DESCRIPTION_TEMPLATE;

	static {
		HTML_TEMPLATE = "<html><body>\n";
		HTML_TEMPLATE += "<div><h1>Command: %s</h1>\n";
		HTML_TEMPLATE += "%s\n";
		HTML_TEMPLATE += "</body></html>";

		ERROR_DESCRIPTION_TEMPLATE = "<div>The image</div>";
		ERROR_DESCRIPTION_TEMPLATE += "<img src='%s'/>";
		ERROR_DESCRIPTION_TEMPLATE += "<div>was not found in</div>";
		ERROR_DESCRIPTION_TEMPLATE += "<img src='%s'/>";
		ERROR_DESCRIPTION_TEMPLATE += "<div><h2>Stack Trace</h2></div>";
		ERROR_DESCRIPTION_TEMPLATE += "<div>%s</div>";

	}

	public void writeError(long id, Region region, PFRML targetImage) {
		String imageNotFoundPath = writeImageNotFound(id, targetImage);
		String regionImagePath = saveCapture(id, region);

		String className = this.getClass().getSimpleName();
		String fileName = id + "_" + className + ".html";
		File file = new File(Utils.RESULT_FOLDER, fileName);

		String error = String.format(HTML_TEMPLATE, className, getContextHtml(imageNotFoundPath, regionImagePath));

		try (FileWriter w = new FileWriter(file)) {
			w.write(error);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String writeImageNotFound(long id, PFRML imageNotFound) {
		if (imageNotFound instanceof String) {
			return (String) imageNotFound;
		}

		if (imageNotFound instanceof Region) {
			return saveCapture(id, (Region) imageNotFound);
		}
		return "";
	}

	private String saveCapture(long id, Region region) {
		return region.saveScreenCapture(Utils.RESULT_FOLDER, id + "_region" + ".png");
	}

	private String getContextHtml(String notFoundImageFileName, String regionImageFileName) {
		return String.format(ERROR_DESCRIPTION_TEMPLATE, notFoundImageFileName, regionImageFileName,
				ExceptionUtils.getStackTrace(new RuntimeException()));
	}

}
