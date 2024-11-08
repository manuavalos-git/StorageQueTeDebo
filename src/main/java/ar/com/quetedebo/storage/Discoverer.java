package ar.com.quetedebo.storage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Discoverer<T> {
	private final File directoryJars;

	public Discoverer(String path) {
		directoryJars = new File(path);
	}

	public List<T> buildExtensions(Class<T> classInterface) {
		List<T> implementations = new ArrayList<>();

		File[] files = directoryJars.listFiles();

		for (File fileJar : files) {
			try {
				List<T> foundImplementations = findInJar(classInterface, fileJar);
				implementations.addAll(foundImplementations);
			} catch (InvocationTargetException | IOException e) {
				e.printStackTrace();
			}
		}

		if (implementations.isEmpty()) {
			throw new IllegalStateException("No implementations found for interface: " + classInterface.getName());
		}

		return implementations;
	}

	private List<T> findInJar(Class<T> classInterface, File fileJar) throws IOException, InvocationTargetException {
		List<T> resultImplementations = new ArrayList<>();

		JarFile jarFile = new JarFile(fileJar);
		Enumeration<JarEntry> entries = jarFile.entries();

		URL[] urls = { new URL("jar:file:" + fileJar + "!/") };
		URLClassLoader classLoader = new URLClassLoader(urls);

		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();

			if (entry.getName().endsWith(".class")) {
				String className = entry.getName().replace("/", ".").replace(".class", "");

				try {
					Class<?> cls = classLoader.loadClass(className);

					if (cls != null && !cls.isInterface() && classInterface.isAssignableFrom(cls)) {
						T implementation = (T) cls.getDeclaredConstructor().newInstance();
						resultImplementations.add(implementation);
					}

				} catch (ClassNotFoundException e) {
					System.err.println("Clase no encontrada: " + className);
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}

		jarFile.close();
		classLoader.close();

		return resultImplementations;
	}

}
