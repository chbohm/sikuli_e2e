package com.hexacta.sikuli.linkedin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.hexacta.sikuli.core.LineReadCallback;
import com.hexacta.sikuli.core.Utils;

public class ResultsProcessor {

	public static void processFile(File f) throws IOException {
		System.out.println("Processing file: "+f.getAbsolutePath());
		ContactDetector contactDetector = new ContactDetector();
		if (!f.getName().startsWith("page") || f.getName().contains(("processed"))) {
			return;
		}
		Utils.readFile(f, contactDetector);

		try (FileWriter writer = new FileWriter(new File(f.getParentFile(), f.getName() + ".processed.csv"))) {
			for (String contacts : contactDetector.getContacts()) {
				String[] contactParts = contacts.split(", ");
				String line = contactParts[0] + ",\"" + contactParts[1].trim() + "\"\n";
				System.out.println(line);
				writer.append(line);
			}
			writer.flush();
		}
	}

	public static void processResultsFolder(File folder) throws IOException {
		try (FileWriter writer = new FileWriter(new File(folder, "all_results.csv"))) {
			File[] processedFiles = folder.listFiles((File file) -> file.getAbsolutePath().endsWith("processed.csv"));
			for (File f : processedFiles) {

				try (FileReader reader = new FileReader(f)) {
					// create object of BufferedReader
					BufferedReader br = new BufferedReader(reader);

					// Read from current file
					String line = br.readLine();
					while (line != null) {
						writer.write(line + "\n");
						line = br.readLine();
					}
					writer.flush();
				}
			}
		}
	}

	private static interface ContactDetectedCallback {
		public void mainContact(String contact);
	}

	private static class DefaultContactDetectedCallback implements ContactDetectedCallback {

		@Override
		public void mainContact(String contact) {
			// Do nothing
		}

	}

	private static class ContactDetector implements LineReadCallback {
		private ContactDetectedCallback callback;
		List<String> detectedContacts = Lists.newArrayList();

		private String lastLine;

		public ContactDetector() {
			super();
			this.callback = new DefaultContactDetectedCallback();
		}

		public ContactDetector(ContactDetectedCallback callback) {
			super();
			this.callback = callback;
		}

		@Override
		public void line(String line) {
			if (line.startsWith("contacto de")) {
				if (lastLine.startsWith("\"https")) {
					callback.mainContact(lastLine);
					this.detectedContacts.add(lastLine);
				}
			}
			lastLine = line;
		}

		public List<String> getContacts() {
			return this.detectedContacts;
		}

	}

	public static void main(String[] args) throws IOException {
		ResultsProcessor.processResultsFolder(new File(
				"C:\\projects\\sikuli_e2e\\searchResults\\2020-01-09_101808\\Busqueda .Net cerca de oficinas"));
	}
}
