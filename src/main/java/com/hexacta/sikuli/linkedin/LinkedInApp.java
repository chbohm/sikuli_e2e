package com.hexacta.sikuli.linkedin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Properties;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Mouse;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import com.hexacta.sikuli.core.ChromeApp;
import com.hexacta.sikuli.core.Utils;
import com.sun.jna.platform.DesktopWindow;

public class LinkedInApp extends ChromeApp {

	float pagerNextEnabledSimilarity = 0.98f;

	protected LinkedInApp(DesktopWindow w) {
		super(w);
		initFromProperties();
	}

	private void initFromProperties() {
		Properties p = new Properties();
		try {
			p.load(LinkedInApp.class.getClassLoader().getResourceAsStream("config.properties"));
			String similarityStr = (String) p.getOrDefault("pager.next.enabled.similarity", "0.98");
			pagerNextEnabledSimilarity = Double.valueOf(similarityStr).floatValue();
		} catch (IOException e) {
			throw new IllegalStateException(
					"The file /com/hexacta/sikuli/linkedin/config.properties was not found inside the package");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static LinkedInApp create2() {
		DesktopWindow w = ChromeApp.init();
		LinkedInApp app = new LinkedInApp(w);
		app.waitMillis(2000);
		return app;
	}

	public static LinkedInApp create() {
		DesktopWindow w = ChromeApp.init();
		LinkedInApp app = new LinkedInApp(w);
		app.gotoUrl("http://www.linkedin.com");
		app.wait("linkedin/start.button.png", 10.0);
		return app;
	}

//	public void logIn(String user, String pass) throws Exception {
//		if (exists("linkedin/me.dropdown.png")) {
//			click("linkedin/me.dropdown.png");
//			click("linkedin/signout.menu.png");
//			waitMillis(3000);
//		}
//		click("linkedin/email.signin.png");
//		paste(user);
//		click("linkedin/password.signin.png");
//		paste(pass);
//		click("linkedin/startSession.button.png");
//		waitMillis(3000);
//	}

	public Region getTextFieldRegion(String imageName) {
		Region lbl = find(imageName);
		return lbl.right(300).offset(20, 0);

	}

	public void gotoSearchPeople() {
		click("linkedin/miRed.button.png");

		wait("linkedin/contactos.menu.png", 15.0);
		click("linkedin/buscar.field.png");

		waitAndClick("linkedin/buscar.personas.button.png", 10.0);
		waitAndClick("linkedin/buscar.todosLosFiltros.png", 10.0);

		wait("linkedin/buscar.aplicar.button.png", 15.0);
	}

	public void search(SearchOptions options) {
		configureSearchOptions(options);
		click("linkedin/buscar.aplicar.button.png");
		wait("linkedin/search.result.title.png");
	}

	private void exportResults(String folderStr) throws IOException {
		File folder = new File(folderStr);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		int i = 1;
		File fileCsv = new File(folder, "page_" + i + ".csv");
		saveAllLinks(fileCsv);
		waitMillis(1000);
		ResultsProcessor.processFile(fileCsv);
		wheelUntilMatch(Mouse.WHEEL_DOWN, "linkedin/fin.resultado.png");
		while (hasMorePages()) {
			click("linkedin/pager.next.enabled.png");
			wait("linkedin/search.result.title.png");
			i++;
			fileCsv = new File(folder, "page_" + i + ".csv");
			saveAllLinks(fileCsv);
			waitMillis(1000);
			ResultsProcessor.processFile(fileCsv);
			wheelUntilMatch(Mouse.WHEEL_DOWN, "linkedin/fin.resultado.png");
		}
		ResultsProcessor.processResultsFolder(folder);

	}

	private boolean hasMorePages() {
		Region match = find(new Pattern("linkedin/pager.next.enabled.png").similar(this.pagerNextEnabledSimilarity));
		System.out.println(match);
		return match != null;
	}

	public void saveAllLinks(File fileName) {
		click("linkedin/link.klipper.button.png");
		click("linkedin/link.klipper.extractAllLinks.png");
		Region region = getAppRegion();
		try {
			region.wait(resolve("linkedin/saveAs.fileName.label.png"), 5.0);
			Region fileNameField = region.find("linkedin/saveAs.fileName.label.png").right(90);
			region.click(fileNameField);
			fileNameField.keyDown(Key.CTRL);
			fileNameField.keyDown("a");
			fileNameField.keyUp();
			fileNameField.paste(fileName.getAbsolutePath());
			region.type(resolve("linkedin/save.button.png"), Key.ENTER);
		} catch (FindFailed e) {
			throw new RuntimeException(e);
		}
	}

	public void configureSearchOptions(SearchOptions options) {
		if (options.firstLevel) {
			click(getCheckboxAtTheLeft("linkedin/buscar.contactos.1er.png"));
			waitMillis(200);
		}

		if (options.secondLevel) {
			click(getCheckboxAtTheLeft("linkedin/buscar.contactos.2do.png"));
			waitMillis(200);
		}

		if (options.thirdLevel) {
			waitMillis(200);
		}

		for (String location : options.locations) {
			configureDropDownTextField("linkedin/buscar.ubicaciones.title.png", location);
		}

		if (!options.role.isEmpty()) {
			configureTextField("linkedin/buscar.cargo.title.png", options.role);
		}
	}

	public Region getCheckboxAtTheLeft(String image) {
		return find(image).left(30);
	}

	public void configureDropDownTextField(String titleFieldImage, String criteria) {
		Region textFieldUnderTitleRegion = scrollAndFind(titleFieldImage).below(40);
		click(textFieldUnderTitleRegion);
		ctrl(textFieldUnderTitleRegion, "a");
		type(textFieldUnderTitleRegion, Key.BACKSPACE);
		String allmost = criteria.substring(0, criteria.length() - 1);
		String lastCharacter = String.valueOf(criteria.charAt(criteria.length() - 1));
		paste(textFieldUnderTitleRegion, allmost);
		type(textFieldUnderTitleRegion, lastCharacter);

		waitMillis(100);
		Region r = textFieldUnderTitleRegion.below(40);
		r.setW(250);
		click(r);
	}

	public <PSI> void configureTextField(PSI titleFieldImage, String criteria) {
		Region textFieldUnderTitleRegion = scrollAndFind(titleFieldImage).below(10);
		textFieldUnderTitleRegion.highlight(1);
		click(textFieldUnderTitleRegion);
		ctrl(textFieldUnderTitleRegion, "a");
		type(textFieldUnderTitleRegion, Key.BACKSPACE);
		paste(textFieldUnderTitleRegion, criteria);
	}

	public <PSI> Region scrollAndFind(PSI image) {
		int maxScrollingAttemps = 20;
		int scrollAttemp = 1;
		Region result = find(image);
		while (result == null && scrollAttemp < maxScrollingAttemps) {
			Mouse.wheel(Mouse.WHEEL_DOWN, 10);
			result = find(image);
			scrollAttemp++;
		}
		if (result != null) {
			result = find(image);
			result.highlight(0.1);
		}
		return result;
	}

	public <PSI> Region getFieldBelow(PSI image) {
		Region item = scrollAndFind(image);
		if (item == null) {
			throw new RuntimeException("Could not find " + image);
		}
		return item.below(40);
	}

	private static SearchOptionsList getSearchOptionsList() throws IOException {
		SearchOptionsList options = null;
		File file = new File("./searchOptionsList.json");
		if (file.exists()) {
			System.out.println("Using configuration options from " + file.getAbsolutePath());
			options = SearchOptionsList.fromJson(readFile(file));
			System.out.print(options.toJson());

		} else {
			System.out.println("Using configuration options from -D properties");
			throw new IllegalArgumentException(String.format("The file %s does not exist", file.getAbsolutePath()));
		}
		return options;
	}

	private static String readFile(File file) throws IOException {
		StringBuilder b = new StringBuilder();
		try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
			String line = reader.readLine();
			while (line != null) {
				b.append(line + "\n");
				line = reader.readLine();
			}
			return b.toString();
		}
	}

	public static void main(String[] args) throws IOException {
		SearchOptionsList searchOptionsList = getSearchOptionsList();
		File resultsFolder = new File("./searchResults/" + Utils.getTimestampStr());
		resultsFolder.mkdirs();
		System.out.println("Results will be written in " + resultsFolder.getAbsolutePath());

		LinkedInApp app = LinkedInApp.create();
		for (SearchOptions searchOptions : searchOptionsList.searchOptionsList) {
			System.out.println("Starting to search options: " + searchOptions.toString());
			File specificSearchFolder = new File(resultsFolder, searchOptions.name);
			specificSearchFolder.mkdirs();
			app.gotoSearchPeople();
			app.search(searchOptions);
			app.exportResults(specificSearchFolder.getAbsolutePath());
		}
		System.out.println("Done!");

	}

}
