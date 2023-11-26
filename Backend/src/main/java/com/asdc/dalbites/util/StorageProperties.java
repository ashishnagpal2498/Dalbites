package com.asdc.dalbites.util;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Configuration properties for file storage.
 */
@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "uploads";

	/**
	 * Gets the folder location for storing files.
	 *
	 * @return The folder location.
	 */

	public String getLocation() {
		return location;
	}

	/**
	 * Sets the folder location for storing files.
	 *
	 * @param location The new folder location.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

}