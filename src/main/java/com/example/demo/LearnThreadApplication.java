package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class LearnThreadApplication {

	public static void main(String[] args) {

		SpringApplication.run(LearnThreadApplication.class, args);

		new Cache();

		Timer t = new Timer();
		// every seven seconds populate cache
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				Cache.populateCache();
			}
		}, 0, 7000);

	}

	static class Cache {

		static HashMap<String, String> files;

		public Cache() {
			populateCache();
			clearCacheThread();
		}

		private void clearCacheThread() {
			Thread tread = new Thread(() -> {
				while (true) {
					try {
						// every five seconds
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Clearing the cache... cache: size " + files.size());
					clearCache();
					System.out.println("Cache clear! New size " + files.size());
				}
			});
			tread.start();
		}

		static void populateCache(){
			files = new HashMap<String, String>(){{
				put("1","first");
				put("2","second");
				put("3","third");
				put("4","fourth");
			}};
			System.out.println("cache Populated! size " + files.size());
		}

		static void clearCache(){
			files.clear();
		}
	}

}
