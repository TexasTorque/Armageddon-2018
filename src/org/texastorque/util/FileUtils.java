package org.texastorque.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class FileUtils {

	private FileUtils() {
	} // Only allow static methods.

	private static final Gson GSON = new GsonBuilder().create();

	/**
	 * Creates a filepath string with a timestamp attached to the filename.
	 *
	 * File path is formatted as `../dir/filename_timestamp.extension`.
	 *
	 * @param dir
	 *            The directory that should contain the file. Should be `/dir` and
	 *            not `/dir/`.
	 * @param filename
	 *            Name of the file without an extension.
	 * @param extension
	 *            The file extension (e.g. `json`, `xml`, etc.).
	 * @return
	 */
	public static String createTimestampedFilepath(String dir, String filename, String extension) {
		SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = timeStampFormat.format(new Date()); //
		return String.format("%s/%s_%s.%s", dir, filename, timestamp, extension);
	}

	/**
	 * Serializes a java object to XML.
	 * 
	 * @param fileLocation
	 *            The file path used to save the XML.
	 * @param encodable
	 *            The XML encodable object to save.
	 * @return True if the file was saved, false otherwise.
	 */
	public static boolean writeToXML(String fileLocation, Object encodable) {
		try (XMLEncoder writer = new XMLEncoder(new FileOutputStream(fileLocation))) {
			writer.writeObject(encodable);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Deserializes a java object from XML.
	 * 
	 * @param fileLocation
	 *            The file path to the XML to read.
	 * @param type
	 *            The class used to cast the XML decoded object.
	 * @return The type-cast object if successful, null otherwise.
	 */
	public static <Type> Type readFromXML(String fileLocation, Class<Type> type) {
		try (XMLDecoder reader = new XMLDecoder(new FileInputStream(fileLocation))) {
			return type.cast(reader.readObject());
		} catch (FileNotFoundException e) {
			System.out.println(String.format("File not found: %s", fileLocation));
		}

		return null;
	}

	/**
	 * Serializes a java object to JSON.
	 * 
	 * @param fileLocation
	 *            The file path used to save the JSON.
	 * @param encodable
	 *            The JSON encodable object to save.
	 * @return True if the file was saved, false otherwise.
	 */
	public static boolean writeToJSON(String fileLocation, Object encodable) {
		try (Writer writer = new FileWriter(fileLocation)) {
			GSON.toJson(encodable, writer);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Deserializes a java object from JSON.
	 * 
	 * @param fileLocation
	 *            The file path to the JSON to read.
	 * @param type
	 *            The class used to cast the JSON decoded object.
	 * @return The type-cast object if successful, null otherwise.
	 */
	public static <Type> Type readFromJSON(String fileLocation, Class<Type> type) {
		try (Reader reader = new BufferedReader(new FileReader(fileLocation))) {
			return GSON.fromJson(reader, type);
		} catch (IOException e) {
			System.out.println(String.format("Could not read JSON from file: %s", fileLocation));
		}

		return null;
	}
}
