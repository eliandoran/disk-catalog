package io.disk_indexer.ui.fileicons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;

import io.disk_indexer.core.entity.Entry;
import javafx.scene.image.Image;

public class ResourceIconPackFileMapper implements FileMapper {
	private Map<String, String> fullMappings;
	private String resourcePath;

	public ResourceIconPackFileMapper(String resourcePath) {
		JSONObject iconPack = new JSONObject(resourceToString(resourcePath + "/iconpack.json"));

		this.resourcePath = resourcePath;
		this.fullMappings = new HashMap<>();

		System.out.println("Title: " + iconPack.get("title"));

		for (Object object : iconPack.getJSONArray("extensionMappings")) {
			if (!(object instanceof JSONObject)) {
				continue;
			}

			JSONObject mapping = (JSONObject)object;
			String imagePath = mapping.getString("with");

			for (Object extensionsObject : mapping.getJSONArray("map")) {
				String extension = filterExtension((String)extensionsObject);
				this.fullMappings.put(extension, imagePath);
			}
		}
	}

	@Override
	public Image obtainIcon(Entry entry) {
		String extension = filterExtension(getExtension(entry.getName()));
		String imagePath = this.fullMappings.get(extension);

		System.out.println("Mapped " + extension + " with " + imagePath);

		if (imagePath != null)
			return new Image(this.resourcePath + "/" + imagePath);

		return null;
	}

	private static String resourceToString(String resourcePath) {
		try (InputStream resourceStream = ResourceIconPackFileMapper.class.getResourceAsStream(resourcePath);
				InputStreamReader resourceReader = new InputStreamReader(resourceStream);
				BufferedReader bufferedReader = new BufferedReader(resourceReader)) {
			return bufferedReader.lines().collect(Collectors.joining("\n"));
		} catch (NullPointerException e) {
			throw new RuntimeException("The icon pack does not exist in resources.", e);
		} catch (IOException e) {
			throw new RuntimeException("Unable to load resource icon pack.", e);
		}
	}

	private static String filterExtension(String extension) {
		return extension.toLowerCase();
	}

	private static String getExtension(String filePath) {
		int lastDotPos = filePath.lastIndexOf('.');

		if (lastDotPos <= 0 || lastDotPos + 1 > filePath.length())
			return "";

		return filePath.substring(lastDotPos + 1);
	}
}
