package com.hexacta.test;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.hexacta.sikuli.whatsapp.Whatsapp;

/**
 * Unit test for simple App.
 */
public class WhatsappTest {
	private static Whatsapp app;

	@BeforeClass
	public static void beforeAll() throws Exception {
		try {
			app = Whatsapp.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void afterAll() throws Exception {
		// app.closeApplication();

	}

//	@Test
//	public void findUser_Successful2() {
//		List<DesktopWindow> windows = WindowUtils.getAllWindows(true).stream()
//				.filter(w -> w.getFilePath().endsWith("chrome.exe")).collect(Collectors.toList());
//
//		for (DesktopWindow window : windows) {
//			System.out.println("\"" + window.getTitle() + "\" " + window.getLocAndSize());
//			Utils.showWindow(window);
//
//		}
//	}

	@Test
	public void findUser_Successful() {
		List<String> contacts = Lists.newArrayList("Lucas Acosta","Sebastián Arce");
		for (String contact : contacts) {
			app.sendToContact(contact, "Hola loco, soy sikuli y esto es un envio masivo de un mensaje. MUUUAAAAAAHAHAHAHHAHAHAHAH");
		}
	}

}
