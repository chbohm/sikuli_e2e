package com.hexacta.sikuli.linkedin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.hexacta.sikuli.core.LineReadCallback;
import com.hexacta.sikuli.core.Utils;

public class ResultsProcessor {

	public static void processResultsFolder(File folder) throws IOException {
		List<String> detectedContacts = Lists.newArrayList();
		ContactDetector contactDetector = new ContactDetector((contact) -> detectedContacts.add(contact));
		for (File f : folder.listFiles()) {
			if (!f.getName().startsWith("page")) {
				continue;
			}
			Utils.readFile(f, contactDetector);
		}

		try (FileWriter writer = new FileWriter(new File(folder, "processedResults.csv"))) {
			for (String contacts : detectedContacts) {
				String[] contactParts = contacts.split(", ");
				String line = contactParts[0] + ",\"" + contactParts[1].trim() + "\"\n";
				System.out.println(line);
				writer.append(line);
			}
			writer.flush();
		}

	}

	private static interface ContactDetectedCallback {
		public void mainContact(String contact);
	}

	private static class ContactDetector implements LineReadCallback {
		private ContactDetectedCallback callback;
		private String lastLine;

		public ContactDetector(ContactDetectedCallback callback) {
			super();
			this.callback = callback;
		}

		@Override
		public void line(String line) {
			if (line.startsWith("contacto de")) {
				if (lastLine.startsWith("\"https")) {
					callback.mainContact(lastLine);
				}
			}
			lastLine = line;
		}

	}

	public static void main(String[] args) throws IOException {
		ResultsProcessor.processResultsFolder(new File("./searchResults/2019-09-12_100417"));
	}
}
